package wdw.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import wdw.protocal.codec.PacketDecoder;
import wdw.protocal.codec.PacketEncoder;
import wdw.server.handler.LoginRequestHandler;
import wdw.server.handler.MessageRequestHandler;

public class NettyServer {
    private static void bind(final ServerBootstrap serverBootstrap, final int port){
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("成功绑定端口 " + port);
                }else{
                    System.out.println("绑定端口失败, 尝试端口 " + (port + 1));
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
    public static void main(String[] args) {

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>(){
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
                        nioSocketChannel.pipeline().addLast(new LoginRequestHandler());
                        nioSocketChannel.pipeline().addLast(new MessageRequestHandler());
                        nioSocketChannel.pipeline().addLast(new PacketEncoder());
                    }
                });
//        serverBootstrap.bind(1000).addListener(new GenericFutureListener<Future<? super Void>>() {
//            public void operationComplete(Future<? super Void> future) throws Exception {
//                if(future.isSuccess()){
//                    System.out.println("成功绑定端口 ");
//                }else{
//                    System.out.println("绑定端口失败！！");
//                }
//            }
//        });
        bind(serverBootstrap, 8000);

    }
}
