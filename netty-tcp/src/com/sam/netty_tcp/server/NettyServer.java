package com.sam.netty_tcp.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * {@link NettyServer} is the TCP server class.
 * @author Sameer Narkhede See <a href="https://narkhedesam.com">https://narkhedesam.com</a>
 * @since Sept 2020
 * 
 */
public class NettyServer {

	private final EventLoopGroup bossLoopGroup;
	
	private final EventLoopGroup workerLoopGroup;
	
	private final ChannelGroup channelGroup;
	
	private final Class<? extends PipelineFactory> pipelineFactoryClass;
	
	/**
	 * Initialize the netty server class
	 * @param pipelineFactoryType {@link Class} of the piprline factory type
	 */
	public NettyServer(Class <? extends PipelineFactory> pipelineFactoryType) {
		// Initialization private members
		
		this.bossLoopGroup = new NioEventLoopGroup();

		this.workerLoopGroup = new NioEventLoopGroup();
  
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
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossLoopGroup, workerLoopGroup)
        .channel(NioServerSocketChannel.class)
        .option(ChannelOption.SO_BACKLOG, 1024)
        .option(ChannelOption.AUTO_CLOSE, true)
        .option(ChannelOption.SO_REUSEADDR, true)
        .childOption(ChannelOption.SO_KEEPALIVE, true)
        .childOption(ChannelOption.TCP_NODELAY, true);

        PipelineFactory pipelineFactory = (PipelineFactory) pipelineFactoryClass.newInstance();
        
        @SuppressWarnings("rawtypes")
		ChannelInitializer initializer = pipelineFactory.createInitializer();
        
        bootstrap.childHandler(initializer);

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
        workerLoopGroup.shutdownGracefully();
    }
	
}
