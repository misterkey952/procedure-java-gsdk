package century.gsdk.ts.net;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
@ChannelHandler.Sharable
public class TimeEncoder extends ChannelOutboundHandlerAdapter {
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ByteBuf byteBuf= Unpooled.compositeBuffer(Long.SIZE);
        byteBuf.writeLong((Long) msg);
        ctx.writeAndFlush(byteBuf,promise);
    }
}
