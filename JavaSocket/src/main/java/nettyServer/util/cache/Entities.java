package nettyServer.util.cache;

import java.util.HashMap;

import nettyServer.util.GameUtils;

/**
 * 实体集
 *
 * @author yangxp
 */
public class Entities {
    private int playerId;
    private HashMap<Class<? extends PersistEntity>, PersistEntity> all = new HashMap<>(256);

    Entities(int playerId) {
        this.playerId = playerId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setEntity(PersistEntity entity) {
        synchronized (all) {
            all.put(entity.actualClass(), entity);
        }
    }

    public <T extends PersistEntity> boolean hasEntity(Class<T> clazz) {
        synchronized (all) {
            return all.containsKey(clazz);
        }
    }

    /**
     * 从缓存获取实体,当从缓存未获取到实体时,会执行{@link CachedDataManager#initCache(int)}加载缓存并返回.
     *
     * @param clazz
     * @return 实体
     * @throws RuntimeException 实体在上一次保存时失败
     */
    @SuppressWarnings("unchecked")
    public <T extends PersistEntity> T getEntity(Class<T> clazz) {
        T t;
        synchronized (all) {
            t = (T) all.get(clazz);
        }
        if (t == null) {
            t = (T) CachedDataManagers.managersMap.get(clazz).initCache(playerId);
            if (t != null) {
                PersistEntity old;
                synchronized (all) {
                    old = all.putIfAbsent(t.actualClass(), t);
                }
                if (old != null) {
                    t = (T) old;
                }
            }
        } else {
//			if (t.del) {
//				return null; // 已被删除
//			}
        }
        return t;
    }

    /**
     * 仅从缓存获取实体,当从缓存未获取到实体时返回null
     *
     * @param clazz
     * @return 实体
     */
    @SuppressWarnings("unchecked")
    public <T extends PersistEntity> T getEntityFromCache(Class<T> clazz) {
        T t;
        synchronized (all) {
            t = (T) all.get(clazz);
        }
        return t;
    }

    /**
     * @param clazz
     * @return 已改变的实体的克隆, 实体没有改变时返回null
     */
    @SuppressWarnings("unchecked")
    public <T extends PersistEntity> T getChangedEntityClone(Class<T> clazz) {
        T t;
        synchronized (all) {
            t = (T) all.get(clazz);
        }
        if (t == null) {
            return null;
        }
        if (t.isChanged()) {
            T clone = null;
            try {
                clone = (T) t.cloneEntity();
            } catch (Exception e) {
                e.printStackTrace();
                GameUtils.RuntimeLog.error("实体已改变,但克隆失败,PlayerId:{},Error:{}", playerId, e.getMessage());
                return null;
            }
            if (clone == null) {
                GameUtils.RuntimeLog.debug("实体已改变,但克隆的结果为null. {}", clazz);
                return null;
            }
            clone.cloneSource = t;
            clone.mod = t.getMod();
            clone.cloneAfter();
            return clone;
        }
        return null;
    }

    public void clear() {
        synchronized (all) {
            all.clear();
            all = null;
        }
    }

}
