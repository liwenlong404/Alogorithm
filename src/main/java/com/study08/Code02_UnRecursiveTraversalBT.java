package com.study08;

import java.util.Stack;

/**
 * @author li
 * @Description: 非递归实现，前序，中序，后序遍历
 * 公理，记住
 * <p>
 * 先序：准备工作，先把根节点压入。
 * 然后重复下面三个步骤：
 * 1.弹出栈，并打印
 * 2.刚才弹出栈的节点，如果有右子节点，压入右子节点
 * 3，如果有左子节点，再压入左子节点
 * 回到步骤1，继续（即while循环，终止条件栈为空）
 * 这样得到的顺序是  头  左  右  先序遍历
 * <p>
 * 后序：
 * 准备工作，先把根节点压入。
 * 然后重复下面三个步骤：
 * 1.弹出栈，并压入另一个准备的栈
 * 2.刚才弹出栈的节点，如果有左子节点，压入左子节点
 * 3，如果有右子节点，在压入右子节点
 * 回到步骤1，继续（即while循环，终止条件栈为空）
 * 顺序和步骤与先序差不多 头  右  左  ，但我们将得到的顺序反过来，便是左 右  头 后序遍历
 * @create 2022/7/30 16:05
 */
public class Code02_UnRecursiveTraversalBT {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    /**
     * 前序
     * @author: Li
     * @dateTime: 2022/7/31 12:59
     */
    public static void pre(Node head) {
        System.out.print("pre-order: ");
        if (head != null) {
            Stack<Node> stack = new Stack<Node>();
            stack.add(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                System.out.print(head.value + " ");
                if (head.right != null) {
                    stack.push(head.right);
                }
                if (head.left != null) {
                    stack.push(head.left);
                }
            }
        }
        System.out.println();
    }

    /**
     * 中序
     * @author: Li
     * @dateTime: 2022/7/31 12:59
     */
    public static void in(Node cur) {
        System.out.print("in-order: ");
        if (cur != null) {
            Stack<Node> stack = new Stack<Node>();
            while (!stack.isEmpty() || cur != null) {
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                } else {
                    cur = stack.pop();
                    System.out.print(cur.value + " ");
                    cur = cur.right;
                }
            }
        }
        System.out.println();
    }

    /**
     * 后序
     * @author: Li
     * @dateTime: 2022/7/31 12:59
     */
    public static void pos1(Node head) {
        System.out.print("pos-order: ");
        if (head != null) {
            Stack<Node> s1 = new Stack<Node>();
            Stack<Node> s2 = new Stack<Node>();
            s1.push(head);
            while (!s1.isEmpty()) {
                head = s1.pop(); // 头 右 左
                s2.push(head);
                if (head.left != null) {
                    s1.push(head.left);
                }
                if (head.right != null) {
                    s1.push(head.right);
                }
            }
            // 左 右 头
            while (!s2.isEmpty()) {
                System.out.print(s2.pop().value + " ");
            }
        }
        System.out.println();
    }

    /**
     * 后序优化版
     * @author: Li
     * @dateTime: 2022/7/31 12:59
     */
    public static void pos2(Node h) {
        System.out.print("pos-order: ");
        if (h != null) {
            Stack<Node> stack = new Stack<Node>();
            stack.push(h);
            Node c = null;
            while (!stack.isEmpty()) {
                c = stack.peek();
                if (c.left != null && h != c.left && h != c.right) {
                    stack.push(c.left);
                } else if (c.right != null && h != c.right) {
                    stack.push(c.right);
                } else {
                    System.out.print(stack.pop().value + " ");
                    h = c;
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        pre(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        pos1(head);
        System.out.println("========");
        pos2(head);
        System.out.println("========");
    }

}
