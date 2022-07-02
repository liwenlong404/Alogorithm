package com.first02;

/**
 * @Description:
 * @author: li
 * @create: 2022-05-06 18:27
 */
public class Test {
    public static void main(String[] args) {
        int testTimes = 10000000;
        int[] counts = new int[60];
        for (int i = 0; i < testTimes; i++) {
            int num = p4();
            counts[num]++;
        }

        for (int i = 17; i < 57; i++) {
            System.out.println(i + "这个数，出现了 " + counts[i] + " 次");
        }

    }

    //等概率返回12 ~ 26
    public static int p1(){
        return  ((int)(Math.random()*15)+12);
    }

    //等概率返回0,1的函数p2
    public static int p2() {
        int tmp;
        do {
            tmp = p1();
        } while (tmp==19);
        return tmp < 19 ? 0 : 1;
    }

    //000000~111111等概率返回，即0~63等概率返回
    public static int p3() {
        return (p2() << 6) + (p2() << 5) + (p2() << 4)+ (p2() << 3)+ (p2() << 2)+ (p2() << 1)+ (p2() <<0);
    }

    //0~39的等概率返回加上17
    public static int p4() {
        int tmp;
        do {
            tmp = p3();
        } while (tmp > 39);
        return tmp+17;
    }
}
