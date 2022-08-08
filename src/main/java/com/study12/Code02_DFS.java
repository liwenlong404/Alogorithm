package com.study12;

import java.util.HashSet;
import java.util.Stack;
/**
 * @Description: 图的深度优先遍历
 *
 *  set注册的时候打印，栈其实记录是的路径状态。
 *  因为栈记录了路径，所有走到尽头后，可以pop弹出回溯，回到上一个节点，找有没有其他的路
 *
 * @author li
 * @create 2022/8/8 10:48
 */
public class Code02_DFS {

	public static void dfs(Node node) {
		if (node == null) {
			return;
		}
		Stack<Node> stack = new Stack<>();
		HashSet<Node> set = new HashSet<>();
		stack.add(node);
		set.add(node);
		System.out.println(node.value);
		while (!stack.isEmpty()) {
			Node cur = stack.pop();
			for (Node next : cur.nexts) {
				if (!set.contains(next)) {
					stack.push(cur);
					stack.push(next);
					set.add(next);
					System.out.println(next.value);
					break;
				}
			}
		}
	}
	
	
	

}
