package century.gsdk.net.http;

/**
 * Author:Century,Write on 2019/11/7
 * Description:
 */
public class LightResponse {
    private String character="UTF-8";
    private String res;

    public LightResponse(String character, String res) {
        this.character = character;
        this.res = res;
    }

    public String getCharacter() {
        return character;
    }

    public String getRes() {
        return res;
    }
}
