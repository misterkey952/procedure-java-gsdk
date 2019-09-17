package century.gsdk.storage.dbm.streng;

import redis.clients.jedis.Jedis;

public class StrengJedis {
	private Jedis jedis;

	public StrengJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public Jedis getJedis() {
		return jedis;
	}
	
	
	
}
