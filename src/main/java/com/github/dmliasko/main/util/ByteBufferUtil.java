package com.github.dmliasko.main.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ByteBufferUtil {
	
	/**
	 * @param prealocated buffer with suitable size; allocate with ByteBuffer.allocate() or ByteBuffer.allocateDirect()
	 * @param channel - readable channel
	 * @return ASCII String
	 * @throws IOException 
	 */
	public static String readFromChannel(ByteBuffer buffer, SocketChannel channel) throws IOException {
		int limit = buffer.limit();
		
		channel.read(buffer);
		buffer.flip();
		
		byte[] bytes = new byte[buffer.limit()];
		buffer.get(bytes, 0, buffer.limit());
		
		// reset buffer
		buffer.position(0);
		buffer.limit(limit);
		
		// telnet sends ASCII
		return new String(bytes, "ASCII");
	}
	
}
