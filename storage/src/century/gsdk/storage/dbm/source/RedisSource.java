package century.gsdk.storage.dbm.source;

import century.gsdk.storage.dbm.config.RedisSourceConfig;
import century.gsdk.storage.dbm.streng.StrengJedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class RedisSource {
	private JedisPool pool;
	private RedisSourceConfig config;
	private ThreadLocal<StrengJedis> localJedis=new ThreadLocal<StrengJedis>();
	public RedisSource(RedisSourceConfig config){
		this.config=config;
        if (pool == null) {
            try {
                JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
                jedisPoolConfig.setMaxTotal(this.config.getMaxJedis());
                jedisPoolConfig.setMaxIdle(this.config.getMaxidel());
                jedisPoolConfig.setMaxWaitMillis(-1);
                pool = new JedisPool(jedisPoolConfig, this.config.getIp(), this.config.getPort(), this.config.getTimeout(), this.config.getPasswd() , this.config.getDb());
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
	}
	
	public RedisSourceConfig getConfig() {
		return config;
	}

	public StrengJedis getJedis(){

		if(localJedis.get()==null){
			localJedis.set(new StrengJedis(pool.getResource()));
		}
		return localJedis.get();
	}
	
	public void release(){
		if(localJedis.get()!=null){
			localJedis.get().getJedis().close();
			localJedis.remove();
		}
	}
}
