package com.train01;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 * @Description: 单调栈
 *
 * 给定一个可能含有重复值的数组arr，i位置的数一定存在如下两个信息。
 *
 * 1. arr[i]的左侧离i最近，并且小于(或者大于)arr[i]的数在哪？
 * 2. arr[i]的右侧离i最近，并且小于(或者大于)arr[i]的数在哪？
 *
 * 如果想得到arr中所有位置的两个信息，怎么能让得到信息的过程尽量快。
 *
 * @author li
 * @create 2022/8/22 16:21
 */
public class Code03_MonotonousStack {

	// arr = [ 3, 1, 2, 3]
	//         0  1  2  3
	//  [
	//     0 : [-1,  1]
	//     1 : [-1, -1]
	//     2 : [ 1, -1]
	//     3 : [ 2, -1]
	//  ]
	public static int[][] getNearLessNoRepeat(int[] arr) {
		//每个位置的信息，
		//如[0][-1,  1]，记录的是下标为0的数。左侧小于其数的下标与右侧小于其的数的下标
		int[][] res = new int[arr.length][2];
		// 只存位置！
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < arr.length; i++) { // 当遍历到i位置的数，arr[i]
			while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
				int j = stack.pop();
				int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
				res[j][0] = leftLessIndex;
				res[j][1] = i;
			}
			stack.push(i);
		}
		while (!stack.isEmpty()) {
			int j = stack.pop();
			int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
			res[j][0] = leftLessIndex;
			res[j][1] = -1;
		}
		return res;
	}

	/**
	 * 有重复值
	 *
	 * 结果记录每个数左侧最小的数的小标，右侧最小的数的下标
	 *
	 * @author: Li
	 * @dateTime: 2022/8/22 16:29
	 */
	public static int[][] getNearLess(int[] arr) {
		//每个位置的信息
		int[][] res = new int[arr.length][2];
		//栈放的是  值的下标的list，相同值的下标放一起
		Stack<List<Integer>> stack = new Stack<>();
		for (int i = 0; i < arr.length; i++) { // i -> arr[i] 进栈

			//栈不为空，且栈顶的值大于当前值。（通过下标获取值）
			while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
				//弹出list
				List<Integer> popIs = stack.pop();
				//弹出后，栈为空，即左侧最小 记录为 -1 。弹出不为空，左侧最小即是栈顶链表的最后的下标，代表的数。将下标记录
				int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
				for (Integer popi : popIs) {
					res[popi][0] = leftLessIndex;
					//右侧最小为当前位置
					res[popi][1] = i;
				}
			}

			//栈不为空，且栈顶值与当前值相等
			if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
				stack.peek().add(Integer.valueOf(i));
			}
			//需要重新建list，并压栈
			else {
				ArrayList<Integer> list = new ArrayList<>();
				list.add(i);
				stack.push(list);
			}
		}

		//循环完成，且栈不为空，将栈中的值，进行清算
		while (!stack.isEmpty()) {
			List<Integer> popIs = stack.pop();
			int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
			for (Integer popi : popIs) {
				res[popi][0] = leftLessIndex;
				res[popi][1] = -1;
			}
		}
		return res;
	}

	// for test
	public static int[] getRandomArrayNoRepeat(int size) {
		int[] arr = new int[(int) (Math.random() * size) + 1];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = i;
		}
		for (int i = 0; i < arr.length; i++) {
			int swapIndex = (int) (Math.random() * arr.length);
			int tmp = arr[swapIndex];
			arr[swapIndex] = arr[i];
			arr[i] = tmp;
		}
		return arr;
	}

	// for test
	public static int[] getRandomArray(int size, int max) {
		int[] arr = new int[(int) (Math.random() * size) + 1];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
		}
		return arr;
	}

	// for test
	public static int[][] rightWay(int[] arr) {
		int[][] res = new int[arr.length][2];
		for (int i = 0; i < arr.length; i++) {
			int leftLessIndex = -1;
			int rightLessIndex = -1;
			int cur = i - 1;
			while (cur >= 0) {
				if (arr[cur] < arr[i]) {
					leftLessIndex = cur;
					break;
				}
				cur--;
			}
			cur = i + 1;
			while (cur < arr.length) {
				if (arr[cur] < arr[i]) {
					rightLessIndex = cur;
					break;
				}
				cur++;
			}
			res[i][0] = leftLessIndex;
			res[i][1] = rightLessIndex;
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[][] res1, int[][] res2) {
		if (res1.length != res2.length) {
			return false;
		}
		for (int i = 0; i < res1.length; i++) {
			if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
				return false;
			}
		}

		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int size = 10;
		int max = 20;
		int testTimes = 2000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int[] arr1 = getRandomArrayNoRepeat(size);
			int[] arr2 = getRandomArray(size, max);
			if (!isEqual(getNearLessNoRepeat(arr1), rightWay(arr1))) {
				System.out.println("Oops!");
				printArray(arr1);
				break;
			}
			if (!isEqual(getNearLess(arr2), rightWay(arr2))) {
				System.out.println("Oops!");
				printArray(arr2);
				break;
			}
		}
		System.out.println("测试结束");
	}
}
