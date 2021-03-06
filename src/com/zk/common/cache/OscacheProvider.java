package com.zk.common.cache;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cache.Cache;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.CacheProvider;
import org.hibernate.cache.Timestamper;
import org.hibernate.util.StringHelper;

import com.opensymphony.oscache.base.CacheEntry;
import com.opensymphony.oscache.base.Config;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.opensymphony.oscache.hibernate.OSCacheProvider;
import com.opensymphony.oscache.util.StringUtil;

public class OscacheProvider implements CacheProvider {

	private static final Log LOG = LogFactory.getLog(OSCacheProvider.class);

	public static final String OSCACHE_CONFIGURATION_RESOURCE_NAME = "com.opensymphony.oscache.configurationResourceName";

	/** The <tt>OSCache</tt> refresh period property suffix. */
	public static final String OSCACHE_REFRESH_PERIOD = "refresh.period";

	/** The <tt>OSCache</tt> CRON expression property suffix. */
	public static final String OSCACHE_CRON = "cron";

	private static GeneralCacheAdministrator cache;

	/**
	 * Builds a new {@link Cache} instance, and gets it's properties from the
	 * GeneralCacheAdministrator {@link GeneralCacheAdministrator} which reads
	 * the properties file (<code>oscache.properties</code>) in the start
	 * method:
	 * 
	 * @see com.opensymphony.oscache.hibernate.OSCacheProvider#start(java.util.Properties)
	 * 
	 * @param region
	 *            the region of the cache
	 * @param properties
	 *            not used
	 * @return the hibernate 2nd level cache
	 * @throws CacheException
	 * 
	 * @see org.hibernate.cache.CacheProvider#buildCache(java.lang.String,
	 *      java.util.Properties)
	 */
	public Cache buildCache(String region, Properties properties)
			throws CacheException {
		if (cache != null) {
			LOG.debug("building cache in OSCacheProvider...");

			String refreshPeriodString = cache.getProperty(StringHelper
					.qualify(region, OSCACHE_REFRESH_PERIOD));
			if(refreshPeriodString == null){
				refreshPeriodString = cache.getProperty(OSCACHE_REFRESH_PERIOD);
			}
			int refreshPeriod = refreshPeriodString == null ? CacheEntry.INDEFINITE_EXPIRY
					: Integer.parseInt(refreshPeriodString.trim());

			String cron = cache.getProperty(StringHelper.qualify(region,
					OSCACHE_CRON));

			return new OscacheImp(cache, refreshPeriod, cron, region);
		}
		throw new CacheException(
				"OSCache was stopped or wasn't configured via method start.");
	}

	/**
	 * @see org.hibernate.cache.CacheProvider#nextTimestamp()
	 */
	public long nextTimestamp() {
		return Timestamper.next();
	}

	/**
	 * This method isn't documented in Hibernate:
	 * 
	 * @see org.hibernate.cache.CacheProvider#isMinimalPutsEnabledByDefault()
	 */
	public boolean isMinimalPutsEnabledByDefault() {
		return false;
	}

	/**
	 * @see org.hibernate.cache.CacheProvider#stop()
	 */
	public void stop() {
		if (cache != null) {
			LOG.debug("Stopping OSCacheProvider...");
			cache.destroy();
			cache = null;
			LOG.debug("OSCacheProvider stopped.");
		}
	}

	/**
	 * @see org.hibernate.cache.CacheProvider#start(java.util.Properties)
	 */
	public void start(Properties hibernateSystemProperties)
			throws CacheException {
		if (cache == null) {
			// construct the cache
			LOG.debug("Starting OSCacheProvider...");
			String configResourceName = null;
			if (hibernateSystemProperties != null) {
				configResourceName = (String) hibernateSystemProperties
						.get(OSCACHE_CONFIGURATION_RESOURCE_NAME);
			}
			if (StringUtil.isEmpty(configResourceName)) {
				cache = new GeneralCacheAdministrator();
			} else {
				Properties propertiesOSCache = Config.loadProperties(
						configResourceName, this.getClass().getName());
				cache = new GeneralCacheAdministrator(propertiesOSCache);
			}
			LOG.debug("OSCacheProvider started.");
		} else {
			LOG.warn("Tried to restart OSCacheProvider, which is already running.");
		}
	}

	/**
	 * @return the cache
	 */
	public static GeneralCacheAdministrator getCache() {
		return cache;
	}
}
