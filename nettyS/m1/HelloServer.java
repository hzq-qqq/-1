package com.nettyS.m1;

import com.sun.corba.se.spi.activation.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

public class HelloServer {
    public static void main(String[] args) {
//        1.服务器端的启动器  —— 负责组装netty 组件
        new ServerBootstrap()
//                添加了组件 BossEventLoop WorkerEventLoop   多线程 充分利用cpu
//                2.通过group 加入了WorkerEventLoop（selector，Thread） 包含了线程和选择器
//                客户端发送的数据首先会走到EventLoopGroup 这里 ，然后 eventLoopGrop（用于处理读事件的eventLoop），会去调用处理器
//                对发送过来的ByteBuf进行解码
                .group(new NioEventLoopGroup())
//                3. 选择服务器的ServerSocketChannel的实现
                .channel(NioServerSocketChannel.class)
//                4.boss 负责处理，建立连接 ， worker（child —— 在netty中） 负责处理读写  决定了worker（child） 能做那些操作（handler）
//        5.代表和客户端建立连接后读写的通道  Initializer 初始化器，负责添加别的handle
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                            //                            6.在 initChannel 中添加具体的handler
                            @Override
//                            注意这个 初始器，只有和客户端建立连接才会执行
//                            accept事件的处理器就会去调用 该初始化方法
                            protected void initChannel(NioSocketChannel ch) throws Exception {
                                //                           StringDecoder handler 负责解码的 —— 将传输过来的ByteBuf（按照字节来管理数据） 转换为字符串
//                                将ByteBuf 还原为字符串
                                ch.pipeline().addLast(new StringDecoder());
//                             读事件
                                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                     打印转换好的字符串
                                        System.out.println(msg);
                                    }
                                });
                            }
                })
//                7.服务器绑定的监听端口
                .bind(8080);
    }
}
