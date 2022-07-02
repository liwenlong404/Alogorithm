package com.study03;

/**
 * @Description: 归并排序中，merge的递归实现和非递归实现
 * @author: li
 * @create: 2022-05-20 16:37
 */
public class Code01_MergeSort {


    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        process(arr, 0, arr.length - 1);
    }


    public static void process(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }
        int mid = L + ((R - L) >> 1);
        process(arr, L, mid);
        process(arr, mid + 1, R);
        merge(arr, L, mid, R);
    }

    public static void merge(int[] arr, int L, int M, int R) {
        //建立一个辅助数组，用于拷贝值。
        int[] help = new int[R - L + 1];
        //i 为help数组的下标
        int i = 0;
        //P1,P2为左右两部分的指针
        int p1 = L;
        int p2 = M + 1;

        //2部分，只要有一个指针尾。停止
        while (p1 <= M && p2 <= R) {
            //两部分进行比较，谁小拷贝到help数组，然后指针后移
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }

        //边界条件
        //当其中一个数组的值拷贝结束，另一个还有值时

        //如果左部分还有值
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        //如果右部分还有值
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }

        //help数组为L到R位置上，长度为R-L+1的一个有序数组
        //将help数组的值写入覆盖arr数组
        for (int j = 0; j < help.length; j++) {
            arr[L + j] = help[j];
        }
    }



    /**
     * 归并排序的非递归实现
     * @author: Li
     * @dateTime: 2022/5/20 18:06
     */
    public static void mergeSort2(int[] arr){
        if (arr == null || arr.length < 2) {
            return;
        }

        int N=arr.length;
        int mergeSize=1;

        while (mergeSize<N){
            //从1开始，例如1,2merge，3,4merge

            //左组开始的位置
            int L=0;

            while (L<N){
                //merge到末尾，右半部分不能继续划分，break
                if (mergeSize >= N - L) {
                    break;
                }
                //左半部分尾部
                int M = L + mergeSize - 1;

                //右指针。如果够步长返回  M+步长 。不够步长返回尾部，即N-1
                int R = M + Math.min(mergeSize, N - M - 1);
                merge(arr, L, M, R);
                L = R + 1;
            }
            // 防止溢出
            if (mergeSize > N / 2) {
                break;
            }
            mergeSize <<= 1;
        }

    }





   /* public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        // 步长
        int mergeSize = 1;
        while (mergeSize < N) { // log N
            // 当前左组的，第一个位置
            int L = 0;
            while (L < N) {
                if (mergeSize >= N - L) {
                    break;
                }
                int M = L + mergeSize - 1;
                int R = M + Math.min(mergeSize, N - M - 1);
                merge(arr, L, M, R);
                L = R + 1;
            }
            // 防止溢出
            if (mergeSize > N / 2) {
                break;
            }
            mergeSize <<= 1;
        }
    }*/


    
    /**
     * 对数器：生成随机数组
     * @author: Li
     * @dateTime: 2022/5/20 17:09
     * @param maxValue 数组中最大值
     * @param maxSize  数组最大长度               
     */
    public static int[] randomArray(int maxSize,int maxValue){
        //随机生成最大长度为maxSize的数组
        int[] arr = new int[(int) (Math.random() * (maxSize + 1))];
        
        //随机生成数组中的值
        for (int i = 0; i < arr.length; i++) {
            arr[i]=(int)(Math.random() * (maxValue + 1));
        }
        return arr;
    }

    /**
     * 使用选择排序进行验证
     * @author: Li
     * @dateTime: 2022/5/20 17:15
     */
    public static void selectSort(int[] arr ){
        if (arr==null||arr.length<2){
            return;
        }
        for (int i = 0; i < arr.length-1; i++) {
            int min=i;
            for (int j=i+1;j<arr.length;j++){
                min= arr[j]<arr[min]?j:min;
            }
            swap(arr, min, i);
        }
    }
    public static void swap(int[] arr,int i,int j){

        int tem=arr[i];
        arr[i]=arr[j];
        arr[j]=tem;
    }

    /**
     * 对数器：复制数组
     * @author: Li
     * @dateTime: 2022/5/20 17:19
     */
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    /**
     * 对数器：打印数组
     * @author: Li
     * @dateTime: 2022/5/20 17:21
     */
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    /**
     * 对数器：判断2个数组是否相等
     * @author: Li
     * @dateTime: 2022/5/20 17:20
     */
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = randomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            mergeSort(arr1);
            selectSort(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("出错了！");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println("测试结束");
    }

}
