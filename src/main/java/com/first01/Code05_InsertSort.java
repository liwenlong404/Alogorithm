package com.first01;

/**
 * @Description: 插入排序
 * @author: li
 * @create: 2022-05-06 16:23
 */
public class Code05_InsertSort {
    public static void main(String[] args) {
        int[] arr={7,5,6,3,7,1,7,5,1,0,3,6,9,6,4};
        printArray(arr);
        System.out.println("insertSort");
        insertSort(arr);
        printArray(arr);

    }


    public static void insertSort(int[] arr){

        if (arr==null||arr.length<2){
            return;
        }

        for (int i=1;i<arr.length;i++){
            // 0 ~ i 做到有序
            while (i-1>=0&&arr[i]<arr[i-1]){
                swap(arr, i,i-1);
                i--;
            }
        }
    }


    /**
     * 将数组arr中下标i和下标j的值交换
     * @author: Li
     * @dateTime: 2022/5/6 15:20
     */
    public static void swap(int[] arr,int i,int j){
        // arr[i] = arr[i] ^ arr[j];
        // arr[j] = arr[i] ^ arr[j];
        // arr[i] = arr[i] ^ arr[j];
        // 不能使用^交换值，因为如果第i个数，本身就是最小值。即i==j，使用异或交换就会异或成为0
        int tem=arr[i];
        arr[i]=arr[j];
        arr[j]=tem;
    }


    public static void printArray(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
}
