package com.train01;

import java.util.LinkedList;
/**
 * @Description: 滑动窗口
 * 假设一个固定大小为W的窗口，依次划过arr，返回每一次滑出窗口的最大值
 * 例如，arr = [4,3,5,4,3,3,6,7], W = 3
 * 返回：[5,5,5,4,6,7]
 * @author li
 * @create 2022/8/22 14:51
 */
public class Code01_SlidingWindowMaxArray {

	// 暴力的对数器方法
	public static int[] right(int[] arr, int w) {
		if (arr == null || w < 1 || arr.length < w) {
			return null;
		}
		int N = arr.length;
		int[] res = new int[N - w + 1];
		int index = 0;
		int L = 0;
		int R = w - 1;
		while (R < N) {
			int max = arr[L];
			for (int i = L + 1; i <= R; i++) {
				max = Math.max(max, arr[i]);

			}
			res[index++] = max;
			L++;
			R++;
		}
		return res;
	}

	public static int[] getMaxWindow(int[] arr, int w) {
		if (arr == null || w < 1 || arr.length < w) {
			return null;
		}

		// 双向队列放下标，根据下标判断过期，也通过小标去数组获取值
		//因为是获取最大值，所有在双向队列中  头到尾，是从大到小
		LinkedList<Integer> qmax = new LinkedList<Integer>();


		//结果
		int[] res = new int[arr.length - w + 1];

		int index = 0;
		//R代表从index 0 开始依次进窗口
		for (int R = 0; R < arr.length; R++) {
			//进窗口
			//双端队列不为空，且队列尾部小于当前入队的数，则不断弹出，直到能够将数放下
			while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[R]) {
				qmax.pollLast();
			}

			//上面满足的放入队列的大小判断，此时将其入队
			qmax.addLast(R);

			//如果窗口没形成W长度，不弹出数字
			//R-W为过期下标，所以当头部为过期下标，就弹出。
			if (qmax.peekFirst() == R - w) {
				qmax.pollFirst();
			}

			//判断是否开始收集答案
			// R >= W-1 则是当形成窗口时，开始每一次收集值。当R来到index(2)，则已经满足3个数，以后每一次都收集值。
			if (R >= w - 1) {
				res[index++] = arr[qmax.peekFirst()];
			}
		}
		return res;
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * (maxValue + 1));
		}
		return arr;
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

	public static void main(String[] args) {
		int testTime = 100000;
		int maxSize = 100;
		int maxValue = 100;
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int w = (int) (Math.random() * (arr.length + 1));
			int[] ans1 = getMaxWindow(arr, w);
			int[] ans2 = right(arr, w);
			if (!isEqual(ans1, ans2)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish");
	}

}
