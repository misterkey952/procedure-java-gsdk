package century.gsdk.storage.dbm.assistant;

import java.lang.reflect.Field;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


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


public class XDBSqlUtil {


	public static void appendCondition(StringBuffer stbm,Field field,Object o,String dbField){
		

		if(field.getType()==Byte.TYPE){
			try {
				stbm.append(dbField+"="+field.getByte(o)+XDBConst.COND_AND);
			} catch (Exception e) {
				stbm.append(dbField+"="+XDBConst.ERR_N_V+XDBConst.COND_AND);
			} 
		}else if(field.getType()==Long.TYPE){

			try {
				stbm.append(dbField+"="+field.getLong(o)+XDBConst.COND_AND);
			} catch (Exception e) {
				stbm.append(dbField+"="+XDBConst.ERR_N_V+XDBConst.COND_AND);
			} 
		}else if(field.getType()==Integer.TYPE){

			try {
				stbm.append(dbField+"="+field.getInt(o)+XDBConst.COND_AND);
			} catch (Exception e) {
				stbm.append(dbField+"="+XDBConst.ERR_N_V+XDBConst.COND_AND);
			}
		}else if(field.getType()==Short.TYPE){

			try {
				stbm.append(dbField+"="+field.getShort(o)+XDBConst.COND_AND);
			} catch (Exception e) {
				stbm.append(dbField+"="+XDBConst.ERR_N_V+XDBConst.COND_AND);
			} 
		}else if(field.getType()==Float.TYPE){

			try {
				stbm.append(dbField+"="+field.getFloat(o)+XDBConst.COND_AND);
			} catch (Exception e) {
				stbm.append(dbField+"="+XDBConst.ERR_N_V+XDBConst.COND_AND);
			}
		}else if(field.getType()==Double.TYPE){

			try {
				stbm.append(dbField+"="+field.getDouble(o)+XDBConst.COND_AND);
			} catch (Exception e) {
				stbm.append(dbField+"="+XDBConst.ERR_N_V+XDBConst.COND_AND);
			} 
		}else if(String.class.isAssignableFrom(field.getType())){
			try {
				stbm.append(dbField+"='"+field.get(o).toString()+"'"+XDBConst.COND_AND);
			} catch (Exception e) {
				stbm.append(dbField+"='"+XDBConst.ERR_V+"'"+XDBConst.COND_AND);
			}
		}else if(Timestamp.class.isAssignableFrom(field.getType())){
			try {
				stbm.append(dbField+"='"+field.get(o).toString()+"'"+XDBConst.COND_AND);
			} catch (Exception e) {
				stbm.append(dbField+"='"+XDBConst.ERR_TIME+"'"+XDBConst.COND_AND);
			}
		}
	
		
		
	}
	
	
	private static void setValueOfObject(Field field,Object o,ResultSet rs,String dbFileName){
		try {
			field.setAccessible(true);
			Class<?> fieldType=field.getType();
			if(fieldType == Byte.TYPE){
				field.setByte(o,rs.getByte(dbFileName));
			}else if(fieldType == Long.TYPE){
				field.setLong(o,rs.getLong(dbFileName));
			}else if(fieldType == Integer.TYPE){
				field.setInt(o,rs.getInt(dbFileName));
			}else if(fieldType == Short.TYPE){
				field.setShort(o,rs.getShort(dbFileName));
			}else if(fieldType == Float.TYPE){
				field.setFloat(o,rs.getFloat(dbFileName));
			}else if(fieldType == Double.TYPE){
				field.setDouble(o,rs.getDouble(dbFileName));
			}else if(fieldType == String.class){
				field.set(o,rs.getString(dbFileName));
			}else if(fieldType == Boolean.TYPE){
				field.setBoolean(o,rs.getBoolean(dbFileName));
			}else if(fieldType==Timestamp.class){
				field.set(o,rs.getTimestamp(dbFileName));
			}
			
		} catch (IllegalArgumentException e) {
				e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void setValueOfObject(Field field,Object o,ResultSet rs){
		setValueOfObject(field, o, rs, field.getName());
	}
	
	

	public static void setValueOfObject(XFieldData fieldData,Object o,ResultSet rs){
			
			try {
				if(rs.getObject(fieldData.getDbField())==null){
					return ;
				}
				setValueOfObject(o.getClass().getDeclaredField(fieldData.getClassField()),o,rs,fieldData.getDbField());
				
			} catch (Exception e) {
				e.printStackTrace();
			}

	}

	public static String createTable(ClassData classData){
		StringBuffer stb=new StringBuffer();
		StringBuffer primary=new StringBuffer();
		stb.append(XDBConst.CREATE_TABLE+" "+classData.getTableName()+" (");
		primary.append(XDBConst.SQL_PRIMARY+" (");
		Class<?> class1=null;
		try {
			class1 = Class.forName(classData.getClassStr());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for(XFieldData fieldData:classData.getKeyList()){
			try {
				Field field=class1.getDeclaredField(fieldData.getClassField());
				stb.append(fieldData.getDbField()+" "+getSqlType(field,fieldData)+" "+XDBConst.SQL_NOTNULL+" "+(fieldData.isAuto_increment()?"AUTO_INCREMENT":"")+",");
				primary.append(fieldData.getDbField()+",");
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
				e.printStackTrace();
			}
		}
		
		primary.deleteCharAt(primary.length()-1);
		for(XFieldData fieldData:classData.getFieldDatas()){
			try {
				Field field=class1.getDeclaredField(fieldData.getClassField());
				stb.append(fieldData.getDbField()+" "+getSqlType(field,fieldData)+",");
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
				e.printStackTrace();
			}
		}
		
		stb.append(primary.toString()+")) "+XDBConst.SQL_CREATE_HOUZHUI);
		return stb.toString();
		
	}
	
	/**
	 * 改表 加字段
	 * @param classData
	 */
	public static String alterTableAddColumn(ClassData classData,Field field,XFieldData fieldData){
		StringBuffer stBuffer=new StringBuffer();
		stBuffer.append(XDBConst.ALTER_TABLE+" "+classData.getTableName()+" "+XDBConst.SQL_ADD+" "+fieldData.getDbField()+" "+getSqlType(field, fieldData));
		return stBuffer.toString();
	}
	
	
	
	public static String getSqlType(Field field,XFieldData fieldData){
		try{
			Class<?> fieldType=field.getType();
			if(fieldType == Byte.TYPE){
				if(fieldData.getLength()>0){
					return "TINYINT("+fieldData.getLength()+")";
				}else{
					return "TINYINT(4)";
				}
			}else if(fieldType == Long.TYPE){
				if(fieldData.getLength()>0){
					return "BIGINT("+fieldData.getLength()+")";
				}else{
					return "BIGINT(20)";
				}
			}else if(fieldType == Integer.TYPE){
				if(fieldData.getLength()>0){
					return "INT("+fieldData.getLength()+")";
				}else{
					return "INT(11)";
				}
				
			}else if(fieldType == Short.TYPE){
				if(fieldData.getLength()>0){
					return "SMALLINT("+fieldData.getLength()+")";
				}else{
					return "SMALLINT(6)";
				}
			}else if(fieldType == Float.TYPE){
				if(fieldData.getLength()>0){
					return "FLOAT("+fieldData.getLength()+")";
				}else{
					return "FLOAT";
				}
			}else if(fieldType == Double.TYPE){
				if(fieldData.getLength()>0){
					return "DOUBLE("+fieldData.getLength()+")";
				}else{
					return "DOUBLE";
				}
			}else if(fieldType == String.class){
				if(fieldData.getLength()==0){
					return "VARCHAR(255)";
				}else{
					if(fieldData.getLength()>1024){
						return "TEXT";
					}else{
						return "VARCHAR("+fieldData.getLength()+")";
					}
				}
				
				
			}else if(fieldType==Timestamp.class){
				return "DATETIME";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		
		return "";
	}
	
	
	
	
	
	
	
	public static String[][] getTreeFen(String[] sqlList){
		String[][] temp=new String[2][];
		int centPoint=sqlList.length/2;
		temp[0]=new String[centPoint];
		temp[1]=new String[sqlList.length-centPoint];
		
		for(int i=0;i<temp[0].length;i++){
			temp[0][i]=sqlList[i];
		}
		
		for(int i=0;i<temp[1].length;i++){
			temp[1][i]=sqlList[i+centPoint];
		}
		
		return temp;
	}
	
	
}
