package century.gsdk.timedemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@ChannelHandler.Sharable
public class TimeClientDecoder extends ChannelInboundHandlerAdapter {
    private static final Logger logger= LoggerFactory.getLogger("DEMO");
    private static final AttributeKey<CompositeByteBuf> BUFFKEY=AttributeKey.valueOf("BUFFER");
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CompositeByteBuf buffer=ctx.channel().attr(BUFFKEY).get();
        if(buffer==null){
            buffer= Unpooled.compositeBuffer(Long.SIZE);
            ctx.channel().attr(BUFFKEY).set(buffer);
        }
        buffer.addComponent(true,(ByteBuf) msg);
        nianbao(buffer,ctx);
    }

    private void nianbao(CompositeByteBuf buffer,ChannelHandlerContext ctx){
        if (buffer.readableBytes() < Long.SIZE) {
            return;
        }
        long timeCount = buffer.readLong();
        logger.info("RECEIVE A TIME {}",timeCount);
    }
}
