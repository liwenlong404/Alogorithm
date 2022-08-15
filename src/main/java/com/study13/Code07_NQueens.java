package com.study13;
/**
 * @Description: 八皇后问题
 * @author li
 * @create 2022/8/15 9:55
 */
public class Code07_NQueens {

	public static int num1(int n) {
		if (n < 1) {
			return 0;
		}
		int[] record = new int[n];
		return process1(0, record, n);
	}

	// 当前来到i行，一共是0~N-1行
	// 在i行上放皇后，所有列都尝试
	// 必须要保证跟之前所有的皇后不打架
	// int[] record record[x] = y 之前的第x行的皇后，放在了y列上
	// 返回：不关心i以上发生了什么，i.... 后续有多少合法的方法数
	public static int process1(int i, int[] record, int n) {
		if (i == n) {
			return 1;
		}
		int res = 0;
		// i行的皇后，放哪一列呢？j列，
		for (int j = 0; j < n; j++) {
			if (isValid(record, i, j)) {
				record[i] = j;
				res += process1(i + 1, record, n);
			}
		}
		return res;
	}

	/**
	 * 判断是否冲突
	 * 因为皇后不可能在行上冲突，所以我们只需要判断是否在列上，斜线上冲突。
	 * 斜线判断：例如 a在3行，6列   b在5行，8列。那么 |3-5|==|6-8|。冲突
	 *
	 * 返回i行皇后，在j列，是否有效
	 * @author: Li
	 * @dateTime: 2022/8/15 9:55
	 */
	public static boolean isValid(int[] record, int i, int j) {
		// 0..i-1
		//判断 i 行，之前的皇后，是否在列上与 j 冲突。
		for (int k = 0; k < i; k++) {
			if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
				return false;
			}
		}
		return true;
	}

	// 请不要超过32皇后问题
	public static int num2(int n) {
		if (n < 1 || n > 32) {
			return 0;
		}
		// 如果你是13皇后问题，limit 最右13个1，其他都是0
		int limit = n == 32 ? -1 : (1 << n) - 1;
		return process2(limit, 0, 0, 0);
	}

	// 7皇后问题
	// limit : 0....0 1 1 1 1 1 1 1  固定变量，划定问题规模
	// 之前皇后的列影响：colLim
	// 之前皇后的左下对角线影响：leftDiaLim
	// 之前皇后的右下对角线影响：rightDiaLim
	public static int process2(int limit, int colLim, int leftDiaLim, int rightDiaLim) {
		//base case 找齐了
		if (colLim == limit) {
			return 1;
		}
		//colLim | leftDiaLim | rightDiaLim  是总限制，如果取反就是可以放的位置，再与上limit，抹掉前面多余的1
		// pos中所有是1的位置，是你可以去尝试皇后的位置
		int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
		int mostRightOne = 0;
		int res = 0;
		while (pos != 0) {
			//提取最右侧的1,剩下位置都是1
			mostRightOne = pos & (~pos +  1);
			//提取用了之后减掉
			pos = pos - mostRightOne;


			res += process2(limit, colLim | mostRightOne, (leftDiaLim | mostRightOne) << 1,
					(rightDiaLim | mostRightOne) >>> 1);
		}
		return res;
	}

	public static void main(String[] args) {
		int n = 15;

		long start = System.currentTimeMillis();
		System.out.println(num2(n));
		long end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + "ms");

		start = System.currentTimeMillis();
		System.out.println(num1(n));
		end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + "ms");

	}
}
