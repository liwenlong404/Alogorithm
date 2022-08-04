## 二叉树的递归套路

可以解决面试中绝大多数的二叉树问题尤其是**树型DP问题**

本质是利用递归遍历二叉树的便利性



1. 假设以X节点为头，假设可以向X左树和X右树要任何信息  
2. 在上一步的假设下，讨论以X为头节点的树，得到答案的**可能性（最重要）** 
3. 列出所有可能性后，确定到底需要向左树和右树要什么样的信息  
4. 把左树信息和右树信息求全集，就是任何一棵子树都需要返回的信息S  
5. 递归函数都返回S，每一棵子树都这么要求  
6. 写代码，在代码中考虑如何把左树的信息和右树信息整合出整棵树的信息   





## 二叉树的递归套路深度实践

> 树形DP问题，都可以用此套路

我们用上面的套路来，练习下面几道题



### 判断二叉树是不是平衡二叉树

给定一棵二叉树的头节点head，返回这颗二叉树是不是平衡二叉树。

> 平衡二叉树： 是一棵空树，或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。 



照上面的套路，一步一步分析。

1.从X节点开始

2.分析得到答案可能性：左树是平衡二叉树，右数也是平衡二叉树，且两数高度差不超过1。

3.分析可能性后，得到，需要2个信息：是否是平衡二叉树，高度。

4.收集信息，每一棵子树都需要返回上面的两个信息。

开始写代码



因为需要：是否是平衡二叉树与高度，则2哥信息，所有设计如下的info类

```
public static class Info{
		public boolean isBalanced;
		public int height;
		
		public Info(boolean i, int h) {
			isBalanced = i;
			height = h;
		}
	}
```



然后开始写递归代码

大致为

1.base case

2.递归收集信息

3.将收集的信息与自身进行结合，处理。

4.返回自身的信息。

​	具体看代码注释

```
	public static Info process(Node x) {
		//base case   
		if(x == null) {
			//将叶子节点，也看做是平衡二叉树，高度 0
			return new Info(true, 0);
		}
		
		//收集信息
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);
		
		//有了左、右树的信息，得到以自己为节点的树的高度。
		int height = Math.max(leftInfo.height, rightInfo.height)  + 1;
		
		//isBalanced设为true，默认为是平衡二叉树，然后走if
		boolean isBalanced = true;
		
		//左树不是平衡二叉树
		if(!leftInfo.isBalanced) {
			isBalanced = false;
		}
		//右树不是平衡二叉树
		if(!rightInfo.isBalanced) {
			isBalanced = false;
		}
		//高度差大于1
		if(Math.abs(leftInfo.height - rightInfo.height) > 1) {
			isBalanced = false;
		}
		
		//此代码设计的是，得到子数不是平衡二叉树，不会立即返回，而是会走完整个流程。
		//最后会返回整个数的info
		return new Info(isBalanced, height);
	}
	
	
```





### 判断二叉树是不是满二叉树

给定一棵二叉树的头节点head，返回这颗二叉树是不是满二叉树。



套路、步骤上面已经说了，所以不细说

#### 方法一

分析可能性：因为是满二叉树，所以我们知道一个公式

h：高度   n：节点数

2 ^h^ - 1 ==  n



所以得到信息，建info类

```
public static class Info1 {
		//高度
		public int height;
		//节点数
		public int nodes;

		public Info1(int h, int n) {
			height = h;
			nodes = n;
		}
	}
```



递归代码

```
	public static Info1 process1(Node head) {
		//既是边界，也是base case 
		if (head == null) {
			return new Info1(0, 0);
		}
		//收集信息
		Info1 leftInfo = process1(head.left);
		Info1 rightInfo = process1(head.right);
		
		//得到自身高度
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		//得到以自身为基础的节点数
		int nodes = leftInfo.nodes + rightInfo.nodes + 1;
		
		return new Info1(height, nodes);
	}
```



得到了节点数，与高度，进行公式判断

```
public static boolean isFull1(Node head) {
		if (head == null) {
			return true;
		}
		Info1 all = process1(head);
		return (1 << all.height) - 1 == all.nodes;
	}
```



#### 方法二

分析可能性：设以X节点为头。即左右子树都是满二叉树，且两子树高度一样。

所以得到信息，建info类。

```
	public static class Info2 {
		//是否是满二叉树
		public boolean isFull;
		//高度
		public int height;

		public Info2(boolean f, int h) {
			isFull = f;
			height = h;
		}
	}
```



递归代码：

```
public static Info2 process2(Node h) {
		//既是边界，也是base case
		if (h == null) {
			return new Info2(true, 0);
		}
		//收集信息
		Info2 leftInfo = process2(h.left);
		Info2 rightInfo = process2(h.right);

		//进行判断，自身节点的isFull  ：左右子树都是满二叉树，且两子树高度一样为满二叉树 true 否则 false
		boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
		//自身节点的height高度
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		//返回自身信息
		return new Info2(isFull, height);
	}
```



进行判断

```
public static boolean isFull2(Node head) {
		if (head == null) {
			return true;
		}
		return process2(head).isFull;
	}
```





### 判断二叉树是不是搜索二叉树

给定一棵二叉树的头节点head，返回这颗二叉树是不是搜索二叉树

> 二叉搜索树：
>
> 1.它或者是一棵空树
>
> 2.若它的左子树不空，则左子树上的最大值小于它的根结点的值
>
> 3.若它的右子树不空，则右子树上的最小值大于它的根结点的值
>
> 4.它的左、右子树也分别为二叉排序树。 



根据搜索二叉树定义，我们可以得到答案可能性。（其实就是定义）

所以我们可以分析出需要的信息，建立info类。

```
	public static class Info {
		//是否是二分搜索树
		public boolean isBST;
		//最大值
		public int max;
		//最小值
		public int min;

		public Info(boolean i, int ma, int mi) {
			isBST = i;
			max = ma;
			min = mi;
		}
	}
```



递归代码

```
public static Info process(Node x) {
		//既是边界，也是base case
		if (x == null) {
			return null;
		}

		//收集信息
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);

		//将自身的value，设为最大值，下面会进行判断
		int max = x.value;

		//获取左子树最大值
		if (leftInfo != null) {
			max = Math.max(max, leftInfo.max);
		}
		//获取右子树最大值
		if (rightInfo != null) {
			max = Math.max(max, rightInfo.max);
		}

		//将自身的value，设为最大值，下面会进行判断
		int min = x.value;
		//获取左子树最小值
		if (leftInfo != null) {
			min = Math.min(min, leftInfo.min);
		}
		//获取右子树最小值
		if (rightInfo != null) {
			min = Math.min(min, rightInfo.min);
		}

		//isBST设为true，默认为是搜索二叉树，然后走if
		boolean isBST = true;
		//左子树不为空，且不是二叉搜索树
		if (leftInfo != null && !leftInfo.isBST) {
			isBST = false;
		}
		//右子树不为空，且不是二叉搜索树
		if (rightInfo != null && !rightInfo.isBST) {
			isBST = false;
		}
		//左子树不为空，且最大值，大于自身
		if (leftInfo != null && leftInfo.max >= x.value) {
			isBST = false;
		}
		//右子树不为空，且最小值，小于自身
		if (rightInfo != null && rightInfo.min <= x.value) {
			isBST = false;
		}
		
		//返回自身相关信息
		return new Info(isBST, max, min);
	}
```



### 二叉树中最大的二叉搜索子树的大小

此题与上面一题相似，是求最大的二叉搜索子树的大下。

但对二叉树搜索子树的判断肯定是不会变的。

只不过我们需要多一个信息，来记录，最大的二叉搜索子树的大小。

建立info类。

```
	public static class Info {
		//是否是二分搜索树
		public boolean isAllBST;
		//最大的二叉搜索树的大小
		public int maxSubBSTSize;
		//最小值
		public int min;
		//最大值
		public int max;

		public Info(boolean is, int size, int mi, int ma) {
			isAllBST = is;
			maxSubBSTSize = size;
			min = mi;
			max = ma;
		}
	}
```



递归代码

```
public static Info process(Node X) {
		//base case
		if(X == null) {
			return null;
		}
		//递归
		Info leftInfo = process(X.left);
		Info rightInfo = process(X.right);

		
		//给将自身的value，赋给min、max，后面进行判断
		int min = X.value;
		int max = X.value;

		//得到左子树最大值，与最小值
		if(leftInfo != null) {
			min = Math.min(min, leftInfo.min);
			max = Math.max(max, leftInfo.max);
		}
		//得到右子树最大值，与最小值
		if(rightInfo != null) {
			min = Math.min(min, rightInfo.min);
			max = Math.max(max, rightInfo.max);
		}

		//初始化为 0；
		int maxSubBSTSize = 0;
		//从左、右子树中找到 maxSubBSTSize
		if(leftInfo != null) {
			maxSubBSTSize = leftInfo.maxSubBSTSize;
		}
		if(rightInfo !=null) {
			maxSubBSTSize = Math.max(maxSubBSTSize, rightInfo.maxSubBSTSize);
		}
		
		//如果不是二叉搜索树，就不会进入if，isAllBST = false会设置给自身
		boolean isAllBST = false;


		//if 进行判断，自身是搜索二叉树
		if(
				// 左树整体需要是搜索二叉树
				(  leftInfo == null ? true : leftInfo.isAllBST    )
				&&
				// 右树整体需要是搜索二叉树
				(  rightInfo == null ? true : rightInfo.isAllBST    )
				&&
				// 左树最大值<x
				(leftInfo == null ? true : leftInfo.max < X.value)
				&&
				// 右树最小值>x		
				(rightInfo == null ? true : rightInfo.min > X.value)
				
				) {
			
			//满足if，自身是搜索二叉树。算出maxSubBSTSize，自身的isAllBST肯定也是true;
			maxSubBSTSize =
					(leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
					+
					(rightInfo == null ? 0 : rightInfo.maxSubBSTSize)
					+
					1;
					isAllBST = true;


		}
		//返回自身信息
		return new Info(isAllBST, maxSubBSTSize, min, max);
	}
```





### 二叉树中最大的二叉搜索子树的头节点

与上面的题类似，只不过将信息：是否是二分搜索树，换成了最大二叉搜索子数的头节点。

info类如下

```
public static class Info {	
		//因为有了最大的二叉搜索子树头节点，就可以判断其是否是二叉搜索树
		public Node maxSubBSTHead;
		public int maxSubBSTSize;
		public int min;
		public int max;

		public Info(Node h, int size, int mi, int ma) {
			maxSubBSTHead = h;
			maxSubBSTSize = size;
			min = mi;
			max = ma;
		}
	}

```



递归

```
	public static Info process(Node X) {
		if (X == null) {
			return null;
		}
		Info leftInfo = process(X.left);
		Info rightInfo = process(X.right);

		//设置值
		int min = X.value;
		int max = X.value;
		Node maxSubBSTHead = null;
		int maxSubBSTSize = 0;

		//信息处理，从左右子树中得到，真正的maxSubBSTSize和maxSubBSTHead
		if (leftInfo != null) {
			min = Math.min(min, leftInfo.min);
			max = Math.max(max, leftInfo.max);
			maxSubBSTHead = leftInfo.maxSubBSTHead;
			maxSubBSTSize = leftInfo.maxSubBSTSize;
		}
		if (rightInfo != null) {
			min = Math.min(min, rightInfo.min);
			max = Math.max(max, rightInfo.max);
			if (rightInfo.maxSubBSTSize > maxSubBSTSize) {
				maxSubBSTHead = rightInfo.maxSubBSTHead;
				maxSubBSTSize = rightInfo.maxSubBSTSize;
			}
		}
		//如果左右子树都为二分搜索树  leftInfo.maxSubBSTHead == X.left其实就是在判断是否是二叉搜索树
		if ((leftInfo == null ? true : (leftInfo.maxSubBSTHead == X.left && leftInfo.max < X.value))
				&& (rightInfo == null ? true : (rightInfo.maxSubBSTHead == X.right && rightInfo.min > X.value))) {
			maxSubBSTHead = X;
			maxSubBSTSize = (leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
					+ (rightInfo == null ? 0 : rightInfo.maxSubBSTSize) + 1;
		}
		return new Info(maxSubBSTHead, maxSubBSTSize, min, max);
	}
```



​		



### 判断二叉树是不是完全二叉树

#### 方法1

给定一棵二叉树的头节点head，返回这颗二叉树是不是完全二叉树



分析可能性：是满二叉树，或者是从左到右依次变满的过程，也为完全二叉树。



细分：满二叉树是完全二叉树。

左子树是完全二叉树。右子树是满二叉树，但左树层数比右树多1。

左子树是满二叉树，右树是完全二叉树，层数相同。

左子树是满二叉树，右树是满二叉树，但左树层数比右树多1。



提取所需要的信息：是否是满二叉树，是否是完全二叉树，层数（高度）

建立info类

```
public static class Info {
		//是否是满二叉树
		public boolean isFull;
		//是否是完全二叉树
		public boolean isCBT;
		//高度
		public int height;

		public Info(boolean full, boolean cbt, int h) {
			isFull = full;
			isCBT = cbt;
			height = h;
		}
	}
```



递归

```
public static Info process(Node x) {
		if (x == null) {
			return new Info(true, true, 0);
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);

		int height = Math.max(leftInfo.height, rightInfo.height) + 1;

		//左是满，右是满，且高度一致。我为满
		boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;

		//几种近况，1.左满、右满   2.左满，右满，且左高度多1(刚好到中线)  3.左满，右完，高度一致  4.左完，右满，左高度多1

		boolean isCBT = false;
		if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
			isCBT = true;
		} else if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
			isCBT = true;
		} else if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
			isCBT = true;
		} else if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
			isCBT = true;
		}
		return new Info(isFull, isCBT, height);
	}
```



#### 方法2

其实可以用宽度优先遍历（按层遍历）来进行判断。

如果左右子节点不双全（没有左右子节点，或只有一个），后序所有遇到的节点(层序遍历)都必须为叶子节点，否则不是完全二叉树

```
public static boolean isCBT1(Node head) {
		if (head == null) {
			return true;
		}
		LinkedList<Node> queue = new LinkedList<>();
		// 是否遇到过左右两个孩子不双全的节点
		boolean leaf = false;
		Node l = null;
		Node r = null;
		queue.add(head);
		while (!queue.isEmpty()) {
			head = queue.poll();
			l = head.left;
			r = head.right;

			//主要的判断
			if (
			// leaf为true，遇到了不双全的节点之后，又发现当前节点不是叶节点（即存在子节点l，r）
			(leaf && (l != null || r != null)) || (l == null && r != null)

			) {
				return false;
			}

			//继续入队列，宽度有限遍历
			if (l != null) {
				queue.add(l);
			}
			if (r != null) {
				queue.add(r);
			}

			//遇到了，不双全的节点。其实只需要用到一次
			if (l == null || r == null) {
				leaf = true;
			}
		}
		return true;
	}

```

### 派对的最大快乐值

给定一棵多叉树的头节点boss，请返回派对的最大快乐值。

员工信息的定义如下:
```java
class Employee {
    public int happy; // 这名员工可以带来的快乐值
    List<Employee> subordinates; // 这名员工有哪些直接下级
}
```

公司的每个员工都符合 Employee 类的描述。整个公司的人员结构可以看作是一棵标准的、 没有环的多叉树。

树的头节点是公司唯一的老板。除老板之外的每个员工都有唯一的直接上级。 

叶节点是没有任何下属的基层员工(subordinates列表为空)，除基层员工外，每个员工都有一个或多个直接下级。

这个公司现在要办party，你可以决定哪些员工来，哪些员工不来，规则：
1.如果某个员工来了，那么这个员工的所有直接下级都不能来
2.派对的整体快乐值是所有到场员工快乐值的累加
3.你的目标是让派对的整体快乐值尽量大



### 二叉树上两个节点的最低公共祖先

给定一棵二叉树的头节点head，和另外两个节点a和b。
返回a和b的最低公共祖先



> 最后两题就不写了，不然文章就太长了。github上有完整代码。