package nettyServer.util;

/**
 * @author xiezuojie
 */
public class TRandom {

    long seed;

    static final long multiplier = 0x5DEECE66DL;
    static final long addend = 0xBL;
    static final long mask = (1L << 48) - 1;

    TRandom() {
        this.seed = System.nanoTime();
    }

    TRandom(long seed) {
        this.seed = (seed ^ multiplier) & mask;
    }

    protected int next(int bits) {
        seed = (seed * multiplier + addend) & mask;
        return (int) (seed >>> (48 - bits));
    }

    public int nextInt() {
        return next(32);
    }

    /**
     *
     * @param bound 不包含,必须是正数
     * @return 0(包含)到bound(不包含)之间的随机整数
     */
    public int nextInt(int bound) {
        if (bound <= 0)
            throw new IllegalArgumentException("bound必须是正数!");

        int r = next(31);
        int m = bound - 1;
        if ((bound & m) == 0) {
            r = (int) ((bound * (long) r) >> 31);
        } else {
            for (int u = r; u - (r = u % bound) + m < 0; u = next(31)) ;
        }
        return r;
    }

    public static void main(String[] args) {
        TRandom TRandom = new TRandom(1);
        for (int i = 0; i < 10; i ++) {
            System.out.println(TRandom.nextInt(100));
        }
        System.out.println();
        TRandom TRandom2 = new TRandom(1);
        for (int i = 0; i < 10; i ++) {
            System.out.println(TRandom2.nextInt(100));
        }
    }
}
