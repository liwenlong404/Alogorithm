package com.study10;

import java.util.ArrayList;
/**
 * @Description: 判断二叉树是不是搜索二叉树
 * @author li
 * @create 2022/8/4 14:42
 */
public class Code03_IsBST {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static boolean isBST1(Node head) {
		if (head == null) {
			return true;
		}
		ArrayList<Node> arr = new ArrayList<>();
		in(head, arr);
		for (int i = 1; i < arr.size(); i++) {
			if (arr.get(i).value <= arr.get(i - 1).value) {
				return false;
			}
		}
		return true;
	}

	public static void in(Node head, ArrayList<Node> arr) {
		if (head == null) {
			return;
		}
		in(head.left, arr);
		arr.add(head);
		in(head.right, arr);
	}

	public static boolean isBST2(Node head) {
		if (head == null) {
			return true;
		}
		return process(head).isBST;
	}

	public static class Info {
		//是否是二分搜索树
		public boolean isBST;
		//最大值
		public int max;
		//最小值
		public int min;

		public Info(boolean i, int ma, int mi) {
			isBST = i;
			max = ma;
			min = mi;
		}

	}

	public static Info process(Node x) {
		//既是边界，也是base case
		if (x == null) {
			return null;
		}

		//收集信息
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);

		//将自身的value，设为最大值，下面会进行判断
		int max = x.value;

		//获取左子树最大值
		if (leftInfo != null) {
			max = Math.max(max, leftInfo.max);
		}
		//获取右子树最大值
		if (rightInfo != null) {
			max = Math.max(max, rightInfo.max);
		}

		//将自身的value，设为最大值，下面会进行判断
		int min = x.value;
		//获取左子树最小值
		if (leftInfo != null) {
			min = Math.min(min, leftInfo.min);
		}
		//获取右子树最小值
		if (rightInfo != null) {
			min = Math.min(min, rightInfo.min);
		}

		//isBST设为true，默认为是搜索二叉树，然后走if
		boolean isBST = true;
		//左子树不为空，且不是二分搜索树
		if (leftInfo != null && !leftInfo.isBST) {
			isBST = false;
		}
		//右子树不为空，且不是二分搜索树
		if (rightInfo != null && !rightInfo.isBST) {
			isBST = false;
		}
		//左子树不为空，且最大值，大于自身
		if (leftInfo != null && leftInfo.max >= x.value) {
			isBST = false;
		}
		//右子树不为空，且最小值，小于自身
		if (rightInfo != null && rightInfo.min <= x.value) {
			isBST = false;
		}

		//返回自身相关信息
		return new Info(isBST, max, min);
	}

	// for test
	public static Node generateRandomBST(int maxLevel, int maxValue) {
		return generate(1, maxLevel, maxValue);
	}

	// for test
	public static Node generate(int level, int maxLevel, int maxValue) {
		if (level > maxLevel || Math.random() < 0.5) {
			return null;
		}
		Node head = new Node((int) (Math.random() * maxValue));
		head.left = generate(level + 1, maxLevel, maxValue);
		head.right = generate(level + 1, maxLevel, maxValue);
		return head;
	}

	public static void main(String[] args) {
		int maxLevel = 4;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (isBST1(head) != isBST2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
