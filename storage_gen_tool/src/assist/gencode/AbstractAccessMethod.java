package assist.gencode;

import assist.buildtable.TableEntity;
import century.gsdk.storage.core.DataType;
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
public abstract class AbstractAccessMethod {
    protected static final Logger logger= LoggerFactory.getLogger("AbstractAccessMethod");
    protected String name;
    protected String returnType;
    protected Map<String,AccessParamEntity> paramEntityMap;
    private List<AccessParamEntity> accessParamEntityList;
    protected AccessEntity accessEntity;
    private List<String> sqlparams=new ArrayList<>();
    private List<String> sqlTables=new ArrayList<>();
    private List<String> sqlstage=new ArrayList<>();
    protected Element element;
    private String sql;
    protected Method method;
    private boolean split;
    public AbstractAccessMethod(AccessEntity accessEntity, Element element) {
        this.accessEntity = accessEntity;
        accessParamEntityList=new ArrayList<>();
        this.element = element;
        sql=XMLTool.getStrTextValue(element,"sql");
        paramEntityMap=new HashMap<>();
        name= XMLTool.getStrAttrValue(element,"name");
        returnType=XMLTool.getStrAttrValue(element,"return");
        List<Element> paramList=element.elements("param");
        for(Element e:paramList){
            AccessParamEntity accessParamEntity=new AccessParamEntity(e);
            accessParamEntityList.add(accessParamEntity);
            paramEntityMap.put(accessParamEntity.getName(),accessParamEntity);
        }

        initSql();
        init();
    }
    private void fillParam(){
        for(int i=0;i<sqlparams.size();i++){
            String filstr=sqlparams.get(i);
            String paramName;
            String dotParamName;
            AccessParamEntity uparam;

            if(filstr.contains(".")){
                paramName=filstr.substring(0,filstr.indexOf("."));
                dotParamName=filstr.substring(filstr.indexOf(".")+1);
                uparam=paramEntityMap.get(paramName);
                String ptype=uparam.getType();
                StructEntity structEntity=accessEntity.getStructEntity(ptype);
                StructFieldEntity fieldEntity=structEntity.getFieldEntity(dotParamName);
                filstr=paramName+".get"+StringTool.toUpperCaseFirst(fieldEntity.getName())+"()";
                method.addBodyLine("preparedStatement.set"+StringTool.toUpperCaseFirst(fieldEntity.getType().getSortJavaType())+"("+(i+1)+","+filstr+");");
            }else{
                uparam=paramEntityMap.get(filstr);
                DataType dataType=DataType.getDataType(uparam.getType());
                method.addBodyLine("preparedStatement.set"+StringTool.toUpperCaseFirst(dataType.getSortJavaType())+"("+(i+1)+","+filstr+");");
            }
        }

    }

    protected void createMethod(){
        method=new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setStatic(true);
        method.addException(new FullyQualifiedJavaType("SQLException"));
        accessEntity.getSimpleGenJava().addMethod(method);
        method.addParameter(new Parameter(new FullyQualifiedJavaType("StorageConnect"),"connect"));
        method.addBodyLine("try {");
        if(split){
            method.addParameter(new Parameter(new FullyQualifiedJavaType("int"),"__split__"));
            method.addBodyLine("PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get(\""+name+"\").splitSql(__split__));");
        }else{
            method.addBodyLine("PreparedStatement preparedStatement=connect.preparedStatement(sqlMap.get(\""+name+"\").sql());");
        }

        for(AccessParamEntity accessParamEntity:accessParamEntityList){
            DataType dataType=DataType.getDataType(accessParamEntity.getType());
            String dt=null;
            if(dataType!=null){
                dt=dataType.getJavaType();
            }
            if(dt==null){
                dt=accessParamEntity.getType();
            }
            method.addParameter(new Parameter(
                    new FullyQualifiedJavaType(dt),
                    accessParamEntity.getName()
            ));
        }
        fillParam();


    }

    protected void appendMethodEnd(){
        method.addBodyLine("} catch (SQLException e) {");
        method.addBodyLine("logger.error(\""+method.getName()+"\",e);");
        method.addBodyLine("throw e;");
        method.addBodyLine("}");
    }

    private void genSqlField(){
        accessEntity.getSimpleGenJava().addStaticBlockLine("sqlMap.put(\""+name+"\",new StorageSql(");
        accessEntity.getSimpleGenJava().addStaticBlockLine("new String[]{");
        for(int i=0;i<sqlstage.size();i++){

            if(i<(sqlstage.size()-1)){
                accessEntity.getSimpleGenJava().addStaticBlockLine("\""+sqlstage.get(i)+"\",");
            }else{
                accessEntity.getSimpleGenJava().addStaticBlockLine("\""+sqlstage.get(i)+"\"");
            }
        }
        accessEntity.getSimpleGenJava().addStaticBlockLine("},");
        accessEntity.getSimpleGenJava().addStaticBlockLine("new SqlTable[]{");
        for(int i=0;i<sqlTables.size();i++){
            TableEntity tableEntity=accessEntity.getDataBaseEntity().getTableEntity(sqlTables.get(i));
            if(!split){
                split=tableEntity.getSplit()>1;
            }
            if(i<(sqlTables.size()-1)){
                accessEntity.getSimpleGenJava().addStaticBlockLine("new SqlTable(\""+tableEntity.getName()+"\","+tableEntity.getSplit()+"),");
            }else{
                accessEntity.getSimpleGenJava().addStaticBlockLine("new SqlTable(\""+tableEntity.getName()+"\","+tableEntity.getSplit()+")");
            }
        }

        accessEntity.getSimpleGenJava().addStaticBlockLine("}");
        accessEntity.getSimpleGenJava().addStaticBlockLine("));");

    }

    private void initSqlTable(){
        char[] chars=sql.toCharArray();
        StringBuilder buildSql=new StringBuilder();
        StringBuilder stb=new StringBuilder();
        boolean appendTable=false;
        for(char cc:chars){
            if(cc=='['){
                appendTable=true;
                buildSql.append("$");
                continue;
            }
            if(cc==']'){
                appendTable=false;
                sqlTables.add(stb.toString());
                stb=new StringBuilder();
                continue;
            }

            if(appendTable){
                stb.append(cc);
            }else{
                buildSql.append(cc);
            }
        }

        sql=buildSql.toString();
    }

    private void initSqlParam(){
        char[] chars=sql.toCharArray();
        StringBuilder buildSql=new StringBuilder();
        StringBuilder stb=new StringBuilder();
        boolean append=false;
        for(char cc:chars){
            if(cc=='?'){
                append=!append;
                if(!append){
                    sqlparams.add(stb.toString());
                    stb=new StringBuilder();
                }else{
                    buildSql.append(cc);
                }
                continue;
            }

            if(append){
                stb.append(cc);
            }else{
                buildSql.append(cc);
            }
        }

        sql=buildSql.toString();
    }

    private void initSql(){
        initSqlTable();
        initSqlParam();
        char[] chars=sql.toCharArray();
        StringBuilder stb=new StringBuilder();
        for(char cc:chars){
            if(cc=='$'){
                sqlstage.add(stb.toString());
                stb=new StringBuilder();
                continue;
            }
            stb.append(cc);
        }

        if(stb.length()>0){
            sqlstage.add(stb.toString());
        }
        genSqlField();
    }


    void init(){}
    abstract void autoGen();
}
