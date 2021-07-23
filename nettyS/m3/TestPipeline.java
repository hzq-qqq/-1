package com.nettyS.m3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class TestPipeline {
    public static void main(String[] args) {
       new ServerBootstrap()
               .group(new NioEventLoopGroup())
               .channel(NioServerSocketChannel.class)
               .childHandler(new ChannelInitializer<SocketChannel>() {
                   @Override
                   protected void initChannel(SocketChannel ch) throws Exception {
                       final ChannelPipeline pipeline = ch.pipeline();
//                       添加处理器   head -> h1 -> tail 并不是加入到最末尾
//                       添加入栈handler    head -> h1 - > h2 -> h3 - > tail
                       pipeline.addLast("h1",new ChannelInboundHandlerAdapter(){
                           @Override
                           public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                               log.debug("1");
                               ByteBuf str = (ByteBuf)msg;
//                               将数据传递给下一个handler。如果不调用，那么这个调用链就会断开  类似于filter
                               super.channelRead(ctx, str);
                           }
                       });
                       pipeline.addLast("h2",new ChannelInboundHandlerAdapter(){
                           @Override
                           public void channelRead(ChannelHandlerContext ctx, Object name) throws Exception {
                               log.debug("2");
                               Student student = new Student(name.toString());
                               super.channelRead(ctx, student);
                           }
                       });
                       pipeline.addLast("h3",new ChannelInboundHandlerAdapter(){
                           @Override
                           public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                               log.debug("3,结果{},class{}",msg,msg.getClass());
//                               super.channelRead(ctx, msg);
                               ctx.writeAndFlush(ctx.alloc().buffer().writeBytes("server..".getBytes(StandardCharsets.UTF_8)));
                           }
                       });
//                       出栈的handler的顺序是从后往前 h1 -> h2 -> h3 -> h5  -> h4
//                      只有正真执行了writeAndFlush（）方法才会触发 出栈handler 执行
                       pipeline.addLast("h4",new ChannelOutboundHandlerAdapter(){
                           @Override
                           public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                               log.debug("4");
                               super.write(ctx,msg,promise);
                           }
                       });
                       pipeline.addLast("h5",new ChannelOutboundHandlerAdapter(){
                           @Override
                           public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                               log.debug("5");
                               super.write(ctx,msg,promise);
                           }
                       });

                   }
               })
               .bind(8080);
    }

    @Data
    @AllArgsConstructor
    static class Student{
        String name;

    }
}
