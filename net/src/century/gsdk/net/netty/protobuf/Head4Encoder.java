package century.gsdk.net.netty.protobuf;
import century.gsdk.net.netty.NettyConnect;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

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
@ChannelHandler.Sharable
public class Head4Encoder extends ChannelOutboundHandlerAdapter {
    protected NettyConnect getNettyConnect(ChannelHandlerContext ctx){
        return ctx.channel().attr(NettyConnect.NETTYCONNECT).get();
    }
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        NettyConnect nettyConnect=getNettyConnect(ctx);
        try{
            if(msg instanceof GeneratedMessageV3){
                GeneratedMessageV3 v3= (GeneratedMessageV3) msg;
                byte[] bbc=v3.toByteArray();
                int msgType=nettyConnect.getMsgType(v3.getParserForType());
                ByteBuf byteBuf= Unpooled.compositeBuffer(bbc.length+4+4);
                byteBuf.writeInt(bbc.length+4);
                byteBuf.writeInt(msgType);
                byteBuf.writeBytes(bbc);
                ctx.writeAndFlush(byteBuf,promise);
            }
        }catch (Exception e){
            nettyConnect.error("Head4Encoder err",e);
        }
    }
}
