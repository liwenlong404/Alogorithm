package com.first05;

/**
 * @Description: 位运算实现加减乘除
 * 测试链接：https://leetcode.com/problems/divide-two-integers
 * @author li
 * @create 2022/5/9 21:53
 */
public class Code02_BitAddMinusMultiDiv {

	/**
	 * 位运算实现加法。 通过 ^ 实现无进位相加，通过  & 然后 <<1 实现进位的值
	 * 无进位  与 进位的值   再次实现第一步流程。直至没有进位信息。 ^ 的值便是结果
	 * @author: Li
	 * @dateTime: 2022/5/9 17:54
	 */
	public static int add(int a, int b) {
		int sum = a;
		while (b != 0) {
			sum = a ^ b;
			b = (a & b) << 1;
			a = sum;
		}
		return sum;
	}

	/**
	 *  得到一个数的相反数。取反加1
	 * @author: Li
	 * @dateTime: 2022/5/9 17:59
	 */
	public static int negNum(int n) {
		return add(~n, 1);
	}

	/**
	 *  位运算减法。a减b。就是a加b的相反数
	 * @author: Li
	 * @dateTime: 2022/5/9 18:00
	 */
	public static int minus(int a, int b) {
		return add(a, negNum(b));
	}

	/**
	 * 位运算乘法。  二进制数 a，b。b最右侧有1.res结果加上a。然后轮到b第二位
	 *  计算b的第二位时，a要相应的左移1位。b要右移1位（补0）
	 * @author: Li
	 * @dateTime: 2022/5/9 18:06
	 */
	public static int multi(int a, int b) {
		int res = 0;
		while (b != 0) {
			//     &1 不等于 0，则最末尾有 1
			if ((b & 1) != 0) {
				//加到res里去
				res = add(res, a);
			}
			a <<= 1;
			b >>>= 1;
		}
		return res;
	}

	public static boolean isNeg(int n) {
		return n < 0;
	}
	/**
	 * 位运算除法
	 * @author: Li
	 * @dateTime: 2022/5/9 18:41
	 */
	public static int div(int a, int b) {
		int x = isNeg(a) ? negNum(a) : a;
		int y = isNeg(b) ? negNum(b) : b;
		int res = 0;
		for (int i = 30; i >= 0; i = minus(i, 1)) {
			if ((x >> i) >= y) {
				res |= (1 << i);
				x = minus(x, y << i);
			}
		}
		return isNeg(a) ^  isNeg(b) ? negNum(res) : res;
	}


	/**
	 * 考虑到系统最小的情况下的，相除
	 * @author: Li
	 * @dateTime: 2022/5/9 21:32
	 */
	public static int divide(int a, int b) {
		if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE) {
			return 1;
		} else if (b == Integer.MIN_VALUE) {
			return 0;
		} else if (a == Integer.MIN_VALUE) {
			if (b == negNum(1)) {
				return Integer.MAX_VALUE;
			} else {
				//因为是系统最小，所以加一，这样就能表示为正int（系统最大）
				//进行相除得到  c
				int c = div(add(a, 1), b);

				//对因为系统最小加了一，对c进行补偿
				//补偿：结果c乘以被除数b，看与原除数差了多少。即原除数 - (结果 * 被除数)
				//得到差的数，单独进与被除数行一次除法，得到差的值，然后与c相加。
				return add(c, div(minus(a, multi(c, b)), b));
			}
		} else {
			return div(a, b);
		}
	}

}
