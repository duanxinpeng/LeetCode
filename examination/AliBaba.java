package examination;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
  阿里巴巴秋招 2020/07/29
 */
public class AliBaba {
    public static void problem1() {
        /**
         * problem1：n头牛，m种颜色，问有多少种组合
         * 1. 二项式展开式
         * 2. 每头牛都有[不带、颜色1、颜色2、...颜色m]等 (m+1) 种情况，所以 n 头牛总共有 (m+1)^n 种情况！
         * 3. 快速幂！
         */
        // mode
        int mode = 1000000007;
        // system in
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        // 计算 (m+1)^n
        long res = 1;
        int base = m + 1;
        // 快速幂
        // 任何一个正整数 n 都可以写成几个2的几次幂相加的情况。可以由 n 的二进制来得到究竟是哪几个数相加
        // 然后将 n 写成若干个2的幂次相加，就可以实现快速幂
        while (n > 0) {
            if ((n & 1) == 1)
                res = res * base % mode;
            base = base * base % mode;
            n >>= 1;
        }
        System.out.println(res);
    }

    public static int[] getMin(int[][] dist, boolean[][] isVisited) {
        /**
         * 得到dist中距离 s 最近的点，也意味着这个点的最短距离就已经被算出来了！
         */
        int minDist = Integer.MAX_VALUE;
        int[] min = {0, 0};
        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist[0].length; j++) {
                if (!isVisited[i][j] && dist[i][j] < minDist) {
                    min[0] = i;
                    min[1] = j;
                    minDist = dist[i][j];
                }
            }
        }
        return min;
    }

    public static boolean isInRange(int x, int y, int n, int m) {
        /**
         * 判断 up、down、left、right 四个方向是否在矩阵范围内！
         */
        if (x>=0 && x <n && y >=0 && y<m)
            return true;
        return false;
    }

    public static int getDist(char[][] chars, int[] curr,int[] next) {
        /**
         * 1.从陆地到海洋，或者从海洋到陆地都消耗5体力
         * 2.从陆地到陆地消耗3体力
         * 3.从海洋到海洋消耗2体力
         */
        if (chars[curr[0]][curr[1]]!=chars[next[0]][next[1]])
            return 5;
        if (chars[curr[0]][curr[1]] == 'C')
            return 3;
        return 2;
    }
    public static void problem2() {
        /**
         * problem2：从源点到目标点消耗的最小体力
         * 输入
         *    4 4 2
         *    CCCS
         *    SSSS
         *    CSCS
         *    SSCC
         *    1 1 3 4
         *    3 1 1 3
         *
         * 输出
         *     13
         *     14
         */
        // system in
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int q = scanner.nextInt();
        char[][] chars = new char[n][m];
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String tmp = scanner.nextLine();
            for (int j = 0; j < m; j++) {
                chars[i][j] = tmp.charAt(j);
            }
        }

        // up, down, left, right
        int[][] adjacent = {{0,-1},{0,1},{-1,0},{1,0}};
        // q次 Dijkstra
        for (int i = 0; i < q; i++) {
            int sx = scanner.nextInt()-1;
            int sy = scanner.nextInt()-1;
            int dx = scanner.nextInt()-1;
            int dy = scanner.nextInt()-1;
            // 保存任何一个节点到source的距离
            int[][] dist = new int[n][m];
            // 记录节点是否已经得到其最短路径！
            boolean[][] isVisited = new boolean[n][m];
            // dist 初始化
            // dist[sx][sy] = 0; 放在循环后面！
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < m; k++) {
                    // MAX_VALUE不能随便用啊！！会溢出的！
                    //dist[j][k] = Integer.MAX_VALUE;
                    dist[j][k] = Integer.MAX_VALUE/2;
                }
            }
            dist[sx][sy] = 0;
            // 每次循环都能得到至少一个节点的最短路径，所以一共需要 n*m 次循环！
            for (int k = 0; k < n * m; k++) {
                // 得到距离 source 最短的节点，同时该点的最短路径也已经被算出来了！
                int[] curr = getMin(dist, isVisited);
                // 恰好是 目标节点！
                if (curr[0] == dx && curr[1] == dy) {
                    System.out.println(dist[dx][dy]);
                    break;
                }
                isVisited[curr[0]][curr[1]] = true;
                // 对相邻节点 next 进行“松弛”操作
                for (int j = 0; j < 4; j++) {
                    int[] next = {curr[0]+adjacent[j][0],curr[1]+adjacent[j][1]};
                    if (isInRange(next[0],next[1],n,m)  && !isVisited[next[0]][next[1]] && dist[next[0]][next[1]] > dist[curr[0]][curr[1]]+getDist(chars,curr,next)) {
                        dist[next[0]][next[1]] = dist[curr[0]][curr[1]]+getDist(chars,curr,next);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        problem2();
    }
}

