package examination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;

public class Tencent {

    public static void problem1() {
        /**
         * 超时的原因竟然是因为java的I/O？
         */
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt() - 1;
        scanner.nextLine();
        String[] strings = scanner.nextLine().trim().split(" ");
        for (int i = 0; i < n; i++) {
            if (i != k) {
                System.out.print(strings[i] + " ");
            }
        }
    }

    public static void problem2() {
        /**
         * aabb
         * 3
         *
         * aab
         */
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int k = scanner.nextInt();
        TreeSet<String> set = new TreeSet<>();
        int n = str.length();
        for (int i = 1; i <= k; i++) {
            for (int j = 0; j < n - k + 1; j++) {
                set.add(str.substring(j, j + i));
            }
        }
        int j = 0;
        for (String s : set) {
            j++;
            if (j == k) {
                System.out.println(s);
                break;
            }
        }
    }

    public static long solve1(long n) {
        long x = n;
        long d = 0;
        long res = 0;
        while (x >= 10) {
            d *= 10;
            d += 9;
            x /= 10;
            res += 9;
        }
        n -= d;
        while (n > 0) {
            res += n % 10;
            n /= 10;
        }
        return res;
    }
    public static long solve(long n) {
        long res = 0;
        while (n > 0) {
            long cur = n % 10;
            n = n / 10;
            if (n > 0 && cur < 9) {
                res += 10 + cur;
                n = n - 1;
            } else {
                res += cur;
            }
        }
        return res;
    }
    public static void problem3() {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for (int i = 0; i < t; i++) {
            long n = scanner.nextLong();
            System.out.println(solve(n));
            System.out.println(solve1(n));
        }
    }

    public static int solve_4(int[] nums, int left, int right) {
        if (left > right)
            return 0;
        // 全部竖着刷
        int res = right-left+1;
        int min = nums[left];
        int minIndex = left;
        for (int i = left; i <= right; i++) {
            if (nums[i]<min) {
                min = nums[i];
                minIndex = i;
            }
        }
        for (int i = left; i <= right; i++) {
            nums[i] -= min;
        }
        return Math.min(res, min+solve_4(nums,left,minIndex-1)+solve_4(nums,minIndex+1,right));
    }
    public static void problem4() {
        /**
         * 作者：〢ヽ夜╰︶￣太美
         * 链接：https://www.nowcoder.com/discuss/486642?type=post&order=time&pos=&page=1&channel=666&source_id=search_post
         * 来源：牛客网
         *
         * 动态规划题，dp[i][j]dp[i][j]表示当前完全刷完了前ii块木板，横着刷的部分能延伸到之后木板的部分高度为jj的最小代价，显然jj不会超过nn，因为所有木板竖着刷答案就是nn了，不存在横着刷高度超过nn的情况。
         *
         * 我们枚举所有的状态，转移情况如下：
         *
         * 竖着刷
         *
         * 此时代价是1，能延伸的部分是jj和当前木板高度取最小值。
         *
         * 横着刷
         *
         * 如果当前木板高度小于jj，则没有代价，否则要补上他们之间的差值，能延伸的部分是当前木板的高度。
         *
         * #include<bits/stdc++.h>
         * using namespace std;
         *
         * const int N = 5e3 + 7;
         * int dp[N][N];
         * int a[N];
         * int main() {
         *     memset(dp, 0x3f, sizeof dp);
         *     dp[0][0] = 0;
         *     int n; scanf("%d", &n);
         *     for(int i = 1; i <= n; i++)scanf("%d", &a[i]);
         *     for(int i = 0; i < n; i++)for(int j = 0; j <= n; j++) {
         *         int high;
         *         //竖着刷
         *         high = min(j, a[i + 1]);
         *         dp[i + 1][high] = min(dp[i + 1][high], dp[i][j] + 1);
         *         //横着刷
         *         if(a[i + 1] < n) {
         *             if(j >= a[i + 1])
         *                  dp[i + 1][a[i + 1]] = min(dp[i + 1][a[i + 1]], dp[i][j]);
         *             else
         *                  dp[i + 1][a[i + 1]] = min(dp[i + 1][a[i + 1]], dp[i][j] + a[i + 1] - j);
         *         }
         *     }
         *     int ans = n;
         *     for(int i = 0; i <= n; i++)ans = min(dp[n][i], ans);
         *     printf("%d\n", ans);
         *     return 0;
         * }
         */
        /**
         * 5
         * 2 2 1 2 1
         *
         * 3
         *
         * 2
         * 2 2
         *
         * 2
         */
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        int max = n;
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
            if (nums[i] > max)
                max = nums[i];
        }
        System.out.println(solve_4(nums,0,n-1));
    }

    public static void problem5() {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int n = scanner.nextInt();
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - i; j++) {
                if (i == 0) {
                    dp[j][j + i] = true;
                } else if (i == 1) {
                    dp[j][j + i] = (s.charAt(j) == s.charAt(j + i));
                } else {
                    dp[j][j + i] = (s.charAt(j) == s.charAt(j + i) && dp[j + 1][j + i - 1]);
                }
            }
        }
        int[][] dp2 = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                if (i == j) {
                    dp2[i][j] = 1;
                } else {
                    if (dp[i][j])
                        dp2[i][j] = 1;
                    else {
                        int tmp = dp2[i][j - 1] + 1;
                        for (int k = i + 1; k < j; k++) {
                            if (dp[k][j]) {
                                if (dp2[i][k - 1] + 1 < tmp) {
                                    tmp = dp2[i][k - 1] + 1;
                                }
                            }
                        }
                        dp2[i][j] = tmp;
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            int l = scanner.nextInt() - 1;
            int r = scanner.nextInt() - 1;
            System.out.println(dp2[l][r]);
        }
    }

    public static void main(String[] args) {
        problem4();
    }
}
