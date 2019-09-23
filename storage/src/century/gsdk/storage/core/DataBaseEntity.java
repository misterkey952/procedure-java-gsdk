package century.gsdk.storage.core;
import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class DataBaseEntity {
    private StorageURL url;
    private String name;
    private int split;
    private Map<String,TableEntity> tables;
    private List<TableEntity> tableList;

    public DataBaseEntity(Element element){
        tables=new HashMap<>();
        tableList=new ArrayList<>();
        name= XMLTool.getStrAttrValue(element,"name");
        split=XMLTool.getIntAttrValue(element,"split");
        List<Element> elements=element.elements("table");
        TableEntity tableEntity;
        for(Element e:elements){
            tableEntity=new TableEntity(e);
            tables.put(tableEntity.getName(),tableEntity);
            tableList.add(tableEntity);
        }
    }

    public DataBaseEntity(Element element,StorageURL url){
        this.url=url;
        tables=new HashMap<>();
        tableList=new ArrayList<>();
        name= XMLTool.getStrAttrValue(element,"name");
        split=XMLTool.getIntAttrValue(element,"split");
        List<Element> elements=element.elements("table");
        TableEntity tableEntity;
        for(Element e:elements){
            tableEntity=new TableEntity(e);
            tables.put(tableEntity.getName(),tableEntity);
            tableList.add(tableEntity);
        }
    }

    public String getName() {
        return name;
    }

    public int getSplit() {
        return split;
    }

    private void genTables(Connection connection, boolean splitTable, AssistExeInfo exeInfo){
        for(TableEntity tableEntity:tableList){
            tableEntity.autoGen(connection,splitTable,exeInfo);
        }
    }

    public void autoGen(AssistExeInfo exeInfo){
        Connection connection=null;
        try {
            connection=getConnectOfDataBase(name,null,exeInfo);
            genTables(connection,split<=1,exeInfo);
            if(split>1){
                for(int i=0;i<split;i++){
                    connection=getConnectOfDataBase(name+"_"+i,connection,exeInfo);
                    genTables(connection,true,exeInfo);
                }
            }
        } catch (Exception e) {
            exeInfo.setCode(AssistErrCode.SYSERR);
            exeInfo.appendString("auto gen database "+name+" err");
            exeInfo.appendException(e);
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    exeInfo.appendString("auto gen database "+name+" close connect err");
                    exeInfo.appendException(e);
                }
            }
        }
    }

    private Connection getConnectOfDataBase(String ldb, Connection conn,AssistExeInfo exeInfo) throws Exception{
        Connection connection=null;
        Statement statement=null;
        try {
            connection=url.connect(ldb);
            if(connection==null){
                if(conn!=null){
                    connection=conn;
                }else{
                    connection=url.connect("test");
                }

                statement=connection.createStatement();
                statement.executeUpdate("create database "+ldb);
                statement.close();
                statement=null;
                connection.close();
                connection=url.connect(ldb);
                exeInfo.appendString("create a database "+ldb+" success");
            }
            connection.setAutoCommit(true);
            return connection;
        } catch (Exception e) {
            if(connection!= null){
                connection.close();
            }
            throw e;
        }finally {
            if(statement!=null){
                statement.close();
            }
        }
    }


    public TableEntity getTableEntity(String name){
        return tables.get(name);
    }

}
