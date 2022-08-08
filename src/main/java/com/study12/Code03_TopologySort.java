package com.study12;

import java.util.*;
/**
 * @Description: 拓扑排序
 * @author li
 * @create 2022/8/8 11:09
 */
public class Code03_TopologySort {

	// directed graph and no loop
	public static List<Node> sortedTopology(Graph graph) {
		// key 某个节点   value 剩余的入度
		HashMap<Node, Integer> inMap = new HashMap<>();
		// 只有剩余入度为0的点，才进入这个队列
		Queue<Node> zeroInQueue = new LinkedList<>();

		//拿到图所有的点集
		for (Node node : graph.nodes.values()) {
			//放入原始剩余入度的点集
			inMap.put(node, node.in);
			//入度为0，放入zeroInQueue
			if (node.in == 0) {
				zeroInQueue.add(node);
			}
		}
		//拓扑排序的结果
		List<Node> result = new ArrayList<>();
		while (!zeroInQueue.isEmpty()) {
			//将入度0的，放入结果
			Node cur = zeroInQueue.poll();
			result.add(cur);

			//因为我已经放入结果，消除我对邻居的影响。如果邻居的入度也为0，就加入zeroInQueue
			for (Node next : cur.nexts) {
				inMap.put(next, inMap.get(next) - 1);
				if (inMap.get(next) == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return result;
	}
}
