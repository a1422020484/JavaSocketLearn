package nettyServer.util.cache;

import java.util.ArrayList;
import java.util.List;

import nettyServer.util.GameUtils;

/**
 * 缓存复合实体管理类
 * 
 * @author xiezuojie
 *
 */
public abstract class CachedCompositeDataManager<T extends CompositePersistEntity<?>> extends CachedDataManager<T> {
	
	@Override
	protected void updatePartOfBatch(List<T> partList) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 批量更新参数表示的实体
	 * @param parameterPersistEntityPartList
	 */
	protected abstract void updatePartOfBatchParameterPersistEntities(List<PersistEntity> parameterPersistEntityPartList);
	
	/**
	 * 批量按id删除
	 * @param deletedIdList
	 */
	protected abstract void deletePartOfBatchDeletedIds(List<Long> deletedIdList);
	
	@SuppressWarnings("unchecked")
	@Override
	protected void updateBatch(List<T> dataList, boolean ignoreCloneSource) {
		int batchSize = getBatchSize();
		List<CompositePersistEntity<PersistEntity>> savingCpeList = new ArrayList<>(16);
		List<PersistEntity> tpeList = new ArrayList<>(batchSize);
		List<Long> deletedIDList = new ArrayList<>(batchSize);
		for (int i = 0; i < dataList.size(); i ++) {
			CompositePersistEntity<PersistEntity> cpe = (CompositePersistEntity<PersistEntity>) dataList.get(i);
			savingCpeList.add(cpe);
			List<Long> delPIDList = cpe.getDeletedPrimaryIds();
			if (delPIDList.size() > 0) {
				deletedIDList.addAll(delPIDList);
			}
			List<PersistEntity> peList = cpe.parameterPersistEntities();
			if (peList != null) {
				tpeList.addAll(peList);
			}
			/*
			 * 为保证CPE的完整性,一次添加此CPE的一批参数实体,在超过批量值时提交一次,可能超出一定范围
			 * 删除的ID由于数据较小,允许值为批量值的4倍
			 */
			if (tpeList.size() > batchSize || deletedIDList.size() > batchSize * 4 || i == dataList.size() - 1) {
				try {
					if (deletedIDList.size() > 0) {
						deletePartOfBatchDeletedIds(deletedIDList);
					}
					if (tpeList.size() > 0) {
						updatePartOfBatchParameterPersistEntities(tpeList);
						if (!ignoreCloneSource) {
							for (PersistEntity b2 : tpeList) {
								if (b2.cloneSource == null) {
									GameUtils.RuntimeLog.debug("克隆的实体没有指定克隆源,{}", b2.getClass());
								} else {
									b2.cloneSource.sav = b2.mod;
								}
							}
						}
					}
					for (CompositePersistEntity<PersistEntity> cpe2 : savingCpeList) {
						cpe2.saveFlag = 1;
						if (!ignoreCloneSource) {
							((CompositePersistEntity<PersistEntity>) cpe2.cloneSource).removeDeleteOK(cpe2.getDeletedPrimaryIds());
							cpe2.cloneSource.sav = cpe2.mod;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				savingCpeList.clear();
				tpeList.clear();
				deletedIDList.clear();
			}
		}
	}
}
