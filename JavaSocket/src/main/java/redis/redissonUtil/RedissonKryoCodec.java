package redis.redissonUtil;

import org.redisson.client.codec.BaseCodec;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;

import io.netty.buffer.Unpooled;
import redis.redissonUtil.kryo.KryoUtil;

/**
 * Redission的Kryo编解码类
 * <p>
 * Map的key默认使用{@link org.redisson.client.codec.StringCodec}
 * <p>
 * <b>使用此类时要注意,序列化后的数据包含类信息,所以如果类名或类路径发生变化将导致反序列化失败,
 * 抛出 {@link java.lang.ClassNotFoundException} </b>
 *
 * @author xiezuojie
 */
public class RedissonKryoCodec extends BaseCodec {

    private Codec mapKeyCodec;

    public RedissonKryoCodec() {
        this.mapKeyCodec = StringCodec.INSTANCE;
    }

    public RedissonKryoCodec(Codec mapKeyCodec) {
        this.mapKeyCodec = mapKeyCodec;
    }

    private final Decoder<Object> decoder = (buf, state) -> {
        try {
            byte[] result = new byte[buf.readableBytes()];
            buf.readBytes(result);
            return KryoUtil.readClassAndObject(result);
        } catch (Exception e) {
            throw new RedissonKryoCodecException(e);
        }
    };

    private final Encoder encoder = in -> {
        try {
            return Unpooled.wrappedBuffer(KryoUtil.writeClassAndObject(in));
        } catch (Exception e) {
            throw new RedissonKryoCodecException(e);
        }
    };

    @Override
    public Decoder<Object> getValueDecoder() {
        return decoder;
    }

    @Override
    public Encoder getValueEncoder() {
        return encoder;
    }

    @Override
    public Decoder<Object> getMapKeyDecoder() {
        return mapKeyCodec.getValueDecoder();
    }

    @Override
    public Encoder getMapKeyEncoder() {
        return mapKeyCodec.getValueEncoder();
    }

    public static class RedissonKryoCodecException extends RuntimeException {

        private static final long serialVersionUID = 917236149895014947L;

        public RedissonKryoCodecException(Throwable cause) {
            super(cause);
        }

    }
}
