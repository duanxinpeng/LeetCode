public class SaleOfShares {
    /////////////////////////////////////////////////////////////
    public int maxProfit1(int[] prices) {
        return method_2_121(prices);
    }
    public int method_1_121(int[] prices) {
        //记录前面的最小值！
        int res = 0;
        int len = prices.length;
        if (len < 2) {
            return 0;
        }
        int min = prices[0];
        for (int i = 1; i < len; i++) {
            if (prices[i]-min > res) {
                res = prices[i]-min;
            }
            if (prices[i] < min) {
                min = prices[i];
            }
        }
        return res;
    }
    public int method_2_121(int[] prices) {
        int n = prices.length;
        //dp[-1][k][0] = 0; dp[-1][k][1] = -infinity
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            //dp[i][1][0] = max(dp[i-1][1][0],dp[i-1][1][1]+prices[i])
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            //dp[i][1][1] = max(dp[i-1][1][1], dp[i-1][0][0]-prices[i])
            dp_i_1 = Math.max(dp_i_1, -prices[i]);
        }
        return dp_i_0;
    }
    ///////////////////////////////////////////////////////////////////////
    public int maxProfit2(int[] prices) {
        //return method_1_122(prices);
        //return method_2_122(prices);
        return mtehod_3_122(prices);
    }
    public int method_1_122(int[] prices) {
        //波峰波谷法，纯粹的贪婪算法，低买、高卖！
        int i= 0;
        int buy = 0;
        int res = 0;
        while (i < prices.length-1) {
            while (i < prices.length-1 && prices[i] >= prices[i+11])
                i++;
            buy = prices[i];
            while (i < prices.length-1 && prices[i] <= prices[i+1])
                i++;
            res += prices[i] - buy;
        }
        return res;
    }
    public int method_2_122(int[] prices) {
        //波峰波谷法的改进
        int res = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i] < prices[i+1]) {
                res += prices[i+1]-prices[i];
            }
        }
        return res;
    }
    public int mtehod_3_122(int[] prices) {
        //动态规划框架
        int n = prices.length;
        //dp[-1][k][0] = 0; dp[-1][k][1] = - infinity
        //k代表允许的最大交易次数，k的主要作用是限制交易，一旦达到交易次数，就不能继续交易了
        //k 是正无穷的意思就是不限制你，随便交易
        //k = infinity 故 k == k-1! 也就是不需要考虑 k 的base case
        int d_i_0 = 0, d_i_1 = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int tmp = d_i_0;
            d_i_0 = Math.max(d_i_0, d_i_1+prices[i]);
            d_i_1 = Math.max(d_i_1, tmp - prices[i]);
        }
        return d_i_0;
    }
    /////////////////////////////////////////////
    public int maxProfitWithFrozen(int[] prices) {
        int n = prices.length;
        if (n == 0)
            return 0;
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
        int dp_pre_0 = 0;
        for (int i = 0; i < n; i++) {
            int tmp = dp_i_0;
            dp_i_0 = Math.max(dp_i_0,dp_i_1+prices[i]);
            dp_i_1 = Math.max(dp_i_1, dp_pre_0 - prices[i]);
            dp_pre_0 = tmp;
        }
        return dp_i_0;
    }
    //////////////////////////////////////////////
    public int maxProfitWithFee(int[] prices, int fee) {
        int n = prices.length;
        if (n == 0)
            return 0;
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int tmp = dp_i_0;
            dp_i_0 = Math.max(dp_i_0,dp_i_1+prices[i]);
            dp_i_1 = Math.max(dp_i_1, tmp - prices[i] - fee);
        }
        return dp_i_0;
    }
    /////////////////////////////////////////////
    public int maxProfit3 (int[] prices) {
        int n = prices.length;
        if (n==0)
            return 0;
        int[][][] dp = new int[n][2+1][2];
        for (int i = 0; i < n; i++) {
            dp[i][0][0] = 0;
            dp[i][0][1] = Integer.MIN_VALUE;
            for (int j = 1; j <= 2; j++) {
                if (i == 0) {
                    dp[i][j][0] = 0;
                    dp[i][j][1] = -prices[i];
                } else {
                    dp[i][j][0] = Math.max(dp[i-1][j][0], dp[i-1][j][1]+prices[i]);
                    dp[i][j][1] = Math.max(dp[i-1][j][1],dp[i-1][j-1][0]-prices[i]);
                }
            }
        }
        return dp[n-1][2][0];
    }
    ///////////////////////////////////////////////////////////
    public int maxProfit4 (int k, int[] prices) {
        int n = prices.length;
        //此时就是无限次交易的问题了！
        if (k > n/2)
            return mtehod_3_122(prices);
        int[][][] dp = new int[n][k+1][2];
        for (int i = 0; i < n; i++) {
            dp[i][0][0] = 0;
            dp[i][0][1] = Integer.MIN_VALUE;
            for (int j = 1; j <= k; j++) {
                if (i == 0) {
                    dp[i][j][0] = 0;
                    dp[i][j][1] = -prices[i];
                } else {
                    dp[i][j][0] = Math.max(dp[i-1][j][0], dp[i-1][j][1]+prices[i]);
                    dp[i][j][1] = Math.max(dp[i-1][j][1],dp[i-1][j-1][0]-prices[i]);
                }
            }
        }
        return dp[n-1][k][0];
    }

 }
