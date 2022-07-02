package com.first01;

/**
 * @Description: 冒泡排序
 * @author: li
 * @create: 2022-05-06 15:50
 */
public class Code04_BubbleSort {

    public static void main(String[] args) {
        int[] arr = {7, 5, 6, 3, 7, 1, 7, 5, 1, 0, 3, 6, 9, 6, 4};
        printArray(arr);
        System.out.println("bubbleSort");
        bubbleSort(arr);
        printArray(arr);

    }

    /**
     * 冒泡排序：两两交换，谁大谁往后，如：1:2,2:3,3:4········
     *
     * @author: Li
     * @dateTime: 2022/5/6 15:52
     */
    public static void bubbleSort(int[] arr) {
        int length = arr.length;
        for (int i = length - 1; i > 0; i--) {
            //每两个数进行比较，前面大进行交换，然后继续两两比较
            //例如下标1和下标2进行比较交换，然后下标2与下标3比较交换。遍历结束，尾部n则为最大数
            //比较到位置 i 时，结束本次循环
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    /**
     * 交换方法
     * @author: Li
     * @dateTime: 2022/5/6 16:15
     */
    public static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }


    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
