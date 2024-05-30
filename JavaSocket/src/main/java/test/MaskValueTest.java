package test;

import com.carrotsearch.hppc.IntIntHashMap;

public class MaskValueTest {

    public int cap = 8;
    public int mask = 7;
//    4 --     0100
//    99 - 10011001
//    8 -- 7 -     0111

    public static void main(String[] args) {
        MaskValueTest maskValueTest = new MaskValueTest();
        for (int i = 0; i < 10; i++) {
            System.out.println(maskValueTest.hashIndex(i));
        }
        IntIntHashMap intIntHashMap = new IntIntHashMap();
        for (int i = 0; i < 100; i++) {
            intIntHashMap.put(i, i);
        }
    }

    public int hashIndex(int key) {
        return hasCode(key) & mask;
    }

    public int hasCode(int key) {
        return key;
    }
}
