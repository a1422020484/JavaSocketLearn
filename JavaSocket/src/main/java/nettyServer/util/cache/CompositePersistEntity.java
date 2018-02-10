package nettyServer.util.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * 复合实体
 * 
 * @author xiezuojie
 *
 * @param <T>
 *            PersistEntity
 */
public abstract class CompositePersistEntity<T extends PersistEntity> extends PersistEntity {
	/**
	 * 删除的ID列表,数据库主键
	 */
	protected ArrayList<Long> deletedPrimaryIds = new ArrayList<>(64);

	/**
	 * 添加已删除的数据库主键,供持久化执行删除
	 * 
	 * @param id
	 */
	protected synchronized void addDeletedPrimaryId(Long id) {
		deletedPrimaryIds.add(id);
	}

	/**
	 * 添加已删除的数据库主键,供持久化执行删除
	 * 
	 * @param ids
	 */
	protected synchronized void addDeletedPrimaryIds(List<Long> ids) {
		if (ids != null) {
			deletedPrimaryIds.addAll(ids);
		}
	}

	/**
	 * 移除已经从数据库删除成功的主键列表
	 * 
	 * @param deleteOKIds
	 */
	protected synchronized void removeDeleteOK(List<Long> deleteOKIds) {
		deletedPrimaryIds.removeAll(deleteOKIds);
	}

	/**
	 * 设置已删除的主键列表 deletedPrimaryIds setter
	 * 
	 * @param deletedPrimaryIds
	 */
	public synchronized void setDeletedPrimaryIds(ArrayList<Long> deletedPrimaryIds) {
		this.deletedPrimaryIds = deletedPrimaryIds;
	}

	/**
	 * deletedPrimaryIds getter
	 * 
	 * @return 已删除的主键列表,用于定时持久化时的删除
	 */
	public synchronized ArrayList<Long> getDeletedPrimaryIds() {
		return deletedPrimaryIds;
	}

	/**
	 * 克隆实体用于保存,复合实体克隆时只需要克隆已改变的参数实体,克隆的实体需要指定克隆源{@link PersistEntity#cloneSource}
	 * <br>
	 * 克隆操作无法保证实体内部数据一致性,
	 * 虽然在克隆时通常只是读取数据进行复制,但在读的过程中有可能会因并发的修改产生异常,所以实体应自己保护内部数据.
	 * <br>
	 * <b>克隆过程中产生异常会导致数据丢失!!!</b>
	 * @return 克隆的实体
	 */
	protected abstract CompositePersistEntity<T> cloneEntity();

	@SuppressWarnings("unchecked")
	@Override
	protected void cloneAfter() {
		if (cloneSource != null) {
			deletedPrimaryIds = (ArrayList<Long>) ((CompositePersistEntity<PersistEntity>) cloneSource).deletedPrimaryIds.clone();
		}
	}

	/**
	 * 此方法会由克隆实体调用,不需要同步保护.
	 * @return 复合实体参数所表示的实体列表,用于在定时持久化时的更新
	 */
	protected abstract List<T> parameterPersistEntities();
}
