package century.gsdk.net.http;

import century.gsdk.net.NetLogger;
import century.gsdk.tools.http.IHttp;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C) <2019>  <Century>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * <p>
 * Author's Email:   misterkey952@gmail.com		280202806@qq.com	yjy116@163.com.
 */
public class CustomHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private Map<String,IHttpHandler> handlerMap;

    public CustomHandler(Map<String, IHttpHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    private void sendRes(HttpResponseStatus status,String msg,ChannelHandlerContext ctx){
        sendRes(status,msg,ctx,null);
    }
    private void sendRes(HttpResponseStatus status,String msg,ChannelHandlerContext ctx,String character){
        if(character!=null){
            character=";"+character;
        }else{
            character="";
        }

        if(msg==null){
            msg="";
        }


        DefaultFullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status,
                Unpooled.wrappedBuffer(msg.getBytes()));
        response.headers().add(HttpHeaderNames.CONTENT_TYPE,HttpHeaderValues.TEXT_PLAIN+character);
        response.headers().add(HttpHeaderNames.CONTENT_LENGTH,response.content().readableBytes());
        response.headers().add(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
        ctx.writeAndFlush(response);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        try{
            LightRequest lq=new LightRequest(request.uri(),request.content());
            IHttpHandler handler=handlerMap.get(lq.getUri());
            if(handler==null){
                sendRes(HttpResponseStatus.NOT_FOUND,"404",ctx);
            }else{

                LightResponse lr=handler.handler(lq);
                if(lr==null){
                    sendRes(HttpResponseStatus.OK,"200",ctx);
                }else{
                    sendRes(HttpResponseStatus.OK,lr.getRes(),ctx,lr.getCharacter());
                }
            }
        }catch(Exception e){
            NetLogger.LightHttpServer.error("handle err",e);
            sendRes(HttpResponseStatus.INTERNAL_SERVER_ERROR,"500",ctx);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        NetLogger.LightHttpServer.error("exceptionCaught",cause);
        if(null != ctx) ctx.close();
    }
}
