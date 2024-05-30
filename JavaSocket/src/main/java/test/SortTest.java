package test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.TreeSet;

public class SortTest {

    public static final Integer[] equipBagArray = new Integer[]{189, 0, 154, 199, 0, 12, 75, 0};

    private static final Comparator<Student> deadTeamCompare = (o1, o2) -> {
        if (o1.isRobot() != o2.isRobot()) {
            if (o1.isRobot()) {
                return 1;
            } else {
                return -1;
            }
        }
        return Integer.compare(o1.age, o2.age);
    };

    static final byte a = 19;

    public static void main(String[] args) {
//        sortEquipBagArray();
//        System.out.println(Arrays.toString(equipBagArray));
//        System.out.println((int) ((long) 323333245 * 10000 / 10000f));
//        System.out.println((int) ((long) 323333245 * 10000 / 10000));
//        System.out.println((long) 323333245 * 10000 / 10000);
//        System.out.println(a);

        Student student1 = new Student(1, false);
        Student student2 = new Student(1, true);
        Student student3 = new Student(2, false);
        Student student4 = new Student(2, true);

        TreeSet<Student> treeSet = new TreeSet<>(deadTeamCompare);
        treeSet.add(student1);
        treeSet.add(student2);
        treeSet.add(student3);
        treeSet.add(student4);
        System.out.println(treeSet);


//        try {
//            try {
//                throw new ConcurrentModificationException();
//            } catch (Exception  e){
//                System.out.println("inner exc" + e.getMessage());
//            }
//        } catch (Exception e) {
//            System.out.println("out exc" + e.getMessage());
//        }
    }

    private static void sortEquipBagArray() {
        int lastZeroIndex = equipBagArray.length;
        for (int i = 0; i < equipBagArray.length; i++) {
            if (lastZeroIndex <= i) {
                break;
            }
            int t = equipBagArray[i];
            int c = 0;
            if (t == 0) {
                for (int j = lastZeroIndex - 1; j > 0; j--) {
                    int h = equipBagArray[j];
                    if (h != 0) {
                        c = h;
                        equipBagArray[j] = 0;
                        lastZeroIndex = j;
                        break;
                    }
                }
            }
            if (c > 0) {
                int groupType = 10;
                int groupNum = 0;
                int nextGroupIndex = 0;

                for (int j = 0; j < i; j++) {
                    int tempValue = equipBagArray[j];
                    int dd = tempValue / groupType;
                    int cdd = tempValue / groupType;
                    if (dd > 0 && dd < 10) {
                        groupNum++;
                        nextGroupIndex += 0;
                    }
                }
            }
        }
        // equipBagArray[i] = c;
        // Arrays.sort(equipBagArray, 0, lastZeroIndex);
        //Arrays.sort(equipBagArray,0, lastZeroIndex, (e1, e2) -> e1/100 == e2/100 ? Integer.compare(e2, e1) : Integer.compare(e1/100, e2/100));
    }

    public static class Student{
        int age;
        boolean robot;

        public Student(int age, boolean robot){
            this.age = age;
            this.robot = robot;
        }

        public void getAge(){

        }

        public boolean isRobot() {
            return robot;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "age=" + age +
                    ", robot=" + robot +
                    '}';
        }
    }




}
