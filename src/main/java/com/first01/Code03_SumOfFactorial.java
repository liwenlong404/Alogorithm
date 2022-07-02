package com.first01;

/**
 * @Description: 计算1~N的阶乘
 * @author: li
 * @create: 2022-05-06 14:47
 */
public class Code03_SumOfFactorial {
    public static void main(String[] args) {
        System.out.println(p(3));
    }

    /**
     * 计算1~N的阶乘：1!+2!+3!+4!+······+N!
     * @author: Li
     * @dateTime: 2022/5/6 14:48
     */
    public static int p(int num){
        int res=0;
        int tem=1;
        for (int i = 1; i <=num; i++) {
            tem*=i;
            res+=tem;
        }
        return res;
    }

}
