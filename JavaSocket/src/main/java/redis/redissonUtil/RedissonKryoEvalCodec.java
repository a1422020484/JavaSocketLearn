//package redis.redissonUtil;
//
//import org.redisson.client.codec.BaseCodec;
//import org.redisson.client.protocol.Decoder;
//import org.redisson.client.protocol.Encoder;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.ByteBufAllocator;
//import io.netty.buffer.Unpooled;
//import io.netty.util.CharsetUtil;
//import redis.redissonUtil.kryo.KryoUtil;
//
///**
// * 适用于EVAL命令时的Codec
// * <p>
// * 可将EVAL的有些ARGV以String类型编码，将String包装成WrapString即可
// *
// * @author xiezuojie
// */
//public class RedissonKryoEvalCodec extends BaseCodec {
//
//    public static final RedissonKryoEvalCodec INSTANCE = new RedissonKryoEvalCodec();
//
//    public static WrapString wrapString(String string) {
//        return new WrapString(string);
//    }
//
//    private final Decoder<Object> decoder = (buf, state) -> {
//        try {
//            byte[] result = new byte[buf.readableBytes()];
//            buf.readBytes(result);
//            return KryoUtil.readClassAndObject(result);
//        } catch (Exception e) {
//            throw new RedissonKryoCodec.RedissonKryoCodecException(e);
//        }
//    };
//
//    private final Encoder encoder = in -> {
//        if (in instanceof RedissonKryoEvalCodec.WrapString) {
//            ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
//            out.writeCharSequence(((WrapString) in).string, CharsetUtil.UTF_8);
//            return out;
//        } else {
//            try {
//                return Unpooled.wrappedBuffer(KryoUtil.writeClassAndObject(in));
//            } catch (Exception e) {
//                throw new RedissonKryoCodec.RedissonKryoCodecException(e);
//            }
//        }
//    };
//
//    @Override
//    public Decoder<Object> getValueDecoder() {
//        return decoder;
//    }
//
//    @Override
//    public Encoder getValueEncoder() {
//        return encoder;
//    }
//
//    private static class WrapString {
//        private String string;
//
//        WrapString(String string) {
//            this.string = string;
//        }
//    }
//
//}
