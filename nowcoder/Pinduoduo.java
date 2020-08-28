package nowcoder;

import java.math.BigInteger;
import java.util.*;

public class Pinduoduo {
    public static void neituiproblem12018() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        if (n <= 3) {
            long res = 1;
            for (int i = 0; i < n; i++) {
                res *= nums[i];
            }
            System.out.println(res);
            return;
        }
        // 寻找最大的三个数，依次放在数组最右边
        for (int i = 0; i < 3; i++) {
            int max = i;
            for (int j = 0; j < n - i; j++) {
                if (nums[j] > nums[max])
                    max = j;
            }
            int tmp = nums[max];
            nums[max] = nums[n - i - 1];
            nums[n - i - 1] = tmp;
        }
        // 寻找最小的两个数，依次放于数组最左边
        for (int i = 0; i < 2; i++) {
            int min = i;
            for (int j = i; j < n; j++) {
                if (nums[j] < nums[min])
                    min = j;
            }
            int tmp = nums[min];
            nums[min] = nums[i];
            nums[i] = tmp;
        }
        System.out.println(Math.max((long) nums[n - 1] * nums[n - 2] * nums[n - 3], (long) nums[0] * nums[1] * nums[n - 1]));
    }

    public static void neitui_problem2_2018() {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] nums = str.trim().split(" ");
        int[] res = new int[nums[0].length() + nums[1].length()];
        for (int i = nums[0].length() - 1; i >= 0; i--) {
            int n1 = nums[0].charAt(i) - '0';
            for (int j = nums[1].length() - 1; j >= 0; j--) {
                int n2 = nums[1].charAt(j) - '0';
                int sum = res[i + j + 1] + n1 * n2;
                res[i + j + 1] = sum % 10;
                // 是否进位
                res[i + j] += sum / 10;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < res.length; i++) {
            if (i == 0 && res[i] == 0)
                continue; // 去掉最前面的0， 最多只有一个0！
            stringBuilder.append(res[i]);
        }
        System.out.println(stringBuilder.toString());
    }

    static int res = 0;

    public static int getNum(int[] h, int[] w, int[] nums, boolean flag) {
        int num = 0;
        if (flag) {
            for (int i = 0; i < w.length; i++) {
                if (h[nums[i]] <= w[i])
                    num++;
            }
        } else {
            for (int i = 0; i < h.length; i++) {
                if (h[i] <= w[nums[i]])
                    num++;
            }
        }
        return num;
    }

    public static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void dfs_3(int[] h, int[] w, int begin, int end, int[] nums, boolean flag) {
        if (begin == end) {
            int tmp = getNum(h, w, nums, flag);
            if (tmp > res)
                res = tmp;
        } else {
            for (int i = begin; i < nums.length; i++) {
                swap(nums, begin, i);
                dfs_3(h, w, begin + 1, end, nums, flag);
                swap(nums, begin, i);
            }
        }
    }

    public static void neitui_problem3_2018() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] h = new int[n];
        for (int i = 0; i < n; i++) {
            h[i] = scanner.nextInt();
        }
        int m = scanner.nextInt();
        int[] w = new int[m];
        for (int i = 0; i < m; i++) {
            w[i] = scanner.nextInt();
        }
        if (n > m) {
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = i;
            }
            dfs_3(h, w, 0, m, nums, true);
        } else {
            int[] nums = new int[m];
            for (int i = 0; i < m; i++) {
                nums[i] = i;
            }
            dfs_3(h, w, 0, n, nums, false);
        }
        System.out.println(res);
    }

    static class Node {
        int x, y;
        int key;
        int step;

        public Node(int x, int y, int key, int step) {
            this.x = x;
            this.y = y;
            this.key = key;
            this.step = step;
        }
    }

    public static int bfs_4(char[][] matrix, int i, int j) {
        /**
         * bfs 一定要在入队列之前标记已访问，不能在入队列之后再标记的！
         */
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] next = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Queue<Node> queue = new LinkedList<>();
        // 需要把钥匙的状态算进来，因为拿到钥匙之后就可以往回走了
        boolean[][][] visited = new boolean[m][n][2024];
        // 必须在入队时标为已访问，而不能在出队列时标！
        visited[i][j][0] = true;
        queue.offer(new Node(i, j, 0, 0));
        while (!queue.isEmpty()) {
            Node tmp = queue.poll();
            int x = tmp.x;
            int y = tmp.y;
            int key = tmp.key;
            // 到达终点
            if (matrix[x][y] == '3')
                return tmp.step;
            // 遇到墙，或者遇到门 但没有钥匙
            if (matrix[x][y] == '0'
                    || (matrix[x][y] >= 'A' && matrix[x][y] <= 'Z' && (key & (1 << (matrix[x][y] - 'A'))) == 0))
                continue;
            // 遇到钥匙则更新钥匙状态
            if (matrix[x][y] >= 'a' && matrix[x][y] <= 'z') {
                key |= (1 << (matrix[x][y] - 'a'));
            }
            for (int k = 0; k < 4; k++) {
                int nx = x + next[k][0];
                int ny = y + next[k][1];
                // 有效且未被访问过的节点入队列
                if (nx >= 0 && ny >= 0 && nx < m && ny < n && !visited[nx][ny][key]) {
                    visited[nx][ny][key] = true;
                    queue.offer(new Node(nx, ny, key, tmp.step + 1));
                }
            }
        }
        return 0;
    }

    public static void neitui_problem4_2018() {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        char[][] matrix = new char[m][n];
        scanner.nextLine();
        for (int i = 0; i < m; i++) {
            String tmp = scanner.nextLine();
            for (int j = 0; j < n; j++) {
                matrix[i][j] = tmp.charAt(j);
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '2') {
                    System.out.println(bfs_4(matrix, i, j));
                    return;
                }
            }
        }
    }

    public static void problem1_2018() {
        Scanner scanner = new Scanner(System.in);
        int offset = scanner.nextInt();
        int n = scanner.nextInt();
        int l1 = scanner.nextInt();
        int l2 = scanner.nextInt();
        int start1 = Math.min(offset, l1);
        int end1 = Math.min(offset + n, l1);
        int start2 = Math.min(l2, Math.max(0, offset - l1));
        int end2 = Math.min(l2, Math.max(0, offset + n - l1));
        System.out.println(start1 + " " + end1 + " " + start2 + " " + end2);
    }

    static class TreeNode {
        int seq;
        int parent = -1;
        int dist = 0;
        List<TreeNode> child = new ArrayList<>();

        public TreeNode(int seq) {
            this.seq = seq;
        }
    }

    public static TreeSet<Integer> dfs_2_2018(TreeNode root, int m) {
        TreeSet<Integer> res = new TreeSet<>();
        res.add(0);
        if (root == null) {
            return res;
        }
        ArrayList<Set<Integer>> arr = new ArrayList<>();
        for (TreeNode child : root.child) {
            arr.add(dfs_2_2018(child, m));
        }
        for (int i = 0; i < root.child.size(); i++) {
            int d = root.child.get(i).dist;
            for (Integer path : arr.get(i)) {
                if (d + path <= m) {
                    res.add(d + path);
                }
            }
        }
        for (int i = 0; i < root.child.size(); i++) {
            for (int j = i + 1; j < root.child.size(); j++) {
                int d = root.child.get(i).dist + root.child.get(j).dist;
                for (Integer path1 : arr.get(i)) {
                    for (Integer path2 : arr.get(j)) {
                        if (d + path1 + path2 <= m) {
                            res.add(d + path1 + path2);
                        }
                    }
                }
            }
        }
        return res;
    }

    public static void problem2_2018() {
        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        TreeNode[] t = new TreeNode[n];
        for (int i = 0; i < t.length; i++) {
            t[i] = new TreeNode(i);
        }
        for (int i = 0; i < n - 1; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int d = scanner.nextInt();
            t[v].parent = u;
            t[v].dist = d;
            t[u].child.add(t[v]);
        }
        int root = -1;
        for (int i = 0; i < n; i++) {
            if (t[i].parent == -1) {
                root = i;
                break;
            }
        }
        System.out.println(dfs_2_2018(t[root], m).last());
    }

    public static void problem3_2018() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] locations = new int[n][2];
        for (int i = 0; i < n; i++) {
            locations[i][0] = scanner.nextInt();
            locations[i][1] = scanner.nextInt();
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if ((!((locations[j][1] - locations[i][1]) * (locations[k][0] - locations[i][0])
                            == (locations[k][1] - locations[i][1]) * (locations[j][0] - locations[i][0]))))
                        res++;
                }
            }
        }
        System.out.println(res);
    }

    public static void problem5_2018() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[] cnadies = new int[m];
        for (int i = 0; i < m; i++) {
            cnadies[i] = scanner.nextInt();
        }
        int[][] bears = new int[n][3];
        for (int i = 0; i < n; i++) {
            bears[i][0] = i;
            bears[i][1] = scanner.nextInt();
            bears[i][2] = scanner.nextInt();
        }
        Arrays.sort(bears, (x, y) -> (y[1] - x[1]));
        Arrays.sort(cnadies);
        boolean[] visited = new boolean[m];
        for (int i = 0; i < n; i++) {
            for (int j = m - 1; j >= 0; j--) {
                if (!visited[j] && cnadies[j] <= bears[i][2]) {
                    bears[i][2] -= cnadies[j];
                    visited[j] = true;
                }
            }
        }
        Arrays.sort(bears, (x, y) -> (x[0] - y[0]));
        for (int i = 0; i < n; i++) {
            System.out.println(bears[i][2]);
        }
    }


    public static void problem1_2019() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        scanner.nextLine();
        String str = scanner.nextLine();
        char[] phone = str.toCharArray();
        int[] nums = new int[10];
        for (int i = 0; i < n; i++) {
            nums[phone[i] - '0']++;
        }
        int cost = Integer.MAX_VALUE;
        String res = "";
        for (int i = 0; i < 10; i++) {
            int tmpCost = 0;
            char[] tmpRes = phone.clone();
            int gap = k - nums[i];
            if (gap <= 0) {
                System.out.println(0);
                System.out.println(str);
                return;
            }
            int p = 1;
            while (gap > 0) {
                int change = i + p;
                // 大改小
                if (change < 10) {
                    int min = Math.min(gap, nums[change]);
                    gap -= min;
                    tmpCost += p * min;
                    // 从前往后
                    for (int l = 0; l < n; l++) {
                        if (tmpRes[l] - '0' == change) {
                            tmpRes[l] = (char) (i + '0');
                            min--;
                            if (min == 0)
                                break;
                        }
                    }

                }

                if (gap == 0)
                    break;

                change = i - p;
                // 小改大
                if (change >= 0) {
                    int min = Math.min(gap, nums[change]);
                    gap -= min;
                    tmpCost += p * min;
                    // 从后往前
                    for (int l = n - 1; l >= 0; l--) {
                        if (tmpRes[l] - '0' == change) {
                            tmpRes[l] = (char) (i + '0');
                            min--;
                            if (min == 0)
                                break;
                        }
                    }
                }
                p++;
            }
            if (tmpCost < cost) {
                cost = tmpCost;
                res = String.valueOf(tmpRes);
            }
        }
        System.out.println(cost);
        System.out.println(res);
    }


    public static boolean dfs_2_2019(int m, int[] nums, int max, int maxIndex, int begin, ArrayList<Integer> res) {
        int n = nums.length;
        if (begin == m) {
            return true;
        }
        // 剪枝
        if (max > (m + 1 - res.size()) / 2)
            return false;

        // 先种植序号小
        for (int i = 0; i < n; i++) {
            // 这种树没有了，或者和前面冲突了
            if (nums[i] <= 0 || (!res.isEmpty() && i == res.get(res.size() - 1)))
                continue;
            res.add(i);
            nums[i]--;
            // 重新定位 max
            if (i == maxIndex) {
                maxIndex = 0;
                max = nums[0];
                for (int j = 0; j < n; j++) {
                    if (nums[j] > max) {
                        max = nums[j];
                        maxIndex = j;
                    }
                }
            }
            // 如果是true就不会将res中的值去掉了呀
            if (dfs_2_2019(m, nums, max, maxIndex, begin + 1, res))
                return true;
            nums[i]++;
            res.remove(res.size() - 1);
        }
        return false;
    }

    public static void problem2_2019() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        int m = 0;
        for (int i = 0; i < n; i++) {
            m += nums[i];
        }
        // 寻找数量最多的种类
        int maxIndex = 0;
        int max = nums[0];
        for (int i = 0; i < n; i++) {
            if (nums[i] > max) {
                max = nums[i];
                maxIndex = i;
            }
        }

        ArrayList<Integer> res = new ArrayList<>();
        if (dfs_2_2019(m, nums, max, maxIndex, 0, res)) {
            // 可以找到排列，打印出字典序最小的那个排列。
            for (int i = 0; i < res.size(); i++) {
                if (i == 0)
                    System.out.print(res.get(i) + 1);
                else
                    System.out.print(" " + (res.get(i) + 1));
            }
        } else {
            System.out.println("-");
        }
    }

    public static void problem3_2019() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        Arrays.sort(nums);
        int min = nums[0] + nums[n - 1];
        int max = min;
        for (int i = 0; i < n / 2; i++) {
            if (nums[i] + nums[n - 1 - i] < min)
                min = nums[i] + nums[n - 1 - i];
            if (nums[i] + nums[n - 1 - i] > max)
                max = nums[i] + nums[n - 1 - i];
        }
        System.out.println(max - min);
    }

    public static void problem4_2019() {
        Scanner scanner = new Scanner(System.in);
        int hp = scanner.nextInt();
        int na = scanner.nextInt();
        int ba = scanner.nextInt();
        if (na * 2 > ba) {
            if (hp % na == 0) {
                System.out.println(hp / na);
            } else {
                System.out.println(hp / na + 1);
            }
        } else {
            int rounds = 2 * (hp / ba);
            int res = hp % ba;
            if (res == 0) {
                System.out.println(rounds);
            } else if (res <= na) {
                System.out.println(rounds + 1);
            } else {
                System.out.println(rounds + 2);
            }
        }
    }

    public static int dfs_1_2020(int n) {
        if (n == 1)
            return 1;
        return 1 + dfs_1_2020(n / 2);
    }

    public static void problem1_2020() {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for (int i = 0; i < t; i++) {
            int n = scanner.nextInt();
            System.out.println(dfs_1_2020(n));
        }
    }

    public static int getMin_2_2020(int n) {
        if (n % 4 == 0 || n % 4 == 3)
            return 0;
        return 1;
    }

    public static void problem2_2020() {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for (int i = 0; i < t; i++) {
            int n = scanner.nextInt();
            System.out.print(getMin_2_2020(n) + " ");
            int tmp = getMin_2_2020(n - 1);
            System.out.println(n - tmp);
        }
    }


    public static BigInteger getCount_3_2020_1(int n, int m) {
        if (n > m) {
            int tmp = n;
            n = m;
            m = tmp;
        }
        // 把选择0个考虑了进来
        BigInteger res = new BigInteger("1");
        for (int i = 1; i <= n + m; i++) {
            int max = Math.min(i, n);
            int min = Math.max(0, i - m);
            BigInteger tmp = new BigInteger("1");
            for (int j = 1; j < min; j++) {
                tmp = tmp.multiply(new BigInteger("" + (i - j + 1))).divide(new BigInteger("" + (1 + j - 1)));
            }
            for (int j = min; j <= max; j++) {
                if (j == 0) {
                    res = res.add(tmp);
                } else {
                    tmp = tmp.multiply(new BigInteger("" + (i - j + 1))).divide(new BigInteger("" + (1 + j - 1)));
                    res = res.add(tmp);
                }
            }
        }
        return res;
    }

    static class Pair {
        int a;
        int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return a == pair.a &&
                    b == pair.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

    static HashMap<Pair, Long> map = new HashMap<>();

    public static long getCount_3_2020(int n, int m, long k) {
        if (map.containsKey(new Pair(n, m)))
            return map.get(new Pair(n, m));
        if (n == 0)
            map.put(new Pair(n, m), (long) m);
        else if (m == 0)
            map.put(new Pair(n, m), (long) n);
        else {
            long tmp1 = getCount_3_2020(n - 1, m, k);
            long tmp2 = getCount_3_2020(n, m - 1, k);
            Pair tmp = new Pair(n, m);
            // 可能会超过 long 的范围也太骚了！
            if (tmp1 > Long.MAX_VALUE / 3 || tmp2 > Long.MAX_VALUE / 3)
                map.put(tmp, Long.MAX_VALUE / 2);
            else
                map.put(tmp, tmp1 + tmp2 + 2);
        }
        return map.get(new Pair(n, m));
    }

    public static void problem3_2020() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        long k = scanner.nextLong();
        StringBuilder builder = new StringBuilder();
        while (k > 0) {
            if (n > 0 && m > 0) {
                // 以a 为 prefix 总共有多少个字符串
                long count = getCount_3_2020(n - 1, m, k) + 1;
                if (count >= k) {
                    builder.append('a');
                    n--;
                    k--;
                } else {
                    builder.append('b');
                    // b 不可能不够！
                    m--;
                    k -= (count + 1); // ??把'b'剪掉了
                }
            } else if (n == 0 && m > 0) {
                builder.append('b');
                m--;
                k--;
            } else if (n > 0 && m == 0) {
                builder.append('a');
                n--;
                k--;
            }
        }
        System.out.println(builder.toString());
    }

    public static void problem4_2020() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        int max = 0;
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
            if (nums[i] > max)
                max = nums[i];
        }
        double res = 0;
        /**
         * 1. 求期望：一共有[1,max]种可能，求出每种可能的概率，就很容易求出期望了
         * 2. 求最大值是a的概率 = 所有骰子都小于等于 a 的概率 - 所有骰子都小于等于 a-1 的概率；（为什么？所有值都小于等于 a 的所有情况中，减去所有值都小于 a 的情况，就是最大值是 a 的所有可能）
         */
        double pre = 0;
        for (int i = 1; i <= max; i++) {
            double cur = 1;
            for (int j = 0; j < n; j++) {
                // double!!!!
                cur *= ((double) Math.min(i, nums[j]) / nums[j]);
            }
            res += ((cur - pre) * i);
            pre = cur;
        }
        System.out.println(String.format("%.2f", res));
    }

    public static void problem5_2020() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();
        int left = 1;
        int right = m * n;
        k = m * n - k + 1;
        while (left < right) {
            int mid = (int)(((long)left + right) / 2);
            // 求小于等于 mid 的数量
            int row = mid / m;
            int count = row * m;
            for (int i = row + 1; i <= n; i++) {
                count += mid / i;
            }
            if (count < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        System.out.println(left);
    }

    public static void main(String[] args) {
        problem5_2020();
    }
}
