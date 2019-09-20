package century.gsdk.storage.core;

import century.gsdk.tools.str.StringTool;
import century.gsdk.tools.xml.XMLTool;
import com.jolbox.bonecp.BoneCPConfig;
import org.dom4j.Element;

import java.sql.Connection;
import java.sql.DriverManager;

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
    private String character="UTF-8";
    private String user;
    private String passwd;
    private String driver;
    private int split;
    private DataProtocolType protoType= DataProtocolType.JDBC;
    private DataBaseType dataBaseType=DataBaseType.MYSQL;
    public StorageInfo(Element element) {
        name=XMLTool.getStrTextValue(element,"name");
        host= XMLTool.getStrTextValue(element,"host");
        user=XMLTool.getStrTextValue(element,"user");
        passwd=XMLTool.getStrTextValue(element,"passwd");
        driver=XMLTool.getStrTextValue(element,"driver");
        split=XMLTool.getIntTextValue(element,"split");
        DataProtocolType dataProtoType= DataProtocolType.getDataType(XMLTool.getStrAttrValue(element,"protocol"));
        if(dataProtoType!=null){
            protoType=dataProtoType;
        }

        DataBaseType dbt=DataBaseType.getDataType(XMLTool.getStrAttrValue(element,"database"));
        if(dbt!=null){
            dataBaseType=dbt;
        }

        String chr=XMLTool.getStrAttrValue(element,"character");
        if(!StringTool.SPACE.equals(chr)){
            character=chr;
        }
    }


    public String genURL(){
        return genURL(name);
    }

    public String genURL(String dbname){
        if(dataBaseType==DataBaseType.MYSQL){
            return protoType.getProtoName()+":"+dataBaseType.getDataBaseTypeName()+"://"+host+"/"+dbname+"?useUnicode=true&amp;characterEncoding="+character;
        }
        return null;
    }

    public Connection connect(String dbName)throws Exception{
        String url=genURL(dbName);
        try {
            Class.forName(driver);
            Connection connection= DriverManager.getConnection(url,user,passwd);
            return connection;
        } catch (Exception e) {
            if(e.getMessage().startsWith("Unknown database")){
                return null;
            }else{
                throw e;
            }
        }
    }


    public BoneCPConfig BoneCPConfig() throws ClassNotFoundException {
        BoneCPConfig config=new BoneCPConfig();
        Class.forName(driver);
        config.setUser(user);
        config.setPassword(passwd);
        config.setJdbcUrl(genURL());

        config.setPartitionCount(1);
        config.setMaxConnectionsPerPartition(20);
        config.setMinConnectionsPerPartition(10);
        config.setIdleConnectionTestPeriodInMinutes(240);
        config.setAcquireIncrement(2);
        return config;
    }


    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public String getCharacter() {
        return character;
    }

    public String getUser() {
        return user;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getDriver() {
        return driver;
    }

    public int getSplit() {
        return split;
    }

    public DataProtocolType getProtoType() {
        return protoType;
    }

    public DataBaseType getDataBaseType() {
        return dataBaseType;
    }

    public void setName(String name) {
        this.name = name;
    }
}
