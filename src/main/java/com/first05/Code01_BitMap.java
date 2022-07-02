package com.first05;

import java.util.HashSet;

/**
 * @Description:  位图的实现
 * @author li
 * @create 2022/5/9 21:50
 */
public class Code01_BitMap {

	public static class BitMap {

		private long[] bits;

		public BitMap(int max) {
			bits = new long[(max + 64) >> 6];
		}

		public void add(int num) {
			//使用1L，即long类型的1，因为int类型1只有32位
			bits[num >> 6] |= (1L << (num & 63));
		}

		public void delete(int num) {
			bits[num >> 6] &= ~(1L << (num & 63));
		}

		public boolean contains(int num) {
			return (bits[num >> 6] & (1L << (num & 63))) != 0;
		}

	}

	public static void main(String[] args) {
		System.out.println("测试开始！");
		int max = 10000;
		BitMap bitMap = new BitMap(max);
		HashSet<Integer> set = new HashSet<>();
		int testTime = 10000000;
		for (int i = 0; i < testTime; i++) {
			int num = (int) (Math.random() * (max + 1));
			double decide = Math.random();
			if (decide < 0.333) {
				bitMap.add(num);
				set.add(num);
			} else if (decide < 0.666) {
				bitMap.delete(num);
				set.remove(num);
			} else {
				if (bitMap.contains(num) != set.contains(num)) {
					System.out.println("Oops!");
					break;
				}
			}
		}
		for (int num = 0; num <= max; num++) {
			if (bitMap.contains(num) != set.contains(num)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束！");
	}

}
