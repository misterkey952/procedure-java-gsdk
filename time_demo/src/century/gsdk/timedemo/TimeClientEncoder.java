package century.gsdk.timedemo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class TimeClientEncoder extends ChannelOutboundHandlerAdapter {
    private static final Logger logger= LoggerFactory.getLogger("TimeClientEncoder");
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        ByteBuf byteBuf= Unpooled.compositeBuffer(Integer.SIZE);
        byteBuf.writeInt((Integer) msg);
        ctx.writeAndFlush(byteBuf,promise);
    }
}
