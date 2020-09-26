package com.sam.netty_udp.entity;

import java.net.InetSocketAddress;

/**
 * {@link Message} is the Entity for parsed message.
 * @author Sameer Narkhede See <a href="https://narkhedesam.com">https://narkhedesam.com</a>
 * @since Sept 2020
 * 
 */
public class Message {

	
	String Message;
	InetSocketAddress sender;

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public void setSender(InetSocketAddress sender) {
		this.sender = sender;
	}
	
	public InetSocketAddress getSender() {
		return this.sender;
	}
}
