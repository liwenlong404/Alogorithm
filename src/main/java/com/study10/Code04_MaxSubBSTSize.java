package com.study10;

import java.util.ArrayList;
/**
 * @Description: 二叉树中最大的二叉搜索子树的大小
 * @author li
 * @create 2022/8/4 11:00
 */
public class Code04_MaxSubBSTSize {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static int getBSTSize(Node head) {
		if (head == null) {
			return 0;
		}
		ArrayList<Node> arr = new ArrayList<>();
		in(head, arr);
		for (int i = 1; i < arr.size(); i++) {
			if (arr.get(i).value <= arr.get(i - 1).value) {
				return 0;
			}
		}
		return arr.size();
	}

	public static void in(Node head, ArrayList<Node> arr) {
		if (head == null) {
			return;
		}
		in(head.left, arr);
		arr.add(head);
		in(head.right, arr);
	}

	public static int maxSubBSTSize1(Node head) {
		if (head == null) {
			return 0;
		}
		int h = getBSTSize(head);
		if (h != 0) {
			return h;
		}
		return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
	}

//	public static int maxSubBSTSize2(Node head) {
//		if (head == null) {
//			return 0;
//		}
//		return process(head).maxSubBSTSize;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	// 任何子树
// 	public static class Info {
// 		public boolean isAllBST;
// 		public int maxSubBSTSize;
// 		public int min;
// 		public int max;
//
// 		public Info(boolean is, int size, int mi, int ma) {
// 			isAllBST = is;
// 			maxSubBSTSize = size;
// 			min = mi;
// 			max = ma;
// 		}
// 	}
//	
//	
//	
//	
// 	public static Info process(Node X) {
// 		//base case
// 		if(X == null) {
// 			return null;
// 		}
// 		//递归
// 		Info leftInfo = process(X.left);
// 		Info rightInfo = process(X.right);
//
//
// 		//给将自身的value，赋给min、max，后面进行判断
// 		int min = X.value;
// 		int max = X.value;
//
// 		//得到左子树最大值，与最小值
// 		if(leftInfo != null) {
// 			min = Math.min(min, leftInfo.min);
// 			max = Math.max(max, leftInfo.max);
// 		}
// 		//得到右子树最大值，与最小值
// 		if(rightInfo != null) {
// 			min = Math.min(min, rightInfo.min);
// 			max = Math.max(max, rightInfo.max);
// 		}
//
// 		//初始化为 0；
// 		int maxSubBSTSize = 0;
// 		//从左、右子树中找到 maxSubBSTSize
// 		if(leftInfo != null) {
// 			maxSubBSTSize = leftInfo.maxSubBSTSize;
// 		}
// 		if(rightInfo !=null) {
// 			maxSubBSTSize = Math.max(maxSubBSTSize, rightInfo.maxSubBSTSize);
// 		}
//
// 		//如果不是二叉搜索树，就不会进入if，isAllBST = false会设置给自身
// 		boolean isAllBST = false;
//
//
// 		//if 进行判断，自身是搜索二叉树
// 		if(
// 				// 左树整体需要是搜索二叉树
// 				(  leftInfo == null ? true : leftInfo.isAllBST    )
// 				&&
// 				// 右树整体需要是搜索二叉树
// 				(  rightInfo == null ? true : rightInfo.isAllBST    )
// 				&&
// 				// 左树最大值<x
// 				(leftInfo == null ? true : leftInfo.max < X.value)
// 				&&
// 				// 右树最小值>x
// 				(rightInfo == null ? true : rightInfo.min > X.value)
//
// 				) {
//
// 			//满足if，自身是搜索二叉树。算出maxSubBSTSize，自身的isAllBST肯定也是true;
// 			maxSubBSTSize =
// 					(leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
// 					+
// 					(rightInfo == null ? 0 : rightInfo.maxSubBSTSize)
// 					+
// 					1;
// 					isAllBST = true;
//
//
// 		}
// 		//返回自身信息
// 		return new Info(isAllBST, maxSubBSTSize, min, max);
// 	}

	public static int maxSubBSTSize2(Node head) {
		if(head == null) {
			return 0;
		}
		return process(head).maxBSTSubtreeSize;
	}

	public static class Info {
		public int maxBSTSubtreeSize;
		public int allSize;
		public int max;
		public int min;

		public Info(int m, int a, int ma, int mi) {
			maxBSTSubtreeSize = m;
			allSize = a;
			max = ma;
			min = mi;
		}
	}

	public static Info process(Node x) {
		if (x == null) {
			return null;
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);
		int max = x.value;
		int min = x.value;
		int allSize = 1;
		if (leftInfo != null) {
			max = Math.max(leftInfo.max, max);
			min = Math.min(leftInfo.min, min);
			allSize += leftInfo.allSize;
		}
		if (rightInfo != null) {
			max = Math.max(rightInfo.max, max);
			min = Math.min(rightInfo.min, min);
			allSize += rightInfo.allSize;
		}
		int p1 = -1;
		if (leftInfo != null) {
			p1 = leftInfo.maxBSTSubtreeSize;
		}
		int p2 = -1;
		if (rightInfo != null) {
			p2 = rightInfo.maxBSTSubtreeSize;
		}
		int p3 = -1;
		boolean leftBST = leftInfo == null ? true : (leftInfo.maxBSTSubtreeSize == leftInfo.allSize);
		boolean rightBST = rightInfo == null ? true : (rightInfo.maxBSTSubtreeSize == rightInfo.allSize);
		if (leftBST && rightBST) {
			boolean leftMaxLessX = leftInfo == null ? true : (leftInfo.max < x.value);
			boolean rightMinMoreX = rightInfo == null ? true : (x.value < rightInfo.min);
			if (leftMaxLessX && rightMinMoreX) {
				int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
				int rightSize = rightInfo == null ? 0 : rightInfo.allSize;
				p3 = leftSize + rightSize + 1;
			}
		}
		return new Info(Math.max(p1, Math.max(p2, p3)), allSize, max, min);
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
			if (maxSubBSTSize1(head) != maxSubBSTSize2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
