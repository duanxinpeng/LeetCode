package examination;

import java.util.Scanner;

public class Ohters {
    // Corn fields POJ 3254
    public static void cornFields() {
        int mod = 100000000;

        Scanner scanner = new Scanner(System.in);
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        int[] map = new int[m];
        // 用 1 代表土地贫瘠（不可种植！），用 0 代表土地肥沃
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int tmp = scanner.nextInt();
                if (tmp == 0)
                    map[i] |= (1 << j);
            }
        }

        int cnt = 1;
        int[] s = new int[1<<n];
        s[0] = 0;
        // 筛选每一行状态，去掉相邻格子同时种植的状态！
        for (int i = 1; i < (1 << n); i++) {
            if ((i & (i << 1)) > 0 || (i & (i >> 1)) > 0)
                continue;
            s[cnt++] = i;
        }

        int[][] dp = new int[m][cnt];
        // 初始化第一行
        for (int i = 0; i < cnt; i++) {
            // 种植在了贫瘠的土地上
            if ((map[0] & s[i]) > 0)
                continue;
            dp[0][i] = 1;
        }
        //遍历第一行到最后一行的所有行，并考虑每一种状态！
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < cnt; j++) {
                //种植到了贫瘠的土地上！
                if ((map[i] & s[j]) > 0)
                    continue;
                for (int k = 0; k < cnt; k++) {
                    // 上一行的状态种植到了贫瘠的土地上，或者和当前行冲突
                    if ((map[i - 1] & s[k]) > 0 || (s[k] & s[j]) > 0)
                        continue;
                    dp[i][j] = ((dp[i][j] % mod) + dp[i - 1][k] % mod) % mod;
                }
            }
        }

        int res = 0;
        for (int i = 0; i < cnt; i++) {
            res = (res % mod + dp[m - 1][i] % mod) % mod;
        }
        System.out.println(res);
    }

    public static void main(String[] args) {
        cornFields();
    }
}
