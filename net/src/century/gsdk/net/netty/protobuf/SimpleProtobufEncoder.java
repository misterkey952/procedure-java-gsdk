package century.gsdk.net.netty.protobuf;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
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
public class SimpleProtobufEncoder extends MessageToByteEncoder {
    private static final Logger logger= LoggerFactory.getLogger("SimpleProtobufEncoder");
    private ProtobufMessageManager protobufMessageManager;

    public SimpleProtobufEncoder(ProtobufMessageManager protobufMessageManager) {
        this.protobufMessageManager = protobufMessageManager;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, ByteBuf byteBuf) throws Exception {
        try{
            if(object instanceof GeneratedMessageV3){
                GeneratedMessageV3 v3= (GeneratedMessageV3) object;
                byte[] bbc=v3.toByteArray();
                int msgType=protobufMessageManager.getMsgType(v3.getParserForType());
                byteBuf.writeInt(msgType);
                byteBuf.writeBytes(bbc);
            }else{
                logger.warn("encode:receive a msg but it's not GeneratedMessageV3 msgType={}",object.getClass().getName());
            }
        }catch (Exception e){
            logger.error("encode err",e);
            throw e;
        }
    }
}
