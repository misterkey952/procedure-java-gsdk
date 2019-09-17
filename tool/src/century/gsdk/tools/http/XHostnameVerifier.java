package century.gsdk.tools.http;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class XHostnameVerifier implements HostnameVerifier{



	public boolean verify(String hostname, SSLSession session) {
		return true;//不校验 全返回true
	}


}
