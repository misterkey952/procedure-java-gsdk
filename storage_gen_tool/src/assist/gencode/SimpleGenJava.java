package assist.gencode;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.*;

import java.io.*;

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
public class SimpleGenJava {
    private String target;
    private String nameSpace;
    private String className;
    private TopLevelClass topLevelClass;
    private GeneratedJavaFile generatedJavaFile;
    private InitializationBlock initializationBlock;
    public SimpleGenJava(String namespace,String target,String className) {
        this.target=target;
        this.nameSpace=namespace;
        this.className=className;
        topLevelClass=new TopLevelClass(namespace+"."+className);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        generatedJavaFile=new GeneratedJavaFile(topLevelClass,
                "",
                "UTF-8",new DefaultJavaFormatter());
    }

    public void addMethod(Method method){
        topLevelClass.addMethod(method);
    }

    public void addField(Field field){
        topLevelClass.addField(field);
    }

    public void importType(String importType){
        topLevelClass.addImportedType(importType);
    }

    public void setFinal(boolean value){
        topLevelClass.setFinal(value);
    }



    public void addStaticBlockLine(String str){
        if(initializationBlock==null){
            initializationBlock=new InitializationBlock();
            initializationBlock.setStatic(true);
            topLevelClass.addInitializationBlock(initializationBlock);
        }

        initializationBlock.addBodyLine(str);
    }


    public void write(){
        String subpath=nameSpace.replaceAll("\\.","/");
        File dir=new File(target+File.separator+subpath);
        File file=new File(target+File.separator+subpath+File.separator+className+".java");
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
            bufferedWriter.write(generatedJavaFile.toString());
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
