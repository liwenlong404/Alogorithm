package com.first02;

/**
 * @Description: random随机
 * @author: li
 * @create: 2022-05-06 18:46
 */
public class Code2_Rand {

    public static void main(String[] args) {

        //问题描述：有一个可以等概率返回1~5的函数p1，使用函数p1，等概率返回1~7的整数
        //只能使用函数p1构建


        /*
        测试p2，等概率返回0,1
         int testTimes = 10000000;
        int count = 0;
        for (int i = 0; i < testTimes; i++) {
            if (p2()==1){
                count++;
            }
        }
        System.out.println((double) count / (double) testTimes);

         */


        /*
        测试p4等概率返回0~6
        int testTimes = 10000000;
        int[] counts = new int[8];
        for (int i = 0; i < testTimes; i++) {
           int num=p4();
           counts[num]++;
        }

        for (int i = 0; i < 8; i++) {
            System.out.println(i + "这个数，出现了 " + counts[i] + " 次");
        }
         */


        //1~7等概率返回
        /*
        int testTimes = 10000000;
        int[] counts = new int[8];
        for (int i = 0; i < testTimes; i++) {
            int num = g();
            counts[num]++;
        }

        for (int i = 0; i < 8; i++) {
            System.out.println(i + "这个数，出现了 " + counts[i] + " 次");
        }

         */


        int testTimes = 10000000;
        int[] counts = new int[28];
        for (int i = 0; i < testTimes; i++) {
            int num = p1();
            counts[num]++;
        }

        for (int i = 0; i < 28; i++) {
            System.out.println(i + "这个数，出现了 " + counts[i] + " 次");
        }

        //从a~b的随机到c~d 的随机
        //17~56等概率返回，思路

        //1.将给定函数改造为0,1等概率发生器
        //2.通过0,1  构造2进制数 0~63
        //3.   17~56==(0~39)+17 即等概率返回0~39
        // 因为p2为0~63，do while循环。则不再0~39之内，重新生成
    }

    /**
     * 等概率返回1~5的整数
     *
     * @author: Li
     * @dateTime: 2022/5/6 18:48
     */
    public static int p1() {
        return ((int) (Math.random() * 5)) + 1;
    }

    /**
     * 将函数p1，改造为等概率返回0,1的函数
     *
     * @author: Li
     * @dateTime: 2022/5/6 18:53
     */
    public static int p2() {
        int tmp;

        //通过p1，等概率返回一个不是3的数，1,2,4,5各位25%
        do {
            tmp = p1();
        } while (tmp == 3);

        //1,2为0   3,4为1   概率各50%
        return tmp < 3 ? 0 : 1;
    }

    /**
     * 000~111等概率返回，即0~7等概率返回
     *
     * @author: Li
     * @dateTime: 2022/5/6 19:07
     */
    public static int p3() {
        return (p2() << 2) + (p2() << 1) + (p2() << 0);
    }

    /**
     * 0~6等概率返回
     *
     * @author: Li
     * @dateTime: 2022/5/6 19:16
     */
    public static int p4() {
        int tmp;
        do {
            tmp = p3();
        } while (tmp == 7);
        return tmp;
    }

    public static int g() {
        return p4() + 1;
    }




    //百度面试题：给一个函数，返回 0 和 1，概率为 p 和 1-p，请你实现一个函数，使得返回 01 概率一样
    public static int x() {
        return Math.random() < 0.84 ? 0 : 1;
    }

    //通过x，等概率返回0,1
    public static int y() {
        int ans = 0;
        do {
            ans = x();
        } while (ans == x());
        //1,0或0,1  出来

        return ans;
    }


}
