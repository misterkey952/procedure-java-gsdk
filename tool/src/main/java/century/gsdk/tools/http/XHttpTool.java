package century.gsdk.tools.http;
import java.util.Map;
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
	private static final IHttp http=new XHttp();
	public static String post(String uri, Map<String, String> param){
		return http.post(uri,param);
	}


	public static String httpsPost(String uri, String data){
		return http.httpsPost(uri,data);
	}

	public static String post(String uri, String data){
		return http.post(uri,data);
	}


	public static String get(String uri){
		return http.get(uri);
	}

	public static String get(String uri, String charter){
		return http.get(uri,charter);
	}
}
