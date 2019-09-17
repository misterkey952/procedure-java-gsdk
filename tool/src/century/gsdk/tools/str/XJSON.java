package century.gsdk.tools.str;

import com.google.gson.Gson;


public class XJSON {
	private static Gson gson=new Gson();

	public static String toJson(Object o){
		return gson.toJson(o);
	}

	public static <T>T parseJson(String str,Class<T> t){
		return gson.fromJson(str,t);
	}
	
}
