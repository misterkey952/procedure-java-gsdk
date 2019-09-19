package century.gsdk.tools.http;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
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
public class XHttpTool {


	private static MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager(); 
	private static HttpClient httpClient = new HttpClient(manager);
	static {
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setConnectionTimeout(10000);
		params.setSoTimeout(10000);
		params.setMaxTotalConnections(10000000);
		manager.setParams(params);
		
	}
	
	
	
	
	public static void setHost(String url, int port, String protol) {
		httpClient.getHostConfiguration().setHost(url, port, protol);
	}

	public static NameValuePair[] parseParamFromMap(Map<String,String> param){
		
		NameValuePair[] nameValuePairs = new NameValuePair[param.size()];
		int i = 0;
		for (String key : param.keySet()) {
			NameValuePair nValuePair = new NameValuePair(key, param.get(key));
			nameValuePairs[i] = nValuePair;
			i++;
		}
		
		return nameValuePairs;
	}

	public static String doPost(String uri,Map<String,String> param) {
		PostMethod postMethod = new PostMethod(uri);
		postMethod.setRequestBody(parseParamFromMap(param));
		try {
			postMethod.getParams().setContentCharset("UTF-8");
			httpClient.executeMethod(postMethod);
			String resString = postMethod.getResponseBodyAsString();
			return resString;
		} catch (HttpException e) {
			System.out.println("HttpException e");
			return e.getMessage();
		} catch (IOException e) {
			System.out.println("IOException e");
			return e.getMessage();
		}finally{
			postMethod.releaseConnection();
		}

	}


	public static String postHttps(String uri,String data) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(uri);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			((HttpsURLConnection) conn).setHostnameVerifier(new XHostnameVerifier());
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(data);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}


	public static String post(String uri, String data) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(uri);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(data);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}

	public static String doGetWeb(String uri) {
		GetMethod postMethod = new GetMethod(uri);
		String tmpRes = "";
		InputStream inputStream = null;
		StringBuffer stb=new StringBuffer();
		try {
			httpClient.executeMethod(postMethod);
			inputStream = postMethod.getResponseBodyAsStream();
			BufferedReader readber = new BufferedReader(new InputStreamReader(inputStream));
			
			while((tmpRes=readber.readLine())!=null){
				stb.append(tmpRes);
			}
			readber.close();
			return stb.toString();
		} catch (Exception e) {
			return null;
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			postMethod.releaseConnection();
		}
	}

	public static String doGet(String uri) {
		GetMethod postMethod = new GetMethod(uri);
		String tmpRes = "";
		InputStream inputStream = null;
		StringBuffer stb=new StringBuffer();
		try {
			httpClient.executeMethod(postMethod);
			inputStream = postMethod.getResponseBodyAsStream();
			BufferedReader readber = new BufferedReader(new InputStreamReader(inputStream));
			
			while((tmpRes=readber.readLine())!=null){
				stb.append(tmpRes);
			}
			readber.close();
			return stb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			postMethod.releaseConnection();
		}
		return stb.toString();
	}

	public static String doGet(String uri,String charter) {
		GetMethod postMethod = new GetMethod(uri);
		String tmpRes = "";
		InputStream inputStream = null;
		StringBuffer stb=new StringBuffer();
		try {
			httpClient.executeMethod(postMethod);
			inputStream = postMethod.getResponseBodyAsStream();
			BufferedReader readber = new BufferedReader(new InputStreamReader(inputStream,charter));
			
			while((tmpRes=readber.readLine())!=null){
				stb.append(tmpRes);
			}
			readber.close();
			return stb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			postMethod.releaseConnection();
		}
		return stb.toString();
	}

}
