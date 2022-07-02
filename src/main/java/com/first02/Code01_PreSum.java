package com.first02;

/**
 * @Description:  一个数组,计算下标L到下标R位置之间的数据和
 * @author li
 * @create 2022/5/6 17:56
 */
public class Code01_PreSum {

	public static class RangeSum1 {

		private int[] arr;

		public RangeSum1(int[] array) {
			arr = array;
		}

		public int rangeSum(int L, int R) {
			int sum = 0;
			for (int i = L; i <= R; i++) {
				sum += arr[i];
			}
			return sum;
		}

	}

	public static class RangeSum2 {

		private int[] preSum;

		public RangeSum2(int[] array) {
			int N = array.length;
			preSum = new int[N];
			preSum[0] = array[0];
			for (int i = 1; i < N; i++) {
				preSum[i] = preSum[i - 1] + array[i];
			}
		}

		public int rangeSum(int L, int R) {
			return L == 0 ? preSum[R] : preSum[R] - preSum[L - 1];
		}

	}

}
