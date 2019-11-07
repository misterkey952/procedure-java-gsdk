package century.gsdk.net.http;

/**
 * Author:Century,Write on 2019/11/7
 * Description:
 */
public interface IHttpHandler {
    LightResponse handler(LightRequest request);
}
