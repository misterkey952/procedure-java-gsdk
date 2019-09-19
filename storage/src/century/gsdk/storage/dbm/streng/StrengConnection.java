package century.gsdk.storage.dbm.streng;

import century.gsdk.storage.dbm.assistant.XDBSqlUtil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
public class StrengConnection {
	private Connection connection;
	private PreparedStatement commonPst=null;
	public StrengConnection(Connection connection,boolean autocommit) throws Exception {
		this.connection = connection;
		this.connection.setAutoCommit(autocommit);
	}

	public Connection getConnection() {
		return connection;
	}
	
	
	public void begin(){
		try {
			connection.setSavepoint();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean commit(){

		try {
			if(commonPst!=null){
				commonPst.executeBatch();
			}
			connection.commit();
			if(commonPst!=null){
				commonPst.clearBatch();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	
	}
	
	public void rollBack(){
		try {
			connection.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void close(){
		if(commonPst!=null){
			try {
				commonPst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	
	public void executeSql(String sql){
		try {
			getCommonPreparedSatement().addBatch(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 查询一条记录的某几个字段
	 * @param sql
	 * @return
	 */
	public Object[] queryRowFields(String sql){
		PreparedStatement queryPst=null;
		ResultSet rSet=null;
		Object[] object=null;
		try {
			queryPst=getCommonPreparedSatement();
			rSet=queryPst.executeQuery(sql);
			if(rSet.next()){
				object=new Object[rSet.getMetaData().getColumnCount()];
				for(int i=0;i<object.length;i++){
					object[i]=rSet.getObject(i+1);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rSet!=null){
				try {
					rSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return object;
	}
	
	/**
	 * 获取多条记录的列值
	 * @param sql
	 * @return
	 */
	public List<Object[]> queryRowsFields(String sql){

		PreparedStatement queryPst=null;
		ResultSet rSet=null;
		Object[] object=null;
		List<Object[]> list=new ArrayList<Object[]>();
		try {
			queryPst=getCommonPreparedSatement();
			rSet=queryPst.executeQuery(sql);
			while(rSet.next()){
				object=new Object[rSet.getMetaData().getColumnCount()];
				for(int i=0;i<object.length;i++){
					object[i]=rSet.getObject(i+1);
				}
				list.add(object);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rSet!=null){
				try {
					rSet.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	
	/**
	 * 获取一个列值
	 * @param sql
	 * @return
	 */
	public Object queryField(String sql){

		PreparedStatement queryPst=null;
		ResultSet rSet=null;
		Object object=null;
		try {
			queryPst=getCommonPreparedSatement();
			rSet=queryPst.executeQuery(sql);
			
			if(rSet.next()){
				object=rSet.getObject(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rSet!=null){
				try {
					rSet.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return object;
	}
	
	public PreparedStatement getCommonPreparedSatement(){
		if(commonPst==null){
			try {
				commonPst=connection.prepareStatement("");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return commonPst;
	}
	
	
	private void groupParam(PreparedStatement pst,Object[] params){
		try {
			for(int i=0;i<params.length;i++){
				int pstIndex=i+1;
				Class<?> fieldType=params[i].getClass();
				if(fieldType== Byte.TYPE||fieldType==Byte.class){
					pst.setByte(pstIndex,(byte) params[i]);
				}else if(fieldType == Long.TYPE||fieldType==Long.class){
					pst.setLong(pstIndex,(long) params[i]);
				}else if(fieldType == Integer.TYPE||fieldType==Integer.class){
					pst.setInt(pstIndex,(int) params[i]);
				}else if(fieldType == Short.TYPE||fieldType==Short.class){
					pst.setShort(pstIndex,(short) params[i]);
				}else if(fieldType == Float.TYPE||fieldType==Float.class){
					pst.setFloat(pstIndex,(float) params[i]);
				}else if(fieldType == Double.TYPE||fieldType==Double.class){
					pst.setDouble(pstIndex,(double) params[i]);
				}else if(fieldType == String.class){
					pst.setString(pstIndex,(String) params[i]);
				}else if(fieldType == Boolean.TYPE||fieldType==Boolean.class){
					pst.setBoolean(pstIndex,(boolean) params[i]);
				}else if(fieldType==Timestamp.class){
					pst.setTimestamp(pstIndex,(Timestamp) params[i]);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}  catch (SQLException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public Object[] queryRowFieldsWithParams(String sql, Object... params) {
		PreparedStatement queryPst=null;
		ResultSet rSet=null;
		Object[] object=null;
		try {
			queryPst=connection.prepareStatement(sql);
			groupParam(queryPst, params);
			rSet=queryPst.executeQuery();
			if(rSet.next()){
				object=new Object[rSet.getMetaData().getColumnCount()];
				for(int i=0;i<object.length;i++){
					object[i]=rSet.getObject(i+1);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rSet!=null){
				try {
					rSet.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(queryPst!=null){
				try {
					queryPst.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return object;
	}

	public List<Object[]> queryRowsFieldsWithParams(String sql, Object... params) {

		PreparedStatement queryPst=null;
		ResultSet rSet=null;
		Object[] object=null;
		List<Object[]> list=new ArrayList<Object[]>();
		try {
			queryPst=connection.prepareStatement(sql);
			groupParam(queryPst, params);
			rSet=queryPst.executeQuery();
			while(rSet.next()){
				object=new Object[rSet.getMetaData().getColumnCount()];
				for(int i=0;i<object.length;i++){
					object[i]=rSet.getObject(i+1);
				}
				
				list.add(object);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rSet!=null){
				try {
					rSet.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(queryPst!=null){
				try {
					queryPst.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			

			
			
		}
		return list;
	}

	public Object queryFieldWithParams(String sql, Object... params) {

		PreparedStatement queryPst=null;
		ResultSet rSet=null;
		Object object=null;
		try {
			queryPst=connection.prepareStatement(sql);
			groupParam(queryPst, params);
			rSet=queryPst.executeQuery();
			
			if(rSet.next()){
				object=rSet.getObject(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rSet!=null){
				try {
					rSet.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(queryPst!=null){
				try {
					queryPst.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			

			
			
		}
		return object;
	}

	public void executeSqlWithParams(String sql, Object... params) {


		PreparedStatement pst=null;
		try {
			pst=connection.prepareStatement(sql);
			groupParam(pst, params);
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
			if(pst!=null){
				try {
					pst.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	}

	public void adapterObjectWithParams(String sql, Object o, Object... params) {
		PreparedStatement queryPst=null;
		ResultSet rSet=null;
		try {
			queryPst=connection.prepareStatement(sql);
			groupParam(queryPst, params);
			rSet=queryPst.executeQuery();
			if(rSet.next()){
				for(Field f:o.getClass().getDeclaredFields()){
					XDBSqlUtil.setValueOfObject(f,o,rSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rSet!=null){
				try {
					rSet.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(queryPst!=null){
				try {
					queryPst.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	
	}

	public void adapterObject(String sql, Object o) {
		PreparedStatement queryPst=getCommonPreparedSatement();
		ResultSet rSet=null;
		try {
			rSet=queryPst.executeQuery(sql);
			if(rSet.next()){
				for(Field f:o.getClass().getDeclaredFields()){
					XDBSqlUtil.setValueOfObject(f,o,rSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rSet!=null){
				try {
					rSet.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public <T> List<T> adapterObjects(String sql, Class<T> tclass) {

		PreparedStatement queryPst=null;
		ResultSet rSet=null;
		List<T> ts=new ArrayList<T>();
		try {
			queryPst=getCommonPreparedSatement();
			rSet=queryPst.executeQuery(sql);
			Field[] fields=tclass.getDeclaredFields();
			while(rSet.next()){
				T object=tclass.newInstance();
				for(Field f:fields){
					XDBSqlUtil.setValueOfObject(f,object,rSet);
				}
				ts.add(object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rSet!=null){
				try {
					rSet.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return ts;
	
	}

	public <T> List<T> adapterObjectsWithParams(String sql, Class<T> tclass, Object... objects) {


		PreparedStatement queryPst=null;
		ResultSet rSet=null;
		List<T> ts=new ArrayList<T>();
		try {
			queryPst=connection.prepareStatement(sql);
			groupParam(queryPst,objects);
			rSet=queryPst.executeQuery();
			Field[] fields=tclass.getDeclaredFields();
			while(rSet.next()){
				T object=tclass.newInstance();
				for(Field f:fields){
					XDBSqlUtil.setValueOfObject(f,object,rSet);
				}
				ts.add(object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(rSet!=null){
				try {
					rSet.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			if(queryPst!=null){
				try {
					queryPst.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return ts;
	}
}
