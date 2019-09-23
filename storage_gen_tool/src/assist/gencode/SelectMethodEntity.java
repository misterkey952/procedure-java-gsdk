package assist.gencode;

import century.gsdk.storage.core.DataType;
import century.gsdk.tools.str.StringTool;
import century.gsdk.tools.xml.XMLTool;
import org.dom4j.Element;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

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
public class SelectMethodEntity extends AbstractAccessMethod{
    private String gen;

    public SelectMethodEntity(AccessEntity accessEntity, Element element) {
        super(accessEntity, element);
    }

    @Override
    void init() {
        gen=XMLTool.getStrAttrValue(element,"gen");
    }

    private void filterGen(String genType){
        if("one".equals(genType)){
            genOne();
        }else if("list".equals(genType)){
            genList();
        }else{
            if(genType.contains("[")){
                String key=genType.substring(genType.indexOf("[")+1,genType.length()-1);
                genMap(key);
            }else{
                genMap(null);
            }
        }
    }


    @Override
    void autoGen() {
        if(gen==null||gen.equals(StringTool.SPACE)){
            genOne();
            genList();
            genMap(null);
            return;
        }
        if(gen.contains("|")){
            String[] genList=gen.split("\\|");
            for(String genType:genList){
                filterGen(genType);
            }
        }else{
            filterGen(gen);
        }
    }

    private void genMap(String key){
        StructEntity structEntity=accessEntity.getStructEntity(returnType);
        if(structEntity==null){
            return;
        }


        createMethod();
        method.setName(name+"Map");

        String fullTypeName;
        FullyQualifiedJavaType javaReturnType;
        fullTypeName=structEntity.getName();

        String keyType;
        if(key==null){
            keyType=structEntity.getFieldEntityList().get(0).getType().getJavaType();
            key=structEntity.getFieldEntityList().get(0).getName();
        }else{
            keyType=structEntity.getFieldEntity(key).getType().getJavaType();
        }

        javaReturnType=new FullyQualifiedJavaType("Map<"+keyType+","+fullTypeName+">");
        method.setReturnType(javaReturnType);

        method.addBodyLine("ResultSet resultSet=preparedStatement.executeQuery();");
        method.addBodyLine("Map<"+keyType+","+fullTypeName+"> map=new HashMap<>();");
        method.addBodyLine("while(resultSet.next()){");
        method.addBodyLine(fullTypeName+" struct=new "+fullTypeName+"(");
        for(int i=0;i<structEntity.getFieldEntityList().size();i++){
            StructFieldEntity fieldEntity=structEntity.getFieldEntityList().get(i);
            if(i<(structEntity.getFieldEntityList().size()-1)){
                method.addBodyLine("resultSet.get"+StringTool.toUpperCaseFirst(fieldEntity.getType().getSortJavaType())+"(\""+fieldEntity.getName()+"\"),");
            }else{
                method.addBodyLine("resultSet.get"+StringTool.toUpperCaseFirst(fieldEntity.getType().getSortJavaType())+"(\""+fieldEntity.getName()+"\")");
            }
        }
        method.addBodyLine(");");
        method.addBodyLine("map.put(struct.get"+StringTool.toUpperCaseFirst(key)+"(),struct);");
        method.addBodyLine("}");
        method.addBodyLine("return map;");
        appendMethodEnd();
    }

    private void genList(){
        createMethod();
        method.setName(name+"List");
        StructEntity structEntity=accessEntity.getStructEntity(returnType);
        String sortTypeName;
        String fullTypeName;
        FullyQualifiedJavaType javaReturnType;
        if(structEntity==null){
            DataType dataType=DataType.getDataType(returnType);
            sortTypeName=dataType.getSortJavaType();
            fullTypeName=dataType.getListOrMapJavaType();
        }else{
            sortTypeName=structEntity.getName();
            fullTypeName=structEntity.getName();
        }
        javaReturnType=new FullyQualifiedJavaType("List<"+fullTypeName+">");
        method.setReturnType(javaReturnType);

        method.addBodyLine("ResultSet resultSet=preparedStatement.executeQuery();");
        method.addBodyLine("List<"+fullTypeName+"> list=new ArrayList<>();");
        method.addBodyLine("while(resultSet.next()){");
        if(structEntity!=null){
            method.addBodyLine(fullTypeName+" struct=new "+fullTypeName+"(");
            for(int i=0;i<structEntity.getFieldEntityList().size();i++){
                StructFieldEntity fieldEntity=structEntity.getFieldEntityList().get(i);
                if(i<(structEntity.getFieldEntityList().size()-1)){
                    method.addBodyLine("resultSet.get"+StringTool.toUpperCaseFirst(fieldEntity.getType().getSortJavaType())+"(\""+fieldEntity.getName()+"\"),");
                }else{
                    method.addBodyLine("resultSet.get"+StringTool.toUpperCaseFirst(fieldEntity.getType().getSortJavaType())+"(\""+fieldEntity.getName()+"\")");
                }
            }
            method.addBodyLine(");");
            method.addBodyLine("list.add(struct);");
            method.addBodyLine("}");
            method.addBodyLine("return list;");
        }else{
            method.addBodyLine(fullTypeName+" value=resultSet.get"+StringTool.toUpperCaseFirst(sortTypeName)+"(1);");
            method.addBodyLine("list.add(value);");
            method.addBodyLine("}");
            method.addBodyLine("return list;");
        }
        appendMethodEnd();
    }

    private void genOne(){
        createMethod();
        method.setName(name+"One");
        StructEntity structEntity=accessEntity.getStructEntity(returnType);
        FullyQualifiedJavaType javaReturnType=null;
        if(structEntity==null){
            javaReturnType=new FullyQualifiedJavaType(DataType.getDataType(returnType).getJavaType());
        }else{
            javaReturnType=new FullyQualifiedJavaType(structEntity.getName());
        }
        method.setReturnType(javaReturnType);

        method.addBodyLine("ResultSet resultSet=preparedStatement.executeQuery();");
        method.addBodyLine("if(resultSet.next()){");
        if(structEntity!=null){
            method.addBodyLine("return new "+javaReturnType.getFullyQualifiedName()+"(");
            for(int i=0;i<structEntity.getFieldEntityList().size();i++){
                StructFieldEntity fieldEntity=structEntity.getFieldEntityList().get(i);
                if(i<(structEntity.getFieldEntityList().size()-1)){
                    method.addBodyLine("resultSet.get"+StringTool.toUpperCaseFirst(fieldEntity.getType().getSortJavaType())+"(\""+fieldEntity.getName()+"\"),");
                }else{
                    method.addBodyLine("resultSet.get"+StringTool.toUpperCaseFirst(fieldEntity.getType().getSortJavaType())+"(\""+fieldEntity.getName()+"\")");
                }
            }
            method.addBodyLine(");");
            method.addBodyLine("}");
            method.addBodyLine("return null;");
        }else{
            method.addBodyLine("return resultSet.get"+StringTool.toUpperCaseFirst(javaReturnType.getShortName())+"(1);");
            method.addBodyLine("}");
            method.addBodyLine("return "+DataType.getDataType(returnType).getDefaultValue()+";");
        }
        appendMethodEnd();
    }

}
