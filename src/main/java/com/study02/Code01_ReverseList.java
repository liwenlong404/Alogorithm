package com.study02;

/**
 * @Description: 翻转链表  first04
 * @author: li
 * @create: 2022-05-16 17:35
 */
public class Code01_ReverseList {
    /**
     * 节点类
     *
     * @author: Li
     * @dateTime: 2022/5/16 17:37
     */
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            value = data;
        }
    }

    /**
     * 翻转单链表
     *
     * @author: Li
     * @dateTime: 2022/5/16 17:39
     */
    public static Node reverseLinkedList(Node head) {
        Node pre = null;
        Node next = null;
        while (head != null) {
            //记录head.next的值，为next
            next = head.next;

            //进行节点的翻转，将现头结点指向pre，指向null
            head.next=pre;

            //pre和next的右移
            pre=head;
            head=next;
        }
        return pre;
    }

}
