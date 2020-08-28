package examination;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/*
拼多多 2020/8/2
 */
public class PinDuoDuo {
    public static void problem1() {
        /**
         * problem1
         * 1. 飞行棋，距离终点距离 K， 共投掷 N 次骰子，判断前 N 次是否到达终点
         *      如果恰好到达，输出 paradox
         *      如果第N次之前无法到达，输出“距离终点的距离 回退次数”。
         * 2. 注意“前N次”！ 第 N 次到达不算，所以应该输出：0 回退次数
         */
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (k == nums[i]) {
                if (i==n-1) {
                    System.out.println(0+" "+count);
                }else {
                    System.out.println("paradox");
                }
                return;
            }
            if (k < nums[i]) {
                k = nums[i] - k;
                count++;
            } else {
                k = k - nums[i];
            }
        }
        System.out.println(k+" "+count);
    }

    public static void problem2() {
        /**
         * problem2
         * 1. 判断骰子种类，根据骰子的 上下、左右、前后 六个面显示的数字判断两个骰子是否可以通过旋转变成同一个骰子，并输出骰子个数
         * 2. 整体思路是想办法将相同的骰子的 上下、左右、前后 六个面显示的数字按照以下规则进行排序之后，转换为唯一的字符串类型 key。
         *      1） 将上下、左右、前后显示的数字打包成三个数据对。
         *      2） 按照数据对中更小的那个元素将三个数据对从小到大排序
         *      3） 排序时需要注意，交换两个数据对的位置相当于进行一次旋转，而旋转大体上可以分为三类：
         *          a） 保持上下不变进行旋转，此时相当于交换左右、前后两个数据对，同时将左右、前后两个数据对其中一个数据对中的两个元素的位置互换！
         *          b） 保持左右不变进行旋转，此时相当于交换上下、前后两个数据对，同时将上下、前后两个数据对其中一个数据对中的两个元素的位置互换！
         *          c） 保持前后不变进行旋转，此时相当于交换上下、左右两个数据对，同时将上下、左右两个数据对其中一个数据对中的两个元素的位置互换！
         *      4） 所以在排序交换数据对位置时，应该同时交换其中一个数据对的两个元素的位置。
         *      5） 同时，还需要保证前两个数据对中两个元素的相对位置应该是更小那个元素在前面。
         *      6） 一旦保证三个数据对从小到达排列、且前两个数据对的元素是从小到大的，那么第三个数据对的元素位置一定是确定的，那么这个骰子可以转换为 key了！
         * 3. 利用哈希表，求出一共有多少种骰子，以及每种骰子的个数
         * 4. 骰子旋转规律上下(AB)、左右(CD)、前后(EF)
         *      1） 保持上下不变，右旋（左旋规律类似）
         *        上下  AB  AB  AB  AB  AB
         *        左右  CD  FE  DC  EF  CD
         *        前后  EF  CD  FE  DC  EF
         *      2） 保持左右不变，前旋
         *        上下  AB  FE  BA  EF  AB
         *        左右  CD  CD  CD  CD  CD
         *        前后  EF  AB  FE  BA  EF
         *      3) 保持前后不变，左旋
         *        上下  AB  DC  BA  CD  AB
         *        左右  CD  AB  DC  BA  CD
         *        前后  EF  EF  EF  EF  EF
         */
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int[][] tmp = new int[3][2];
            for (int j = 0; j < 3; j++) {
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                tmp[j][0] = a;
                tmp[j][1] = b;
            }
            String tmpStr = getString(tmp);
            map.put(tmpStr, map.getOrDefault(tmpStr, 0) + 1);
        }
        int size = map.size();
        int[] res = new int[size];
        int p = 0;
        for (String str : map.keySet()) {
            res[p] = map.get(str);
            p++;
        }
        Arrays.sort(res);
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                System.out.print(res[i]);
            } else {
                System.out.print(" " + res[i]);
            }
        }
    }
    public static String getString(int[][] a) {
        /**
         * 1. 相同的骰子，一定可以通过保持上下不变右旋、保持左右不变前旋、保持前后不变左旋三种旋变为完全相同的骰子！
         * 2. 通过三种旋转，对上下、左右、前后三个数据对进行排序，使得其按照数据对中更小的那一个元素从小到大排列
         * 3. 同时还需要保证前两个数据对中的元素是从小到大的！（多旋转几次一定可以达到这样的要求）
         * 4. 排序之后，将其按照元素出现的次序输出为字符串类型的 key！如果相同的骰子，一定可以得到相同的 key！
         */
        // 借鉴选择排序的思路
        for (int i = 0; i < 2; i++) {
            // 找到最小的数据对（前面说过了如何比较数据对大小）
            int minIndex = i;
            int min = Math.min(a[i][0], a[i][1]);
            for (int j = 1; j < 3; j++) {
                if (Math.min(a[j][0], a[j][1]) < min) {
                    min = Math.min(a[j][0], a[j][1]);
                    minIndex = j;
                }
            }
            if (minIndex == i ) {
                // 如果该数据对已经在正确的位置，且数据对内元素是从小到大的，无需排序
                if (a[i][0] < a[i][1])
                    continue;
                else  // 如果数据对内元素相对位置不对，需要通过与下一个数据对交换位置来调整数据对内元素相对位置
                    minIndex = minIndex+1;
            }

            // 通过将当前数据对与最小数据对交换位置，可以保证将数据对的位置以及数据对内的数据的相对位置调整到正确的位置！
            while (true) {
                if (Math.min(a[i][0], a[i][1]) == min && a[i][0] < a[i][1])
                    break;
                // i 一定比 minIndex 小！
                int tmp0 = a[i][0];
                int tmp1 = a[i][1];
                a[i][0] = a[minIndex][1];
                a[i][1] = a[minIndex][0];
                a[minIndex][0] = tmp0;
                a[minIndex][1] = tmp1;
            }
        }
        //按照元素出现的顺序输出为字符串类型的 key！
        String res = "";
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                res += a[i][j];
            }
        }
        return res;
    }

    public static void problem3() {
        /**
         * 1. 给定 N 套中餐+M套晚餐，每种套餐都有对应的 热量值和美味值
         * 2. 每顿饭只能选择一份套餐，但是可以选择吃两顿顿饭，或者一顿饭，问为了摄入美味值大于等于 t，最少需要摄入多少热量？
         * 3. 思路
         *       1）. 以热量为容量，以美味程度为value的背包问题
         *       2）. 恰好填满背包，所以初始化小心点
         *       3）. 分组背包问题，分为两组、每组相互冲突，只能选一件
         */
        // 输入
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int t = scanner.nextInt();
        // 特殊情况
        if (t == 0) {
            System.out.println(0);
            return;
        }
        int[][] zhong = new int[n][2];
        int[][] wan = new int[m][2];
        for (int i = 0; i < n; i++) {
            // 热量值
            zhong[i][0] = scanner.nextInt();
            // 美味值
            zhong[i][1] = scanner.nextInt();
        }
        for (int i = 0; i < m; i++) {
            wan[i][0] = scanner.nextInt();
            wan[i][1] = scanner.nextInt();
        }
        // 1. 以热量为容量，以美味程度为value的背包问题
        // 2. 恰好填满背包，所以初始化小心点
        // 3. 分组背包问题，分为两组、每组相互冲突，只能选一件
        int maxHeat = 1000; //？？？？
        int[] dp = new int[maxHeat];
        dp[0] = 0;
        // 为了恰好填满背包！
        for (int i = 1; i < maxHeat; i++) {
            dp[i] = Integer.MIN_VALUE;
        }
        for (int i = 0; i < n; i++) {
            dp[zhong[i][0]] = Math.max(dp[zhong[i][0]],zhong[i][1]);
        }
        // 因为热量是从大到小的，所以对于每个热量，只考虑了当前数组种的一种套餐！
        // 保证了在晚餐组只选择一份套餐！
        for (int i = maxHeat-1; i >= 0; i--) {
            for (int j = 0; j < m; j++) {
                if (i>=wan[j][0])
                    dp[i] = Math.max(dp[i],dp[i-wan[j][0]]+wan[j][1]);
            }
        }
        // 热量值从小到大遍历，寻找满足美味值的最小热量！
        for (int i = 0; i < maxHeat; i++) {
            if (dp[i] >= t) {
                System.out.println(i);
                return;
            }
        }
        System.out.println(-1);
    }

    static int count = 0;
    public static void problem4() {
        /**
         * 1. 给定一个 6*6 的花园，分为三十六块区域，每块区域用 '#' 表示没有建筑物，用 '*' 表示有建筑物。
         * 2. 现有 6 种农作物，想要种在该花园内，但是要求每块区域在种植时不能与其上下左右四块区域种植的农作物相同，问有多少种组合？
         * 3. 有两种思路
         *      1） dfs
         *      2） 动态规划
         */
        // system in
        Scanner scanner = new Scanner(System.in);
        int[][] mat = new int[6][6];
        for (int i = 0; i < 6; i++) {
            String tmp = scanner.nextLine();
            for (int j = 0; j < 6; j++) {
                if (tmp.charAt(j) == '#')
                    mat[i][j] = 0;
                else
                    mat[i][j] = 7;
            }
        }
//        dfs(mat,0,0);
//        System.out.println(count);
        dynamicProgramming(mat);
    }
    public static void dynamicProgramming(int[][] mat) {
        /**
         * 1. 总共有以下四条限制，条件4）是一个隐性的条件限制。
         *      1） 有建筑物的地方不能种植
         *      2） 左右相邻的区域不能种植同一种作物
         *      3） 上下相邻的区域不能种植同一种作物
         *      4） 没有建筑的地方必须种植
         *    //2. 每一行的状态一共有6*5*5*5*5*5 种！已经把限制 2） 考虑进来了
         * 2. 每一行的状态都有 7*7*7*7*7*7 种，之所以需要把不种植考虑进来，是因为有些地形无法种植农作物！
         *      这也导致无法利用限制条件 2） 对状态数量进行缩减？，想要进行缩减，必须结合地形，但是如果相邻两行的状态都不一样的话，就很麻烦
         * 3. dp[i][j] 表示 第 i 行在第 j 种状态下，最多有多少种种植方式。
         * 6. 最后将 dp 最后一行的所有状态的数值加起来就是最终的结果。
         */
        //int count = 6*5*5*5*5*5;  由于限制条件1的存在，无法通过限制条件2削减状态数量！
        int count = 7*7*7*7*7*7;
        int[][] status = new int[count][6];
        int p = 0;
        for (int i = 0; i <= 6; i++) {
            for (int j = 0; j <= 6; j++) {
                for (int k = 0; k <= 6; k++) {
                    for (int l = 0; l <= 6; l++) {
                        for (int m = 0; m <= 6; m++) {
                            for (int n = 0; n <= 6; n++) {
                                status[p][0] = i;
                                status[p][1] = j;
                                status[p][2] = k;
                                status[p][3] = l;
                                status[p][4] = m;
                                status[p][5] = n;
                            }
                        }
                    }
                }
            }
        }

        int[][] dp = new int[6][count];
        for (int i = 0; i < count; i++) {
            if (constraint1And2And4(mat[0],status[i]))
                dp[0][i] = 1;
        }

        for (int i = 1; i < 6; i++) {
            for (int j = 0; j < count; j++) {
                if (constraint1And2And4(mat[i],status[j])) {
                    for (int k = 0; k < count; k++) {
                        if (constraint1And2And4(mat[i],status[k]) && constraint3(status[k],status[j])) {
                            dp[i][j] += dp[i-1][k];
                        }
                    }
                }
            }
        }
        int res = 0;
        for (int i = 0; i < count; i++) {
            res += dp[5][i];
        }
        System.out.println(res);
    }
    public static boolean constraint3(int[] curr, int[] last) {
        // 限制条件 3
        for (int i = 0; i < 6; i++) {
            if (curr[i]!=0 && curr[i] == last[i])
                return false;
        }
        return true;
    }
    public static boolean constraint1And2And4(int[] buildings, int[] state) {
        for (int i = 0; i < 6; i++) {
            // 限制条件1 & 限制条件4
            if ((buildings[i]==7&&state[i]!=0)||(buildings[i]==0&&state[i]==0))
                return false;
            // 限制条件 2
            if (i > 0 && state[i]!=0 && state[i]==state[i-1])
                return false;
        }
        return true;
    }

    public static void dfs(int[][] mat,int i, int j) {
        if (mat[i][j] == 7) {
            int x,y;
            if (j < mat[0].length-1) {
                x = i;
                y = j+1;
                dfs(mat,x,y);
            } else if (i < mat.length-1) {
                x = i+1;
                y = 0;
                dfs(mat,x,y);
            } else {
                count++;
            }
        } else {
            for (int k = 1; k <= 6; k++) {
                if ((j-1>=0 && mat[i][j-1]==k)||(i-1>=0 && mat[i-1][j]==k))
                    continue;
                int x,y;
                if (j < mat[0].length-1) {
                    x = i;
                    y = j+1;
                    dfs(mat,x,y);
                } else if (i < mat.length-1) {
                    x = i+1;
                    y = 0;
                    dfs(mat,x,y);
                } else {
                    count++;
                }
            }
        }
    }

    public static void main(String[] args) {
        /**
         * 输入：
         *      10 2
         *      6 3
         * 输出：
         *      1 0
         */
        //problem1();
        /**
         * 输入：
         *      2
         *      1 2 4 3 6 5
         *      1 2 5 6 3 4
         * 输出
         *      2
         *      1 1
         * 输入：
         *      2
         *      1 2 4 3 6 5
         *      2 1 5 6 3 4
         * 输出：
         *      1
         *      2
         */
        //problem2();
        /**
         * 输入：
         *      2 2 7
         *      3 5
         *      6 7
         *      2 2
         *      3 4
         * 输出：
         *      5
         */
        //problem3();
        /*
输入
#*****
******
******
******
******
*****#
输出
36
         */
        problem4();
    }
}
