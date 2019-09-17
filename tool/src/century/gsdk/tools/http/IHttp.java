package century.gsdk.tools.http;

import java.util.Map;


public interface IHttp {
	
	
	public void setTimeOut(int timeOut);
	
	public String doPost(String uri, Map<String, String> param);

	public String postHttps(String uri, String data);

	public String post(String uri, String data);


	public String doGet(String uri);

	public String doGet(String uri, String charter);
	
	public void reset();
	
}
