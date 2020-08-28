public class CombineSum4 {
    public int combinationSum4(int[] nums, int target) {
        int[][] dp = new int[nums.length][target+1];
        for (int i = 1; i <= target ; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i == nums[j]) {
                    dp[j][i] = 1;
                }
                if (i > nums[j]) {
                    for (int k = 0; k < nums.length; k++) {
                        dp[j][i] += dp[k][i-nums[j]];
                    }
                }
            }
        }
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            res += dp[i][target];
        }
        return res;
    }
}
