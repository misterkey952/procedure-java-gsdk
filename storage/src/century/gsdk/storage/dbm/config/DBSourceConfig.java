package century.gsdk.storage.dbm.config;

public class DBSourceConfig {
	/** 数据库连接*/
	private String url;
	/** 数据库用户*/
	private String user;
	/** 数据库密码*/
	private String password;
	/** 最大连接数*/
	private short maxCon;
	private short minCon;
	/** 驱动*/
	private String driver;
	/** 数据库表结构对应的包名*/
	private String packages;
	/** 是否自动建表  create自动创建表   update 自动更新表*/
	private String auto;
	private boolean autoCommit;

	public String getUrl() {
		return url;
	}

	public short getMinCon() {
		return minCon;
	}

	public void setMinCon(short minCon) {
		this.minCon = minCon;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public short getMaxCon() {
		return maxCon;
	}

	public void setMaxCon(short maxCon) {
		this.maxCon = maxCon;
	}

	public boolean isAutoCommit() {
		return autoCommit;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}


	public String getAuto() {
		return auto;
	}

	public void setAuto(String auto) {
		this.auto = auto;
	}


}