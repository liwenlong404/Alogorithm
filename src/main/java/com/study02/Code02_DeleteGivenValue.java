package com.study02;
/**
 * @Description:  删除链表中value为num的值
 * @author li
 * @create 2022/5/16 18:15
 */
public class Code02_DeleteGivenValue {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	// head = removeValue(head, 2);
	public static Node removeValue(Node head, int num) {
		//返回的是head节点
		//边界条件，如果head为需要删除的节点
		//head来到第一个不需要删的位置
		while (head != null) {
			if (head.value != num) {
				break;
			}
			head = head.next;
		}


		Node pre = head;
		Node cur = head;
		while (cur != null) {
			if (cur.value == num) {
				pre.next = cur.next;
			} else {
				pre = cur;
			}
			cur = cur.next;
		}
		return head;
	}

	//TODO 对数器验证

}
