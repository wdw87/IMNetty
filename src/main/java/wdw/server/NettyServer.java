package wdw.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import wdw.client.handler.JoinGroupResponseHandler;
import wdw.client.handler.SendToGroupResponseHandler;
import wdw.protocal.codec.PacketDecoder;
import wdw.protocal.codec.PacketEncoder;
import wdw.protocal.codec.Spliter;
import wdw.server.handler.*;

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
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>(){
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new Spliter());
                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
//                        nioSocketChannel.pipeline().addLast(new LoginRequestHandler());
//                        nioSocketChannel.pipeline().addLast(new AuthHandler());
//                        nioSocketChannel.pipeline().addLast(new MessageRequestHandler());
//                        nioSocketChannel.pipeline().addLast(new CreateGroupRequestHandler());
//                        nioSocketChannel.pipeline().addLast(new LogoutRequestHandler());
//                        nioSocketChannel.pipeline().addLast(new JoinGroupRequestHandler());
//                        nioSocketChannel.pipeline().addLast(new QuitGroupRequestHandler());
//                        nioSocketChannel.pipeline().addLast(new ListGroupRequestHandler());

                        nioSocketChannel.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        nioSocketChannel.pipeline().addLast(AuthHandler.INSTANCE);
//                        nioSocketChannel.pipeline().addLast(MessageRequestHandler.INSTANCE);
//                        nioSocketChannel.pipeline().addLast(CreateGroupRequestHandler.INSTANCE);
//                        nioSocketChannel.pipeline().addLast(LogoutRequestHandler.INSTANCE);
//                        nioSocketChannel.pipeline().addLast(JoinGroupRequestHandler.INSTANCE);
//                        nioSocketChannel.pipeline().addLast(QuitGroupRequestHandler.INSTANCE);
//                        nioSocketChannel.pipeline().addLast(ListGroupRequestHandler.INSTANCE);
                        nioSocketChannel.pipeline().addLast(IMHandler.INSTANCE);

                        nioSocketChannel.pipeline().addLast(new PacketEncoder());
                    }
                });

        bind(serverBootstrap, 8000);

    }
}
