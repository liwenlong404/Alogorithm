package com.study12;
/**
 * @Description: 边描述、有向边
 * @author li
 * @create 2022/8/8 10:06
 */
public class Edge {
	//权重
	public int weight;

	public Node from;
	public Node to;

	public Edge(int weight, Node from, Node to) {
		this.weight = weight;
		this.from = from;
		this.to = to;
	}

}
