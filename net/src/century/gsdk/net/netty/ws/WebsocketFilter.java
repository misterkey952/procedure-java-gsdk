package century.gsdk.net.netty.ws;

import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
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
