package century.gsdk.ts.net;

import century.gsdk.net.core.SessionCloseHook;
import century.gsdk.net.netty.NettySession;
import century.gsdk.ts.objs.GTime;
import century.gsdk.ts.objs.GTimeManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
@ChannelHandler.Sharable
public class TimeDecoder extends ChannelInboundHandlerAdapter {
    private static final AttributeKey<CompositeByteBuf> BUFFKEY=AttributeKey.valueOf("BUFFER");
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CompositeByteBuf buffer=ctx.channel().attr(BUFFKEY).get();
        if(buffer==null){
            buffer= Unpooled.compositeBuffer(Integer.SIZE);
            ctx.channel().attr(BUFFKEY).set(buffer);
        }
        buffer.addComponent(true,(ByteBuf) msg);
        nianbao(buffer,ctx);
    }

    private void nianbao(CompositeByteBuf buffer,ChannelHandlerContext ctx){
        if (buffer.readableBytes() < 4) {
            return;
        }
        int timeID = buffer.readInt();
        final GTime gTime= GTimeManager.getInstance().getGTime(timeID);
        if(gTime!=null){
            gTime.getTimeEventHandle().registerSession(new NettySession((SocketChannel) ctx.channel(), new SessionCloseHook() {
                @Override
                public void hook() {
                    gTime.getTimeEventHandle().removeSession(session);
                }
            }));
        }
    }


    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println("--------------");
    }
}
