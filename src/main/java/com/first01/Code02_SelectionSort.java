package com.first01;

/**
 * @Description: 选择排序
 * @author: li
 * @create: 2022-05-06 15:03
 */
public class Code02_SelectionSort {

    public static void main(String[] args) {
        int[] arr={7,5,6,3,7,1,7,5,1,0,3,6,9,6,4};
        printArray(arr);
        System.out.println("selectSort");
        selectSort(arr);
        printArray(arr);

    }

    /**
     * 选择排序：循环遍历，每次遍历完成，将找到的最小值放到开头
     * @author: Li
     * @dateTime: 2022/5/6 15:07
     */
    public static void selectSort(int[] arr ){
        if (arr==null||arr.length<2){
            return;
        }

        for (int i = 0; i < arr.length-1; i++) {
            int min=i;
            for (int j=i+1;j<arr.length;j++){
                //找到此轮循环的最小值下标
               min= arr[j]<arr[min]?j:min;
            }

            //将min与i交换
            swap(arr, min, i);
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
