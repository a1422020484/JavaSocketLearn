package nettyTest.netty3Test.bufferbufTest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class MainByteBufTest1 {

	public static void main(String[] args) {
		ByteBuf[] byteBufArray = new ByteBuf[] { Unpooled.wrappedBuffer(new byte[] { 'i' }) };
		System.out.println(byteBufArray.length);
		
		ByteBuf byteBuf = Unpooled.wrappedBuffer(new byte[] { 'i','a','b','c' });
//		byteBuf.discardReadBytes();
		MainByteBufTest1.printReadAndWriterIndex(byteBuf);
		byteBuf.setIndex(1, 2);
		MainByteBufTest1.printReadAndWriterIndex(byteBuf);
		
	}

	public static void printReadAndWriterIndex(ByteBuf byteBuf){
//		System.out.println("readerindex == " + byteBuf.readerIndex());
//		System.out.println("writerindex == " + byteBuf.writerIndex());
		System.out.println(byteBuf.toString());
	}
}
