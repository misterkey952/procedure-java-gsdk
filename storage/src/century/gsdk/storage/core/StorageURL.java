package century.gsdk.storage.core;

import century.gsdk.tools.str.StringTool;
import century.gsdk.tools.xml.XMLTool;
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
 * Author's Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class StorageURL {
    private String host;
    private String character="UTF-8";
    private String user;
    private String passwd;
    private String driver;
    private DataProtocolType protoType= DataProtocolType.JDBC;
    private DataBaseType dataBaseType=DataBaseType.MYSQL;

    public StorageURL(Element element) {
        host= XMLTool.getStrTextValue(element,"host");
        user=XMLTool.getStrTextValue(element,"user");
        passwd=XMLTool.getStrTextValue(element,"passwd");
        driver=XMLTool.getStrTextValue(element,"driver");
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

    public DataProtocolType getProtoType() {
        return protoType;
    }

    public DataBaseType getDataBaseType() {
        return dataBaseType;
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
            return DriverManager.getConnection(url,user,passwd);
        } catch (Exception e) {
            if(e.getMessage().startsWith("Unknown database")){
                return null;
            }else{
                throw e;
            }
        }
    }
}
