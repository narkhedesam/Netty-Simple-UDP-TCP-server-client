package com.sam.netty_udp.server;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * {@link NettyServer} is the UDP server class.
 * @author Sameer Narkhede See <a href="https://narkhedesam.com">https://narkhedesam.com</a>
 * @since Sept 2020
 * 
 */
public class NettyServer {

	private final EventLoopGroup bossLoopGroup;
	
	private final ChannelGroup channelGroup;
	
	private final Class<? extends PipelineFactory> pipelineFactoryClass;
	
	/**
	 * Initialize the netty server class
	 * @param pipelineFactoryType {@link Class} of the piprline factory type
	 */
	public NettyServer(Class <? extends PipelineFactory> pipelineFactoryType) {
		// Initialization private members
		
		this.bossLoopGroup = new NioEventLoopGroup();

		this.channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
		
		this.pipelineFactoryClass = pipelineFactoryType;
	}
	
	
	/**
	 * Startup the TCP server 
	 * 
	 * @param port port of the server
	 * @throws Exception if any {@link Exception}
	 */
	public final void startup(int port) throws Exception {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(bossLoopGroup)
        .channel(NioDatagramChannel.class)
        .option(ChannelOption.AUTO_CLOSE, true)
        .option(ChannelOption.SO_BROADCAST, true);

        PipelineFactory pipelineFactory = (PipelineFactory) pipelineFactoryClass.newInstance();
        
        @SuppressWarnings("rawtypes")
		ChannelInitializer initializer = pipelineFactory.createInitializer();
        
        bootstrap.handler(initializer);

        try {
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            channelGroup.add(channelFuture.channel());
        } catch (Exception e) {
            shutdown();
            throw e;
        }
    }

    /**
     * Shutdown the server 
 	 * @throws Exception
     */
    public final void shutdown() throws Exception {
        channelGroup.close();
        bossLoopGroup.shutdownGracefully();
    }
	
}
