package century.gsdk.tools.encryption;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
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
public class GDigestTool {
    private static Map<Byte,String> ASCII_NOT_PRINT=new HashMap<Byte, String>();
    private static Map<Byte,Character> ASCII_CAN_PRINT=new HashMap<Byte, Character>();
    private static Base64 base64=new Base64();
    static{
        ASCII_NOT_PRINT.put((byte)0,"NUT");
        ASCII_NOT_PRINT.put((byte)1,"SOH");
        ASCII_NOT_PRINT.put((byte)2,"STX");
        ASCII_NOT_PRINT.put((byte)3,"ETX");
        ASCII_NOT_PRINT.put((byte)4,"EOT");
        ASCII_NOT_PRINT.put((byte)5,"ENQ");
        ASCII_NOT_PRINT.put((byte)6,"ACK");
        ASCII_NOT_PRINT.put((byte)7,"BEL");
        ASCII_NOT_PRINT.put((byte)8,"BS");
        ASCII_NOT_PRINT.put((byte)9,"HT");
        ASCII_NOT_PRINT.put((byte)10,"LF");
        ASCII_NOT_PRINT.put((byte)11,"VT");
        ASCII_NOT_PRINT.put((byte)12,"FF");
        ASCII_NOT_PRINT.put((byte)13,"CR");
        ASCII_NOT_PRINT.put((byte)14,"SO");
        ASCII_NOT_PRINT.put((byte)15,"SI");
        ASCII_NOT_PRINT.put((byte)16,"DLE");
        ASCII_NOT_PRINT.put((byte)17,"DCI");
        ASCII_NOT_PRINT.put((byte)18,"DC2");
        ASCII_NOT_PRINT.put((byte)19,"DC3");
        ASCII_NOT_PRINT.put((byte)20,"DC4");
        ASCII_NOT_PRINT.put((byte)21,"NAK");
        ASCII_NOT_PRINT.put((byte)22,"SYN");
        ASCII_NOT_PRINT.put((byte)23,"TB");
        ASCII_NOT_PRINT.put((byte)24,"CAN");
        ASCII_NOT_PRINT.put((byte)25,"EM");
        ASCII_NOT_PRINT.put((byte)26,"SUB");
        ASCII_NOT_PRINT.put((byte)27,"ESC");
        ASCII_NOT_PRINT.put((byte)28,"FS");
        ASCII_NOT_PRINT.put((byte)29,"GS");
        ASCII_NOT_PRINT.put((byte)30,"RS");
        ASCII_NOT_PRINT.put((byte)31,"US");
        ASCII_NOT_PRINT.put((byte)127,"DEL");


        for(byte i=32;i<=126;i++){
            ASCII_CAN_PRINT.put(i,(char) i);
        }

    }

    public static String firstUp(String str){

        if (str == null || str.isEmpty()) {
            return null;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());

    }

    public static String firstLower(String str){

        if (str == null || str.isEmpty()) {
            return null;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1, str.length());

    }



    public static String convertBytesToASCII(byte[] datas){
        StringBuffer stb=new StringBuffer();
        for(int i=0;i<datas.length;i++){
            if(datas[i]>=0&&datas[i]<=31||datas[i]==127){
                stb.append("<"+ASCII_NOT_PRINT.get(datas[i])+">");
            }else if(datas[i]>=32&&datas[i]<=126){
                stb.append(ASCII_CAN_PRINT.get(datas[i]).charValue());
            }else{
                stb.append("["+datas[i]+"]");
            }
        }
        return stb.toString();
    }


    public static String md5Hex(String str){
        return DigestUtils.md5Hex(str);
    }




    public static String base64Encode(String text){
        return new String(base64.encode(text.getBytes()));
    }


    public static String base64Decode(String text){
        return new String(base64.decode(text.getBytes()));
    }
}
