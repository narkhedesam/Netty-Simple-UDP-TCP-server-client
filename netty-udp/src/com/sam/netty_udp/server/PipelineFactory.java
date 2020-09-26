package com.sam.netty_udp.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.DatagramChannel;

/**
 * {@link PipelineFactory} is the pipeline Factory interface.
 * @author Sameer Narkhede See <a href="https://narkhedesam.com">https://narkhedesam.com</a>
 * @since Sept 2020
 * 
 */
public interface PipelineFactory {

	// Socket Channel initializer
	ChannelInitializer<DatagramChannel> createInitializer();
}
