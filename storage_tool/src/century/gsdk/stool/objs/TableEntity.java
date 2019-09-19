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
public class TableEntity {
    private String name;
    private int split;
    private String character;
    private Map<String,ColumnEntity> columns;

    public TableEntity(Element element) {
        columns=new HashMap<>();
        name= XMLTool.getStrAttrValue(element,"name");
        split=XMLTool.getIntAttrValue(element,"split");
        character=XMLTool.getStrAttrValue(element,"character");
        List<Element> list=element.elements();
        ColumnEntity columnEntity;
        for(Element e:list){
            columnEntity=new ColumnEntity(e);
            columns.put(columnEntity.getName(),columnEntity);
        }
    }

    public String getName() {
        return name;
    }

    public int getSplit() {
        return split;
    }

    public String getCharacter() {
        return character;
    }

    public ColumnEntity getColumn(String name){
        return columns.get(name);
    }
    public List<ColumnEntity> getColumnList(){
        return new ArrayList<>(columns.values());
    }
}
