package assist.buildtable;

import assist.AssistApplication;
import century.gsdk.tools.str.StringTool;
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
public class TableEntity {
    private String name;
    private int split;
    private String character;
    private Map<String,ColumnEntity> columns;
    private List<ColumnEntity> columnEntities;
    private String engine="MyISAM";
    public TableEntity(Element element) {
        columns=new HashMap<>();
        columnEntities=new ArrayList<>();
        name= XMLTool.getStrAttrValue(element,"name");
        split=XMLTool.getIntAttrValue(element,"split");
        character=XMLTool.getStrAttrValue(element,"character");
        String et=XMLTool.getStrAttrValue(element,"engine");
        if(!StringTool.SPACE.equals(et)){
            engine=et;
        }
        List<Element> list=element.elements();
        ColumnEntity columnEntity;
        for(Element e:list){
            columnEntity=new ColumnEntity(e);
            columns.put(columnEntity.getName(),columnEntity);
            columnEntities.add(columnEntity);
        }
    }

    public String getName() {
        return name;
    }


    private String genCreateSql(String tableName){
        StringBuilder str=new StringBuilder();
        str.append("CREATE TABLE ").append(tableName).append("(");
        Map<String,KeyTypeEntity> keyMapList=new HashMap<>();
        for(int i=0;i<columnEntities.size();i++){
            ColumnEntity columnEntity=columnEntities.get(i);
            str.append(columnEntity.getName());
            if(columnEntity.getLength()>0){
                str.append(" ")
                .append(columnEntity.getDataType().getMysqlType()).append("(").append(columnEntity.getLength()).append(")");
            }else{
                str.append(" ");
                str.append(columnEntity.getDataType().getMysqlType());
            }

            if(columnEntity.getDefaultValue()!=null&&!StringTool.SPACE.equals(columnEntity.getDefaultValue())){
                str.append(" ").append("default '").append(columnEntity.getDefaultValue()).append("'");
            }

            if(columnEntity.isIncr()){
                str.append(" AUTO_INCREMENT");
            }

            if(columnEntity.getKeyType()!=null&&!StringTool.SPACE.equals(columnEntity.getKeyType())){
                KeyTypeEntity keyTypeEntity=keyMapList.get(columnEntity.getKeyType());
                if(keyTypeEntity==null){
                    keyTypeEntity=new KeyTypeEntity(columnEntity.getKeyType(),name);
                    keyMapList.put(columnEntity.getKeyType(),keyTypeEntity);
                }
                keyTypeEntity.addField(columnEntity.getName());
            }

            if(i<(columnEntities.size()-1)){
                str.append(",");
            }
        }

        if(keyMapList.size()>0){
            List<KeyTypeEntity> list=new ArrayList<>(keyMapList.values());
            for(int i=0;i<list.size();i++){
                KeyTypeEntity keyTypeEntity=list.get(i);
                str.append(",").append(keyTypeEntity.genSql());
            }
        }

        str.append(")");
        str.append("ENGINE=").append(engine).append(" ").append("DEFAULT CHARSET=").append(character);
        return str.toString();
    }

    public void autoGen(Connection connection,boolean splitTable){
        Statement statement=null;
        try{
            statement=connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS "+name);
            statement.executeUpdate(genCreateSql(name));
            AssistApplication.logger.info("create table {} complete",name);
            if(split>1&&splitTable){
                for(int i=0;i<split;i++){
                    statement.executeUpdate("DROP TABLE IF EXISTS "+name+"_"+i);
                    statement.executeUpdate(genCreateSql(name+"_"+i));
                    AssistApplication.logger.info("create table {}_{} complete",name,i);
                }
            }
        }catch(Exception e){
            AssistApplication.logger.error("autoGenTable "+name,e);
        }finally {
            if(statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    AssistApplication.logger.error("autoGenTable close statement err",e);
                }
            }
        }
    }

    public List<ColumnEntity> getColumnEntities() {
        return columnEntities;
    }

    public int getSplit() {
        return split;
    }
}
