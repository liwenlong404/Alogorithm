## 引

假设一个字符串str，长度为N。还有个字符串match，长度为M。且M <= N。

确定str中是否有某个子串是等于match的，有就返回该子串的的头在str中的位置。

## 暴力匹配

关于上面的问题，我们可以很快的想到所以双重for循环，以str中的每个开头，都去尝试匹配match。

代码也很简单，所以就省略了。但暴力解，很明显时间复杂度为O(N * M)。

所以我们可以用KMP算法求解，且时间复杂度为O(N)。



## KMP算法流程

### next数组

使用KMP算法有一个重要的点便是，我们需要有一个辅助的next数组。

next数组存储的信息是，match的每个位置，前后缀数组的最大匹配长度。

什么意思？

例如match字符串"ABCDABCDAB"，将其化为数组。

| index        | 0    | 1    | 2    | 3    | 4    | 5    | 6    |
| ------------ | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| match[index] | A    | B    | C    | A    | B    | C    | D    |

index数组怎么求？

match数组从0到6位置。“ABCABCD”。如果要计算match[6]的值，我们就去找除开6，在0到5位置的“ABCABC”。

前缀与后缀相等的最大长度。

例如：0位置的 “A” 等于5位置的 “ C” 吗？不等

01位置的 “AB” 等于45位置的 “BC” 吗？不等

012位置的 “ABC” 等于345位置的 "ABC" 吗？等于。最大长度为3

则match[6] = 3。

所以与match对应的next数组如下

| index        | 0    | 1    | 2    | 3    | 4    | 5    | 6    |
| ------------ | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| match[index] | A    | B    | C    | A    | B    | C    | D    |
| next[index]  | -1   | 0    | 0    | 0    | 1    | 2    | 3    |



其实就是每个位置，之前的部分。其找到的前缀、后缀相等的最大长度。

但不包括其本身，如“AAAAAAB”，那么B对应的next数组的值，为5。因为前面虽然有6个A，但除去6个A整体不算，最大前后缀相等的最大长度为5。

### 求解

通过对match数组进行信息收集，构建出next数组后。我们就可以使用next数组，对问题进行求解。

假设str长度为100，match长度为16。

首先，我们肯定是以str的第一个字符为开头，与match进行一一比对。

str的前9个字符与match的前9个字符都是相等的。但在第10个位置出错了，str[10] = C，match[10] = E。匹配不上。

按照之前暴力匹配的方法，我们肯定是，会以str[2]为开头，继续与match比对。

但在KMP算法中，我们不会跳到第2个位置继续比对。

因为在第10个位置比较失败，所以我们就会去查找next[10]等于多少，如果next[10] = 6。我们就会将str[10]与match[6]继续对比。

如果str[10]仍然与match[6]不匹配，我们就找到next[6]的值，例如next[6]=3，我们就将str[10]与match[3]进行对比。

如果对比成功，肯定就是str后移一位，match后移一位。继续对比，直到对比完成。或者中间出错，使用next数组跳转，进行对比。

当然，如果我们不断使用next数组的值，在match数组中跳转。

当next[]的值为0时，str后移一位，以其为开头，与mathc数组一一对比。

 

举个跳转的例子：

| index        | 0    | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    |      |      |      | ....... |
| ------------ | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ------- |
| str[index]   | A    | A    | B    | A    | A    | C    | T    | A    | A    | B    | A    | A    | B    | ....... |
| match[index] | A    | A    | B    | A    | A    | C    | T    | A    | A    | B    | A    | A    | K    | ....... |
| next[index]  | -1   | 0    | 1    | 0    | 1    | 2    | 0    | 0    | 1    | 2    | 3    | 4    | 5    | ....... |

我们可以看到，前面str和match都能相等，在9位置的时候不等了。且next[9] = 5，所以我们跳到match[5]，与str[9]继续比较。match[5] = C 并不等于 str[9] = B。

所以我们继续跳，next[5] = 2 ，我们将str[9] 继续与 match [2] 进行比较，然后可以看到都为B。

所以比对完成，继续比对，就是str[10]与match[3]、str[11]与match[4]，就是都后移继续比较。



## 流程解释

可能有些会有疑问，跳转是什么意思？为什么要跳转着进行比较？

#### 要点1

我们根据match数组，建立了next数组。next数组记录了，其最大前后缀相等的长度。

捋一捋

我们将str与match一一对比。而到某个位置，假定为16，不相等了，比对失败。

是不是意味着在str的0到15位置，和match的0到15位置是完全相等的。

假设当前位置，在next中的值为5。即当前位置，之前的最大的前后缀相等的长度为5。

即可得出，因为str的11到15，和match的11到15相等。且因为match的前后缀相等最大长度为5。

所以match的0到4位置，和match的11到15位置相等。所以match的0到4位置，和str的11到15位置相等。

str不动，之前str[16]比对失败，继续将str[16]与跳转后的match[5]进行比对。

**隐含的意义就是，我们是从str以11位置为开头，去与match进行匹配的**，只不过因为11到15部分，已经知道是与match的0到4相同，所以就不用进行比较了。



#### 要点2

上面从要点1可得知，比对失败后我们会进行跳转。**跳转的隐含的意义就是，我们是从str以11位置为开头，去与match进行匹配的**

这代表着，我们在以str[0]位置为开头，与match比较，在str[16]位置时，突然与match不同，比较失败了。

我们没有像暴力算法那样，继续以str[1]位置为开头，进行比较。而是直接以11位置为开头的子串，与match比较。

为什么我们忽略了，1到11中间那么多的数，以其开头的子串？

因为1到11中间，不可能出现，能以其为开头，匹配上match的结果。

为什么不可能出现？

因为我们在建立next数组时就知道了。

假设如果5位置，str[5]为开头，能完全匹配上match。

因为由上一次比对得知，16位置出错，但str[0.......15]，和match[0........15]是相等的

所以str[5.......15]，和match[5........15]也肯定是相等的。

如果str[5]为开头能完全匹配上match，意味着str[5......15]肯定与match[0........10]相等。

有此可得match[5........15]与match[0........10]相等。这是什么？

这意味着next[16]记录的前后缀数组的最大匹配长度应该为11，但我们之前生成的next数组，16位置的值明明是5。

与我们收集的信息相悖，所以不可能。



## 怎么求解Next数组

next数组如此重要，怎么求解next数组？且时间复杂度小，因为我们要让KMP算法的时间复杂度为O(N)。



如果我们要收集match[i]位置的next值，

我们就将 match[i-1]的next值拿到。例如next[i-1] = 7，那么我们就将match[7]与 match[i-1]比较。

如果不相等。

我们就将match[7]的next值拿到。   例如为next[7] = 3，我们就将match[3]与match[i-1]比较。

如果相等。

那么match[i]的next值，就为3+1=4。

其含义就是如果我们要求 i 位置的next值，就通过next[i-1]的值开始往前跳。

如果跳到某一步，当前跳到的值与match[i-1]相等，那么next[i]的值就为，跳到的next值+1。



## 代码

```
package com.train03;
/**
 * @description: kmp算法
 * @author li
 * @create 2022/8/28 18:40
 */
public class Code01_KMP {

	public static int getIndexOf(String s, String m) {
		if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
			return -1;
		}
		char[] str = s.toCharArray();
		char[] match = m.toCharArray();
		//str中，比对到的位置
		int x = 0;
		//match中，比对到的位置
		int y = 0;
		// O(M) m <= n
		int[] next = getNextArray(match);
		// O(N)
		while (x < str.length && y < match.length) {
			if (str[x] == match[y]) {
				x++;
				y++;
			}
			//y == 0 与 next[y] == -1 等效，意味着match数组的的y已经跳到开头。无法继续跳，需要x++移动，然后与0位置的y重新比对
			else if (y==0) {
				x++;
			}
			//没配上，且y也不在 0 位置。通过next数组找到，y位置之前的最大前后缀匹配长度，然后y跳到与长度相对于的位置
			else {
				y = next[y];
			}
		}

		// y 越界了，代表match已经走完，匹配上了。当前 x 位置减 y 即使，匹配子串的开头index
		// y 没越界，意味着因为着 x 没使 match中的 y 走完。即没匹配上。返回-1
		return y == match.length ? x - y : -1;
	}

	/**
	 *
	 * 通过match数组，获取next数组
	 * @author: Li
	 * @dateTime: 2022/8/28 18:44
	 */
	public static int[] getNextArray(char[] match) {
		if (match.length == 1) {
			return new int[] { -1 };
		}
		int[] next = new int[match.length];

		//0,1 位置固定不变
		next[0] = -1;
		next[1] = 0;

		// 目前在哪个位置上求next数组的值，从 2 位置开始求
		int i = 2;

		/*
		cn俩含义:
		1.i-1位置的最长前缀跟最长后缀的最大长度
		2.当前是哪一个字符要跟 i-1 位置作比较，如果比对成功, i 位置的值就是 cn + 1
		其实就是既是next的值，也是要跳转的地方的index
		 */
		int cn = 0;
		while (i < next.length) {
			// 配成功的时候
			if (match[i - 1] == match[cn]) {
				next[i++] = ++cn;
			} else if (cn > 0) {
				cn = next[cn];
			} else {
				next[i++] = 0;
			}
		}
		return next;
	}

	// for test
	public static String getRandomString(int possibilities, int size) {
		char[] ans = new char[(int) (Math.random() * size) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
		}
		return String.valueOf(ans);
	}

	public static void main(String[] args) {
		int possibilities = 5;
		int strSize = 20;
		int matchSize = 5;
		int testTimes = 5000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			String str = getRandomString(possibilities, strSize);
			String match = getRandomString(possibilities, matchSize);
			if (getIndexOf(str, match) != str.indexOf(match)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish");
	}

}

```

