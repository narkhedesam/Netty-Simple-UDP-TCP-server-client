package com.sam.netty_tcp;

import com.sam.netty_tcp.client.Client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * Client Main is the {@link Client} handler.
 * @author Sameer Narkhede See <a href="https://narkhedesam.com">https://narkhedesam.com</a>
 * @since Sept 2020
 * 
 */
public class ClientMain {
	
	
	public static void main(String[] args) {
		
		try {
	        // Create a client
			System.out.println("Creating new Client");
			
			Client client = new Client(11111);
	        ChannelFuture channelFuture = client.startup();
	        
	        System.out.println("New Client is created");
	        
	        // wait for 5 seconds
	        Thread.sleep(5000);
	        // check the connection is successful 
	        if (channelFuture.isSuccess()) {
	        	// send message to server
	            channelFuture.channel().writeAndFlush(Unpooled.wrappedBuffer("Hello".getBytes())).addListener(new ChannelFutureListener() {
	    			@Override
	    			public void operationComplete(ChannelFuture future) throws Exception {
	    				System.out.println(future.isSuccess()? "Message sent to server : Hello" : "Message sending failed");
	    			}
	    		});
	        }
	        // timeout before closing client
	        Thread.sleep(5000);
	        // close the client
	        client.shutdown();
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    	System.out.println("Try Starting Server First !!");
	    }
	    finally {
	    	
	    }
	}
	
}
