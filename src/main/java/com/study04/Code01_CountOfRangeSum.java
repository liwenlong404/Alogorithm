package com.study04;
/**
 * @Description:
 *
 * https://leetcode.com/problems/count-of-range-sum/
 *
 * 给你一个整数数组nums 以及两个整数lower 和 upper 。
 * 求数组中，值位于范围 [lower, upper] （包含lower和upper）之内的 区间和的个数 。
 *
 * 区间和S(i, j)表示在nums中，位置从i到j的元素之和，包含i和j(i ≤ j)。
 *
 * 示例 1：
 * 输入：nums = [-2,5,-1], lower = -2, upper = 2
 * 输出：3
 * 解释：存在三个区间：[0,0]、[2,2] 和 [0,2] ，对应的区间和分别是：-2 、-1 、2 。
 * 示例 2：
 *
 * 输入：nums = [0], lower = 0, upper = 0
 * 输出：1
 *
 *
 * 思路：通过原数组构建一个前缀和数组。即0~0，0~1,0~2,0~length
 *       然后在前缀和数组上进一步操作。以尾数为标准再进一步划分。例如0~7,，1~7，2~7，3~7······7~7分别的前缀和。
 *
 *       那么例：5~7的前缀和：为  1~7 减 1~5
 *
 *       如果0~7的前缀和为100， [lower , upper]为[10,40]
 *       那么在0~6的范围上的前缀和，有0~X满足[60,90]的，那么0~7减去0~X即X~7满足
 *
 *
 *
 * @author li
 * @create 2022/5/23 18:06
 */
public class Code01_CountOfRangeSum {

	public static int countRangeSum(int[] nums, int lower, int upper) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		long[] sum = new long[nums.length];
		sum[0] = nums[0];
		for (int i = 1; i < nums.length; i++) {
			sum[i] = sum[i - 1] + nums[i];
		}
		return process(sum, 0, sum.length - 1, lower, upper);
	}

	public static int process(long[] sum, int L, int R, int lower, int upper) {
		if (L == R) {
			return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
		}
		int M = L + ((R - L) >> 1);
		return process(sum, L, M, lower, upper) + process(sum, M + 1, R, lower, upper)
				+ merge(sum, L, M, R, lower, upper);
	}

	public static int merge(long[] arr, int L, int M, int R, int lower, int upper) {
		int ans = 0;
		int windowL = L;
		int windowR = L;
		// [windowL, windowR)
		for (int i = M + 1; i <= R; i++) {
			//右组的第一个数需要的范围[min,max]
			long min = arr[i] - upper;
			long max = arr[i] - lower;

			//两个窗口都从L，即左组开头去求，满足该范围的位置，小于max，大于min。
			//得到一个范围，此范围的皆满足。即  windowR-windowL个。
			//右组第一个得到，继续循环去求右组的。
			while (windowR <= M && arr[windowR] <= max) {
				windowR++;
			}
			while (windowL <= M && arr[windowL] < min) {
				windowL++;
			}
			ans += windowR - windowL;
		}
		long[] help = new long[R - L + 1];
		int i = 0;
		int p1 = L;
		int p2 = M + 1;
		while (p1 <= M && p2 <= R) {
			help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
		}
		while (p1 <= M) {
			help[i++] = arr[p1++];
		}
		while (p2 <= R) {
			help[i++] = arr[p2++];
		}
		for (i = 0; i < help.length; i++) {
			arr[L + i] = help[i];
		}
		return ans;
	}

}
