package wdw.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import wdw.client.handler.LoginResponseHandler;
import wdw.client.handler.MessageResponseHandler;
import wdw.protocal.PacketCodeC;
import wdw.protocal.codec.PacketDecoder;
import wdw.protocal.codec.PacketEncoder;
import wdw.protocal.codec.Spliter;
import wdw.protocal.request.MessageRequestPacket;
import wdw.util.LoginUtil;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class NettyClient {
    private static final int MAX_RETRY = 5;

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("connect " + port + " succeed");
                //连接成功后启动控制台线程
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.out.println("reconnect failed !!");
            } else {
                int order = MAX_RETRY - retry + 1;
                System.out.println("try to reconnect :" + order);
                int delay = 1 << order;
                System.err.println(new Date() + "连接失败 " + "第 " + order + " 次重连...");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                //if (LoginUtil.hadLogin(channel)) {
                    System.out.println("please input message :");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();
                    MessageRequestPacket packet = new MessageRequestPacket();
                    packet.setMessage(line);
                    channel.writeAndFlush(packet);
                //}
            }
        }).start();
    }

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new Spliter());
                        socketChannel.pipeline().addLast(new PacketDecoder());
                        socketChannel.pipeline().addLast(new LoginResponseHandler());
                        socketChannel.pipeline().addLast(new MessageResponseHandler());
                        socketChannel.pipeline().addLast(new PacketEncoder());

                    }
                });

        connect(bootstrap, "127.0.0.1", 8000, 5);

    }
}
