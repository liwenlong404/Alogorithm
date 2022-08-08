package com.study12;


import java.util.ArrayList;

/**
 * @Description: 点结构的描述
 * @author li
 * @create 2022/8/8 10:04
 */
public class Node {
	//编号，也可为string
	public int value;
	//入度
	public int in;
	//出度
	public int out;
	//直接邻居
	public ArrayList<Node> nexts;
	//边
	public ArrayList<Edge> edges;

	public Node(int value) {
		this.value = value;
		in = 0;
		out = 0;
		nexts = new ArrayList<>();
		edges = new ArrayList<>();
	}
}
