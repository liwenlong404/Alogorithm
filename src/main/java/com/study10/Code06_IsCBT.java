package com.study10;

import java.util.LinkedList;
/**
 * @Description: 判断二叉树是不是完全二叉树
 * @author li
 * @create 2022/8/4 15:00
 */
public class Code06_IsCBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	/**
	 * 宽度优先遍历
	 * @author: Li
	 * @dateTime: 2022/8/4 15:09
	 */
	public static boolean isCBT1(Node head) {
		if (head == null) {
			return true;
		}
		LinkedList<Node> queue = new LinkedList<>();
		// 是否遇到过左右两个孩子不双全的节点
		boolean leaf = false;
		Node l = null;
		Node r = null;
		queue.add(head);
		while (!queue.isEmpty()) {
			head = queue.poll();
			l = head.left;
			r = head.right;

			//主要的判断
			if (
			// leaf为true，遇到了不双全的节点之后，又发现当前节点不是叶节点（即存在子节点l，r）
			(leaf && (l != null || r != null)) || (l == null && r != null)

			) {
				return false;
			}

			//继续入队列，宽度有限遍历
			if (l != null) {
				queue.add(l);
			}
			if (r != null) {
				queue.add(r);
			}

			//遇到了，不双全的节点。其实只需要用到一次
			if (l == null || r == null) {
				leaf = true;
			}
		}
		return true;
	}

	public static boolean isCBT2(Node head) {
		return process(head).isCBT;
	}

	public static class Info {
		//是否是满二叉树
		public boolean isFull;
		//是否是完全二叉树
		public boolean isCBT;
		//高度
		public int height;

		public Info(boolean full, boolean cbt, int h) {
			isFull = full;
			isCBT = cbt;
			height = h;
		}
	}

	public static Info process(Node x) {
		if (x == null) {
			return new Info(true, true, 0);
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);

		int height = Math.max(leftInfo.height, rightInfo.height) + 1;

		//左是满，右是满，且高度一致。我为满
		boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;

		//几种近况，1.左满、右满   2.左满，右满，且左高度多1(刚好到中线)  3.左满，右完，高度一致  4.左完，右满，左高度多1

		boolean isCBT = false;
		if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
			isCBT = true;
		} else if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
			isCBT = true;
		} else if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
			isCBT = true;
		} else if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
			isCBT = true;
		}
		return new Info(isFull, isCBT, height);
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
		int maxLevel = 5;
		int maxValue = 100;
		int testTimes = 1000000;
		for (int i = 0; i < testTimes; i++) {
			Node head = generateRandomBST(maxLevel, maxValue);
			if (isCBT1(head) != isCBT2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
