package com.first03;

/**
 * @Description: 二分查找
 * @author: li
 * @create: 2022-05-07 20:13
 */
public class Code01_BSExist {


    /**
     * 有序数组二分查找
     * @author: Li
     * @dateTime: 2022/5/7 20:16
     */
    public static boolean find(int[] arr,int num){
        if (arr.length==0||arr==null){
            return false;
        }
        int L=0;
        int R =arr.length-1;

        //arr[0·····N-1]  num  arr[L·····R]  num
        while (L<=R){
            int mid=L+(R-L)>>1;
            if (arr[mid]==num){
                return true;
            }else if (arr[mid]<num){
                //如果没找到，调整的是L,R的位置，然后重新循环会得到新的mid
                L=mid+1;
            }else {
                R=mid-1;
            }
        }
        return false;

    }

}
