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

import century.gsdk.tools.ToolLogger;
import org.apache.commons.httpclient.HttpClient;
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
public class XHttp implements IHttp{
	private MultiThreadedHttpConnectionManager manager = new MultiThreadedHttpConnectionManager(); 
	private HttpClient httpClient = new HttpClient(manager);
	private static final int DEFAULT_TIMEOUT=300;
	private static final int DEFAULT_CONNECTS=100000;
	public XHttp(){
		manager = new MultiThreadedHttpConnectionManager();
		httpClient = new HttpClient(manager);

		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setConnectionTimeout(DEFAULT_TIMEOUT);
		params.setSoTimeout(DEFAULT_TIMEOUT);
		params.setMaxTotalConnections(DEFAULT_CONNECTS);
		manager.setParams(params);
	}


	private NameValuePair[] parseParamFromMap(Map<String,String> param){

		NameValuePair[] nameValuePairs = new NameValuePair[param.size()];
		int i = 0;
		for (String key : param.keySet()) {
			NameValuePair nValuePair = new NameValuePair(key, param.get(key));
			nameValuePairs[i] = nValuePair;
			i++;
		}

		return nameValuePairs;
	}

	@Override
	public String post(String uri, Map<String, String> param) {

		PostMethod postMethod = new PostMethod(uri);
		postMethod.setRequestBody(parseParamFromMap(param));
		try {
			postMethod.getParams().setContentCharset("UTF-8");
			httpClient.executeMethod(postMethod);
			String resString = postMethod.getResponseBodyAsString();
			return resString;
		} catch (Exception e) {
			ToolLogger.HTTP.error("Http.doPost err",e);
			return e.getMessage();
		} finally{
			postMethod.releaseConnection();
		}
	}

	@Override
	public String httpsPost(String uri, String data) {
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
			ToolLogger.HTTP.error("Http.postHttps err",e);
		}
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ToolLogger.HTTP.error("Http.postHttps close err",ex);
			}
		}
		return result.toString();
	}

	@Override
	public String post(String uri, String data) {

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
			ToolLogger.HTTP.error("Http.post err",e);
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
				ToolLogger.HTTP.error("Http.post close err",ex);
			}
		}
		return result.toString();
	
	}

	@Override
	public String get(String uri) {
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
			ToolLogger.HTTP.error("Http.doGet err",e);
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					ToolLogger.HTTP.error("Http.doGet close err",e);
				}
			}
			postMethod.releaseConnection();
		}
		return stb.toString();
	}

	@Override
	public String get(String uri, String charter) {
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
			ToolLogger.HTTP.error("Http.doGet err charter",e);
			return stb.toString();
		} finally {
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {

					ToolLogger.HTTP.error("Http.doGet close err charter",e);
				}
			}
			postMethod.releaseConnection();
		}
	}

	public void setTimeOut(int timeOut) {
		manager.getParams().setConnectionTimeout(timeOut);
		manager.getParams().setSoTimeout(timeOut);
	}

	public void reset() {
		manager.getParams().setConnectionTimeout(DEFAULT_TIMEOUT);
		manager.getParams().setSoTimeout(DEFAULT_TIMEOUT);
	}

}
