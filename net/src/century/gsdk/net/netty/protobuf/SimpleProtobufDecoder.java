package century.gsdk.net.netty.protobuf;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
public class SimpleProtobufDecoder extends ByteToMessageDecoder {
    private static final Logger logger= LoggerFactory.getLogger("SimpleProtobufDecoder");
    private ProtobufMessageManager protobufMessageManager;
    public SimpleProtobufDecoder(ProtobufMessageManager protobufMessageManager) {
        this.protobufMessageManager = protobufMessageManager;
    }
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int msgType=byteBuf.readInt();
        Parser parser=protobufMessageManager.getParser(msgType);
        if(parser!=null){
            byte[] bb=new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bb);
            try {
                GeneratedMessageV3 v3 = (GeneratedMessageV3)parser.parseFrom(bb);
                channelHandlerContext.fireChannelRead(v3);
            } catch (InvalidProtocolBufferException e) {
                logger.error("decode:parse msg from bytes err msgType="+msgType,e);
                throw e;
            }
        }else{
            logger.warn("decode:the msgType={} is not exist",msgType);
        }
    }
}
