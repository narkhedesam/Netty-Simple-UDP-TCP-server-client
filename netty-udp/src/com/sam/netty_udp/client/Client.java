package com.sam.netty_udp.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;


/**
 * Client is the Netty UDP client.
 * @author Sameer Narkhede See <a href="https://narkhedesam.com">https://narkhedesam.com</a>
 * @since Sept 2020
 * 
 */
public class Client {
    int port;
    Channel channel;
    EventLoopGroup workGroup = new NioEventLoopGroup();

    /**
     * Constructor
     * @param port {@link Integer} port of server
     */
    public Client(int port){
        this.port = port;
    }

    /**
     * 	Startup the client
     * 
     * @return {@link ChannelFuture} 
     * @throws Exception
     */
    public ChannelFuture startup() throws Exception {
        try{
            Bootstrap b = new Bootstrap();
            b.group(workGroup);
            b.channel(NioDatagramChannel.class);
            b.handler(new ChannelInitializer<DatagramChannel>() {
                protected void initChannel(DatagramChannel datagramChannel) throws Exception {
                	datagramChannel.pipeline().addLast(new NettyHandler());
                }
            });
            ChannelFuture channelFuture = b.connect("127.0.0.1", this.port).sync();
            this.channel = channelFuture.channel();

            return channelFuture;
        }finally{
        }
    }
    
    /**
     *	Shutdown a client 
     */
    public void shutdown(){
        workGroup.shutdownGracefully();
    }
}
