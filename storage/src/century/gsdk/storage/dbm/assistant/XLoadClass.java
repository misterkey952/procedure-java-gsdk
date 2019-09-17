package century.gsdk.storage.dbm.assistant;

import java.io.File;



import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;




public class XLoadClass {
	
	

	

	public static void loadClass(Map<String,ClassData> classMap,String packages){
		
		
		List<Class<?>> classList=getClassFromPackage(packages);
		for(Class<?> classo:classList){
			DBA xDb=classo.getAnnotation(DBA.class);
			ClassData classData=new ClassData();
			if(xDb!=null){
				classData.setTableName(xDb.Table());
			}else{
				classData.setTableName(classo.getSimpleName());
			}
			classData.setClassStr(classo.getName());
			
			getFieldData(classo.getDeclaredFields(),classData);
			genertSql(classData);
			classMap.put(classo.getName(),classData);
			
			
		}
		
		
	}

	private static void genertSql(ClassData classData){
		StringBuffer stb=new StringBuffer();
		stb.append(XDBConst.INSERT_SQL+" "+classData.getTableName()+" "+XDBConst.VALUE+"(");
		for(int i=0;i<classData.getKeyList().size();i++){
			if(i!=classData.getKeyList().size()-1){
				stb.append(XDBConst.PARAM+",");
			}else{
				stb.append(XDBConst.PARAM+",");
			}
			
		}
		
		for(int i=0;i<classData.getFieldDatas().size();i++){
			if(i!=classData.getFieldDatas().size()-1){
				stb.append(XDBConst.PARAM+",");
			}else{
				stb.append(XDBConst.PARAM);
			}
		}
		stb.append(")");
		classData.setInsertSql(stb.toString());
		
		
		stb=new StringBuffer();
		stb.append(XDBConst.UPD_SQL+" "+classData.getTableName()+" "+XDBConst.SET+" ");
		for(int i=0;i<classData.getFieldDatas().size();i++){
			if(i!=classData.getFieldDatas().size()-1){
				stb.append(classData.getFieldDatas().get(i).getDbField()+"="+XDBConst.PARAM+",");
			}else{
				stb.append(classData.getFieldDatas().get(i).getDbField()+"="+XDBConst.PARAM);
			}
		}
		stb.append(" "+XDBConst.COND_WHERE);
		for(int i=0;i<classData.getKeyList().size();i++){
			if(i!=classData.getKeyList().size()-1){
				stb.append(" "+classData.getKeyList().get(i).getDbField()+"="+XDBConst.PARAM+" "+XDBConst.COND_AND);
			}else{
				stb.append(" "+classData.getKeyList().get(i).getDbField()+"="+XDBConst.PARAM);
			}
		}
		
		classData.setUpdateSql(stb.toString());
		stb=new StringBuffer();
		stb.append(XDBConst.DEL_SQL+" "+XDBConst.FROM_SQL+" "+classData.getTableName()+" "+XDBConst.COND_WHERE);
		
		for(int i=0;i<classData.getKeyList().size();i++){
			if(i!=classData.getKeyList().size()-1){
				stb.append(" "+classData.getKeyList().get(i).getDbField()+"="+XDBConst.PARAM+" "+XDBConst.COND_AND);
			}else{
				stb.append(" "+classData.getKeyList().get(i).getDbField()+"="+XDBConst.PARAM);
			}
		}
		
		classData.setDeleteSql(stb.toString());
		
		
	}
	
	
	private static void getFieldData(Field[] fields,ClassData classData){
		List<XFieldData> fieldDatas=new ArrayList<XFieldData>();
		List<XFieldData> keyDatas=new ArrayList<XFieldData>();
		
		
		for(Field field:fields){
			XFieldData xFieldData=new XFieldData();
			DBA xDb=field.getAnnotation(DBA.class);
			xFieldData.setClassField(field.getName());
			if(xDb!=null){
				if("".equals(xDb.Field())){
					xFieldData.setDbField(field.getName());
				}else{
					xFieldData.setDbField(xDb.Field());
				}
				
				xFieldData.setLength(xDb.Length());
				if(xDb.Key()){
					keyDatas.add(xFieldData);
				}else{
					fieldDatas.add(xFieldData);
				}
				
				xFieldData.setAuto_increment(xDb.Increment());
				
			}else{
				xFieldData.setDbField(field.getName());
				fieldDatas.add(xFieldData);
			}
			
			
		}
		List<Integer> keyList=new ArrayList<Integer>();
		
		for(int index:keyList){
			fieldDatas.remove(index);
		}
		
		
		classData.setFieldDatas(fieldDatas);
		classData.setKeyList(keyDatas);
		
	}

	public static List<Class<?>> getClassFromPackage(String packages){
		List<String> classNameList=getClassName(packages,true);
		List<Class<?>> classList=new ArrayList<Class<?>>();
		
		for(String className:classNameList){
			try {
				Class<?> class1=Class.forName(className);
				classList.add(class1);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		
		return classList;
		
	}
	
	
	

    public static List<String> getClassName(String packageName,boolean childPackage) {  
        List<String> fileNames = new ArrayList<String>();
        List<String> tempFile=null;
        URLClassLoader loader = (URLClassLoader) Thread.currentThread().getContextClassLoader();  
        String packagePath = packageName.replace(".", "/");  
        URL[] urlList = loader.getURLs();//loader.getResource(packagePath);  
        
        for(int i=0;i<urlList.length;i++){
        	URL url=urlList[i];
        	 if (url != null) {
                String pathString=url.getPath();
                if(pathString.lastIndexOf(".jar")>=0){
                	String jarPath = url.getPath() + "!/" + packagePath;  
                	tempFile = getClassNameByJar(jarPath, childPackage);  
                }else{
                	tempFile = getClassNameByFile(url.getPath()+packagePath, null, childPackage,packageName);
                }

             }
        	 
        	 if(tempFile.size()>0){
        		 fileNames.addAll(tempFile);
        	 }
        	
        	 
        }
        
         
        return fileNames;  
    }
    
    
    
    

    private static List<String> getClassNameByFile(String filePath, List<String> className, boolean childPackage,String packages) {  
        List<String> myClassName = new ArrayList<String>();  
        File file = new File(filePath);  
        File[] childFiles = file.listFiles();
        
        if(childFiles!=null){
            for (File childFile : childFiles) {
                if (childFile.isDirectory()) {  
                    if (childPackage) {  
                        myClassName.addAll(getClassNameByFile(childFile.getPath(), myClassName, childPackage,packages));  
                    }  
                } else {  
                    String childFilePath = childFile.getPath();  
                    if (childFilePath.endsWith(".class")) {
                        childFilePath = childFilePath.replace(File.separator, ".");
                        childFilePath=childFilePath.substring(childFilePath.indexOf(packages)).replace(".class","");
                        myClassName.add(childFilePath);
                    }  
                }  
            }
        }
        

  
        return myClassName;  
    }
    

    private static List<String> getClassNameByJar(String jarPath, boolean childPackage) {  
        List<String> myClassName = new ArrayList<String>();  
        String[] jarInfo = jarPath.split("!");  
        String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));  
        String packagePath = jarInfo[1].substring(1);
        JarFile jarFile = null;  
        try {  
        	jarFile = new JarFile(jarFilePath);  
            Enumeration<JarEntry> entrys = jarFile.entries();  
            while (entrys.hasMoreElements()) {  
                JarEntry jarEntry = entrys.nextElement();  
                String entryName = jarEntry.getName();  
                if (entryName.endsWith(".class")) {  
                    if (childPackage) {  
                        if (entryName.startsWith(packagePath)) {  
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));  
                            myClassName.add(entryName);  
                        }  
                    } else {  
                        int index = entryName.lastIndexOf("/");  
                        String myPackagePath;  
                        if (index != -1) {  
                            myPackagePath = entryName.substring(0, index);  
                        } else {  
                            myPackagePath = entryName;  
                        }  
                        if (myPackagePath.equals(packagePath)) {  
                            entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));  
                            myClassName.add(entryName);  
                        }  
                    }  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
        	if(jarFile!=null){
        		try {
					jarFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return myClassName;  
    }  
    
}
