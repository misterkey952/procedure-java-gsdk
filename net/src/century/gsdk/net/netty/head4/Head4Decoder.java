package century.gsdk.net.netty.head4;
import century.gsdk.net.core.NetHandleThread;
import century.gsdk.net.core.NetMessageHandle;
import century.gsdk.net.core.NetSession;
import century.gsdk.net.netty.NettyConnect;
import century.gsdk.net.netty.NettySession;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;
@ChannelHandler.Sharable
public class Head4Decoder extends ChannelInboundHandlerAdapter{
    private static final AttributeKey<CompositeByteBuf> BUFFKEY=AttributeKey.valueOf("BUFFER");
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CompositeByteBuf buffer=ctx.channel().attr(BUFFKEY).get();
        if(buffer==null){
            buffer=Unpooled.compositeBuffer();
            ctx.channel().attr(BUFFKEY).set(buffer);
        }
        buffer.addComponent(true,(ByteBuf) msg);
		nianbao(buffer,ctx);
	}


    private void nianbao(CompositeByteBuf buffer,ChannelHandlerContext ctx){
        if (buffer.readableBytes() < 4) {
            return;
        }

        int length = buffer.readInt();
        if (buffer.readableBytes() < length) {
            buffer.readerIndex(0);
            return;
        }
        ByteBuf dd=buffer.slice(buffer.readerIndex(),length);
        decode(ctx,dd);
        buffer.readerIndex(buffer.readerIndex()+length);
        buffer.discardReadComponents();
        nianbao(buffer,ctx);
    }


    private void decode(ChannelHandlerContext ctx, ByteBuf byteBuf){
        NettyConnect nettyConnect=ctx.channel().attr(NettyConnect.NETTYCONNECT).get();
        int msgType=byteBuf.readInt();
        Parser parser=nettyConnect.getParser(msgType);
        if(parser!=null){
            byte[] bb=new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bb);
            GeneratedMessageV3 v3= null;
            try {
                v3 = (GeneratedMessageV3)parser.parseFrom(bb);
                NetMessageHandle msgHandle=nettyConnect.getMessageHandle(msgType);
                if(msgHandle!=null){
                    NetSession session=nettyConnect.session(ctx.channel());
                    ctx.fireUserEventTriggered(new NetHandleThread(session,v3,msgHandle));
                }else{
                    nettyConnect.warn("hasn't MessageHandle msgType={}",msgType);
                }

            } catch (InvalidProtocolBufferException e) {
                nettyConnect.error("decode err",e);
            }
        }else{
            nettyConnect.warn("the msgType {} has not parse",msgType);
        }
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettyConnect nettyConnect=nettyConnect(ctx);
        NetSession session=new NettySession((SocketChannel) ctx.channel());
        nettyConnect.registerSession(session);
        nettyConnect.info("register a session {}",session.remoteIP(),session.remotePort());
        ctx.channel().closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                nettyConnect.info("a client close");
            }
        });
        ctx.fireChannelActive();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.fireExceptionCaught(cause);
        ctx.close();
        nettyConnect(ctx).error("a channel error",cause);
    }

	public NettyConnect nettyConnect(ChannelHandlerContext ctx){
        return ctx.channel().attr(NettyConnect.NETTYCONNECT).get();
    }
}
