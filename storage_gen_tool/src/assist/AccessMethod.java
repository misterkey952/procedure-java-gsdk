package assist;

import century.gsdk.storage.core.DataType;
import century.gsdk.tools.str.StringTool;
import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
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
public class AccessMethod {
    private Method method;
    private String sql;
    private List<Parameter> userParams;
    private Map<String,StructEntity> structType;
    private TopLevelClass topLevelClass;
    private TableEntity tableEntity;
    private Element element;
    private String sqlFieldName;
    private String mapKey;
    private List<String> pfileList;
    private AccessMethod copy(){
        AccessMethod cop=new AccessMethod();
        cop.sql=sql;
        cop.userParams=userParams;
        cop.pfileList=pfileList;
        cop.structType=structType;
        cop.topLevelClass=topLevelClass;
        cop.tableEntity=tableEntity;
        cop.element=element;
        cop.method=new Method();
        cop.method.addParameter(new Parameter(new FullyQualifiedJavaType("StorageConnect"),"connect"));
        cop.method.setStatic(method.isStatic());
        cop.method.setVisibility(method.getVisibility());
        cop.method.setReturnType(method.getReturnType());
        cop.method.setName(method.getName());
        cop.method.setConstructor(method.isConstructor());
        cop.method.getParameters().addAll(userParams);
        cop.method.getExceptions().addAll(method.getExceptions());
        cop.sqlFieldName=sqlFieldName;
        cop.mapKey=mapKey;
        return cop;
    }

    private AccessMethod(){
    }
    public AccessMethod(Element element, Map<String,StructEntity> structType) {
        this.element=element;
        this.structType=structType;
        method=new Method();
        mapKey=XMLTool.getStrAttrValue(element,"mapKey");
        userParams=new ArrayList<>();
        method.setName(XMLTool.getStrAttrValue(element,"name"));
        sqlFieldName=method.getName();
        String type=XMLTool.getStrAttrValue(element,"return");
        FullyQualifiedJavaType javaType=null;
        DataType dataType=DataType.getDataType(type);
        if(dataType==null){
            if(structType.keySet().contains(type)){
                javaType=new FullyQualifiedJavaType(type);
            }
        }else{
            javaType=new FullyQualifiedJavaType(dataType.getJavaType());
        }
        if(javaType!=null){
            method.setReturnType(javaType);
        }

        method.setVisibility(JavaVisibility.PUBLIC);
        method.setStatic(true);
        method.addParameter(new Parameter(new FullyQualifiedJavaType("StorageConnect"),"connect"));
        sql=XMLTool.getStrTextValue(element,"sql");
        List<Element> params=element.elements("param");
        if(params!=null&&params.size()>0){
            for(Element pe:params){
                javaType=null;
                type=XMLTool.getStrAttrValue(pe,"type");
                dataType=DataType.getDataType(type);
                if(dataType==null){
                    if(structType.keySet().contains(type)){
                        javaType=new FullyQualifiedJavaType(type);
                    }
                }else{
                    javaType=new FullyQualifiedJavaType(dataType.getJavaType());
                }
                String name=XMLTool.getStrAttrValue(pe,"name");
                if(javaType!=null){
                    Parameter parameter=new Parameter(javaType,name);
                    userParams.add(parameter);
                    method.addParameter(parameter);
                }
            }
        }

        method.addException(new FullyQualifiedJavaType("SQLException"));
    }

    private void fillParam(){
        for(int i=0;i<pfileList.size();i++){
            String filstr=pfileList.get(i);
            Parameter parameter=userParams.get(i);
            if(filstr.contains(".")){
                String ptype=parameter.getType().getShortName();
                StructEntity structEntity=structType.get(ptype);
                ColumnEntity field=structEntity.getColumn(filstr.substring(filstr.indexOf(".")+1));
                filstr=filstr.substring(0,filstr.indexOf("."))+".get"+StringTool.toUpperCaseFirst(field.getName())+"()";
                method.addBodyLine("preparedStatement.set"+StringTool.toUpperCaseFirst(field.getDataType().getSortJavaType())+"("+(i+1)+","+filstr+");");
            }else{
                method.addBodyLine("preparedStatement.set"+StringTool.toUpperCaseFirst(parameter.getType().getShortName())+"("+(i+1)+","+filstr+");");
            }
        }

    }

    public List<String> getPfileList() {
        return pfileList;
    }

    public void setPfileList(List<String> pfileList) {
        this.pfileList = pfileList;
    }

    private void genListQuery(){
        FullyQualifiedJavaType tmpType=method.getReturnType();
        method.setReturnType(new FullyQualifiedJavaType("List<"+tmpType.getShortName()+">"));
        method.addBodyLine("try {");
        if(tableEntity.getSplit()>1){
            method.addParameter(new Parameter(new FullyQualifiedJavaType("int"),"splitID"));
            method.addBodyLine("PreparedStatement preparedStatement=connect.preparedStatement("+sqlFieldName+"[splitID%"+sqlFieldName+".length]);");
        }else{
            method.addBodyLine("PreparedStatement preparedStatement=connect.preparedStatement("+sqlFieldName+");");
        }

        method.addBodyLine("List<"+tmpType.getShortName()+"> list=new ArrayList<>();");
        method.addBodyLine(tmpType.getShortName()+" struct;");
        fillParam();

        String returyType=tmpType.getShortName();
        method.addBodyLine("ResultSet resultSet=preparedStatement.executeQuery();");
        method.addBodyLine("while(resultSet.next()){");
        method.addBodyLine("struct=new "+returyType+"(");
        StructEntity structEntity=structType.get(returyType);
        for(int i=0;i<structEntity.getList().size();i++){
            ColumnEntity columnEntity=structEntity.getList().get(i);
            if(i<(structEntity.getList().size()-1)){
                method.addBodyLine("resultSet.get"+StringTool.toUpperCaseFirst(columnEntity.getDataType().getSortJavaType())+"(\""+columnEntity.getName()+"\"),");
            }else{
                method.addBodyLine("resultSet.get"+StringTool.toUpperCaseFirst(columnEntity.getDataType().getSortJavaType())+"(\""+columnEntity.getName()+"\")");
            }
        }
        method.addBodyLine(");");
        method.addBodyLine("list.add(struct);");
        method.addBodyLine("}");


        method.addBodyLine("return list;");
        method.addBodyLine("} catch (SQLException e) {");
        method.addBodyLine("logger.error(\""+method.getName()+"\",e);");
        method.addBodyLine("throw e;");
        method.addBodyLine("}");
    }



    private void genMapQuery(){
        FullyQualifiedJavaType tmpType=method.getReturnType();
        String returyType=tmpType.getShortName();
        StructEntity structEntity=structType.get(returyType);
        ColumnEntity keyColumn=structEntity.getColumn(mapKey);
        if(keyColumn==null){
            keyColumn=structEntity.getList().get(0);
        }

        method.setReturnType(new FullyQualifiedJavaType("Map<"+keyColumn.getDataType().getSortJavaType()+","+returyType+">"));
        method.addBodyLine("try {");
        if(tableEntity.getSplit()>1){
            method.addParameter(new Parameter(new FullyQualifiedJavaType("int"),"splitID"));
            method.addBodyLine("PreparedStatement preparedStatement=connect.preparedStatement("+sqlFieldName+"[splitID%"+sqlFieldName+".length]);");
        }else{
            method.addBodyLine("PreparedStatement preparedStatement=connect.preparedStatement("+sqlFieldName+");");
        }

        method.addBodyLine("Map<"+keyColumn.getDataType().getSortJavaType()+","+returyType+"> map=new HashMap<>();");
        method.addBodyLine(tmpType.getShortName()+" struct;");
        fillParam();


        method.addBodyLine("ResultSet resultSet=preparedStatement.executeQuery();");
        method.addBodyLine("while(resultSet.next()){");
        method.addBodyLine("struct=new "+returyType+"(");

        for(int i=0;i<structEntity.getList().size();i++){
            ColumnEntity columnEntity=structEntity.getList().get(i);
            if(i<(structEntity.getList().size()-1)){
                method.addBodyLine("resultSet.get"+StringTool.toUpperCaseFirst(columnEntity.getDataType().getSortJavaType())+"(\""+columnEntity.getName()+"\"),");
            }else{
                method.addBodyLine("resultSet.get"+StringTool.toUpperCaseFirst(columnEntity.getDataType().getSortJavaType())+"(\""+columnEntity.getName()+"\")");
            }
        }
        method.addBodyLine(");");
        method.addBodyLine("map.put(struct.get"+StringTool.toUpperCaseFirst(keyColumn.getName())+"(),struct);");
        method.addBodyLine("}");


        method.addBodyLine("return map;");
        method.addBodyLine("} catch (SQLException e) {");
        method.addBodyLine("logger.error(\""+method.getName()+"\",e);");
        method.addBodyLine("throw e;");
        method.addBodyLine("}");
    }


    private void executeUpdate(){

    }


    private void genQuery(){
        method.addBodyLine("try {");
        if(tableEntity.getSplit()>1){
            method.addParameter(new Parameter(new FullyQualifiedJavaType("int"),"splitID"));
            method.addBodyLine("PreparedStatement preparedStatement=connect.preparedStatement("+method.getName()+"[splitID%"+method.getName()+".length]);");
        }else{
            method.addBodyLine("PreparedStatement preparedStatement=connect.preparedStatement("+method.getName()+");");
        }
        fillParam();

        String returyType=method.getReturnType().getShortName();
        method.addBodyLine("ResultSet resultSet=preparedStatement.executeQuery();");
        method.addBodyLine("if(resultSet.next()){");
        method.addBodyLine(returyType+" struct=new "+returyType+"(");
        StructEntity structEntity=structType.get(returyType);
        for(int i=0;i<structEntity.getList().size();i++){
            ColumnEntity columnEntity=structEntity.getList().get(i);
            if(i<(structEntity.getList().size()-1)){
                method.addBodyLine("resultSet.get"+StringTool.toUpperCaseFirst(columnEntity.getDataType().getSortJavaType())+"(\""+columnEntity.getName()+"\"),");
            }else{
                method.addBodyLine("resultSet.get"+StringTool.toUpperCaseFirst(columnEntity.getDataType().getSortJavaType())+"(\""+columnEntity.getName()+"\")");
            }
        }
        method.addBodyLine(");");
        method.addBodyLine("return struct;");
        method.addBodyLine("}");



        method.addBodyLine("} catch (SQLException e) {");
        method.addBodyLine("logger.error(\""+method.getName()+"\",e);");
        method.addBodyLine("throw e;");
        method.addBodyLine("}");
        if(method.getReturnType()!=null){
            method.addBodyLine("return null;");
        }



        AccessMethod listMethod=copy();
        AccessMethod mapMethod=copy();
        listMethod.getMethod().setName(method.getName()+"List");
        listMethod.genListQuery();
        topLevelClass.addMethod(listMethod.getMethod());


        mapMethod.getMethod().setName(method.getName()+"Map");
        mapMethod.genMapQuery();
        topLevelClass.addMethod(mapMethod.getMethod());

    }

    public void genBody(TableEntity tableEntity,TopLevelClass topLevelClass){
        this.topLevelClass=topLevelClass;
        this.tableEntity=tableEntity;
        if(sql.startsWith("SELECT")||sql.startsWith("select")){
            genQuery();
        }else{
            executeUpdate();
        }

    }

    public Method getMethod() {
        return method;
    }

    public String getSql() {
        return sql;
    }
}
