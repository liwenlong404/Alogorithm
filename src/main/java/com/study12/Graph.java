package com.study12;

import java.util.HashMap;
import java.util.HashSet;
/**
 * @Description: 图结构的描述
 * @author li
 * @create 2022/8/8 10:07
 */
public class Graph {
	//点集
	public HashMap<Integer, Node> nodes;
	//边集
	public HashSet<Edge> edges;
	
	public Graph() {
		nodes = new HashMap<>();
		edges = new HashSet<>();
	}
}
