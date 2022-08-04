package com.study10;
/**
 * @Description: 判断二叉树是不是平衡二叉树
 * @author li
 * @create 2022/8/4 10:59
 */
public class Code01_IsBalanced {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static boolean isBalanced1(Node head) {
		boolean[] ans = new boolean[1];
		ans[0] = true;
		process1(head, ans);
		return ans[0];
	}

	public static int process1(Node head, boolean[] ans) {
		if (!ans[0] || head == null) {
			return -1;
		}
		int leftHeight = process1(head.left, ans);
		int rightHeight = process1(head.right, ans);
		if (Math.abs(leftHeight - rightHeight) > 1) {
			ans[0] = false;
		}
		return Math.max(leftHeight, rightHeight) + 1;
	}

	public static boolean isBalanced2(Node head) {
		return process(head).isBalanced;
	}
	
	public static class Info{
		public boolean isBalanced;
		public int height;
		
		public Info(boolean i, int h) {
			isBalanced = i;
			height = h;
		}
	}
	
	public static Info process(Node x) {
		//base case
		if(x == null) {
			//将叶子节点，也看做是平衡二叉树，高度 0
			return new Info(true, 0);
		}

		//收集信息
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);

		//有了左、右树的信息，得到以自己为节点的树的高度。
		int height = Math.max(leftInfo.height, rightInfo.height)  + 1;

		//isBalanced设为true，默认为是平衡二叉树，然后走if
		boolean isBalanced = true;

		//左树不是平衡二叉树
		if(!leftInfo.isBalanced) {
			isBalanced = false;
		}
		//右树不是平衡二叉树
		if(!rightInfo.isBalanced) {
			isBalanced = false;
		}
		//高度差大于1
		if(Math.abs(leftInfo.height - rightInfo.height) > 1) {
			isBalanced = false;
		}

		//此代码设计的是，得到子数不是平衡二叉树，不会立即返回，而是会走完整个流程。
		//最后会返回整个数的info
		return new Info(isBalanced, height);
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
			if (isBalanced1(head) != isBalanced2(head)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("finish!");
	}

}
