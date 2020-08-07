package nettyTest.netty4test1.byteBuf;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author yangxp
 * 2020年4月8日下午4:39:15
 * <p>描述：
 */
public class ByteBufCreate {
	public static void main(String[] args) {
		// 分别用来分配池化的和未池化的ByteBuf
		ByteBuf buf1 = Unpooled.buffer();
		ByteBuffer byteBuffer = ByteBuffer.allocate(10);
		
		byte[] byte1 = new byte[] {1,2,3,4,5};
		ByteBuf buf2 = Unpooled.wrappedBuffer(byte1);
		
		int n = 'A' + '6' - '3';
		System.out.println(n);
	}
	
	
	public static void printByteBuf(ByteBuf buf) {
		if (buf.hasArray()) {
			System.out.println("buffer3 array: " + buf.array());
			System.out.println("Buffer3 array offset: " + buf.arrayOffset());
		}
		System.out.println("Capacity: " + buf.capacity());
//		System.out.println("Limit: " + buf.);
//		System.out.println("Position: " + buf.position());
//		System.out.println("Remaining: " + buf.remaining());
	}
}
