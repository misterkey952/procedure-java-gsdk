package century.gsdk.storage.dbm.source;

import century.gsdk.storage.dbm.config.RedisSourceConfig;
import century.gsdk.storage.dbm.streng.StrengJedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *     Copyright (C) <2019>  <Century>
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *     Author Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
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
