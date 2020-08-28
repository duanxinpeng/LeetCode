public class StrMatching {
    //LeeCode-10 模式串匹配！
    public boolean isMatch(String s, String p) {
        return method_1_10(s, p);
    }

    public boolean method_1_10(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();
        // 模式串为0，只有当s为空时才能匹配！
        boolean[][] dp = new boolean[sLen + 1][pLen + 1];
        dp[0][0] = true;
        // s 应该从 null 开始
        for (int i = 0; i <= sLen; i++) {
            for (int j = 1; j <= pLen; j++) {
                if (p.charAt(j - 1) == '*') {
                    // 无论 i 和 j-1 是否匹配，都可以当作不匹配！
                    dp[i][j] = dp[i][j - 2];
                    if (matches(s, p, i, j - 1)) {
                        // 只有i 和 j-1 匹配的时候这里才有意义
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else {
                    // 若不为 * 则对应位置必须匹配！此时需要考虑到 s 为 null 的情况！
                    dp[i][j] = matches(s, p, i, j) && dp[i - 1][j - 1];
                }
            }
        }
        return dp[sLen][pLen];
    }

    public boolean method_2_10(String s, String p) {
        /**
         * 这道题本质上仍然是一个二维的动态规划问题，
         * 但是因为其变化比较复杂（不再是连续加减），
         * 而且带有回溯问题，所以最好是自底向上，而不是自顶向下；
         * 带有回溯问题的动态规划就只能用递归来解决了；
         * 如果不带回溯的话，自顶向下和自底向上是一样的；
         *
         * 之所以是一个带回溯的动态规划，是因为‘*’，分为去或者不去两种情况的；不同的选择有不同的结果；
         */
        int slen = s.length(), plen = p.length();
        if (plen == 0) {
            return slen == 0;
        }
        boolean[][] dp = new boolean[slen + 1][plen + 1];
        dp[slen][plen] = true;
        for (int i = 0; i < slen - 1; i++) {
            dp[i][plen] = false;
        }
        for (int i = slen; i >= 0; i--) {
            for (int j = plen - 1; j >= 0; j--) {
                boolean first = i < slen && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
                if (j < plen - 1 && p.charAt(j + 1) == '*') {
                    dp[i][j] = dp[i][j + 2] || (first && dp[i + 1][j]);
                } else {
                    dp[i][j] = first && dp[i + 1][j + 1];
                }
            }
        }
        return dp[0][0];
    }

    public boolean matches(String s, String p, int i, int j) {
        if (i == 0)
            return false;
        if (p.charAt(j - 1) == '.')
            return true;
        return s.charAt(i - 1) == p.charAt(j - 1);
    }
}
