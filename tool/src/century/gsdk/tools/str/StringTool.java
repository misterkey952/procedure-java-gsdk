package century.gsdk.tools.str;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.*;
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
public  class StringTool {
    public final static String SPACE="";
    private static Date parseDate(String value){

        if(value==null||"".equals(value)){
            return null;
        }

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND,0);
        String[] dateStrs=value.split(" ");
        String[] yyyymmdd=null;
        if(dateStrs[0].indexOf("/")>0){
            yyyymmdd=dateStrs[0].split("/");
        }else if(dateStrs[0].indexOf("-")>0){
            yyyymmdd=dateStrs[0].split("-");
        }
        if(yyyymmdd!=null){
            calendar.set(Calendar.YEAR,Integer.parseInt(yyyymmdd[0]));

            if(yyyymmdd.length>1&&!"".equals(yyyymmdd[1])){
                if(yyyymmdd[1].startsWith("0")){
                    yyyymmdd[1]=yyyymmdd[1].replaceFirst("0","");
                    if("".equals(yyyymmdd[1])){
                        yyyymmdd[1]="0";
                    }
                }
                calendar.set(Calendar.MONTH,Integer.parseInt(yyyymmdd[1])-1);
            }else{
                calendar.set(Calendar.MONTH,0);
            }

            if(yyyymmdd.length>2&&!"".equals(yyyymmdd[2])){
                if(yyyymmdd[2].startsWith("0")){
                    yyyymmdd[2]=yyyymmdd[2].replaceFirst("0","");
                    if("".equals(yyyymmdd[2])){
                        yyyymmdd[2]="0";
                    }
                }
                calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(yyyymmdd[2]));
            }else{
                calendar.set(Calendar.DAY_OF_MONTH,1);
            }
        }



        if(dateStrs.length>1&&!"".equals(dateStrs[1])){
            String[] hhmmss=dateStrs[1].split(":");
            calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hhmmss[0]));
            if(hhmmss.length>1&&!"".equals(hhmmss[1])){
                if(hhmmss[1].startsWith("0")){
                    hhmmss[1]=hhmmss[1].replaceFirst("0","");
                    if("".equals(hhmmss[1])){
                        hhmmss[1]="0";
                    }
                }
                calendar.set(Calendar.MINUTE,Integer.parseInt(hhmmss[1]));
            }else{
                calendar.set(Calendar.MINUTE,0);
            }

            if(hhmmss.length>2&&!"".equals(hhmmss[2])){
                if(hhmmss[2].startsWith("0")){
                    hhmmss[2]=hhmmss[2].replaceFirst("0","");
                    if("".equals(hhmmss[2])){
                        hhmmss[2]="0";
                    }
                }
                calendar.set(Calendar.SECOND,Integer.parseInt(hhmmss[2]));
            }else{
                calendar.set(Calendar.SECOND,0);
            }
        }else{
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
        }
        return calendar.getTime();
    }

    public static boolean[] convertBooleanArr(String value,String split){
        if(value==null||"".equals(value)){
            return null;
        }

        String[] str=value.trim().split(split);
        boolean[] t=new boolean[str.length];
        for(int i=0;i<str.length;i++){
            if(str[i].trim().toLowerCase().equals("true")){
                t[i]=true;
            }else{
                t[i]=false;
            }
        }
        return t;
    }

    public static long[] convertLongArr(String value, String split){
        if(value==null||"".equals(value)){
            return null;
        }
        String[] str=value.trim().split(split);
        long[] t=new long[str.length];
        for(int i=0;i<str.length;i++){
            t[i]=Long.parseLong(str[i]);
        }
        return t;
    }

    public static int[] convertIntArr(String value,String split){
        if(value==null||"".equals(value)){
            return null;
        }
        String[] str=value.trim().split(split);
        int[] t=new int[str.length];
        for(int i=0;i<str.length;i++){
            t[i]=Integer.parseInt(str[i]);
        }
        return t;
    }

    public static double[] convertDoubleArr(String value,String split){
        if(value==null||"".equals(value)){
            return null;
        }
        String[] str=value.trim().split(split);
        double[] t=new double[str.length];
        for(int i=0;i<str.length;i++){
            t[i]=Double.parseDouble(str[i]);
        }
        return t;
    }

    public static byte[] convertByteArr(String value,String split){
        if(value==null||"".equals(value)){
            return null;
        }
        String[] str=value.trim().split(split);
        byte[] t=new byte[str.length];
        for(int i=0;i<str.length;i++){
            t[i]=Byte.parseByte(str[i]);
        }
        return t;
    }

    public static short[] convertShortArr(String value,String split){
        if(value==null||"".equals(value)){
            return null;
        }
        String[] str=value.trim().split(split);
        short[] t=new short[str.length];
        for(int i=0;i<str.length;i++){
            t[i]=Short.parseShort(str[i]);
        }
        return t;
    }

    public static float[] convertFloatArr(String value,String split){
        if(value==null||"".equals(value)){
            return null;
        }
        String[] str=value.trim().split(split);
        float[] t=new float[str.length];
        for(int i=0;i<str.length;i++){
            t[i]=Float.parseFloat(str[i]);
        }
        return t;
    }

    public static String[] convertStringArr(String value,String split){
        if(value==null){
            return null;
        }
        return value.trim().split(split);
    }


    public static boolean convertBoolean(String value){
        if(value==null||"".equals(value)){
            return false;
        }
        if(value.trim().toLowerCase().equals("true")){
            return true;
        }
        return false;
    }


    public static long convertLong(String value){
        if(value==null||"".equals(value)){
            return 0;
        }
        return Long.parseLong(value.trim());
    }

    public static int convertInt(String value){
        if(value==null||"".equals(value)){
            return 0;
        }
        return Integer.parseInt(value.trim());
    }

    public static double convertDouble(String value){
        if(value==null||"".equals(value)){
            return 0;
        }
        return Double.parseDouble(value.trim());
    }

    public static byte convertByte(String value){
        if(value==null||"".equals(value)){
            return 0;
        }
        return Byte.parseByte(value.trim());
    }

    public static short convertShort(String value){
        if(value==null||"".equals(value)){
            return 0;
        }
        return Short.parseShort(value.trim());
    }

    public static float convertFloat(String value){
        if(value==null||"".equals(value)){
            return 0;
        }
        return Float.parseFloat(value.trim());
    }

    public static String convertString(String value){
        if(value==null){
            return SPACE;
        }
        return value.trim();
    }

    public static Date convertDate(String value){
        if(value==null||"".equals(value)){
            return null;
        }
        return parseDate(value.trim());
    }


    public static Timestamp convertTimestamp(String value){
        if(value==null||"".equals(value)){
            return null;
        }
        return new Timestamp(parseDate(value.trim()).getTime());
    }


    public static String toLowerCaseFirst(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }


    public static String toUpperCaseFirst(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    public static String exceptionStack(Throwable e) {
        if (e == null){
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
