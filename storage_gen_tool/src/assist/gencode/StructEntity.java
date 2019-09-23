package assist.gencode;
import century.gsdk.storage.core.ColumnEntity;
import century.gsdk.storage.core.TableEntity;
import century.gsdk.tools.str.StringTool;
import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;
import org.mybatis.generator.api.dom.java.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class StructEntity {
    private static final Logger logger= LoggerFactory.getLogger("StructEntity");
    private String name;
    private String applyTable;
    private List<StructFieldEntity> fieldEntityList;
    private Map<String,StructFieldEntity> fieldEntityMap;
    private AccessEntity accessEntity;
    private SimpleGenJava simpleGenJava;
    public StructEntity(Element element,AccessEntity accessEntity) {
        this.accessEntity = accessEntity;
        fieldEntityList=new ArrayList<>();
        fieldEntityMap=new HashMap<>();
        name= XMLTool.getStrAttrValue(element,"name");
        applyTable=XMLTool.getStrAttrValue(element,"applyTable");
        simpleGenJava=new SimpleGenJava(accessEntity.getNamespace(),accessEntity.getTarget(),name);
        TableEntity tableEntity=accessEntity.getDataBaseEntity().getTableEntity(applyTable);
        if(tableEntity!=null){
            for(ColumnEntity columnEntity:tableEntity.getColumnEntities()){
                StructFieldEntity structFieldEntity=new StructFieldEntity(
                        columnEntity.getName(),
                        columnEntity.getDataType()
                );

                fieldEntityMap.put(structFieldEntity.getName(),structFieldEntity);
                fieldEntityList.add(structFieldEntity);
            }
        }else{
            List<Element> fieldNodeList=element.elements("field");
            for(Element fieldNode:fieldNodeList){
                StructFieldEntity structFieldEntity=new StructFieldEntity(fieldNode);
                fieldEntityMap.put(structFieldEntity.getName(),structFieldEntity);
                fieldEntityList.add(structFieldEntity);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void autoGen(){
        Method method=new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.addBodyLine("");
        method.setName(name);
        simpleGenJava.addMethod(method);
        method=new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName(name);
        simpleGenJava.addMethod(method);
        for(StructFieldEntity columnEntity:fieldEntityList){
            Field field=new Field();
            field.setName(columnEntity.getName());
            field.setType(new FullyQualifiedJavaType(columnEntity.getType().getJavaType()));
            field.setVisibility(JavaVisibility.PRIVATE);
            simpleGenJava.addField(field);
            Method getset=new Method();
            getset.setName("get"+ StringTool.toUpperCaseFirst(field.getName()));
            getset.setVisibility(JavaVisibility.PUBLIC);
            getset.setReturnType(field.getType());
            getset.addBodyLine("return this."+field.getName()+";");
            simpleGenJava.addMethod(getset);
            getset=new Method();
            getset.setName("set"+StringTool.toUpperCaseFirst(field.getName()));
            getset.setVisibility(JavaVisibility.PUBLIC);
            getset.addParameter(new Parameter(field.getType(),field.getName()));
            getset.addBodyLine("this."+field.getName()+"="+field.getName()+";");
            simpleGenJava.addMethod(getset);

            method.addParameter(new Parameter(field.getType(),field.getName()));
            method.addBodyLine("this."+field.getName()+"="+field.getName()+";");
        }

        simpleGenJava.write();
    }

    public StructFieldEntity getFieldEntity(String name){
        return fieldEntityMap.get(name);
    }

    public List<StructFieldEntity> getFieldEntityList() {
        return fieldEntityList;
    }
}
