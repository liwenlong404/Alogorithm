package com.study09;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;

/**
 * @author li
 * @Description:  二叉树的打印
 * <p>
 * <p>
 * 首先，二叉树大概的样子是把打印结果顺时针旋转90度；
 * <p>
 *
 *                                              v3v
 *                             v5v
 *                                              ^1^
 *            H2H
 *                                              v8v
 *                            ^3^
 *                                              ^7^
 *
 *
 * <p>
 * 接下来，怎么清晰地确定任何一个节点的父节点呢？
 * 如果一个节点打印结果的前缀与后缀都有"H"，说明这个节点是头节点，当然就不存在父节点。
 * 如果一个节点打印结果的前缀与后缀都有"v"，表示父节点在该节点所在列的前一列，在该节点所在行的下方，并且是离该节点最近的节点。
 * 如果一个节点打印结果的前缀与后缀都有"^"，表示父节点在该节点所在列的前一列，在该节点所在行的上方，并且是离该节点最近的节点。
 * <p>
 * ２、一个需要重点考虑的问题——规定节点打印时占用的统一长度。我们必须规定一个节点在打印时到底占多长。
 * 在Java中，整型值占用长度最长的值是Integer.MIN_VALUE（即-2147483648），占用的长度为11，加上前缀和后缀（"H"、"v"或"^"）之后占用长度为13。
 * 为了在打印之后更好地区分，再把前面加上两个空格，后面加上两个空格，总共占用长度为17。
 * 也就是说长度为17的空间必然可以放下任何一个32位整数，同时样式还不错。
 * 至此，我们约定，打印每一个节点的时候，必须让每一个节点在打印时占用长度都为17，如果不足，前后都用空格补齐。
 * <p>
 * ３、确定了打印的样式，规定了占用长度的标准，最后来解释具体的实现。
 * 打印的整体过程结合了二叉树先右子树、再根节点、最后左子树的递归遍历过程。
 * 如果递归到一个节点，首先遍历它的右子树。右子树遍历结束后，回到这个节点。
 * 如果这个节点所在层为layer，那么先打印layer*17个空格（不换行）
 * 然后开始制作该节点的打印内容，这个内容当然包括节点的值，以及确定的前后缀字符。
 * 如果该节点是其父节点的右孩子，前后缀为"v"，如果是其父节点的左孩子，前后缀为"^"，如果是头节点，前后缀为"H"。
 * 最后在前后分别贴上数量几乎一致的空格，占用长度为17的打印内容就制作完成了，打印这个内容后换行，最后进行左子树的遍历过程。
 * @create 2022/7/31 16:19
 */
public class Code02_PrintBinaryTree {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }


    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(-222222222);
        head.right = new Node(3);
        head.left.left = new Node(Integer.MIN_VALUE);
        head.right.left = new Node(55555555);
        head.right.right = new Node(66);
        head.left.left.right = new Node(777);
        printTree(head);

        head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.right.left = new Node(5);
        head.right.right = new Node(6);
        head.left.left.right = new Node(7);
        printTree(head);

        head = new Node(1);
        head.left = new Node(1);
        head.right = new Node(1);
        head.left.left = new Node(1);
        head.right.left = new Node(1);
        head.right.right = new Node(1);
        head.left.left.right = new Node(1);
        printTree(head);

    }

}
