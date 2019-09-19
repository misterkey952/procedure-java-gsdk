package century.gsdk.stool.objs;

import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;

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
    private String dbName;
    private int split;
    private DataBaseConnection connection;
    private Map<String,TableEntity> tables;

    public DataBaseEntity(Element element) {

        connection=new DataBaseConnection(element.element("connection"));

        tables=new HashMap<>();
        dbName= XMLTool.getStrAttrValue(element,"name");
        split=XMLTool.getIntAttrValue(element,"split");
        List<Element> elements=element.elements("table");
        TableEntity tableEntity;
        for(Element e:elements){
            tableEntity=new TableEntity(e);
            tables.put(tableEntity.getName(),tableEntity);
        }
    }

    public String getDbName() {
        return dbName;
    }

    public int getSplit() {
        return split;
    }

    public DataBaseConnection getConnection() {
        return connection;
    }

    public TableEntity getTableEntity(String name){
        return tables.get(name);
    }
    public List<TableEntity> getTableList(){
        return new ArrayList<>(tables.values());
    }
}
