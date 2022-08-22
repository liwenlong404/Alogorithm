package com.study14;

public class Code02_CoinsWayNoLimit {

	public static int coinsWay(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		return process(arr, 0, aim);
	}

	/**
	 * 方法1，暴力递归
	 *  arr[index....] 所有的面值，每一个面值都可以任意选择张数，组成正好rest这么多钱，方法数多少？
	 * @author: Li
	 * @dateTime: 2022/8/15 16:59
	 */
	public static int process(int[] arr, int index, int rest) {

		/*
		因为后面的for循环中，限定了拿的金额，所以不可能超，所以不需要此if
		if (rest<0){
			return 0;
		}
		*/

		if (index == arr.length) { // 没钱了
			return rest == 0 ? 1 : 0;
		}
		int ways = 0;
		for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
			ways += process(arr, index + 1, rest - (zhang * arr[index]));
		}
		return ways;
	}



	public static int ways2(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}

		int[][] dp=new int[arr.length+1][aim+1];
		//一开始，所有的过程，都没有计算
		//dp[..][..] = -1
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[0].length; j++) {
				dp[i][j]=-1;
			}
		}
		return process2(arr, 0, aim,dp);
	}


	/**
	 * 方法2：计划搜索
	 *
	 * 如果index和rest的参数组合，是没算过的，dp[index][rest] == -1
	 * 如果index和rest的参数组合，是算过的，dp[index][rest] > -1
	 * @author: Li
	 * @dateTime: 2022/8/15 16:59
	 */
	public static int process2(int[] arr, int index, int rest,int[][] dp) {

		//如果之前，算过此子问题的解，直接返回
		if (dp[index][rest]!=-1){
			return dp[index][rest];
		}

		//没钱结尾了，将结果加入缓存
		if (index == arr.length) {
			dp[index][rest]= rest == 0 ? 1 : 0;
			return dp[index][rest];
		}

		int ways = 0;
		for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
			ways += process2(arr, index + 1, rest - (zhang * arr[index]),dp);
		}
		//返回前，放入缓存
		dp[index][rest]=ways;
		return ways;
	}



	/**
	 * 动态规划，但有枚举行为。所以与计划搜索等效
	 * @author: Li
	 * @dateTime: 2022/8/15 17:46
	 */
	public static int dp1(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		int N = arr.length;
		int[][] dp = new int[N + 1][aim + 1];

		/*
		由此递归中的代码可知，index = N ，且rest为0 时，值为1.可以向二维数组直接设置值
		if (index == arr.length) { // 没钱了
			return rest == 0 ? 1 : 0;
		}*/
		dp[N][0] = 1;//dp[N][1...aim] = 0


		for (int index = N - 1; index >= 0; index--) {
			for (int rest = 0; rest <= aim; rest++) {
				int ways = 0;
				for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
					ways += dp[index + 1][rest - (zhang * arr[index])];
				}
				dp[index][rest] = ways;
			}
		}
		return dp[0][aim];
	}

	/**
	 * 省掉枚举行为
	 * @author: Li
	 * @dateTime: 2022/8/15 18:01
	 */
	public static int dp2(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0) {
			return 0;
		}
		int N = arr.length;
		int[][] dp = new int[N + 1][aim + 1];
		dp[N][0] = 1;
		for (int index = N - 1; index >= 0; index--) {
			for (int rest = 0; rest <= aim; rest++) {
				dp[index][rest] = dp[index + 1][rest];
				if (rest - arr[index] >= 0) {
					dp[index][rest] += dp[index][rest - arr[index]];
				}
			}
		}
		return dp[0][aim];
	}

	// 为了测试
	public static int[] randomArray(int maxLen, int maxValue) {
		int N = (int) (Math.random() * maxLen);
		int[] arr = new int[N];
		boolean[] has = new boolean[maxValue + 1];
		for (int i = 0; i < N; i++) {
			do {
				arr[i] = (int) (Math.random() * maxValue) + 1;
			} while (has[arr[i]]);
			has[arr[i]] = true;
		}
		return arr;
	}

	// 为了测试
	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// 为了测试
	public static void main(String[] args) {
		int maxLen = 10;
		int maxValue = 30;
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[] arr = randomArray(maxLen, maxValue);
			int aim = (int) (Math.random() * maxValue);
			int ans1 = coinsWay(arr, aim);
			int ans2 = dp1(arr, aim);
			int ans3 = dp2(arr, aim);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("Oops!");
				printArray(arr);
				System.out.println(aim);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				break;
			}
		}
		System.out.println("测试结束");
	}

}
