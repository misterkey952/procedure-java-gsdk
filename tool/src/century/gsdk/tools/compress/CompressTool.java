package century.gsdk.tools.compress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
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
public class CompressTool {
	private static final Logger logger= LoggerFactory.getLogger("CompressTool");
	public static byte[] compressGzip(byte[] data){
		if(data==null||data.length==0) {
			return data;
		}
		ByteArrayOutputStream byteOutputStream=null;
		GZIPOutputStream gzipOutputStream=null;
		try {
			byteOutputStream=new ByteArrayOutputStream();
			gzipOutputStream=new GZIPOutputStream(byteOutputStream);
			gzipOutputStream.write(data);
			gzipOutputStream.close();
			return byteOutputStream.toByteArray();
		}catch(Exception e) {
			logger.error("compressGzip err",e);
			return data;
		}finally {
			if(byteOutputStream!=null) {
				try {
					byteOutputStream.close();
				} catch (IOException e) {
					logger.error("compressGzip close stream err",e);
				}
			}
		}
		
	
	}

	public static byte[] unCompressGzip(byte[] data){
		if(data==null||data.length==0) {
			return data;
		}
		ByteArrayOutputStream out=null;
		ByteArrayInputStream in=null;
		GZIPInputStream ungzip=null;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(data);
			ungzip = new GZIPInputStream(in);
			byte[] buffer=new byte[256];
			int readSize;
			while((readSize=ungzip.read(buffer))>=0) {
				out.write(buffer,0,readSize);
			}
			return out.toByteArray();
		}catch(Exception e) {
			logger.error("unCompressGzip err",e);
			return data;
		}finally {
			if(out!=null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("unCompressGzip close outstream err",e);
				}
			}
			
			if(in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("unCompressGzip close instream err",e);
				}
			}
			
			if(ungzip!=null) {
				try {
					ungzip.close();
				} catch (IOException e) {
					logger.error("unCompressGzip close ungzip err",e);
				}
			}
		}

      
	
	}
	
}
