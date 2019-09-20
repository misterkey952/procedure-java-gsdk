package assist;
import century.gsdk.tools.str.StringTool;
import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.*;

import java.io.*;
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
    private String namespace;
    private String storage;
    private String table;
    private String name;
    private List<StructEntity> structEntities;
    private DataBaseEntity dataBaseEntity;
    private TableEntity tableEntity;
    private List<AccessMethod> methods;
    public AccessEntity(Element element) {
        methods=new ArrayList<>();
        structEntities=new ArrayList<>();
        namespace= XMLTool.getStrAttrValue(element,"namespace");
        storage=XMLTool.getStrAttrValue(element,"storage");
        table=XMLTool.getStrAttrValue(element,"table");
        name=XMLTool.getStrAttrValue(element,"name");
        List<Element> elementList=element.elements("struct");
        Map<String,StructEntity> structType= new HashMap<>();
        dataBaseEntity=AssistApplication.getInstance().getDataBaseEntity(storage);
        tableEntity=dataBaseEntity.getTableEntity(table);
        StructEntity tableStruct=new StructEntity(name+"Struct",tableEntity.getColumnEntities());
        structType.put(name+"Struct",tableStruct);
        for(Element eee:elementList){
            StructEntity structEntity=new StructEntity(eee);
            structEntities.add(structEntity);
            structType.put(structEntity.getName(),structEntity);
        }

        elementList=element.elements("method");
        for(Element eee:elementList){
            methods.add(new AccessMethod(eee,structType));
        }
    }


    private void autoGenAccessAssist(){
        String assName=name+"Assist";
        TopLevelClass topLevelClass=new TopLevelClass(namespace+"."+assName);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        GeneratedJavaFile generatedJavaFile=new GeneratedJavaFile(topLevelClass,
                AssistApplication.getInstance().getProject(),
                "UTF-8",new DefaultJavaFormatter());

        topLevelClass.setFinal(true);
        topLevelClass.addImportedType("century.gsdk.storage.core.StorageConnect");
        topLevelClass.addImportedType("org.slf4j.Logger");
        topLevelClass.addImportedType("org.slf4j.LoggerFactory");
        topLevelClass.addImportedType("java.sql.PreparedStatement");
        topLevelClass.addImportedType("java.sql.ResultSet");
        topLevelClass.addImportedType("java.sql.SQLException");
        topLevelClass.addImportedType("java.util.List");
        topLevelClass.addImportedType("java.util.ArrayList");
        topLevelClass.addImportedType("java.util.Map");
        topLevelClass.addImportedType("java.util.HashMap");

        Field field=new Field();
        field.setName("logger");
        field.setType(new FullyQualifiedJavaType("Logger"));
        field.setFinal(true);
        field.setStatic(true);
        field.setInitializationString("LoggerFactory.getLogger(\""+assName+"\")");
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);
        for(AccessMethod accessMethod:methods){
            field=new Field();
            field.setName(accessMethod.getMethod().getName());
            accessMethod.setPfileList(new ArrayList<>());
            field.setVisibility(JavaVisibility.PRIVATE);
            field.setFinal(true);
            field.setStatic(true);
            topLevelClass.addField(field);
            String sql=prepSql(accessMethod.getSql(),accessMethod.getPfileList());
            if(tableEntity.getSplit()>1){
                field.setType(new FullyQualifiedJavaType("String[]"));
                StringBuilder inistr=new StringBuilder();
                inistr.append("new String[]{");
                for(int i=0;i<tableEntity.getSplit();i++){
                    inistr.append("\r\n");
                    inistr.append("\t\t");
                    sql=sql.replaceAll("#table",tableEntity.getName()+"_"+i);
                    inistr.append("\"").append(sql);
                    inistr.append("\"");
                    if(i<(tableEntity.getSplit()-1)){
                        inistr.append(",");
                    }
                }
                inistr.append("\r\n\t}");
                field.setInitializationString(inistr.toString());
            }else{
                field.setType(new FullyQualifiedJavaType("String"));
                sql=sql.replaceAll("#table",tableEntity.getName());
                field.setInitializationString("\""+sql+"\"");
            }

            accessMethod.genBody(tableEntity,topLevelClass);
            topLevelClass.addMethod(accessMethod.getMethod());
        }

        output(generatedJavaFile.toString(),assName+".java");
    }


    private String prepSql(String sql,List<String> list){
        char[] chars=sql.toCharArray();
        StringBuilder stb=new StringBuilder();
        boolean append=true;
        StringBuilder sstb=null;
        for(char cc:chars){
            if(append){
                stb.append(cc);
            }else{
                if(cc!='?'){
                    sstb.append(cc);
                }

            }
            if(cc=='?'){
                if(append){
                    sstb=new StringBuilder();
                }else{
                    list.add(sstb.toString());
                }
                append=!append;

            }

        }
        return stb.toString();
    }

    private void autoGenStructs(StructEntity structEntity){
        TopLevelClass topLevelClass=new TopLevelClass(namespace+"."+structEntity.getName());
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        GeneratedJavaFile generatedJavaFile=new GeneratedJavaFile(topLevelClass,
                AssistApplication.getInstance().getProject(),
                "UTF-8",new DefaultJavaFormatter());

        Method method=new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.addBodyLine("");
        method.setName(structEntity.getName());
        topLevelClass.addMethod(method);
        method=new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName(structEntity.getName());
        topLevelClass.addMethod(method);
        for(ColumnEntity columnEntity:structEntity.getList()){
            Field field=new Field();
            field.setName(columnEntity.getName());
            field.setType(new FullyQualifiedJavaType(columnEntity.getDataType().getJavaType()));
            field.setVisibility(JavaVisibility.PRIVATE);
            topLevelClass.addField(field);
            Method getset=new Method();
            getset.setName("get"+ StringTool.toUpperCaseFirst(field.getName()));
            getset.setVisibility(JavaVisibility.PUBLIC);
            getset.setReturnType(field.getType());
            getset.addBodyLine("return this."+field.getName()+";");
            topLevelClass.addMethod(getset);
            getset=new Method();
            getset.setName("set"+StringTool.toUpperCaseFirst(field.getName()));
            getset.setVisibility(JavaVisibility.PUBLIC);
            getset.addParameter(new Parameter(field.getType(),field.getName()));
            getset.addBodyLine("this."+field.getName()+"="+field.getName()+";");
            topLevelClass.addMethod(getset);

            method.addParameter(new Parameter(field.getType(),field.getName()));
            method.addBodyLine("this."+field.getName()+"="+field.getName()+";");
        }
        output(generatedJavaFile.toString(),structEntity.getName()+".java");
    }

    private void autoGenTableStruct(){
        String structName=name+"Struct";
        TopLevelClass topLevelClass=new TopLevelClass(namespace+"."+structName);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        GeneratedJavaFile generatedJavaFile=new GeneratedJavaFile(topLevelClass,
                AssistApplication.getInstance().getProject(),
                "UTF-8",new DefaultJavaFormatter());


        Method method=new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.addBodyLine("");
        method.setName(structName);
        topLevelClass.addMethod(method);
        method=new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName(structName);
        topLevelClass.addMethod(method);
        for(ColumnEntity columnEntity:tableEntity.getColumnEntities()){
            Field field=new Field();
            field.setName(columnEntity.getName());
            field.setType(new FullyQualifiedJavaType(columnEntity.getDataType().getJavaType()));
            field.setVisibility(JavaVisibility.PRIVATE);
            topLevelClass.addField(field);
            Method getset=new Method();
            getset.setName("get"+ StringTool.toUpperCaseFirst(field.getName()));
            getset.setVisibility(JavaVisibility.PUBLIC);
            getset.setReturnType(field.getType());
            getset.addBodyLine("return this."+field.getName()+";");
            topLevelClass.addMethod(getset);
            getset=new Method();
            getset.setName("set"+StringTool.toUpperCaseFirst(field.getName()));
            getset.setVisibility(JavaVisibility.PUBLIC);
            getset.addParameter(new Parameter(field.getType(),field.getName()));
            getset.addBodyLine("this."+field.getName()+"="+field.getName()+";");
            topLevelClass.addMethod(getset);

            method.addParameter(new Parameter(field.getType(),field.getName()));
            method.addBodyLine("this."+field.getName()+"="+field.getName()+";");
        }
        output(generatedJavaFile.toString(),structName+".java");
    }


    public void autoGen(){
        autoGenTableStruct();
        for(StructEntity structEntity:structEntities){
            autoGenStructs(structEntity);
        }

        autoGenAccessAssist();

    }


    private void output(String str,String fileName){
        String path=AssistApplication.getInstance().getProject();
        String subpath=namespace.replaceAll("\\.","/");
        File dir=new File(path+File.separator+subpath);
        File file=new File(path+File.separator+subpath+File.separator+fileName);
        BufferedWriter bufferedWriter=null;
        try{
            if(!dir.exists()){
                if(!dir.mkdirs()){
                    throw new Exception("mkdirs err");
                }
            }

            if(file.exists()){
                if(!file.delete()){
                    throw new Exception("delete err");
                }

                if(!file.createNewFile()){
                    throw new Exception("createNewFile err");
                }

            }


            bufferedWriter=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bufferedWriter.write(str);
            bufferedWriter.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(bufferedWriter!=null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
