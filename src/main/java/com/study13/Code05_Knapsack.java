package com.study13;
/**
 * @Description: 背包问题
 * @author li
 * @create 2022/8/9 11:22
 */
public class Code05_Knapsack {

	// 所有的货，重量和价值，都在w和v数组里
	// 为了方便，其中没有负数
	// bag背包容量，不能超过这个载重
	// 返回：不超重的情况下，能够得到的最大价值
	public static int maxValue(int[] w, int[] v, int bag) {
		if (w == null || v == null || w.length != v.length || w.length == 0) {
			return 0;
		}
		// 尝试函数！
		return process(w, v, 0,0, bag);
	}


	/**
	 *
	 * 不变：w[]  v[]  bag
	 * index...  最大价值
	 * 0...index-1 上做了货物选择，使得已经达到了多少重量alreadyW
	 * 如果返回 -1  则认为没有方案
	 * @author: Li
	 * @dateTime: 2022/8/9 11:28
	 */
	public static int process(int[] w, int[] v, int index,int alreadyW, int bag) {
		if (alreadyW > bag) {
			return -1;
		}
		//没货了
		if (index == w.length) {
			return 0;
		}

		//可能性1  不当前货
		int p1 = process(w, v, index + 1, alreadyW,bag);

		int p2next = process(w, v, index + 1, alreadyW+w[index],bag);
		int p2 = -1;
		if (p2next != -1) {
			p2 = v[index] + p2next;
		}
		return Math.max(p1, p2);
	}


	/**
	 *
	 * 只剩下rest空间
	 * index...  货物自由选择，但剩余空间不要小于0
	 * 返回index...  货物能够获得的最大价值
	 *
	 * @author: Li
	 * @dateTime: 2022/8/9 11:33
	 */
	public static int process(int[] w, int[] v, int index, int rest) {
		if (rest < 0) {
			return -1;
		}
		if (index == w.length) {
			return 0;
		}
		//不要当前货
		int p1 = process(w, v, index + 1, rest);
		int p2 = 0;

		//要当前货，剩余空间减少
		int next = process(w, v, index + 1, rest - w[index]);
		if (next != -1) {
			p2 = v[index] + next;
		}
		return Math.max(p1, p2);
	}




	public static int dp(int[] w, int[] v, int bag) {
		if (w == null || v == null || w.length != v.length || w.length == 0) {
			return 0;
		}
		int N = w.length;
		int[][] dp = new int[N + 1][bag + 1];
		for (int index = N - 1; index >= 0; index--) {
			for (int rest = 0; rest <= bag; rest++) {
				int p1 = dp[index + 1][rest];
				int p2 = 0;
				int next = rest - w[index] < 0 ? -1 : dp[index + 1][rest - w[index]];
				if (next != -1) {
					p2 = v[index] + next;
				}
				dp[index][rest] = Math.max(p1, p2);
			}
		}
		return dp[0][bag];
	}

	public static void main(String[] args) {
		int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
		int[] values = { 5, 6, 3, 19, 12, 4, 2 };
		int bag = 15;
		System.out.println(maxValue(weights, values, bag));
		System.out.println(dp(weights, values, bag));
	}

}
