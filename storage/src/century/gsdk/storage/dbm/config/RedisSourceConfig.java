package century.gsdk.storage.dbm.config;

public class RedisSourceConfig {
	/** redis服务器的IP*/
	private String ip;
	/** 端口号*/
	private int port;
	/** 最大的redis连接*/
	private int maxJedis;
	/** redis密码*/
	private String passwd;
	private int db;
	private int timeout;
	private int maxidel;
	private boolean openp;
	public String getIp() {
		return ip;
	}
	
	public boolean isOpenp() {
		return openp;
	}

	public void setOpenp(boolean openp) {
		this.openp = openp;
	}

	public int getDb() {
		return db;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getMaxidel() {
		return maxidel;
	}

	public void setMaxidel(int maxidel) {
		this.maxidel = maxidel;
	}

	public void setDb(int db) {
		this.db = db;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getMaxJedis() {
		return maxJedis;
	}
	public void setMaxJedis(int maxJedis) {
		this.maxJedis = maxJedis;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	
}
