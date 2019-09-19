package century.gsdk.tools;

import java.io.FileInputStream;





import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Properties;
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
public class XUtil {
	public static void copySameFieldValue(Object tag,Object dataObj){
		Class<?> dataClass=dataObj.getClass();
		Field[] tagFields=tag.getClass().getDeclaredFields();
		for(Field field:tagFields){
			try {
				Field dataField=dataClass.getDeclaredField(field.getName());
				
				if(dataField==null){
					continue;
				}
				dataField.setAccessible(true);
				field.setAccessible(true);
				field.set(tag,dataField.get(dataObj));
			} catch (IllegalAccessException e) {
				continue;
			} catch (NoSuchFieldException e) {
				continue;
			} catch (SecurityException e) {
				continue;
			}catch(Exception e){
				continue;
			}
		}
}

	public static <T>T adapterProperties(String path,Class<T> t){
		Properties p=new Properties();
		InputStream is=null;
		try {
			T tobj=t.newInstance();
			is=new FileInputStream(path);
			p.load(is);
			Field[] fields=t.getDeclaredFields();
			for(Field f:fields){
				f.setAccessible(true);
				if(Byte.TYPE==f.getType()){
					f.setByte(tobj,Byte.parseByte(p.getProperty(f.getName())));
				}else if(Boolean.TYPE==f.getType()){
					if("true".equals(p.getProperty(f.getName()))){
						f.setBoolean(tobj,true);
					}else{
						f.setBoolean(tobj,false);
					}
					
				}else if(Short.TYPE==f.getType()){
					f.setShort(tobj,Short.parseShort(p.getProperty(f.getName())));
				}else if(Integer.TYPE==f.getType()){
					f.setInt(tobj,Integer.parseInt(p.getProperty(f.getName())));
				}else if(Long.TYPE==f.getType()){
					f.setLong(tobj,Long.parseLong(p.getProperty(f.getName())));
				}else if(Float.TYPE==f.getType()){
					f.setFloat(tobj,Float.parseFloat(p.getProperty(f.getName())));
				}else if(Double.TYPE==f.getType()){
					f.setDouble(tobj,Double.parseDouble(p.getProperty(f.getName())));
				}else if(String.class==f.getType()){
					f.set(tobj,p.getProperty(f.getName()));
				}
			
			}
			
			
			return tobj;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	
	}

	public static String getProcessorId(){
		String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		String processID = processName.substring(0, processName.indexOf('@'));
		return processID;
	}

	public static void registCloseListener(Runnable exeThread) {
		Runtime.getRuntime().addShutdownHook(new Thread(exeThread));
	}
	

	public static String getStackTrace(Exception aThrowable) {
		
		if(aThrowable==null){
			return "";
		}
		    StringWriter stringWriter = new StringWriter();
		    //必须将StringWriter封装成PrintWriter对象，
		    //以满足printStackTrace的要求
		    PrintWriter printWriter = new PrintWriter(stringWriter);
		    //获取堆栈信息
		    aThrowable.printStackTrace(printWriter);
		    //转换成String，并返回该String
		    StringBuffer error = stringWriter.getBuffer();
		    return error.toString();
	}
	


	
	public static boolean ping(String ipAddress){
		try{
			boolean status = InetAddress.getByName(ipAddress).isReachable(1000);     // 当返回值是true时，说明host是可用的，false则不可。
			return status;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
    }
	
	public static boolean telnetPort(String ipAddress,int port){
		Socket socket=null;
		boolean isok=false;
		try{
			socket=new Socket();
			SocketAddress address = new InetSocketAddress(ipAddress, port);
			socket.connect(address, 50);
			isok= socket.isConnected();
		}catch(Exception e){
			isok=false;
		}finally {
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return isok;
    }
}
