package com.train03;
/**
 * @description: kmp算法
 * @author li
 * @create 2022/8/28 18:40
 */
public class Code01_KMP {

	public static int getIndexOf(String s, String m) {
		if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
			return -1;
		}
		char[] str = s.toCharArray();
		char[] match = m.toCharArray();
		//str中，比对到的位置
		int x = 0;
		//match中，比对到的位置
		int y = 0;
		// O(M) m <= n
		int[] next = getNextArray(match);
		// O(N)
		while (x < str.length && y < match.length) {
			if (str[x] == match[y]) {
				x++;
				y++;
			}
			//y == 0 与 next[y] == -1 等效，意味着match数组的的y已经跳到开头。无法继续跳，需要x++移动，然后与0位置的y重新比对
			else if (y==0) {
				x++;
			}
			//没配上，且y也不在 0 位置。通过next数组找到，y位置之前的最大前后缀匹配长度，然后y跳到与长度相对于的位置
			else {
				y = next[y];
			}
		}

		// y 越界了，代表match已经走完，匹配上了。当前 x 位置减 y 即使，匹配子串的开头index
		// y 没越界，意味着因为着 x 没使 match中的 y 走完。即没匹配上。返回-1
		return y == match.length ? x - y : -1;
	}

	/**
	 *
	 * 通过match数组，获取next数组
	 * @author: Li
	 * @dateTime: 2022/8/28 18:44
	 */
	public static int[] getNextArray(char[] match) {
		if (match.length == 1) {
			return new int[] { -1 };
		}
		int[] next = new int[match.length];

		//0,1 位置固定不变
		next[0] = -1;
		next[1] = 0;

		// 目前在哪个位置上求next数组的值，从 2 位置开始求
		int i = 2;

		/*
		cn俩含义:
		1.i-1位置的最长前缀跟最长后缀的最大长度
		2.当前是哪一个字符要跟 i-1 位置作比较，如果比对成功, i 位置的值就是 cn + 1
		其实就是既是next的值，也是要跳转的地方的index
		 */
		int cn = 0;
		while (i < next.length) {
			// 配成功的时候
			if (match[i - 1] == match[cn]) {
				next[i++] = ++cn;
			} else if (cn > 0) {
				cn = next[cn];
			} else {
				next[i++] = 0;
			}
		}
		return next;
	}

	// for test
	public static String getRandomString(int possibilities, int size) {
		char[] ans = new char[(int) (Math.random() * size) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
		}
		return String.valueOf(ans);
	}

	public static void main(String[] args) {
		int possibilities = 5;
		int strSize = 20;
		int matchSize = 5;
		int testTimes = 5000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			String str = getRandomString(possibilities, strSize);
			String match = getRandomString(possibilities, matchSize);
			if (getIndexOf(str, match) != str.indexOf(match)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish");
	}

}
