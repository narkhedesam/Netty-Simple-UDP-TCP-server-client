package com.sam.netty_tcp.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;


/**
 * {@link MessagePipelineFactory} is the pipeline factory type class.
 * @author Sameer Narkhede See <a href="https://narkhedesam.com">https://narkhedesam.com</a>
 * @since Sept 2020
 * 
 */
public class MessagePipelineFactory implements PipelineFactory{
	private final int availableProcessors;
    private final EventExecutorGroup executors;
    
    
    /**
     * Constructor fott {@link MessagePipelineFactory}
     */
    public MessagePipelineFactory() {
        availableProcessors = Runtime.getRuntime().availableProcessors();
        executors = new DefaultEventExecutorGroup(availableProcessors);
    }
    
	/**
	 *	Pipeline Factory method for channel initialization
	 */
	@Override
	public ChannelInitializer<SocketChannel> createInitializer() {
		
		return new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// Create chanel pipeline
            	ChannelPipeline pipeline = ch.pipeline();
            	
            	final MessageDecoder decoder = new MessageDecoder();
            	
            	pipeline.addLast("decoder", decoder);
            	
            	final MessageHandler handler = new MessageHandler();
            	
            	pipeline.addLast(executors, "handler", handler);
				
			}
			
		};
	}

	
	
}
