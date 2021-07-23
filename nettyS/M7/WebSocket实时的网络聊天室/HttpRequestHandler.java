package com.nettyS.M7.WebSocket实时的网络聊天室;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.io.File;
import java.net.URL;

public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private final String wsUri;
    private static File INDEX = null;

    static {
        URL location = HttpRequestHandler.class
                .getProtectionDomain()
                .getCodeSource().getLocation();
        try{
            String path = location.toURI() + "index.html";
            path = !path.contains("file:") ? path : path.substring(5);
            INDEX  = new File(path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (wsUri.equalsIgnoreCase(request.getUri())){
            ctx.fireChannelRead(request.retain());
        }else{
            if (HttpHeaders.is100ContinueExpected(request)){
                send100Continue(ctx);
            }
        }
    }
    private void send100Continue(ChannelHandlerContext ctx) {
        final DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

}
