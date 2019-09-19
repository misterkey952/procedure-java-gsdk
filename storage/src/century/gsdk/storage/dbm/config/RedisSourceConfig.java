package century.gsdk.storage.dbm.config;
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
