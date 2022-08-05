package com.study11;

import java.util.HashSet;
/**
 * @Description: 点亮str中所有需要点亮的位置至少需要几盏灯
 * @author li
 * @create 2022/8/5 16:56
 */
public class Code03_Light {

	public static int minLight1(String road) {
		if (road == null || road.length() == 0) {
			return 0;
		}
		return process(road.toCharArray(), 0, new HashSet<>());
	}

	// str[index....]位置，自由选择放灯还是不放灯
	// str[0..index-1]位置呢？已经做完决定了，那些放了灯的位置，存在lights里
	// 要求选出能照亮所有.的方案，并且在这些有效的方案中，返回最少需要几个灯
	public static int process(char[] str, int index, HashSet<Integer> lights) {
		// 结束的时候
		if (index == str.length) {

			//验证此方案是否能将所有点照亮
			for (int i = 0; i < str.length; i++) {
				// 当前位置是点的话
				if (str[i] != 'X') {
					//当前位置，前一个，自己，后一个，都没灯
					if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
						return Integer.MAX_VALUE;
					}
				}
			}
			return lights.size();

		}
		// str还没结束
		else {
			// i X .
			int no = process(str, index + 1, lights);
			int yes = Integer.MAX_VALUE;
			if (str[index] == '.') {
				lights.add(index);
				yes = process(str, index + 1, lights);
				lights.remove(index);
			}
			return Math.min(no, yes);
		}
	}

	public static int minLight2(String road) {
		char[] str = road.toCharArray();
		int i = 0;
		int light = 0;
		while (i < str.length) {
			if (str[i] == 'X') {
				i++;
			} else {
				//灯数量++，因为无论我后面是灯还是X，都会放一盏灯
				light++;

				//判断是否结尾，break
				if (i + 1 == str.length) {
					break;
				}
				else {
					//如果下一个是X ，跳到下下个。
					if (str[i  + 1] == 'X') {
						i = i + 2;

					//如果下一个是灯，跳3格，因为无论是 。。。 还是 。。X   灯数量已经+1，跳到下一步继续判断zz
					} else {
						i = i + 3;
					}
				}
			}
		}
		return light;
	}

	// for test
	public static String randomString(int len) {
		char[] res = new char[(int) (Math.random() * len) + 1];
		for (int i = 0; i < res.length; i++) {
			res[i] = Math.random() < 0.5 ? 'X' : '.';
		}
		return String.valueOf(res);
	}

	public static void main(String[] args) {
		int len = 20;
		int testTime = 100000;
		for (int i = 0; i < testTime; i++) {
			String test = randomString(len);
			int ans1 = minLight1(test);
			int ans2 = minLight2(test);
			if (ans1 != ans2) {
				System.out.println("oops!");
			}
		}
		System.out.println("finish!");
	}
}
