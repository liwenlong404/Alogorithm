# trie、桶排序、排序总结

#前缀树 #桶排序 #计数排序 #基数排序 

---


## 前缀树
1）单个字符串中，字符从前到后的加到一棵多叉树上  
2）字符放在路上，节点上有专属的数据项（常见的是pass和end值）  
3）所有样本都这样添加，如果没有路就新建，如有路就复用  
4）沿途节点的pass值增加1，每个字符串结束时来到的节点end值增加1  

可以完成前缀相关的查询   


### 例子
设计一种结构。用户可以：
```html
1）void insert(String str)            添加某个字符串，可以重复添加，每次算1个
2）int search(String str)             查询某个字符串在结构中还有几个
3) void delete(String str)           删掉某个字符串，可以重复删除，每次算1个
4）int prefixNumber(String str)       查询有多少个字符串，是以str做前缀的
```


### 前缀树路的实现方式

#### 固定数组实现前缀树

```
public static class Node1 {
		public int pass;
		public int end;
		public Node1[] nexts;

		// char tmp = 'b'  (tmp - 'a')
		public Node1() {
			pass = 0;
			end = 0;
			// 0    a
			// 1    b
			// 2    c
			// ..   ..
			// 25   z
			// nexts[i] == null   i方向的路不存在
			// nexts[i] != null   i方向的路存在
			nexts = new Node1[26];
		}
	}

	/**
	 * 第一种  固定数组实现
	 * @author: Li
	 * @dateTime: 2022/7/24 17:26
	 */
	public static class Trie1 {
		private Node1 root;

		public Trie1() {
			root = new Node1();
		}

		public void insert(String word) {
			if (word == null) {
				return;
			}
			char[] str = word.toCharArray();
			Node1 node = root;
			node.pass++;
			int path = 0;
			for (int i = 0; i < str.length; i++) { // 从左往右遍历字符
				path = str[i] - 'a'; // 由字符，对应成走向哪条路
				if (node.nexts[path] == null) {
					//如果节点没有路，new一个
					node.nexts[path] = new Node1();
				}
				//跳到这个新建节点上
				node = node.nexts[path];
				//pass++
				node.pass++;
			}
			//字符结尾处的end++
			node.end++;
		}

		public void delete(String word) {
			//删除前先搜索确定
			if (search(word) != 0) {
				char[] chs = word.toCharArray();
				Node1 node = root;
				node.pass--;
				int path = 0;
				for (int i = 0; i < chs.length; i++) {
					path = chs[i] - 'a';
					//当一个节点的p值为0时，就可以将其和后面的一起删除
					//从父节点开始判断子节点的pass值，然后置null
					if (--node.nexts[path].pass == 0) {
						node.nexts[path] = null;
						return;
					}
					node = node.nexts[path];
				}
				node.end--;
			}
		}

		// word这个单词之前加入过几次
		public int search(String word) {
			if (word == null) {
				return 0;
			}
			char[] chs = word.toCharArray();
			Node1 node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					return 0;
				}
				node = node.nexts[index];
			}
			return node.end;
		}

		// 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
		public int prefixNumber(String pre) {
			if (pre == null) {
				return 0;
			}
			char[] chs = pre.toCharArray();
			Node1 node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					return 0;
				}
				node = node.nexts[index];
			}
			return node.pass;
		}
	}
```

#### 哈希表实现前缀树

```
public static class Node2 {
		public int pass;
		public int end;
		public HashMap<Integer, Node2> nexts;

		public Node2() {
			pass = 0;
			end = 0;
			nexts = new HashMap<>();
		}
	}

	public static class Trie2 {
		private Node2 root;

		public Trie2() {
			root = new Node2();
		}

		public void insert(String word) {
			if (word == null) {
				return;
			}
			char[] chs = word.toCharArray();
			Node2 node = root;
			node.pass++;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = (int) chs[i];
				if (!node.nexts.containsKey(index)) {
					node.nexts.put(index, new Node2());
				}
				node = node.nexts.get(index);
				node.pass++;
			}
			node.end++;
		}

		public void delete(String word) {
			if (search(word) != 0) {
				char[] chs = word.toCharArray();
				Node2 node = root;
				node.pass--;
				int index = 0;
				for (int i = 0; i < chs.length; i++) {
					index = (int) chs[i];
					if (--node.nexts.get(index).pass == 0) {
						node.nexts.remove(index);
						return;
					}
					node = node.nexts.get(index);
				}
				node.end--;
			}
		}

		// word这个单词之前加入过几次
		public int search(String word) {
			if (word == null) {
				return 0;
			}
			char[] chs = word.toCharArray();
			Node2 node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = (int) chs[i];
				if (!node.nexts.containsKey(index)) {
					return 0;
				}
				node = node.nexts.get(index);
			}
			return node.end;
		}

		// 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
		public int prefixNumber(String pre) {
			if (pre == null) {
				return 0;
			}
			char[] chs = pre.toCharArray();
			Node2 node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = (int) chs[i];
				if (!node.nexts.containsKey(index)) {
					return 0;
				}
				node = node.nexts.get(index);
			}
			return node.pass;
		}
	}
```

**拓展：**
目前节点只是封装了p值跟e值, 可以封装别的更丰富的信息来解决某些问题
如果一些题带有前缀查询特征, 前缀树就可以通过每个节点增加更多信息支持本题目快速解决




## 不基于比较的排序
桶排序思想下的排序：计数排序 & 基数排序 

1)桶排序思想下的排序都是不基于比较的排序

2)时间复杂度为O(N)，额外空间负载度O(M)

3)应用范围有限，需要样本的数据状况满足桶的划分 

### 计数排序和基数排序
####  计数排序

```
// only for 0~200 value
	public static void countSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(max, arr[i]);
		}
		int[] bucket = new int[max + 1];
		for (int i = 0; i < arr.length; i++) {
			bucket[arr[i]]++;
		}
		int i = 0;
		for (int j = 0; j < bucket.length; j++) {
			while (bucket[j]-- > 0) {
				arr[i++] = j;
			}
		}
	}
```



#### 基数排序代码

```
// 只限正整数
	public static void radixSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		radixSort(arr, 0, arr.length - 1, maxbits(arr));
	}

	/**
	 * 找到最大值，得到最大值位数
	 * @author: Li
	 * @dateTime: 2022/7/24 17:51
	 */
	public static int maxbits(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(max, arr[i]);
		}
		int res = 0;
		while (max != 0) {
			res++;
			max /= 10;
		}
		return res;
	}

	/**
	 *
	 * 理论上10进制的基数排序，可以准备10个"桶"，来进行倒入倒出操作，进行排序
	 * 但可以进行优化，使用2个数组，来进行排序
	 *
	 *  10进制为例
	 *  准备一个与原数组等长的数组help
	 *  for循环，for (int d = 1; d <= digit; d++) 即从个位开始，终止为最大值的十进制位数digit
	 * 准备一个count[0..9]数组，对所有数的个位数的出现次数，进行一个计数。
	 * 例：所有数，个位上，0出现4次，则count[0]=4
	 *
	 * 完成count计数后，对count进行改造
	 *
	 * count只是个位数出现次数，对count进行累加
	 * 例如：0位3个，count[0]=3, 1位5个，但要加上0位的，则count[1]=3+5,
	 * 2位2个，加上1位的。count[2]=2+5, 后面以此类推
	 *
	 * 然后对数组进行倒序的排列
	 *
	 * 从最后一个数开始，获取个位数值，通过这个值去count数组里找对应数值下标的数。
	 * 将这个数减一，就是存放在help数组中的位置。
	 * 因为改造后的count数组，存放的是范围。如上面的count[0]=3,count[1]=5,count[2]=7
	 *
	 * 0位，在排序后的数组中，会排在<=3的范围上。1位会排在<=5的范上。
	 * 而我们是倒序开始排列，例如：倒序第一个的个位是1，就将其排在5-1=4的位置上。然后自减1
	 * 相当于模拟了，原始的10个桶的队列操作
	 *
	 * arr[L..R]排序  ,  最大值的十进制位数digit(例：max：100   digit：3)
	 * @author: Li
	 * @dateTime: 2022/7/24 18:25
	 */
	public static void radixSort(int[] arr, int L, int R, int digit) {
		//10进制
		final int radix = 10;

		int i = 0, j = 0;
		//数组等长help
		// 有多少个数准备多少个辅助空间
		int[] help = new int[R - L + 1];

		// 有多少位就进出几次，例如最大值为4位，则所有数，需要进行4次倒出覆盖操作
		for (int d = 1; d <= digit; d++) {
			// 10个空间
		    // count[0] 当前位(d位)是0的数字有多少个
			// count[1] 当前位(d位)是(0和1)的数字有多少个
			// count[2] 当前位(d位)是(0、1和2)的数字有多少个
			// count[i] 当前位(d位)是(0~i)的数字有多少个
			int[] count = new int[radix]; // count[0..9]
			for (i = L; i <= R; i++) {
				// 103  1   3  d为1取个位的数，d为2取十位的数
				// 209  1   9
				j = getDigit(arr[i], d);

				//count计数，j为3，则index为3的位置，加一，代表有一个3
				count[j]++;
			}
			//上面循环结束后，所有数的d位，计数完成
			//进行累加，例如：0位3个，count[0]=3, 1位5个，但要加上0位的，count[1]=3+5,后面以此类推
			//变成   count'   数组
			for (i = 1; i < radix; i++) {
				count[i] = count[i] + count[i - 1];
			}
			for (i = R; i >= L; i--) {
				j = getDigit(arr[i], d);
				help[count[j] - 1] = arr[i];
				count[j]--;
			}
			for (i = L, j = 0; i <= R; i++, j++) {
				arr[i] = help[j];
			}
		}
	}

	public static int getDigit(int x, int d) {
		return ((x / ((int) Math.pow(10, d - 1))) % 10);
	}
```

1）一般来讲，计数排序要求，样本是整数，且范围比较窄

2）一般来讲，基数排序要求，样本是10进制的正整数

一旦要求稍有升级，改写代价增加是显而易见的

## 排序算法的稳定性
稳定性是指同样大小的样本再排序之后不会改变相对次序

对基础类型来说，稳定性毫无意义

对非基础类型来说，稳定性有重要意义

有些排序算法可以实现成稳定的，而有些排序算法无论如何都实现不成稳定的

## 排序算法总结

|          | 时间复杂度 | 额外空间复杂度 | 稳定性 |
| -------- | ---------- | -------------- | ------ |
| 选择排序 | O(N^2)     | O(1)           | 无     |
| 冒泡排序 | O(N^2)     | O(1)           | 有     |
| 插入排序 | O(N^2)     | O(1)           | 有     |
| 归并排序 | O(N* logN) | O(N)           | 有     |
| 随机快排 | O(N* logN) | O(logN)        | 无     |
| 堆排序   | O(N* logN) | O(1)           | 无     |
|          |            |                |        |
| 计数排序 | O(N)       | O(M)           | 有     |
| 基数排序 | O(N)       | O(N)           | 有     |

1）不基于比较的排序，对样本数据有严格要求，不易改写  
2）基于比较的排序，只要规定好两个样本怎么比大小就可以直接复用  
3）基于比较的排序，时间复杂度的极限是O($N*logN$)  
4）时间复杂度O($N*logN$)、额外空间复杂度低于O(N)、且稳定的基于比较的排序是不存在的。  
5）为了绝对的速度选快排、为了省空间选堆排、为了稳定性选归并  

## 常见的坑

1）归并排序的额外空间复杂度可以变成O(1)，“归并排序 内部缓存法”，但是将变得不再稳定。
    ==> 可以, 方法很难, 都不稳定了, 为什么不用堆排序?

2）“原地归并排序" 是垃圾贴，会让时间复杂度变成O(N^2)  
    ==> 额外空间复杂度可以变成O(1), 但让时间复杂度退变成N^2, 用插入排序多好

3）快速排序稳定性改进，“01 stable sort”，但是会对样本数据要求更多。
   ==> 可以, 要求对数据范围做限制, 快排就是基于比较的排序, 对数据状况做 限制, 为什么不用不基于比较的桶排序呢?

问题：在整型数组中把奇数放在左边偶数放在右边且保持稳定性，且时间复杂度O($N$)，额外空间复杂度O(1)

这是一个01标准的partition，奇数放左边，偶数放右边。但快排的partition过程是无法做到稳定性的，问题说奇数放左边，偶数放右边，能做到稳定性。所有快排为什么不改成稳定的？


## 工程上对排序的改进

1)稳定性的考虑 

2)充分利用O(N*logN)和O(N^2)排序各自的优势

---

