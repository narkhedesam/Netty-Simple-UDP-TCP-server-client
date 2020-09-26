package com.sam.netty_tcp;

import com.sam.netty_tcp.server.MessagePipelineFactory;
import com.sam.netty_tcp.server.NettyServer;

/**
 * {@link Entry} class is the main class for starting the TCP server.
 *  
 * @author Sameer Narkhede See <a href="https://narkhedesam.com">https://narkhedesam.com</a>
 * @since Sept 2020
 * 
 */
public class Entry {
	public static void main(String[] args) {
		
		System.out.println("In Main Method");
		
		// Create netty server 
		try {
			
			System.out.println("Starting up the server on port 11111 ...");
			
			new NettyServer(MessagePipelineFactory.class).startup(11111);
			
			System.out.println("TCP server started on port 11111 ...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
