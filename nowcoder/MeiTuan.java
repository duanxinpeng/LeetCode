package nowcoder;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

public class MeiTuan {
    //problem1
    public static void problem1() {
        /**
         * 1. 注意 || 的判定原则
         * 2. 注意Deque 的 push和offer的区别！push/pop
         */
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        str.trim();
        String[] strs = str.split(" ");
        if ((!strs[strs.length - 1].equals("true")) && (!strs[strs.length - 1].equals("false"))) {
            System.out.println("error");
            return;
        }
        Deque<Boolean> booleanDeque = new ArrayDeque<>();
        Deque<String> conjs = new ArrayDeque<>();
        for (int k = 0; k < strs.length; k++) {
            if (k % 2 == 0 && strs[k].equals("true")) {
                if (!booleanDeque.isEmpty() && conjs.peek().equals("and")) {
                    conjs.poll();
                    //booleanDeque.offer(booleanDeque.poll()&&true);
                    booleanDeque.push(booleanDeque.poll() && true);
                } else {
                    //booleanDeque.offer(true);
                    booleanDeque.push(true);
                }
            } else if (k % 2 == 0 && strs[k].equals("false")) {
                if (!booleanDeque.isEmpty() && conjs.peek().equals("and")) {
                    conjs.poll();
                    //booleanDeque.offer(booleanDeque.poll()&&false);
                    booleanDeque.push(booleanDeque.poll() && false);
                } else {
                    //booleanDeque.offer(false);
                    booleanDeque.push(false);
                }
            } else if (k % 2 == 1 && strs[k].equals("and")) {
                //conjs.offer("and");
                conjs.push("and");
            } else if (k % 2 == 1 && strs[k].equals("or")) {
                //conjs.offer("or");
                conjs.push("or");
            } else {
                System.out.println("error");
                return;
            }
        }
        while (!conjs.isEmpty()) {
            conjs.poll();
            // 这样写不行的！，||后面的操作有可能不执行的！！！
            //booleanDeque.offer(booleanDeque.poll()||booleanDeque.poll());
            boolean tmp = booleanDeque.poll();
            boolean tmp2 = booleanDeque.poll();
            //booleanDeque.offer(tmp||tmp2);
            booleanDeque.push(tmp || tmp2);
        }
        System.out.println(booleanDeque.poll());
    }

    //problem2
    public static void problem2() {
        Scanner scanner = new Scanner(System.in);
        String p = scanner.nextLine();
        String t = scanner.nextLine();
        boolean[][] dp = new boolean[p.length() + 1][t.length() + 1];
        dp[0][0] = true;
        for (int i = 1; i < t.length() + 1; i++) {
            dp[0][i] = false;
        }
        for (int i = 1; i < p.length() + 1; i++) {
            dp[i][0] = p.charAt(i - 1) == '*' && dp[i - 1][0];
        }
        for (int i = 1; i < p.length() + 1; i++) {
            for (int j = 1; j < t.length() + 1; j++) {
                if (p.charAt(i - 1) != '*') {
                    dp[i][j] = (p.charAt(i - 1) == '?' || p.charAt(i - 1) == t.charAt(j - 1)) && dp[i - 1][j - 1];
                } else {
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                }
            }
        }
        if (dp[p.length()][t.length()])
            System.out.println(1);
        else
            System.out.println(0);
    }

    public static void problem2_1() {
        Scanner scanner = new Scanner(System.in);
        String p = scanner.nextLine();
        String t = scanner.nextLine();
        p = p.replace('?', '.').replace("*", ".*");
        if (Pattern.matches(p, t)) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }
    }

    // problem3
    static double max = -100000;
    static int[] permutation;

    public static double getWeight(double[][] weights, int[] nums) {
        double res = 0;
        for (int i = 0; i < nums.length; i++) {
            res += weights[i][nums[i] - 1];
        }
        return res;
    }

    public static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void dfs_3(double[][] weights, int begin, int[] nums) {
        permutation = new int[nums.length];
        if (begin == nums.length - 1) {
            double tmp = getWeight(weights, nums);
            if (tmp > max) {
                max = tmp;
                permutation = nums.clone();
            }
        }
        if (begin < nums.length - 1) {
            for (int i = begin; i < nums.length; i++) {
                swap(nums, begin, i);
                dfs_3(weights, begin + 1, nums);
                swap(nums, begin, i);
            }
        }
    }

    public static void problem3() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        double[][] weights = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                weights[i][j] = scanner.nextDouble();
            }
        }
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = i + 1;
        }
        dfs_3(weights, 0, nums);
        System.out.println(String.format("%.2f", max));
        for (int i = 0; i < n; i++) {
            System.out.print((i + 1) + " " + permutation[i]);
            System.out.println();
        }
    }

    // 用KM算法解决 问题三
    public static double min(double a, double b) {
        if (a < b)
            return a;
        else
            return b;
    }

    public static boolean dfs_km(int girl, double[] slack, int[] match, double[] expire_girl, double[] expire_boy, boolean[] vis_girl, boolean[] vis_boy, double[][] map) {
        vis_girl[girl] = true;
        for (int i = 0; i < map[0].length; i++) {
            if (vis_boy[i])
                continue;
            double gap = expire_girl[girl] + expire_boy[i] - map[girl][i];
            if (gap == 0) { // 符合要求
                vis_boy[i] = true;
                if (match[i] == -1 || dfs_km(match[i], slack, match, expire_girl, expire_boy, vis_girl, vis_boy, map)) {
                    match[i] = girl;
                    return true;
                }
            } else {
                slack[i] = Math.min(slack[i], gap); // slack 可以理解为男生要得到女生的倾心，还需要多少期望值。
            }
        }
        return false;
    }

    public static double km(double[][] map) {
        // 女生数量
        int m = map.length;
        // 男生数量
        int n = map[0].length;
        int[] match = new int[n];
        for (int i = 0; i < n; i++) {
            match[i] = -1;
        }
        // 男生期望值初始化为0
        double[] expire_boy = new double[n];
        // 女生期望值初始化为和她相连的男生的最大好感度
        double[] expire_girl = new double[m];
        for (int i = 0; i < m; i++) {
            expire_girl[i] = map[i][0];
            for (int j = 0; j < n; j++) {
                expire_girl[i] = Math.max(expire_girl[i], map[i][j]);
            }
        }
        double[] slack = new double[m];
        boolean[] visit_girl;
        boolean[] visit_boy;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                // 因为要取最小值，所以初始化为无穷大
                slack[j] = Integer.MAX_VALUE;
            }
            while (true) {
                // 为每个女生解决归宿问题，如果找不到就降低期望值，直到找到为止
                visit_girl = new boolean[m];
                visit_boy = new boolean[n];
                if (dfs_km(i, slack, match, expire_girl, expire_boy, visit_girl, visit_boy, map))
                    break; // 找到归宿 退出
                // 如果不能找到，就降低期望
                // 最小可降低的期望值
                double d = Integer.MAX_VALUE;
                for (int j = 0; j < n; j++) {
                    if (!visit_boy[j])
                        d = min(d, slack[j]);
                }
                for (int j = 0; j < m; j++) {
                    if (visit_girl[j])
                        expire_girl[j] -= d;
                }
                for (int j = 0; j < n; j++) {
                    if (visit_boy[j])
                        expire_boy[j] += d;
                    else
                        slack[j] -= d;
                }
            }
        }
        double res = 0;
        for (int i = 0; i < m; i++) {
            res += map[match[i]][i];
        }
        System.out.println(res);
        int[][] tmp = new int[n][2];
        for (int i = 0; i < n; i++) {
            tmp[i][0] = match[i] + 1;
            tmp[i][1] = i + 1;
        }
        Arrays.sort(tmp, (x, y) -> {
            return x[1] - y[1];
        });
        for (int i = 0; i < n; i++) {
            System.out.println(tmp[i][0] + " " + tmp[i][1]);
        }
        return res;
    }

    public static void problem3_1() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        double[][] weights = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                weights[i][j] = scanner.nextDouble();
            }
        }
        km(weights);
    }

    //problem4
    public static void problem4() {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int[][] regions = new int[26][2];
        int len = str.length();
        for (int i = 0; i < len; i++) {
            regions[str.charAt(i) - 'A'][1] = i + 1;
        }
        for (int i = len - 1; i >= 0; i--) {
            regions[str.charAt(i) - 'A'][0] = i;
        }
        Arrays.sort(regions, (x, y) -> {
            return x[0] - y[0];
        });
        int j = 0;
        while (regions[j][0] == 0 && regions[j][1] == 0)
            j++;
        int[][] res = new int[26][2];
        res[0][0] = regions[j][0];
        res[0][1] = regions[j][1];
        j++;
        int p = 0;
        for (; j < 26; j++) {
            if (regions[j][0] < res[p][1]) {
                res[p][1] = Math.max(res[p][1], regions[j][1]);
            } else {
                res[++p][0] = regions[j][0];
                res[p][1] = regions[j][1];
            }
        }
        for (int i = 0; i <= p; i++) {
            if (i == 0) {
                System.out.print(res[i][1] - res[i][0]);
            } else {
                System.out.print(" " + (res[i][1] - res[i][0]));
            }
        }
    }
    // problem 5

    public static void problem5() {

        int[][] adjacent = new int[26][26];
        int[] exist = new int[26];

        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] strs = str.trim().split(" ");
        for (int k = 1; k < strs.length; k++) {
            String pre = strs[k - 1];
            String next = strs[k];
            int n = Math.min(pre.length(), next.length());
            int i = 0;
            for (i = 0; i < n; i++) {
                exist[pre.charAt(i) - 'a'] = 1;
                exist[next.charAt(i) - 'a'] = 1;
                if (pre.charAt(i) != next.charAt(i)) {
                    adjacent[pre.charAt(i) - 'a'][next.charAt(i) - 'a'] = 1;
                    break;
                }
            }
            for (; i < n; i++) {
                exist[pre.charAt(i) - 'a'] = 1;
                exist[next.charAt(i) - 'a'] = 1;
            }
            if (pre.length() < next.length()) {
                for (int j = pre.length(); j < next.length(); j++) {
                    exist[next.charAt(j) - 'a'] = 1;
                }
            } else {
                for (int j = next.length(); j < pre.length(); j++) {
                    exist[pre.charAt(j) - 'a'] = 1;
                }
            }
        }

        int[] indegree = new int[26];
        int[] outdegree = new int[26];
        for (int i = 0; i < 26; i++) {
            int out = 0;
            int in = 0;
            for (int j = 0; j < 26; j++) {
                if (adjacent[i][j] == 1)
                    out++;
                if (adjacent[j][i] == 1)
                    in++;
            }
            outdegree[i] = out;
            indegree[i] = in;
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < 26; i++) {
            if (exist[i] == 1 && indegree[i] == 0)
                queue.add(i);
        }

        String res = "";
        int count = 0;
        for (int i = 0; i < 26; i++) {
            if (exist[i] == 1)
                count++;
        }
        while (!queue.isEmpty()) {
            if (queue.size() > 1) {
                System.out.println("invalid");
                return;
            }
            int node = queue.poll();
            count--;
            res += (char) (node + 'a');
            for (int i = 0; i < 26; i++) {
                if (adjacent[node][i] == 1) {
                    indegree[i]--;
                    if (indegree[i] == 0)
                        queue.add(i);
                }
            }
        }
        if (count == 0)
            System.out.println(res);
        else
            System.out.println("invalid");
    }

    ///MT1 最大差值 贪心
    public int getDis(int[] A, int n) {
        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (A[j] - A[i] > max)
                    max = A[j] - A[i];
            }
        }
        return max;
    }

    //MT2棋子反转 数组 模拟
    public void flip(int[][] A, int x, int y) {
        x = x - 1;
        y = y - 1;
        if (x >= 0 && x < A.length && y >= 0 && y < A[0].length) {
            A[x][y] = 1 - A[x][y];
        }
    }

    public int[][] flipChess(int[][] A, int[][] f) {
        // write code here
        for (int i = 0; i < f.length; i++) {
            flip(A, f[i][0] - 1, f[i][1]);
            flip(A, f[i][0] + 1, f[i][1]);
            flip(A, f[i][0], f[i][1] - 1);
            flip(A, f[i][0], f[i][1] + 1);
        }
        return A;
    }

    //美团点评2020校招系统开发方向笔试题
    public static void problem1_2020() {
        Scanner scanner = new Scanner(System.in);
        String a = scanner.nextLine().replaceAll("\"", "");
        String b = scanner.nextLine().replaceAll("\"", "");
        BigInteger ba = new BigInteger(a);
        BigInteger bb = new BigInteger(b);
        System.out.println("\"" + ba.add(bb).toString() + "\"");
//        int aLen = a.length();
//        int bLen = b.length();
//        int len = Math.max(aLen, bLen);
//        int[] nums = new int[len + 1];
//        for (int i = 0; i < len; i++) {
//            if (i < a.length()) {
//                nums[len - i - 1] += (a.charAt(aLen - i - 1) - '0');
//            }
//            if (i < b.length()) {
//                nums[len - i - 1] += b.charAt(bLen - i - 1) - '0';
//            }
//        }
//        for (int i = len - 1; i > 0; i--) {
//            nums[i] = nums[i] % 10;
//            nums[i - 1] += nums[i] / 10;
//        }
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < len; i++) {
//            if (i == 0 && nums[i] == 0)
//                continue;
//            builder.append(nums[i]);
//        }
//        System.out.println(builder.toString());
    }

    public static void problem1_2020_2() {
        Scanner scanner = new Scanner(System.in);
        String a = scanner.nextLine().replaceAll("\"", "");
        String b = scanner.nextLine().replaceAll("\"", "");
        StringBuilder builder = new StringBuilder();
        boolean flag = true;
        if (a.charAt(0) == '-' && b.charAt(0) == '-') {
            a = a.substring(1, a.length());
            b = b.substring(1, b.length());
            builder.append('-');
        } else if (a.charAt(0) == '-') {
            String tmp = a.substring(1, a.length());
            a = b;
            b = tmp;
            flag = false;
        } else if (b.charAt(0) == '-') {
            b = b.substring(1, b.length());
            flag = false;
        }
        int aLen = a.length();
        int bLen = b.length();
        if (flag) {
            int len = Math.max(aLen, bLen);
            int[] nums = new int[len + 1];
            for (int i = 0; i < len; i++) {
                if (i < a.length()) {
                    nums[len - i - 1] += (a.charAt(aLen - i - 1) - '0');
                }
                if (i < b.length()) {
                    nums[len - i - 1] += b.charAt(bLen - i - 1) - '0';
                }
            }
            for (int i = len - 1; i > 0; i--) {
                nums[i] = nums[i] % 10;
                nums[i - 1] += nums[i] / 10;
            }
            for (int i = 0; i < len; i++) {
                if (i == 0 && nums[i] == 0)
                    continue;
                builder.append(nums[i]);
            }
        } else {

        }
        System.out.println(builder.toString());
    }

    public static void problem2_2020() {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        int len = string.length();
        boolean[][] dp = new boolean[len][len];
        int res = 0;
        // 循环顺序不能错，i 代表长度 j 代表开始位置！
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - i; j++) {
                if (i == 0) {
                    dp[j][j] = true;
                    res++;
                } else if (i == 1) {
                    dp[j][i + j] = (string.charAt(j) == string.charAt(j + 1));
                    if (dp[j][i + j])
                        res++;
                } else {
                    dp[j][i + j] = (string.charAt(j) == string.charAt(i + j) && dp[j + 1][i + j - 1]);
                    if (dp[j][i + j])
                        res++;
                }
            }
        }
        System.out.println(res);
    }


    public static void problem3_2020() {
        /**
         * 思路错了！！！
         * 1. 贪心不能解决问题，因为最小的可能会影响左右两个，但是次小的可能只会影响一个（比如在最左边或者最右边）
         * 2. 必须用动态规划
         *
         */
        class Pair {
            int i, j, c;

            public Pair(int i, int j, int c) {
                this.i = i;
                this.j = j;
                this.c = c;
            }
        }
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] c = new int[n];
        int res = 0;
        ArrayList<Pair> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            c[i] = scanner.nextInt();
        }
        for (int i = 0; i < n - 1; i++) {
            list.add(new Pair(c[i], c[i + 1], c[i] + c[i + 1]));
        }
        for (int i = 0; i < n - 1; i++) {
            int minIndex = 0;
            Pair min = list.get(minIndex);
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).c < min.c) {
                    minIndex = j;
                    min = list.get(j);
                }
            }
            res += min.c;
            // 更新
            if (minIndex > 0) {
                Pair tmp = list.get(minIndex - 1);
                tmp.c = tmp.i + min.c;
                tmp.j = min.c;
            }
            if (minIndex < list.size() - 1) {
                Pair tmp = list.get(minIndex + 1);
                tmp.c = tmp.j + min.c;
                tmp.i = min.c;
            }
            list.remove(minIndex);
        }
        System.out.println(res);
    }

    public static void problem3_1_2020() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] c = new int[n];
        for (int i = 0; i < n; i++) {
            c[i] = scanner.nextInt();
        }
        int[][] sum = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (i == j)
                    sum[i][i] = c[i];
                else {
                    sum[i][j] = sum[i][j - 1] + c[j];
                }
            }
        }
        int[][] dp = new int[n][n];
        for (int len = 1; len < n; len++) {
            for (int i = 0; i < n - len; i++) {
                int min = Integer.MAX_VALUE;
                for (int j = i; j < i + len; j++) {
                    int tmp = dp[i][j] + dp[j + 1][i + len] + sum[i][i + len];
                    if (tmp < min)
                        min = tmp;
                }
                dp[i][i + len] = min;
            }
        }

        System.out.println(dp[0][n - 1]);
    }

    public static class TrieNode {
        TrieNode[] next = new TrieNode[128];
        int[] count = new int[128];
    }
    public static void problem4_2020() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        TrieNode root = new TrieNode();
        scanner.nextLine();
        String[] strings = new String[n];
        for (int i = 0; i < n; i++) {
            strings[i] = scanner.nextLine();
            int len = strings[i].length();
            TrieNode p = root;
            for (int j = 0; j < len; j++) {
                char cur = strings[i].charAt(j);
                if (p.count[cur]==0) {
                    p.next[cur] = new TrieNode();
                }
                p.count[cur]++;
                p = p.next[cur];
            }
        }
        for (int i = 0; i < n; i++) {
            String tmp = strings[i];
            TrieNode p = root;
            // 从字典树中删掉该字符串
            for (int j = 0; j < tmp.length(); j++) {
                char cur = tmp.charAt(j);
                p.count[cur]--;
                if (p.count[cur]==0) {
                    p.next[cur] = null;
                    break;
                }
                p = p.next[cur];
            }
            String res = "";
            p = root;
            for (int j = 0; j < tmp.length(); j++) {
                char cur = tmp.charAt(j);
                res+=cur;
                if (p.count[cur]>0)
                    p = p.next[cur];
                else
                    break;
            }
            System.out.println(res);
            // 将该字符串重新添加到字典树中
            int len = tmp.length();
            p = root;
            for (int j = 0; j < len; j++) {
                char cur = tmp.charAt(j);
                if (p.count[cur]==0) {
                    p.next[cur] = new TrieNode();
                }
                p.count[cur]++;
                p = p.next[cur];
            }
        }
    }

    public static void problem1_math() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int x = scanner.nextInt();
        int p = scanner.nextInt();
        int q = scanner.nextInt();
        double best = (double)x*p/((n+1)*q-n*p);
        if (best < 1)
            best = 1;
        else if (best > m)
            best = m;
        System.out.println(String.format("%.2f",(double)best));
    }
    public static void problem1_simulation() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = scanner.nextInt()-1;
            int res = 1;
            int up = 1;
            while (n > 0) {
                if (n > up+1) {
                    res += up-1;
                    n -= (up+1);
                    up++;
                } else {
                    if (n <= up) {
                        res += n;
                        n = 0;
                    } else {
                        res += (up-1);
                        n = 0;
                    }
                }
            }
            System.out.println(res);
        }
    }
    public static void main(String[] args) {
        problem1_simulation();
    }
}
