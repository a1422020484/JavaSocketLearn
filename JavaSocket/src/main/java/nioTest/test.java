package nioTest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class test {
	public static void main(String[] args) {
		ByteBuf buf = Unpooled.buffer();
		buf.writeByte(126);
		
		byte[] tBytes = new byte[buf.writerIndex()];
		buf.getBytes(0, tBytes);
		
		ByteBuf buf2 = Unpooled.wrappedBuffer(tBytes);
						
		System.out.println(buf2.readByte());
	}
}
