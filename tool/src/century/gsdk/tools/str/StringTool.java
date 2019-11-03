package century.gsdk.tools.str;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.sql.Time;
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
    public final static String DEFAULT_SPLIT_OF_ARR=";";
    private static String encodeDate(long time){
        StringBuilder stringBuilder=new StringBuilder();
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.MILLISECOND,0);
        stringBuilder.append(calendar.get(Calendar.YEAR)).append("-").append(calendar.get(Calendar.MONTH)+1).append("-")
                .append(calendar.get(Calendar.DAY_OF_MONTH)).append(" ").append(calendar.get(Calendar.HOUR_OF_DAY)).append(calendar.get(Calendar.MINUTE))
                .append(calendar.get(Calendar.SECOND));
        return stringBuilder.toString();
    }


    private static Date decodeDate(String value){
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


    public static String valueOf(boolean[] value){
        return valueOf(value,DEFAULT_SPLIT_OF_ARR);
    }

    public static String valueOf(byte[] value){
        return valueOf(value,DEFAULT_SPLIT_OF_ARR);
    }
    public static String valueOf(short[] value){
        return valueOf(value,DEFAULT_SPLIT_OF_ARR);
    }
    public static String valueOf(int[] value){
        return valueOf(value,DEFAULT_SPLIT_OF_ARR);
    }
    public static String valueOf(long[] value){
        return valueOf(value,DEFAULT_SPLIT_OF_ARR);
    }
    public static String valueOf(float[] value){
        return valueOf(value,DEFAULT_SPLIT_OF_ARR);
    }
    public static String valueOf(double[] value){
        return valueOf(value,DEFAULT_SPLIT_OF_ARR);
    }

    public static String valueOf(String[] value){
        return valueOf(value,DEFAULT_SPLIT_OF_ARR);
    }


    public static String valueOf(boolean[] value,String split){

        if(value==null){
            return "null";
        }

        StringBuilder stb=new StringBuilder();
        for(int i=0;i<value.length;i++){
            stb.append(String.valueOf(value[i]));
            if(i<value.length-1){
                stb.append(split);
            }
        }

        return stb.toString();
    }

    public static boolean[] convertBooleanArr(String value){
        return   convertBooleanArr(value,DEFAULT_SPLIT_OF_ARR);
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

    public static String valueOf(long[] value,String split){
        if(value==null){
            return "null";
        }

        StringBuilder stb=new StringBuilder();
        for(int i=0;i<value.length;i++){
            stb.append(String.valueOf(value[i]));
            if(i<value.length-1){
                stb.append(split);
            }
        }

        return stb.toString();
    }

    public static long[] convertLongArr(String value){
        return convertLongArr(value,DEFAULT_SPLIT_OF_ARR);
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

    public static String valueOf(int[] value,String split){
        if(value==null){
            return "null";
        }

        StringBuilder stb=new StringBuilder();
        for(int i=0;i<value.length;i++){
            stb.append(String.valueOf(value[i]));
            if(i<value.length-1){
                stb.append(split);
            }
        }

        return stb.toString();
    }

    public static int[] convertIntArr(String value){
        return convertIntArr(value,DEFAULT_SPLIT_OF_ARR);
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

    public static String valueOf(double[] value,String split){
        if(value==null){
            return "null";
        }

        StringBuilder stb=new StringBuilder();
        for(int i=0;i<value.length;i++){
            stb.append(String.valueOf(value[i]));
            if(i<value.length-1){
                stb.append(split);
            }
        }

        return stb.toString();
    }


    public static double[] convertDoubleArr(String value){
        return convertDoubleArr(value,DEFAULT_SPLIT_OF_ARR);
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


    public static String valueOf(byte[] value,String split){
        if(value==null){
            return "null";
        }

        StringBuilder stb=new StringBuilder();
        for(int i=0;i<value.length;i++){
            stb.append(String.valueOf(value[i]));
            if(i<value.length-1){
                stb.append(split);
            }
        }

        return stb.toString();
    }

    public static byte[] convertByteArr(String value){
        return convertByteArr(value,DEFAULT_SPLIT_OF_ARR);
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

    public static String valueOf(short[] value,String split){

        if(value==null){
            return "null";
        }
        StringBuilder stb=new StringBuilder();
        for(int i=0;i<value.length;i++){
            stb.append(String.valueOf(value[i]));
            if(i<value.length-1){
                stb.append(split);
            }
        }

        return stb.toString();
    }

    public static short[] convertShortArr(String value){
        return convertShortArr(value,DEFAULT_SPLIT_OF_ARR);
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

    public static String valueOf(float[] value,String split){
        if(value==null){
            return "null";
        }

        StringBuilder stb=new StringBuilder();
        for(int i=0;i<value.length;i++){
            stb.append(String.valueOf(value[i]));
            if(i<value.length-1){
                stb.append(split);
            }
        }

        return stb.toString();
    }

    public static float[] convertFloatArr(String value){
        return convertFloatArr(value,DEFAULT_SPLIT_OF_ARR);
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

    public static String valueOf(String[] value,String split){
        if(value==null){
            return "null";
        }

        StringBuilder stb=new StringBuilder();
        for(int i=0;i<value.length;i++){
            stb.append(value[i]);
            if(i<value.length-1){
                stb.append(split);
            }
        }

        return stb.toString();
    }


    public static String[] convertStringArr(String value){
        return convertStringArr(value,DEFAULT_SPLIT_OF_ARR);
    }

    public static String[] convertStringArr(String value,String split){
        if(value==null){
            return null;
        }
        return value.trim().split(split);
    }


    public static String valueOf(Boolean value){
        return String.valueOf(value);
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

    public static String valueOf(Long value){
        return String.valueOf(value);
    }


    public static long convertLong(String value){
        if(value==null||"".equals(value)){
            return 0;
        }
        return Long.parseLong(value.trim());
    }

    public static String valueOf(Integer value){
        return String.valueOf(value);
    }

    public static int convertInt(String value){
        if(value==null||"".equals(value)){
            return 0;
        }
        return Integer.parseInt(value.trim());
    }

    public static String valueOf(Double value){
        return String.valueOf(value);
    }

    public static double convertDouble(String value){
        if(value==null||"".equals(value)){
            return 0;
        }
        return Double.parseDouble(value.trim());
    }

    public static String valueOf(Byte value){
        return String.valueOf(value);
    }

    public static byte convertByte(String value){
        if(value==null||"".equals(value)){
            return 0;
        }
        return Byte.parseByte(value.trim());
    }

    public static String valueOf(Short value){
        return String.valueOf(value);
    }

    public static short convertShort(String value){
        if(value==null||"".equals(value)){
            return 0;
        }
        return Short.parseShort(value.trim());
    }

    public static String valueOf(Float value){
        return String.valueOf(value);
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

    public static String valueOf(Date value){
        if(value==null){
            return "null";
        }
        return encodeDate(value.getTime());
    }

    public static String valueOf(Timestamp[] value){
        return valueOf(value,DEFAULT_SPLIT_OF_ARR);
    }
    public static String valueOf(Date[] value){
        return valueOf(value,DEFAULT_SPLIT_OF_ARR);
    }

    public static String valueOf(Date[] value,String split){
        if(value==null){
            return "null";
        }
        StringBuilder stb=new StringBuilder();
        for(int i=0;i<value.length;i++){
            stb.append(encodeDate(value[i].getTime()));
            if(i<value.length-1){
                stb.append(split);
            }
        }
        return stb.toString();
    }

    public static String valueOf(Timestamp[] value,String split){
        if(value==null){
            return "null";
        }
        StringBuilder stb=new StringBuilder();
        for(int i=0;i<value.length;i++){
            stb.append(encodeDate(value[i].getTime()));
            if(i<value.length-1){
                stb.append(split);
            }
        }
        return stb.toString();
    }


    public static Date[] convertDateArr(String value){
        return convertDateArr(value,DEFAULT_SPLIT_OF_ARR);
    }

    public static Date[] convertDateArr(String value,String split){
        if(value==null||"".equals(value)){
            return null;
        }

        String[] stbValue=convertStringArr(value,split);
        Date[] dates=new Date[stbValue.length];
        for(int i=0;i<dates.length;i++){
            dates[i]=convertDate(stbValue[i]);
        }
        return dates;
    }


    public static Timestamp[] convertTimestampArr(String value){
        return convertTimestampArr(value,DEFAULT_SPLIT_OF_ARR);
    }

    public static Timestamp[] convertTimestampArr(String value, String split){
        if(value==null||"".equals(value)){
            return null;
        }

        String[] stbValue=convertStringArr(value,split);
        Timestamp[] dates=new Timestamp[stbValue.length];
        for(int i=0;i<dates.length;i++){
            dates[i]=convertTimestamp(stbValue[i]);
        }
        return dates;
    }

    public static Date convertDate(String value){
        if(value==null||"".equals(value)){
            return null;
        }
        return decodeDate(value.trim());
    }


    public static String valueOf(Timestamp value){
        if(value==null){
            return "null";
        }
        return encodeDate(value.getTime());
    }

    public static Timestamp convertTimestamp(String value){
        if(value==null||"".equals(value)){
            return null;
        }
        return new Timestamp(decodeDate(value.trim()).getTime());
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

    public static String toBinary(byte value){
        return Integer.toBinaryString((value & 0xFF) + 0x100).substring(1);
    }

    public static byte binaryToByte(String value){
        return (byte) Integer.parseInt(value,2);
    }

    public static boolean supportType(Class clazz){
        if(clazz==Byte.class||clazz==byte.class){
            return true;
        }else if(clazz==Short.class||clazz==short.class){
            return true;
        }else if(clazz==Integer.class||clazz==int.class){
            return true;
        }else if(clazz==Long.class||clazz==long.class){
            return true;
        }else if(clazz==Float.class||clazz==float.class){
            return true;
        }else if(clazz==Double.class||clazz==double.class){
            return true;
        }else if(clazz==Boolean.class||clazz==boolean.class){
            return true;
        }else if(clazz==Date.class){
            return true;
        }else if(clazz==Timestamp.class){
            return true;
        }else if(clazz==String.class){
            return true;
        }if(clazz==Byte[].class||clazz==byte[].class){
            return true;
        }else if(clazz==Short[].class||clazz==short[].class){
            return true;
        }else if(clazz==Integer[].class||clazz==int[].class){
            return true;
        }else if(clazz==Long[].class||clazz==long[].class){
            return true;
        }else if(clazz==Float[].class||clazz==float[].class){
            return true;
        }else if(clazz==Double[].class||clazz==double[].class){
            return true;
        }else if(clazz==Boolean[].class||clazz==boolean[].class){
            return true;
        }else if(clazz==Date[].class){
            return true;
        }else if(clazz==Timestamp[].class){
            return true;
        }else if(clazz==String[].class){
            return true;
        }
        return false;
    }

    public static String valueOf(Object object){
        if(object.getClass()==Byte.class||object.getClass()==byte.class){
            return valueOf((Byte)object);
        }else if(object.getClass()==Short.class||object.getClass()==short.class){
            return valueOf((Short)object);
        }else if(object.getClass()==Integer.class||object.getClass()==int.class){
            return valueOf((Integer)object);
        }else if(object.getClass()==Long.class||object.getClass()==long.class){
            return valueOf((Long)object);
        }else if(object.getClass()==Float.class||object.getClass()==float.class){
            return valueOf((Float) object);
        }else if(object.getClass()==Double.class||object.getClass()==double.class){
            return valueOf((Double) object);
        }else if(object.getClass()==Boolean.class||object.getClass()==boolean.class){
            return valueOf((Boolean)object);
        }else if(object.getClass()==Date.class){
            return valueOf((Date)object);
        }else if(object.getClass()==Timestamp.class){
            return valueOf((Timestamp)object);
        }else if(object.getClass()==String.class){
            return object.toString();
        }if(object.getClass()==Byte[].class||object.getClass()==byte[].class){
            return valueOf((byte[])object);
        }else if(object.getClass()==Short[].class||object.getClass()==short[].class){
            return valueOf((short[])object);
        }else if(object.getClass()==Integer[].class||object.getClass()==int[].class){
            return valueOf((int[])object);
        }else if(object.getClass()==Long[].class||object.getClass()==long[].class){
            return valueOf((long[])object);
        }else if(object.getClass()==Float[].class||object.getClass()==float[].class){
            return valueOf((float[]) object);
        }else if(object.getClass()==Double[].class||object.getClass()==double[].class){
            return valueOf((double[]) object);
        }else if(object.getClass()==Boolean[].class||object.getClass()==boolean[].class){
            return valueOf((boolean[]) object);
        }else if(object.getClass()==Date[].class){
            return valueOf((Date[])object);
        }else if(object.getClass()==Timestamp[].class){
            return valueOf((Timestamp[])object);
        }else if(object.getClass()==String[].class){
            return valueOf((String[])object);
        }
        return "null";
    }

}
