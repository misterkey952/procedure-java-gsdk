package century.gsdk.net.netty.ws;

import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;

@ChannelHandler.Sharable
public class WebsocketFilter extends ChannelInboundHandlerAdapter {
    private WebSocketServerHandshakerFactory wsFactory;
    private ChannelFutureListener channelFutureListener;
    public WebsocketFilter(String host,int port,ChannelFutureListener channelFutureListener){
        wsFactory=new WebSocketServerHandshakerFactory("ws://"+host+":"+port, null, false);
        this.channelFutureListener=channelFutureListener;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg)  throws Exception{
        if(msg instanceof FullHttpRequest){
            handleHttpMessage(ctx, (FullHttpRequest) msg);
        }else if(msg instanceof WebSocketFrame){
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleHttpMessage(ChannelHandlerContext ctx,FullHttpRequest msg)throws Exception{
        if(!msg.decoderResult().isSuccess()||
                (!"websocket".equals(msg.headers().get("Upgrade")))){
            ctx.close();
            return ;
        }
        WebSocketServerHandshaker handshaker=wsFactory.newHandshaker(msg);
        if(handshaker==null){
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        }else{
            handshaker.handshake(ctx.channel(),msg).addListener(channelFutureListener);
        }

    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame msg){
        ctx.fireChannelRead(msg.content());
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
    }
}
