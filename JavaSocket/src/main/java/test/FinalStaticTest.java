package test;

import java.util.ArrayList;
import java.util.List;

public class FinalStaticTest {

    public static final ThreadLocal<List<Integer>> testStaticList1 = ThreadLocal.withInitial(ArrayList::new);

    public final ThreadLocal<List<Integer>> testStaticList2 = ThreadLocal.withInitial(ArrayList::new);


    public static void main(String[] args) {
        System.out.println(FinalStaticTest.testStaticList1.hashCode());

        FinalStaticTest finalStaticTest1 = new FinalStaticTest();
        FinalStaticTest finalStaticTest2 = new FinalStaticTest();
        FinalStaticTest finalStaticTest3 = new FinalStaticTest();

        System.out.println(finalStaticTest1.testStaticList1.hashCode() + " " + finalStaticTest1.testStaticList2.hashCode());
        System.out.println(finalStaticTest2.testStaticList1.hashCode() + " " + finalStaticTest2.testStaticList2.hashCode());
        System.out.println(finalStaticTest3.testStaticList1.hashCode() + " " + finalStaticTest3.testStaticList2.hashCode());


    }
}
