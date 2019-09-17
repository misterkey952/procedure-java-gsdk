package century.gsdk.net.netty.head4;
import century.gsdk.net.netty.NettyConnect;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
@ChannelHandler.Sharable
public class Head4Encoder extends ChannelOutboundHandlerAdapter {
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        NettyConnect nettyConnect=ctx.channel().attr(NettyConnect.NETTYCONNECT).get();
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
