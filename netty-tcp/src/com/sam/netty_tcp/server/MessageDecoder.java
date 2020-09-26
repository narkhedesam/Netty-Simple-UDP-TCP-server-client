package com.sam.netty_tcp.server;

import java.util.List;

import com.sam.netty_tcp.entity.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;


/**
 * {@link MessageDecoder} is the Message decoder from byte to string using {@link ByteToMessageDecoder}.
 * @author Sameer Narkhede See <a href="https://narkhedesam.com">https://narkhedesam.com</a>
 * @since Sept 2020
 * 
 */
public class MessageDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		int readableBytes = in.readableBytes();
		if (readableBytes <= 0) {
			return;
		}

		String msg = in.toString(CharsetUtil.UTF_8);
		in.readerIndex(in.readerIndex() + in.readableBytes());
		
		Message message = new Message();
		message.setMessage(msg);
		
		
		out.add(message);

	}

}
