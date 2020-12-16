//package redis.redissonUtil.kryo;
//
//import com.esotericsoftware.kryo.Kryo;
//import com.esotericsoftware.kryo.io.Input;
//import com.esotericsoftware.kryo.io.Output;
//
///**
// * 封装了Kryo的input和output的获取,让input和output可重复利用
// *
// * @author xiezuojie
// */
//public class KryoWrap {
//    private Kryo kryo;
//    private Input input;
//    private Output output;
//
//    KryoWrap(Kryo kryo) {
//        this.kryo = kryo;
//        this.input = new Input();
//        this.output = new Output(KryoUtil.outputBufferSize, KryoUtil.outputMaxBufferSize);
//    }
//
//    Kryo getKryo() {
//        return kryo;
//    }
//
//    /**
//     * input在使用时必须先调用{@link Input#setBuffer(byte[])}
//     *
//     * @return {@link Input}
//     */
//    Input getInput() {
//        return input;
//    }
//
//    /**
//     * output已经{@link Output#reset()}过,使用时不能在一次获取后还没有使用完时再获取第二次,
//     * 因为第二次获取将会把第一次获取的output的标记改变,使已写入的数据被清除
//     *
//     * @return {@link Output}
//     */
//    Output getOutput() {
//        // 适应配置MaxBufferSize改变
//        if (output.getMaxCapacity() != KryoUtil.outputMaxBufferSize) {
//            output = new Output(KryoUtil.outputBufferSize, KryoUtil.outputMaxBufferSize);
//        } else {
//            output.reset();
//        }
//        return output;
//    }
//}
