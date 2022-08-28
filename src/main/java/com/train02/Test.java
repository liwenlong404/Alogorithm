package com.train02;

/**
 * @Description: 象棋问题
 * <p>
 * 请同学们自行搜索或者想象一个象棋的棋盘，
 * 然后把整个棋盘放入第一象限，棋盘的最左下角是(0,0)位置
 * 那么整个棋盘就是横坐标上9条线、纵坐标上10条线的区域
 * 给你三个 参数 x，y，k
 * 返回“马”从(0,0)位置出发，必须走k步
 * 最后落在(x,y)上的方法数有多少种?
 * @author: li
 * @create: 2022-08-26 18:39
 */
public class Test {

    /**
     * 暴力递归法
     */
    public static int ways1(int x, int y, int k) {
        return f(x, y, k);
    }

    /**
     * 从(0,0)出发，有K步要走，并且要走完，来到X,Y位置的方法数有多少
     *
     * @author: Li
     * @dateTime: 2022/8/26 18:41
     */
    public static int f(int x, int y, int k) {
        //base case 步数为0，是否到达x，y
        //因为可以模拟为：从(x,y)走到(0,0)。所以x=0,y=0,且k=0时，为1种可能
        if (k == 0) {
            return x == 0 && y == 0 ? 1 : 0;
        }
        //越界
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }

        //有步数要走，x，y也是棋盘上的位置
        //任意一个点，周围有8个点可以去。暴力递归所有可能
        return f(x + 2, y - 1, k - 1)
                + f(x + 2, y + 1, k - 1)
                + f(x + 1, y + 2, k - 1)
                + f(x - 1, y + 2, k - 1)
                + f(x - 2, y + 1, k - 1)
                + f(x - 2, y - 1, k - 1)
                + f(x - 1, y - 2, k - 1)
                + f(x + 1, y - 2, k - 1);
    }

    /**
     * 将上面的暴力递归法，改成动态规划
     *
     * @author: Li
     * @dateTime: 2022/8/26 19:00
     */
    public static int ways2(int x, int y, int k) {
        int[][][] dp = new int[10][9][k + 1];


        /*
        三维表，从k=0，底层开始填
        根据暴力递归的base case可得，x=0,y=0,k=0时，为1
        dp[..][..][0] = 0, 步数k为0，xy未到则为1
         */
        dp[0][0][0] = 1;

        for (int level = 1  ; level <= k; level++) {
            //level层，x，y

            //x的可能性
            for (int i = 0; i < 10; i++) {
                //y的可能性
                for (int j = 0; j < 9; j++) {
                    //因为k步的位置，是依赖于k-1步的
                    //所以level层,依赖于level-1层。getvalue先判断是否越界，然后依次填值
                    dp[i][j][level] = getValue(dp,i + 2, j - 1, level - 1) +
                                    getValue(dp,i + 2, j + 1, level - 1) +
                                    getValue(dp,i + 1, j + 2, level - 1) +
                                    getValue(dp,i - 1, j + 2, level - 1) +
                                    getValue(dp,i - 2, j + 1, level - 1) +
                                    getValue(dp,i - 2, j - 1, level - 1) +
                                    getValue(dp,i - 1, j - 2, level - 1) +
                                    getValue(dp,i + 1, j - 2, level - 1) ;
                }
            }
        }

        return dp[x][y][k];

    }

    /**
     * 判断是否越界，然后取值
     *
     * @author: Li
     * @dateTime: 2022/8/26 19:17
     */
    public static int getValue(int[][][] dp, int x, int y, int k) {
        //越界
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }

        return dp[x][y][k];
    }


    //验证 从(0,0)出发，到(2,3) 走3步，应有4种可能
    public static void main(String[] args) {
        int x = 2;
        int y = 3;
        int k = 3;
        System.out.println(ways2(x, y, k));
    }
}










