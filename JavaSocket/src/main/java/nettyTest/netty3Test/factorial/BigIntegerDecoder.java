package nettyTest.netty3Test.factorial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.math.BigInteger;
import java.util.List;

public class BigIntegerDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() < 5) {
			return;
		}

		in.markReaderIndex();

		int magicNumber = in.readUnsignedByte();
		if (magicNumber != 'F') {
			in.resetReaderIndex();
			throw new CorruptedFrameException("Invalid magic number");
		}
		// Wait until the whole data is available.
		int dataLength = in.readInt();
		if (in.readableBytes() < dataLength) {
			in.resetReaderIndex();
			return;
		}

		// Convert the received data into a new BigInteger.
		byte[] decoded = new byte[dataLength];
		in.readBytes(decoded);

		out.add(new BigInteger(decoded));
	}

}
