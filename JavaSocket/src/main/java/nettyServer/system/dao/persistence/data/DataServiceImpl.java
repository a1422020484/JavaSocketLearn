/**
 * 
 */
package nettyServer.system.dao.persistence.data;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

import nettyServer.system.dao.persistence.data.ObjectManager.SaveState;


public class DataServiceImpl implements DataService {
	private static final Logger logger = LogManager.getLogger(DataServiceImpl.class
			.getName());

	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private Lock readLock = readWriteLock.readLock();
	private Lock writeLock = readWriteLock.writeLock();
	
	private long intervalTime;
	
	private boolean enabled;

	private SaveState saveState = SaveState.WaitSave;

	private AtomicLong saveCount = new AtomicLong(0);

	private final LinkedHashMap<Class<ReadonlyObject>, ManagedObjectLoadSlaver<ReadonlyObject>> loadSlaverMap = new LinkedHashMap<Class<ReadonlyObject>, ManagedObjectLoadSlaver<ReadonlyObject>>();

	private final LinkedHashMap<Class<? extends SaveableObject>, SaveableObjectSaveSlaver<SaveableObject>> saveSlaverMap = new LinkedHashMap<>();

	private ObjectManager objectManger = new ObjectManagerImpl();

	public DataServiceImpl() {

	}

	/** DataService implements */
	@Override
	public void setDataSaveIntervalTime(long intervalTime) {
		this.intervalTime = intervalTime;
	}

	@Override
	public long getDataSaveIntervalTime() {
		return intervalTime;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public long getSavedCount() {
		return saveCount.get();
	}

	@Override
	public boolean isSaving() {
		readLock.lock();
		try {
			return saveState == SaveState.Saving;
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public SaveState saveState() {
		readLock.lock();
		try {
			return saveState;
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public ObjectManager getObjectManager() {
		return objectManger;
	}

	/** DataService implements */
	private final class ObjectManagerImpl implements ObjectManager {
		@Override
		public void addReadonlyObjectLoadSlaver(
				Class<ReadonlyObject> objectType,
				ManagedObjectLoadSlaver<ReadonlyObject> objectLoadSlaver) {
			if (objectType == null || objectLoadSlaver == null) {
				throw new NullPointerException();
			}

			loadSlaverMap.put(objectType, objectLoadSlaver);
		}

		@Override
		public void addSaveableObjectSaveSlaver(
				Class<? extends SaveableObject> objectType,
				SaveableObjectSaveSlaver<SaveableObject> objectSaveSlaver) {
			if (objectType == null || objectSaveSlaver == null) {
				throw new NullPointerException();
			}

			saveSlaverMap.put(objectType, objectSaveSlaver);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T extends ManagedObject> Collection<T> load(Class<T> type) {
			Class<? extends ReadonlyObject> readonlyOjbectClass = null;
			try {
				readonlyOjbectClass = type.asSubclass(ReadonlyObject.class);
			} catch (ClassCastException e) {
				readonlyOjbectClass = null;
			}
			if (readonlyOjbectClass != null) {
				ManagedObjectLoadSlaver<ReadonlyObject> readonlyObjectLoadSlaver = loadSlaverMap
						.get(type);
				if (readonlyObjectLoadSlaver != null) {
					return (Collection<T>) readonlyObjectLoadSlaver.load();
				} else {
					throw new RuntimeException(type.getName()
							+ " not find load slaver.");
				}
			} else {
				Class<? extends SaveableObject> saveableObjectClass = null;
				try {
					saveableObjectClass = type.asSubclass(SaveableObject.class);
				} catch (ClassCastException e) {
					saveableObjectClass = null;
				}
				if (saveableObjectClass != null) {
					SaveableObjectSaveSlaver<SaveableObject> saveableObjectSaveSlaver = saveSlaverMap
							.get(type);
					Collection<T> collection = null;
					if (saveableObjectSaveSlaver != null) {
						collection = (Collection<T>) saveableObjectSaveSlaver
								.load();
					}
					return collection;
				}
			}

			throw new RuntimeException(
					type
							+ " must be extends ReadonlyObject or extends SaveableObject.");
		}

		@Override
		public <T extends SaveableObject> Future<SaveState> save (
				SaveMode saveMode, Class<T> type, T... objects) throws Exception {
			if (objects == null) {
				logger.error("The objects can not be null.");
				throw new NullPointerException("The objects can not be null.");
			}

			SaveableObjectSaveSlaver<SaveableObject> saveSlaver = saveSlaverMap
					.get(type);

			if (saveSlaver == null) {
				logger
						.error(type.getName()
								+ " the SaveabelObject not find SaveablObjectSaveSlaver to save.");
				throw new RuntimeException(
						type.getName()
								+ " the SaveabelObject not find SaveablObjectSaveSlaver to save.");
			}

			return saveSlaver.save(saveMode, objects);
		}
	}

	
	private void doBatchSave() {
		if (logger.isDebugEnabled()) {
			logger.debug("pre batch save work,free memory: " + Runtime.getRuntime().freeMemory());
		}
		
		for (Entry<Class<? extends SaveableObject>, SaveableObjectSaveSlaver<SaveableObject>> entry : saveSlaverMap.entrySet()) {
			SaveableObjectSaveSlaver<SaveableObject> saveSlaver = entry.getValue();
			if (saveSlaver != null) {
				SaveJob<SaveableObject> saveJob = saveSlaver
						.drainAndSaveCurrentSaveableObjects();
				if (saveJob != null) {
					Future<SaveState> future = saveJob.getTranscation();
					// TODO: if the sate was failed,need process the case
					SaveState state = SaveState.Failed;
					
					try {
						state = future.get();
					} catch (Exception e) {
						logger.error("doBathSave error ", e);
						e.printStackTrace();
					}
					
					if (logger.isDebugEnabled()) {
						logger.debug("Current " + saveSlaver.getClass().getSimpleName() + " batch save state: " + state);
					}
				}
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("****after batch save work,free memory: "
					+ Runtime.getRuntime().freeMemory());
		}
	}

	@Override
	public ServiceDescriptor getDescriptorForType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void callMethod(MethodDescriptor method, RpcController controller, Message request, RpcCallback<Message> done) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Message getRequestPrototype(MethodDescriptor method) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message getResponsePrototype(MethodDescriptor method) {
		// TODO Auto-generated method stub
		return null;
	}
}
