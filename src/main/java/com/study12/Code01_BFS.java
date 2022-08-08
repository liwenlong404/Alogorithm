package com.study12;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
/**
 * @Description: 图的宽度优先遍历
 *
 * 同一层的顺序无所谓。
 *
 *
 * @author li
 * @create 2022/8/8 10:43
 */
public class Code01_BFS {

	// 从node出发，进行宽度优先遍历
	public static void bfs(Node start) {
		if (start == null) {
			return;
		}
		Queue<Node> queue = new LinkedList<>();
		HashSet<Node> set = new HashSet<>();
		queue.add(start);
		set.add(start);
		while (!queue.isEmpty()) {
			//弹出打印
			Node cur = queue.poll();
			System.out.println(cur.value);

			//没进入过set的的直接邻居，放入set和队列
			for (Node next : cur.nexts) {
				if (!set.contains(next)) {
					set.add(next);
					queue.add(next);
				}
			}
		}
	}

}
