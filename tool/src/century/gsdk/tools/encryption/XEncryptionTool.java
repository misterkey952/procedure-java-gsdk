package century.gsdk.tools.encryption;


public class XEncryptionTool {

	public static byte[] xencrypt(byte[] data) {
		byte tmp=0;
		int size=data.length/2;
		for(int i=0;i<size;i++) {
			tmp=data[i];
			data[i]=data[data.length-1-i];
			data[data.length-1-i]=tmp;
		}
		return data;
	}
}
