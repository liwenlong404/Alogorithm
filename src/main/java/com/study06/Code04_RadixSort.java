package com.study06;

import java.util.Arrays;
/**
 * @Description: 基数排序
 * @author li
 * @create 2022/7/24 17:44
 */
public class Code04_RadixSort {

	// 只限正整数
	public static void radixSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		radixSort(arr, 0, arr.length - 1, maxbits(arr));
	}

	/**
	 * 找到最大值，得到最大值位数
	 * @author: Li
	 * @dateTime: 2022/7/24 17:51
	 */
	public static int maxbits(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(max, arr[i]);
		}
		int res = 0;
		while (max != 0) {
			res++;
			max /= 10;
		}
		return res;
	}

	/**
	 *
	 * 理论上10进制的基数排序，可以准备10个"桶"，来进行倒入倒出操作，进行排序
	 * 但可以进行优化，使用2个数组，来进行排序
	 *
	 *  10进制为例
	 *  准备一个与原数组等长的数组help
	 *  for循环，for (int d = 1; d <= digit; d++) 即从个位开始，终止为最大值的十进制位数digit
	 * 准备一个count[0..9]数组，对所有数的个位数的出现次数，进行一个计数。
	 * 例：所有数，个位上，0出现4次，则count[0]=4
	 *
	 * 完成count计数后，对count进行改造
	 *
	 * count只是个位数出现次数，对count进行累加
	 * 例如：0位3个，count[0]=3, 1位5个，但要加上0位的，则count[1]=3+5,
	 * 2位2个，加上1位的。count[2]=2+5, 后面以此类推
	 *
	 * 然后对数组进行倒序的排列
	 *
	 * 从最后一个数开始，获取个位数值，通过这个值去count数组里找对应数值下标的数。
	 * 将这个数减一，就是存放在help数组中的位置。
	 * 因为改造后的count数组，存放的是范围。如上面的count[0]=3,count[1]=5,count[2]=7
	 *
	 * 0位，在排序后的数组中，会排在<=3的范围上。1位会排在<=5的范上。
	 * 而我们是倒序开始排列，例如：倒序第一个的个位是1，就将其排在5-1=4的位置上。然后自减1
	 * 相当于模拟了，原始的10个桶的队列操作
	 *
	 * arr[L..R]排序  ,  最大值的十进制位数digit(例：max：100   digit：3)
	 * @author: Li
	 * @dateTime: 2022/7/24 18:25
	 */
	public static void radixSort(int[] arr, int L, int R, int digit) {
		//10进制
		final int radix = 10;

		int i = 0, j = 0;
		//数组等长help
		// 有多少个数准备多少个辅助空间
		int[] help = new int[R - L + 1];

		// 有多少位就进出几次，例如最大值为4位，则所有数，需要进行4次倒出覆盖操作
		for (int d = 1; d <= digit; d++) {
			// 10个空间
		    // count[0] 当前位(d位)是0的数字有多少个
			// count[1] 当前位(d位)是(0和1)的数字有多少个
			// count[2] 当前位(d位)是(0、1和2)的数字有多少个
			// count[i] 当前位(d位)是(0~i)的数字有多少个
			int[] count = new int[radix]; // count[0..9]
			for (i = L; i <= R; i++) {
				// 103  1   3  d为1取个位的数，d为2取十位的数
				// 209  1   9
				j = getDigit(arr[i], d);

				//count计数，j为3，则index为3的位置，加一，代表有一个3
				count[j]++;
			}
			//上面循环结束后，所有数的d位，计数完成
			//进行累加，例如：0位3个，count[0]=3, 1位5个，但要加上0位的，count[1]=3+5,后面以此类推
			//变成   count'   数组
			for (i = 1; i < radix; i++) {
				count[i] = count[i] + count[i - 1];
			}
			for (i = R; i >= L; i--) {
				j = getDigit(arr[i], d);
				help[count[j] - 1] = arr[i];
				count[j]--;
			}
			for (i = L, j = 0; i <= R; i++, j++) {
				arr[i] = help[j];
			}
		}
	}

	public static int getDigit(int x, int d) {
		return ((x / ((int) Math.pow(10, d - 1))) % 10);
	}

	// for test
	public static void comparator(int[] arr) {
		Arrays.sort(arr);
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random());
		}
		return arr;
	}

	// for test
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

	// for test
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

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100000;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			radixSort(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		radixSort(arr);
		printArray(arr);

	}

}
