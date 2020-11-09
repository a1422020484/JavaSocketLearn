//package redis.redissonUtil.kryo;
//
//import com.esotericsoftware.kryo.Kryo;
//import com.esotericsoftware.kryo.SerializerFactory;
//import com.esotericsoftware.kryo.io.Input;
//import com.esotericsoftware.kryo.io.Output;
//import org.apache.commons.codec.binary.Base64;
//
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author xiezuojie
// */
//public final class KryoUtil {
//
//    private static final String DEFAULT_ENCODING = "UTF-8";
//
//    private static ThreadLocal<KryoWrap> kryoLocal = ThreadLocal.withInitial(KryoUtil::newKryoWrap);
//
//    /**
//     * 初始Output缓冲区大小,在数据大时Kryo可自动增长,但不会超过最大缓冲区大小
//     */
//    public static int outputBufferSize = 4096;
//    /**
//     * 最大Output缓冲区大小,超过此限制时抛出异常,序列化失败
//     */
//    public static int outputMaxBufferSize = 1024 * 1024 * 2;
//
//    /**
//     * 将对象序列化为字节数组<br/>
//     * 可通过{@link #readFromBytes(byte[], Class)}反序列化为对象
//     *
//     * @param obj 对象, NOT NULL
//     * @param <T> 对象的类型
//     * @return 序列化后的字节数组
//     */
//    public static <T> byte[] writeToBytes(T obj) {
//        if (obj == null) {
//            throw new RuntimeException("obj is null");
//        }
//
//        KryoWrap kryoWrap = getInstance();
//        Kryo kryo = kryoWrap.getKryo();
//        Output output = kryoWrap.getOutput();
//        kryo.writeObject(output, obj);
//        return output.toBytes();
//    }
//
//    /**
//     * 将字节数组反序列化为对象
//     *
//     * @param byteArray 使用{@link #writeToBytes(Object)}序列化后的字节数组
//     * @param type      对象的Class
//     * @param <T>       对象的类型
//     * @return 反序列化后的对象
//     */
//    public static <T> T readFromBytes(byte[] byteArray, Class<T> type) {
//        KryoWrap wrap = getInstance();
//        Kryo kryo = wrap.getKryo();
//        Input input = wrap.getInput();
//        input.setBuffer(byteArray);
//        return kryo.readObject(input, type);
//    }
//
//    /**
//     * 将对象序列化为字节数组,并且包含类型信息<br/>
//     * 可通过{@link #readClassAndObject(byte[])}反序列化为对象
//     *
//     * @param obj 对象, NOT NULL
//     * @return 序列化后的字节数组
//     */
//    public static byte[] writeClassAndObject(Object obj) {
//        if (obj == null) {
//            throw new RuntimeException("obj is null");
//        }
//
//        KryoWrap kryoWrap = getInstance();
//        Kryo kryo = kryoWrap.getKryo();
//        Output output = kryoWrap.getOutput();
//        kryo.writeClassAndObject(output, obj);
//        return output.toBytes();
//    }
//
//    /**
//     * 将字节数组反序列化为对象,类型是序列化时所包含的类型信息
//     *
//     * @param byteArray 使用{@link #writeClassAndObject(Object)}序列化后的字节数组
//     * @return 反序列化后的对象
//     */
//    public static Object readClassAndObject(byte[] byteArray) {
//        KryoWrap wrap = getInstance();
//        Kryo kryo = wrap.getKryo();
//        Input input = wrap.getInput();
//        input.setBuffer(byteArray);
//        return kryo.readClassAndObject(input);
//    }
//
//    /**
//     * 将对象序列化为字节数组后,通过Base64编码成字符串
//     *
//     * @param obj 任意对象
//     * @param <T> 对象的类型
//     * @return 序列化后的字符串
//     */
//    public static <T> String writeToString(T obj) {
//        try {
//            return new String(Base64.encodeBase64(writeToBytes(obj)), DEFAULT_ENCODING);
//        } catch (UnsupportedEncodingException e) {
//            throw new IllegalStateException(e);
//        }
//    }
//
//    /**
//     * 将String反序列化为对象
//     *
//     * @param str  {@link #writeToString(Object)} 方法序列化后的字符串
//     * @param type 对象的 Class
//     * @param <T>  对象的类型
//     * @return 对象
//     */
//    public static <T> T readFromString(String str, Class<T> type) {
//        try {
//            return readFromBytes(Base64.decodeBase64(str.getBytes(DEFAULT_ENCODING)), type);
//        } catch (UnsupportedEncodingException e) {
//            throw new IllegalStateException(e);
//        }
//    }
//
//    /**
//     * 在Redis控制台查询到的数据是通过Kryo编码后的,不可读,此方法将控制台输出的内容返序列化得到对象
//     *
//     * @param redisCmdPrint
//     * @return Object
//     */
//    public static Object redisCmdPrintToObject(String redisCmdPrint) {
//        List<Integer> intList = new ArrayList<>();
//
//        String tstr = "";
//        int idx = -1;
//
//        /*
//         * Redis控制台输出的是ascii编码,不能显示的码以16进制显示,能显示的就正常显示
//         * ascii码表: http://ascii.911cha.com/
//         *
//         * Redis输出的16进制是以'\'开头的,例\x01,长度是4位char
//         * 过滤掉\x就得到了16进制的值
//         */
//        for (char c : redisCmdPrint.toCharArray()) {
//            if (idx >= 0 && idx <= 3) {
//                if (idx > 0) {
//                    tstr += c;
//                }
//                idx++;
//                if (idx == 3) {
////                    BigInteger bigInteger = new BigInteger(t, 16);
////                    int iv = bigInteger.intValue();
//                    int iv = Integer.parseInt(tstr, 16);
//                    // 负数是增加了256的,所以值大于127时需要减去256
//                    if (iv > 127) {
//                        iv = iv - 256;
//                    }
//                    intList.add(iv);
//                    tstr = "";
//                    idx = -1;
//                }
//                continue;
//            }
//            if (c == '\\') {
//                idx = 0;
//                continue;
//            }
//
//            intList.add((int) c);
//        }
//
//        Output output = new Output(1024, 1024 * 1024 * 10);
//        for (Integer integer : intList) {
//            output.writeByte(integer);
//        }
//        byte[] newBytes = output.toBytes();
//        output.close();
//
//        return KryoUtil.readClassAndObject(newBytes);
//    }
//
//    /**
//     * 获得当前线程的 {@link Kryo} 实例
//     *
//     * @return 当前线程的 {@link Kryo} 实例
//     */
//    public static Kryo getKryo() {
//        return kryoLocal.get().getKryo();
//    }
//
//
//    private static KryoWrap getInstance() {
//        return kryoLocal.get();
//    }
//
//    private static KryoWrap newKryoWrap() {
//        /**
//         * 不要轻易改变这里的配置！
//         * 任何一项配置的更改可能导致序列化的格式发生变化, 导致已存在的数据不能被正确反序列化
//         */
//
//        Kryo kryo = new Kryo();
//
//        // 关闭循环引用支持可获得更好的性能
//        // 如果不开启,在对象有循环引用时,会栈溢出,
//        // 但如果存在循环引用,那么应该是设计上不合理,需要开发者优化结构
//        kryo.setReferences(false);
//
//        // 不要求强制注册类
//        // (注册行为无法保证多个JVM 内同一个类的注册编号相同,而且业务系统中大量的Class也难以一一注册)
//        // 1. 在手动注册时,如果不自己分配ID,那么Kryo将自动分配,此时问题来了,
//        //    Kryo并没有保证在多个JVM下同一个类分配的ID一定相同
//        // 强制注册虽然性能更好,但每个类都要手动注册实在是太麻烦,这点性能损耗也在可接受范围,所以不强制
//        kryo.setRegistrationRequired(false);
//
//        /*
//         * Kryo在反序列化创建新实例时需要Class有无参数构造方法,
//         * 在没有找到无参数构造方法时,会抛出异常,此时可以选择使用Objenesis提供的StdInstantiatorStrategy来实例化,
//         * StdInstantiatorStrategy实例化对象不需要调用构造方法,但为了更好的移植性,建议类都有无参数构造方法
//         */
////        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
//
//        // 为了更好的兼容性,使用CompatibleFieldSerializer,避免结构的经常变化导致数据序列化/反序列化异常
//        SerializerFactory.CompatibleFieldSerializerFactory defaultFactory = new SerializerFactory.CompatibleFieldSerializerFactory();
//        defaultFactory.getConfig().setReadUnknownTagData(false);
//        defaultFactory.getConfig().setChunkedEncoding(true); // 支持删除字段
//
//        kryo.setDefaultSerializer(defaultFactory);
//
//        KryoWrap wrap = new KryoWrap(kryo);
//        return wrap;
//    }
//
//}
