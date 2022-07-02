package com.study01;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @Description:
 * @author: li
 * @create: 2022-05-16 12:08
 */
public class KM {


    /**
     * 验证方法，通过hash实现。数为key，出现次数为value。
     *
     * @author: Li
     * @dateTime: 2022/5/16 12:43
     */
    public static int test(int[] arr, int k, int m) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        int ans = 0;
        //循环比对value找到出现次数为k的数，返回作为key的那个数
        for (int num : map.keySet()) {
            if (map.get(num) == k) {
                ans = num;
                break;
            }
        }
        return ans;
    }


    /**
     * 前提：请保证arr中，只有一种数出现了K次，其他数都出现了M次
     * 通过一个32位的数组，记录所有数的位信息。因为K<M,所以位信息为M的倍数，即可以整除的M次的数。就是所有M次的数
     * 不可以整除的就是K次的数
     *
     * @author: Li
     * @dateTime: 2022/5/16 12:09
     */
    public static int onlyKTimes(int[] arr, int k, int m) {
        //记录数的位信息
        //t[0] 就是int数   0二进制位置的1   出现的次数
        int[] t = new int[32];


        //记录所有数的位信息
        //即使存在2个for循环，但第二个for循环固定为32次。所以时间复杂度仍为O(n)
        for (int num : arr) {
            for (int i = 0; i <= 31; i++) {
                t[i] += (num >> i) & 1;
            }
        }

        int ans = 0;

        for (int i = 0; i <= 31; i++) {
            if (t[i] % m != 0) {
                ans |= (1 << i);
            }
        }
        return ans;

    }


    /**
     * 随机生成数组
     * @author: Li
     * @dateTime: 2022/5/16 13:32
     * @param maxKinds 数的最大的种数
     */
    public static int[] randomArray(int maxKinds, int range, int k, int m) {
        int ktimeNum = randomNumber(range);
        // K次 times
        int times = k;
        // 一共几种数  最小2
        int numKinds = (int) (Math.random() * maxKinds) + 2;
        //数组长度， k * 1 + (numKinds - 1) * m
        int[] arr = new int[times + (numKinds - 1) * m];
        int index = 0;

        //将k填入数组
        for (; index < times; index++) {
            arr[index] = ktimeNum;
        }
        //种类减一
        numKinds--;


        HashSet<Integer> set = new HashSet<>();
        set.add(ktimeNum);


        while (numKinds != 0) {
            int curNum = 0;
            do {
                curNum = randomNumber(range);
            } while (set.contains(curNum));
            set.add(curNum);
            numKinds--;
            //填随机random出来的这个数
            for (int i = 0; i < m; i++) {
                arr[index++] = curNum;
            }
        }
        // arr 填好了

        //因为数组是有规律的，打乱它
        for (int i = 0; i < arr.length; i++) {
            // i 位置的数，我想随机和j位置的数做交换
            int j = (int) (Math.random() * arr.length);// 0 ~ N-1
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }


    // 为了测试
    // [-range, +range]
    public static int randomNumber(int range) {
        return (int) (Math.random() * (range + 1)) - (int) (Math.random() * (range + 1));
    }

    public static void main(String[] args) {
        int kinds = 5;
        int range = 30;
        int testTime = 100000;
        int max = 9;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int a = (int) (Math.random() * max) + 1; // a 1 ~ 9
            int b = (int) (Math.random() * max) + 1; // b 1 ~ 9
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            // k < m
            if (k == m) {
                m++;
            }
            int[] arr = randomArray(kinds, range, k, m);
            int ans1 = test(arr, k, m);
            int ans2 = onlyKTimes(arr, k, m);
            int ans3 = onlyKTimes(arr, k, m);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(ans1);
                System.out.println(ans3);
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");
    }
}
