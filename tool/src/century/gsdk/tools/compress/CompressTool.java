package century.gsdk.tools.compress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
