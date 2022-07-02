package com.first03;

/**
 * @Description: 二分，局部最小问题
 * @author: li
 * @create: 2022-05-07 21:23
 */
public class Code03_BSAwesome {


    public static void main(String[] args) {
        int testTimes = 1000000;
        int maxLen = 20;
        int maxValue = 100;

        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int ans = oneMinIndex(arr);
            if (!check(arr, ans)){
                printArr(arr);
                System.out.println(ans);
                break;
            }
        }

        System.out.println("测试结束");
    }


    //arr 整体无序
    //arr 相邻的数不等
    public static int oneMinIndex(int[] arr) {
        if (arr.length == 0 || arr == null) {
            return -1;
        }

        if (arr.length == 1) {
            return 0;
        }

        if (arr[0] < arr[1]) {
            return 0;
        }

        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }

        int L = 0;
        int R = arr.length - 1;

        //最少3个数时二分
        while (L < R - 1) {
            int mid = L + ((R - L) >> 1);

            //得到局部最小
            if (arr[mid] < arr[mid - 1] && arr[mid] < arr[mid + 1]) {
                return mid;
            } else {
                //递增趋势  抛弃右边
                if (arr[mid] > arr[mid - 1]) {
                    R = mid - 1;
                } else {
                    //递减趋势  抛弃左边
                    L = mid + 1;
                }
            }
        }

        return arr[L] < arr[R] ? L : R;

    }


    //对数器验证

    /**
     * 生成随机数组，且相邻值不相等
     *
     * @param maxLen   数组最大长度
     * @param maxValue 数组中的最大值
     * @author: Li
     * @dateTime: 2022/5/7 21:41
     */
    public static int[] randomArray(int maxLen, int maxValue) {
        //随机生成最大长度为maxLen的随机长度
        int len = (int) (Math.random() * maxLen);
        int[] arr = new int[len];

        //生成数组的值，且相邻值不等
        if (len > 0) {
            arr[0] = (int) (Math.random() * maxValue);
            for (int i = 1; i < len; i++) {
                do {
                    arr[i] = (int) (Math.random() * maxValue);
                } while (arr[i] == arr[i - 1]);
            }
        }
        return arr;
    }

    /**
     * 生成固定长度，随机值的数组
     * @author: Li
     * @dateTime: 2022/5/7 22:33
     */
    public static int[] randomValueArray(int len, int maxValue) {
        int[] arr = new int[len];

        //生成数组的值，且相邻值不等
        if (len > 0) {
            arr[0] = (int) (Math.random() * maxValue);
            for (int i = 1; i < len; i++) {
                do {
                    arr[i] = (int) (Math.random() * maxValue);
                } while (arr[i] == arr[i - 1]);
            }
        }
        return arr;
    }

    /**
     * 验证方法，验证的到的值是否真的是局部最小
     *
     * @author: Li
     * @dateTime: 2022/5/7 21:49
     */
    public static boolean check(int[] arr, int minIndex) {
        if (arr.length == 0) {
            return minIndex == -1;
        }

        int left = minIndex - 1;
        int right = minIndex + 1;

        //在是否越界的情况下比较,越界了，则mid为最左，同样返回true
        boolean leftBigger = left >= 0 ? arr[left] > arr[minIndex] : true;

        boolean rightBigger = right < arr.length ? arr[right] > arr[minIndex] : true;

        return leftBigger && rightBigger;
    }


    /**
     * 打印方法
     *
     * @author: Li
     * @dateTime: 2022/5/7 21:58
     */
    public static void printArr(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }


}
