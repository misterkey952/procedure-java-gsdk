package assist.gencode;
import assist.AssistApplication;
import assist.buildtable.DataBaseEntity;
import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;
import org.mybatis.generator.api.dom.java.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
public class AccessEntity {
    private static final Logger logger= LoggerFactory.getLogger("AccessEntity");
    private String namespace;
    private String storage;
    private String name;
    private String target;
    private Map<String,StructEntity> structEntities;
    private List<AbstractAccessMethod> accessMethodList;
    private SimpleGenJava simpleGenJava;
    private DataBaseEntity dataBaseEntity;
    public AccessEntity(Element element) {
        structEntities=new HashMap<>();
        accessMethodList=new ArrayList<>();
        namespace= XMLTool.getStrAttrValue(element,"namespace");
        storage=XMLTool.getStrAttrValue(element,"storage");
        name=XMLTool.getStrAttrValue(element,"name");
        target=XMLTool.getStrAttrValue(element,"target");
        List<Element> elementList=element.elements("struct");
        dataBaseEntity= AssistApplication.getInstance().getDataBaseEntity(storage);
        if(dataBaseEntity==null){
            logger.error("the storage is not exist ["+storage+"] now exit");
            System.exit(0);
        }


        simpleGenJava=new SimpleGenJava(namespace,target,name);
        for(Element e:elementList){
            StructEntity structEntity=new StructEntity(e,this);
            structEntities.put(structEntity.getName(),structEntity);
        }

        elementList=element.elements("select");
        if(elementList!=null){
            for(Element e:elementList){
                AbstractAccessMethod abstractAccessMethod=new SelectMethodEntity(this,e);
                accessMethodList.add(abstractAccessMethod);
            }
        }


        elementList=element.elements("update");
        if(elementList!=null){
            for(Element e:elementList){
                AbstractAccessMethod abstractAccessMethod=new UpdateMethodEntity(this,e);
                accessMethodList.add(abstractAccessMethod);
            }
        }


        elementList=element.elements("insert");
        if(elementList!=null){
            for(Element e:elementList){
                AbstractAccessMethod abstractAccessMethod=new InsertMethodEntity(this,e);
                accessMethodList.add(abstractAccessMethod);
            }
        }


        elementList=element.elements("delete");
        if(elementList!=null){
            for(Element e:elementList){
                AbstractAccessMethod abstractAccessMethod=new DeleteMethodEntity(this,e);
                accessMethodList.add(abstractAccessMethod);
            }
        }

    }

    public void autoGen(){
        for(StructEntity structEntity:structEntities.values()){
            structEntity.autoGen();
        }

        simpleGenJava.setFinal(true);
        simpleGenJava.importType("century.gsdk.storage.core.StorageConnect");
        simpleGenJava.importType("org.slf4j.Logger");
        simpleGenJava.importType("org.slf4j.LoggerFactory");
        simpleGenJava.importType("java.sql.PreparedStatement");
        simpleGenJava.importType("java.sql.ResultSet");
        simpleGenJava.importType("java.sql.SQLException");
        simpleGenJava.importType("java.util.List");
        simpleGenJava.importType("java.util.ArrayList");
        simpleGenJava.importType("java.util.Map");
        simpleGenJava.importType("java.util.HashMap");
        simpleGenJava.importType("century.gsdk.storage.core.SqlTable");
        simpleGenJava.importType("century.gsdk.storage.core.StorageSql");


        Field field=new Field();
        field.setName("logger");
        field.setType(new FullyQualifiedJavaType("Logger"));
        field.setFinal(true);
        field.setStatic(true);
        field.setInitializationString("LoggerFactory.getLogger(\""+name+"\")");
        field.setVisibility(JavaVisibility.PRIVATE);
        simpleGenJava.addField(field);

        field=new Field();
        field.setName("sqlMap");
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setFinal(true);
        field.setStatic(true);
        field.setType(new FullyQualifiedJavaType("Map<String,StorageSql>"));
        field.setInitializationString("new HashMap<>()");
        simpleGenJava.addField(field);
        for(AbstractAccessMethod abstractAccessMethod:accessMethodList){
            abstractAccessMethod.autoGen();
        }


        simpleGenJava.write();
        logger.info("auto gen a accessAssist [{}]",name);
    }


    public StructEntity getStructEntity(String name){
        return structEntities.get(name);
    }
    public String getNamespace() {
        return namespace;
    }

    public String getStorage() {
        return storage;
    }

    public String getName() {
        return name;
    }

    public String getTarget() {
        return target;
    }

    public SimpleGenJava getSimpleGenJava() {
        return simpleGenJava;
    }

    public DataBaseEntity getDataBaseEntity() {
        return dataBaseEntity;
    }
}
