package com.study12;
/**
 * @Description: 其他图结构，转为自己用的顺手的图结构
 * @author li
 * @create 2022/8/8 10:10
 */
public class GraphGenerator {

	// matrix 所有的边
	// N*3 的矩阵
	// [weight, from节点上面的值，to节点上面的值]
	// 
	// [ 5 , 0 , 7]
	// [ 3 , 0,  1]
	// 
	public static Graph createGraph(int[][] matrix) {
		Graph graph = new Graph();
		for (int i = 0; i < matrix.length; i++) {
			 // 拿到每一条边， matrix[i] 
			int weight = matrix[i][0];
			int from = matrix[i][1];
			int to = matrix[i][2];

			//得到from、to 点，然后通过graph先建立点集
			if (!graph.nodes.containsKey(from)) {
				graph.nodes.put(from, new Node(from));
			}
			if (!graph.nodes.containsKey(to)) {
				graph.nodes.put(to, new Node(to));
			}
			Node fromNode = graph.nodes.get(from);
			Node toNode = graph.nodes.get(to);


			//建边
			Edge newEdge = new Edge(weight, fromNode, toNode);
			//设置点的next
			fromNode.nexts.add(toNode);

			//设置出度
			fromNode.out++;

			//设置入度
			toNode.in++;

			//添加点的直接边
			fromNode.edges.add(newEdge);
			//添加到大图图的边集
			graph.edges.add(newEdge);
		}
		return graph;
	}

}
