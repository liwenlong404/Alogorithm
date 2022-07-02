package com.first01;

/**
 * @Description:  位运算
 * @author: li
 * @create: 2022-05-06 13:55
 */
public class Code01_PrintB {


    /**
     * 打印整数二进制数
     * @author: Li
     * @dateTime: 2022/5/6 13:59
     */
    public static void print(int num){
        for (int i = 31; i >=0 ; i--) {
            System.out.print((num&(1<<i))==0?"0":"1");
        }
    }




    public static void main(String[] args) {
        //32
        // int num=4;
        // print(num);

        // int a=Integer.MAX_VALUE;
        // System.out.println(a);
        // print(a);
        //int范围 -2^31~2^31-1，最高为为符号位

        //负数，最高位为1，后面的取反加1
        //11111111111111111111111111111111
        //print(-1);

        //右移>>   左边用符号位补
        //右移>>>      用0补


        //~N+1就是N的相反数

        int a=-5;
        int b=(~a+1);
        System.out.println(b);

    }
}
