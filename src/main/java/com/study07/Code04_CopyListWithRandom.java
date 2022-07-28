package com.study07;

import java.util.HashMap;

/**
 * @Description:
 *  测试链接 : https://leetcode.com/problems/copy-list-with-random-pointer/
 *  深度复制带有rand指针的链表
 *
 *  有2个思路：
 *  方法1：循环一次，用hashmap记录clone每个节点，key为原节点，value为clone节点
 *  然后再次循环，找到key的next，赋给对应value的next，key的rand赋给value的ran。
 *  循环结束，复制完成。
 *
 *  方法2：方法1，是通过hashmap构建对应关系，进行复制。我们可以在链表上直接进行操作构建对应关系
 *  思路为，将每个节点复制，连接到对应节点后，如1-2-3-4-5，clone并链接就成为：1-1'-2-2'-3-3'-4-4'-5-5'
 *  这样也构建了对应关系，操作我们可以一对一对的取出进行操作。
 *  例如1的rand节点指向3，那么1'就应该指向3'，所以   1'.rand==1.rand.next
 *  循环构建对应的rand节点
 *  循环结束后。分离链表。
 *
 * @author li
 * @create 2022/7/28 15:34
 */
public class Code04_CopyListWithRandom {

	public static class Node {
		int val;
		Node next;
		Node random;

		public Node(int val) {
			this.val = val;
			this.next = null;
			this.random = null;
		}
	}

	public static Node copyRandomList1(Node head) {
		// key 老节点
		// value 新节点
		HashMap<Node, Node> map = new HashMap<Node, Node>();
		Node cur = head;
		while (cur != null) {
			map.put(cur, new Node(cur.val));
			cur = cur.next;
		}
		cur = head;
		while (cur != null) {
			// cur 老
			// map.get(cur) 新
			// 新.next ->  cur.next克隆节点找到
			map.get(cur).next = map.get(cur.next);
			map.get(cur).random = map.get(cur.random);
			cur = cur.next;
		}
		return map.get(head);
	}

	public static Node copyRandomList2(Node head) {
		if (head == null) {
			return null;
		}
		Node cur = head;
		Node next = null;
		// 1 -> 2 -> 3 -> null
		// 1 -> 1' -> 2 -> 2' -> 3 -> 3'
		while (cur != null) {
			next = cur.next;
			cur.next = new Node(cur.val);
			cur.next.next = next;
			cur = next;
		}
		cur = head;
		Node copy = null;
		// 1 1' 2 2' 3 3'
		// 依次设置 1' 2' 3' random指针
		while (cur != null) {
			next = cur.next.next;
			copy = cur.next;
			copy.random = cur.random != null ? cur.random.next : null;
			cur = next;
		}
		Node res = head.next;
		cur = head;
		// 老 新 混在一起，next方向上，random正确
		// next方向上，把新老链表分离
		while (cur != null) {
			next = cur.next.next;
			copy = cur.next;
			cur.next = next;
			copy.next = next != null ? next.next : null;
			cur = next;
		}
		return res;
	}

}
