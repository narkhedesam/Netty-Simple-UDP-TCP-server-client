package com.sam.netty_udp.server;


import com.sam.netty_udp.entity.Message;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

/**
 * {@link MessageHandler} is the UDP Message Handler and reply the client after message parsing.
 * @author Sameer Narkhede See <a href="https://narkhedesam.com">https://narkhedesam.com</a>
 * @since Sept 2020
 * 
 */
public class MessageHandler extends SimpleChannelInboundHandler<Message> {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		cause.printStackTrace();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

		handleMessage(ctx, msg);

	}

	/**
	 * Actual Message handling and reply to server.
	 * 
	 * @param ctx  {@link ChannelHandlerContext}
	 * @param msg  {@link Message}
	 */
	private void handleMessage(ChannelHandlerContext ctx, Message msg) {

		System.out.println("Message Received : " + msg.getMessage());

		ByteBuf buf = Unpooled.wrappedBuffer("Hey Sameer Here!!!!".getBytes());

		// Send reply
		final WriteListener listener = new WriteListener() {
			@Override
			public void messageRespond(boolean success) {
				System.out.println(success ? "reply success" : "reply fail");
			}
		};

		ctx.channel().writeAndFlush(new DatagramPacket(buf, msg.getSender())).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (listener != null) {
					listener.messageRespond(future.isSuccess());
				}
			}
		});
	}

	/**
	 * {@link WriteListener} is the lister message status interface.
	 * @author Sameer Narkhede See <a href="https://narkhedesam.com">https://narkhedesam.com</a>
	 * @since Sept 2020
	 * 
	 */
	public interface WriteListener {
		void messageRespond(boolean success);
	}

}
