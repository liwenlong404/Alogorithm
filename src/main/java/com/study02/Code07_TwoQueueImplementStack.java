package com.study02;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
/**
 * @Description: 用队列实现栈
 * 思路：准备a，b两个队列。a队列1,2,3,4,5.
 * 想要模拟栈，拿到1.就将2,3,4,5放入b队列。将a队列的1返回
 * 依然是2个队列来回倒
 * @author li
 * @create 2022/5/16 19:55
 */
public class Code07_TwoQueueImplementStack {

	public static class TwoQueueStack<T> {
		public Queue<T> queue;
		public Queue<T> help;

		public TwoQueueStack() {
			 queue = new LinkedList<>();
			help = new LinkedList<>();
		}

		/**
		 * 正常存放数据
		 * @author: Li
		 * @dateTime: 2022/5/16 20:06
		 */
		public void push(T value) {
			queue.offer(value);
		}

		/**
		 * 拿数据
		 * @author: Li
		 * @dateTime: 2022/5/16 20:07
		 */
		public T poll() {
			//将queue的数据倒入help，直至queue剩一个数
			while (queue.size() > 1) {
				help.offer(queue.poll());
			}


			T ans = queue.poll();

			//将2个队列的引用地址互换，即下一次用时，存数的依然是queue，help是倒数的
			Queue<T> tmp = queue;
			queue = help;
			help = tmp;
			return ans;
		}

		/**
		 * 查看栈顶
		 * @author: Li
		 * @dateTime: 2022/5/16 20:07
		 */
		public T peek() {
			while (queue.size() > 1) {
				help.offer(queue.poll());
			}

			T ans = queue.poll();
			help.offer(ans);


			Queue<T> tmp = queue;
			queue = help;
			help = tmp;
			return ans;
		}

		public boolean isEmpty() {
			return queue.isEmpty();
		}

	}

	public static void main(String[] args) {
		System.out.println("test begin");
		TwoQueueStack<Integer> myStack = new TwoQueueStack<>();
		Stack<Integer> test = new Stack<>();
		int testTime = 1000000;
		int max = 1000000;
		for (int i = 0; i < testTime; i++) {
			if (myStack.isEmpty()) {
				if (!test.isEmpty()) {
					System.out.println("Oops");
				}
				int num = (int) (Math.random() * max);
				myStack.push(num);
				test.push(num);
			} else {
				if (Math.random() < 0.25) {
					int num = (int) (Math.random() * max);
					myStack.push(num);
					test.push(num);
				} else if (Math.random() < 0.5) {
					if (!myStack.peek().equals(test.peek())) {
						System.out.println("Oops");
					}
				} else if (Math.random() < 0.75) {
					if (!myStack.poll().equals(test.pop())) {
						System.out.println("Oops");
					}
				} else {
					if (myStack.isEmpty() != test.isEmpty()) {
						System.out.println("Oops");
					}
				}
			}
		}

		System.out.println("test finish!");

	}

}
