package examination;

import java.util.*;

public class MeiTuan {
    static int res = 0;
    static int m = 0;
    static int n = 0;
    static int times;

    public static void dfs(int begin, int len) {
        if (len == m) {
            res++;
            res = res % 998244353;
        } else {
            for (int i = 1; i <= times; i++) {
                if (begin * i > n)
                    break;
                dfs(begin * i, len + 1);
            }
        }
    }
    public static void problem5() {
        /**
         * 小团的装饰物
         * 时间限制： 3000MS
         * 内存限制： 655360KB
         * 题目描述：
         * 小团正在装饰自己的书桌，他的书桌上从左到右有m个空位需要放上装饰物。商店中每个整数价格的装饰物恰好有一种，且每种装饰物的数量无限多。
         *
         * 小团去商店的时候，想到了一个购买方案，他要让右边的装饰物价格是左边的倍数。用数学语言来说，假设小团的m个装饰物价格为a1,a2...am，那么对于任意的1≤i≤j≤m，aj是ai的倍数。
         *
         * 小团是一个节约的人，他希望最贵的装饰物不超过n元。现在，请你计算小团有多少种购买的方案？
         *
         *
         *
         * 输入描述
         * 输入包含两个数，n和m（1≤n,m≤1000）
         *
         * 输出描述
         * 输出一个数，结果对998244353取模，表示购买的方案数。
         *
         *
         * 样例输入
         * 4 2
         * 样例输出
         * 8
         *
         * 提示
         * 样例解释
         * [1,1][1,2][1,3][1,4][2,2][2,4][3,3][4,4]共8种
         */
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        m = scanner.nextInt();
        times = n;
        for (int i = 1; i <= n; i++) {
            dfs(i, 1);
        }
        System.out.println(res);
    }


    public static void problem4() {
        /**
         * 小团的车辆调度
         * 时间限制： 4000MS
         * 内存限制： 655360KB
         * 题目描述：
         * 小团是美团汽车租赁公司的调度师，某个时刻A和B两地都向该公司提交了租车的订单，分别需要a和b辆汽车。
         * 此时，公司的所有车辆都在外运营，通过北斗定位，可以得到所有车辆的位置，
         * 小团分别计算了每辆车前往A地和B地完成订单的利润。
         * 作为一名精明的调度师，当然是想让公司的利润最大化了。
         *
         * 请你帮他分别选择a辆车完成A地的任务，选择b辆车完成B地的任务。使得公司获利最大,
         * 每辆车最多只能完成一地的任务。
         *
         *
         *
         * 输入描述
         * 输入第一行包含三个整数n，a，b，分别表示公司的车辆数量和A，B两地订单所需数量,保证a+b<=n。(1<=n<=2000)
         *
         * 接下来有n行，每行两个正整数x，y，分别表示该车完成A地任务的利润和B地任务的利润。
         *
         * 输出描述
         * 输出仅包含一个正整数，表示公司最大获得的利润和。
         *
         *
         * 样例输入
         * 5 2 2
         * 4 2
         * 3 3
         * 5 4
         * 5 3
         * 1 5
         * 样例输出
         * 18
         */
        /**
         * 1. 背包问题
         * 2. 恰好装满的背包问题
         * 3. 背包容量是二维的背包问题
         */
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int[][] nums = new int[n][2];
        for (int i = 0; i < n; i++) {
            nums[i][0] = scanner.nextInt();
            nums[i][1] = scanner.nextInt();
        }
        int[][][] dp = new int[n + 1][a + 1][b + 1];
        for (int i = 0; i < a + 1; i++) {
            for (int j = 0; j < b + 1; j++) {
                dp[0][i][j] = Integer.MIN_VALUE;
            }
        }
        dp[0][0][0] = 0;
        for (int i = 1; i < n + 1; i++) {
            for (int j = 0; j < a + 1; j++) {
                for (int k = 0; k < b + 1; k++) {
                    dp[i][j][k] = dp[i - 1][j][k];
                    if (j > 0)
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - 1][k] + nums[i - 1][0]);
                    if (k > 0) {
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j][k - 1] + nums[i - 1][1]);
                    }
                }
            }
        }
        System.out.println(dp[n][a][b]);
    }

    public static void problem4_1() {
        // 1. 贪婪的思路肯定错的！
        /**
         * 贪婪是错误的特例
         *  99 100
         *  1 99
         *  2 99
         *  3 99
         *  a 选3个，b选1个
         */
        class Pair {
            int i, a, b;

            public Pair(int i, int a, int b) {
                this.i = i;
                this.a = a;
                this.b = b;
            }
        }

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        PriorityQueue<Pair> queue = new PriorityQueue<>((x, y) -> {
            return Math.max(y.a, y.b) - Math.max(x.a, x.b);
        });

        for (int i = 0; i < n; i++) {
            queue.offer(new Pair(0, scanner.nextInt(), scanner.nextInt()));
        }

        int res = 0;
        while (a > 0 && b > 0) {
            Pair p = queue.poll();
            if (p.a > p.b) {
                res += p.a;
                a--;
            } else {
                res += p.b;
                b--;
            }
        }
        if (a > 0) {
            PriorityQueue<Pair> queue1 = new PriorityQueue<>((x, y) -> {
                return y.a - x.a;
            });
            while (!queue.isEmpty()) {
                queue1.offer(queue.poll());
            }
            while (a > 0) {
                res += queue1.poll().a;
                a--;
            }
        }
        if (b > 0) {
            PriorityQueue<Pair> queue1 = new PriorityQueue<>((x, y) -> {
                return y.b - x.b;
            });
            while (!queue.isEmpty()) {
                queue1.offer(queue.poll());
            }
            while (b > 0) {
                res += queue1.poll().b;
                b--;
            }
        }
        System.out.println(res);
    }


    static class DisjointSet {
        private int[] s;

        public DisjointSet(int n) {
            s = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                s[i] = -1;
            }
        }

        public void union(int num1, int num2) {
            int f1 = find(num1);
            int f2 = find(num2);
            if (f1 != f2) {
                s[f1] = f2;
            }
        }

        public int find(int num) {
            if (s[num] == -1) {
                return num;
            }
            return s[num] = find(s[num]);
        }
    }

    public static void problem3() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        boolean[] exist = new boolean[n + 1];
        DisjointSet disjointSet = new DisjointSet(n);
        for (int i = 0; i < m; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            exist[a] = true;
            exist[b] = true;
            disjointSet.union(a, b);
        }
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        for (int i = 1; i < n + 1; i++) {
            if (exist[i]) {
                int f = disjointSet.find(i);
                if (f == -1) {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(i);
                    map.put(i, list);
                } else {
                    if (map.containsKey(f)) {
                        map.get(f).add(i);
                    } else {
                        ArrayList<Integer> list = new ArrayList<>();
                        list.add(i);
                        map.put(f, list);
                    }
                }
            }
        }
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        for (Integer f : map.keySet()) {
            Collections.sort(map.get(f));
            res.add(map.get(f));
        }
        Collections.sort(res, (x, y) -> {
            return x.get(0) - y.get(0);
        });
        System.out.println(res.size());
        for (int i = 0; i < res.size(); i++) {
            for (int j = 0; j < res.get(i).size(); j++) {
                if (j == 0) {
                    System.out.print(res.get(i).get(j));
                } else {
                    System.out.print(" " + res.get(i).get(j));
                }
            }
            System.out.println();
        }
    }

    public static void problem2() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int res = 0;
        while (n > 0) {
            String begin = scanner.nextLine();
            String[] ls = begin.trim().split(" ");
            if (ls[0].equals(ls[1])) {
                res++;
                continue;
            }
            begin = begin.trim().split(" ")[0];
            n--;
            while (true) {
                String tmp = scanner.nextLine().trim().split(" ")[1];
                if (tmp.equals(begin)) {
                    res++;
                    n--;
                    break;
                } else {
                    n--;
                    if (n == 0)
                        break;
                }
            }
        }
        System.out.println(res);
    }

    public static boolean isNixu(int n, int m) {
        String a = String.valueOf(n);
        String b = String.valueOf(m);
        int i = 0;
        int j = b.length() - 1;
        while (b.charAt(j) == '0') {
            j--;
        }
        while (i < a.length() && j >= 0) {
            if (a.charAt(i) != b.charAt(j))
                return false;
            i++;
            j--;
        }
        if (i == a.length() && j == -1)
            return true;
        else
            return false;
    }

    public static void problem1() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int res = 0;
        ArrayList<int[]> list = new ArrayList<>();
        for (int i = 1; i * 4 <= n; i++) {
            if (isNixu(i, i * 4)) {
                res++;
                int[] tmp = {i, i * 4};
                list.add(tmp);
            }
        }
        System.out.println(res);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i)[0] + " " + list.get(i)[1]);
        }
    }

    public static void main(String[] args) {
        problem5();
    }
}
