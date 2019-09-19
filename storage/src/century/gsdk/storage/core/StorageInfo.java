package century.gsdk.storage.core;

/**
 * Copyright (C) <2019>  <Century>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * <p>
 * Author Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class StorageInfo {
    private String name;
    private String host;
    private String protoType= "jdbc";
    private String dataBaseType="mysql";
    private String character="UTF-8";
    private String user;
    private String pwd;
    private String driver;
    private int split;


    public StorageInfo(String name, String host, String protoType, String dataBaseType, String character, String user, String pwd, String driver, int split) {
        this.name = name;
        this.host = host;
        this.protoType = protoType;
        this.dataBaseType = dataBaseType;
        this.character = character;
        this.user = user;
        this.pwd = pwd;
        this.driver = driver;
        this.split = split;
    }

    public StorageInfo(String name, String host, String user, String pwd, String driver, int split) {
        this.name = name;
        this.host = host;
        this.user = user;
        this.pwd = pwd;
        this.driver = driver;
        this.split = split;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return protoType+":"+dataBaseType+"://"+host+"/"+getName()+"?useUnicode=true&amp;characterEncoding="+character;
    }

    public String getUser() {
        return user;
    }

    public String getPwd() {
        return pwd;
    }

    public String getDriver() {
        return driver;
    }

    public int getSplit() {
        return split;
    }
}
