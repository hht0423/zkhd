package com.zk.common.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.Timestamper;

import com.opensymphony.oscache.base.CacheEntry;
import com.opensymphony.oscache.base.EntryRefreshPolicy;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.zk.common.util.SysContext;

public class OscacheImp implements CacheInterFace,Cache{
	
	  /** The OSCache 2.4 cache administrator. */
    private static GeneralCacheAdministrator cache;
    private final int refreshPeriod;
    private final String cron;
    private final String regionName;
    private final String[] regionGroups;
    private static Map<Integer,EntryRefreshPolicyImpl>  entryRefreshPolicys= new HashMap<Integer, EntryRefreshPolicyImpl>(2);
	
	

	public OscacheImp() {
		this(OscacheProvider.getCache(), -1, null, "custumerRegion");
	}
	
	public OscacheImp(GeneralCacheAdministrator cache, int refreshPeriod,String cron, String region) {
		if(OscacheImp.cache==null){
			OscacheImp.cache = cache;
		}
        this.refreshPeriod = refreshPeriod;
        this.cron = cron;
        this.regionName = region;
        this.regionGroups = new String[] {region};
	}

	public final void flushAllCache(){
		cache.flushAll();
	}
	
	public void flushCache(String key) {
		cache.flushEntry(key);
		SysContext.getCacheKeys().remove(key);
	}

	public Object getFromCache(String key) {
		Object obj = null;
		try {
			obj = cache.getFromCache( key, refreshPeriod, cron );
		} catch (NeedsRefreshException e) {
			cache.cancelUpdate(key);
		}
		if (obj == null) {
			SysContext.getCacheKeys().remove(key);
		}
		return obj;
	}

	public void putToCache(String key, Object obj) {
		cache.putInCache( key, obj, regionGroups );
		List<String> cacheKeys = SysContext.getCacheKeys();
		if (!cacheKeys.contains(key))
			cacheKeys.add(key);
	}
	
	// 此方法的第三个参数表示缓存有效时间
	public void putToCache(String key, Object obj, final int second) {
		EntryRefreshPolicyImpl policy = entryRefreshPolicys.get(Integer.valueOf(second));
		if(policy==null){
			policy = new EntryRefreshPolicyImpl(second);
			entryRefreshPolicys.put(Integer.valueOf(second), policy);
		}
		cache.putInCache(key, obj, regionGroups, policy);
		List<String> cacheKeys = SysContext.getCacheKeys();
		if (!cacheKeys.contains(key))
			cacheKeys.add(key);
	}
	
	
	
	class EntryRefreshPolicyImpl implements EntryRefreshPolicy{
		private static final long serialVersionUID = 1L;
		private int second;

		public EntryRefreshPolicyImpl(int second){
			this.second = second;
		}
		
		public boolean needsRefresh(CacheEntry arg0) {
			Long now = System.currentTimeMillis();
			Long time = arg0.getLastUpdate() + second * 1000;
			return now > time;
		}
	}

	public void removeFromCache(String key) {
		flushCache(key);
	}

	
	///////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////
	///////super methods///////////////////////////////////////
	///////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////
	
	

    
    /**
     * @see org.hibernate.cache.Cache#get(java.lang.Object)
     */
    public Object get(Object key) throws CacheException {
        try {
            Object obj = cache.getFromCache( toString(key), refreshPeriod, cron );
            if (obj == null) {
    			SysContext.getCacheKeys().remove(key);
    		}
            return obj;
        }
        catch (NeedsRefreshException e) {
            cache.cancelUpdate( toString(key) );
            return null;
        }
    }

    /**
     * @see org.hibernate.cache.Cache#put(java.lang.Object, java.lang.Object)
     */
    public void put(Object key, Object value) throws CacheException {
    	String stringKey = toString(key);
        cache.putInCache( stringKey, value, regionGroups );
        List<String> cacheKeys = SysContext.getCacheKeys();
		if (!cacheKeys.contains(stringKey))
			cacheKeys.add(stringKey);
    }

    /**
     * @see org.hibernate.cache.Cache#remove(java.lang.Object)
     */
    public void remove(Object key) throws CacheException {
    	String stringKey = toString(key);
        cache.flushEntry( stringKey );
        SysContext.getCacheKeys().remove(key);
    }

    /**
     * @see org.hibernate.cache.Cache#clear()
     */
    public void clear() throws CacheException {
        cache.flushGroup(regionName);
    }

    /**
     * @see org.hibernate.cache.Cache#destroy()
     */
    public void destroy() throws CacheException {
        synchronized (cache) {
            cache.destroy();
        }
    }

    /**
     * @see org.hibernate.cache.Cache#lock(java.lang.Object)
     */
    public void lock(Object key) throws CacheException {
        // local cache, so we use synchronization
    }

    /**
     * @see org.hibernate.cache.Cache#unlock(java.lang.Object)
     */
    public void unlock(Object key) throws CacheException {
        // local cache, so we use synchronization
    }

    /**
     * @see org.hibernate.cache.Cache#nextTimestamp()
     */
    public long nextTimestamp() {
        return Timestamper.next();
    }

    /**
     * @see org.hibernate.cache.Cache#getTimeout()
     */
    public int getTimeout() {
        return Timestamper.ONE_MS * 60000; //ie. 60 seconds
    }

    /**
     * @see org.hibernate.cache.Cache#toMap()
     */
    @SuppressWarnings("unchecked")
	public Map toMap() {
        throw new UnsupportedOperationException();
    }    

    /**
     * @see org.hibernate.cache.Cache#getElementCountOnDisk()
     */
    public long getElementCountOnDisk() {
        return -1;
    }

    /**
     * @see org.hibernate.cache.Cache#getElementCountInMemory()
     */
    public long getElementCountInMemory() {
        return -1;
    }
    
    /**
     * @see org.hibernate.cache.Cache#getSizeInMemory()
     */
    public long getSizeInMemory() {
        return -1;
    }

    /**
     * @see org.hibernate.cache.Cache#getRegionName()
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * @see org.hibernate.cache.Cache#update(java.lang.Object, java.lang.Object)
     */
    public void update(Object key, Object value) throws CacheException {
        put(key, value);
    }    

    /**
     * @see org.hibernate.cache.Cache#read(java.lang.Object)
     */
    public Object read(Object key) throws CacheException {
        return get(key);
    }
    
    private String toString(Object key) {
        return new StringBuilder(String.valueOf(key)).append(".").append(regionName).toString();
    }
	

}
