package century.gsdk.net.netty.protobuf;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class SimpleProtobufEncoder extends ChannelOutboundHandlerAdapter {
    private static final Logger logger= LoggerFactory.getLogger("SimpleProtobufEncoder");
    private ProtobufMessageManager protobufMessageManager;
    private ByteBuf buffer= Unpooled.buffer();
    public SimpleProtobufEncoder(ProtobufMessageManager protobufMessageManager) {
        this.protobufMessageManager = protobufMessageManager;
    }

    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        buffer.clear();
        try{
            if(msg instanceof GeneratedMessageV3){
                GeneratedMessageV3 v3= (GeneratedMessageV3) msg;
                byte[] bbc=v3.toByteArray();
                int msgType=protobufMessageManager.getMsgType(v3.getParserForType());
                buffer.writeInt(msgType);
                buffer.writeBytes(bbc);
                ctx.writeAndFlush(buffer,promise);
            }else{
                logger.warn("encode:receive a msg but it's not GeneratedMessageV3 msgType={}",msg.getClass().getName());
            }
        }catch (Exception e){
            logger.error("encode err",e);
            throw e;
        }
    }

}
