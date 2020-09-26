package com.sam.netty_udp.server;

import java.net.InetSocketAddress;
import java.util.List;

import com.sam.netty_udp.entity.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;


/**
 * {@link MessageDecoder} is the Message decoder from byte to string using {@link MessageToMessageDecoder}.
 * @author Sameer Narkhede See <a href="https://narkhedesam.com">https://narkhedesam.com</a>
 * @since Sept 2020
 * 
 */
public class MessageDecoder extends MessageToMessageDecoder<DatagramPacket> {

	@Override
	protected void decode(ChannelHandlerContext ctx, DatagramPacket packet, List<Object> out) throws Exception {
		
		InetSocketAddress sender = packet.sender();
		
		ByteBuf in = packet.content();
		int readableBytes = in.readableBytes();
		if (readableBytes <= 0) {
			return;
		}

		String msg = in.toString(CharsetUtil.UTF_8);
		in.readerIndex(in.readerIndex() + in.readableBytes());
		
		Message message = new Message();
		message.setMessage(msg);
		message.setSender(sender);
		
		out.add(message);
		
	}

}
