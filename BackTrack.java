import java.util.*;

public class BackTrack {
    char[][] numChars = {
            {'a','b','c'},
            {'d','e','f'},
            {'g','h','i'},
            {'j','k','l'},
            {'m','n','o'},
            {'p','q','r','s'},
            {'t','u','v'},
            {'w','x','y','z'}};
    // LeeCode -17 电话号码组合
    public void dfs_17(int[] digits, int index, List<String> res, StringBuilder builder) {
        if (index == digits.length) {
            res.add(builder.toString());
            return;
        }
        for (int i = 0; i < numChars[digits[index]].length; i++) {
            builder.append(numChars[digits[index]][i]);
            dfs_17(digits,index+1,res,builder);
            builder.deleteCharAt(builder.length()-1);
        }
    }
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        char[] chars = digits.toCharArray();
        int[] nums = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            nums[i] = chars[i]-'0'-2;
        }
        dfs_17(nums,0,res,builder);
        return res;
    }
    // LeeCode-338 比特位计数
    // 如果 i 是奇数，1的个数一定比前一个偶数多 1， 因为 最低为是 1 是由前面那个 偶数最低位 加 1 得来得！
    // 如果 i 是偶数，1的个数一定等于 i/2； 因为最低为是 0， 除以二 相当于右移一位，把最低位的 0 抹掉！
    public int[] countBits(int num) {
        int[] res = new int[num+1];
        res[0] = 0;
        for (int i = 1; i <= num; i++) {
            if ((i&1) == 0) {
                res[i] = res[i/2];
            } else {
                res[i] = res[i-1]+1;
            }
        }
        return res;
    }
    //LeeCode-312 戳气球
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] coins = new int[n+2];
        coins[0] = coins[n+1] = 1;
        for (int i = 1; i < n+1; i++) {
            coins[i] = nums[i];
        }
        int[][] dp = new int[n+2][n+2];
        for (int i = 2; i <= n+1; i++) {
            for (int j = 0; j < n+2-i; j++) {
                for (int k = j+1; k < j+i; k++) {
                    dp[j][j+i] = Math.max(dp[j][j+i],coins[k]*coins[j]*coins[j+i]);
                }
            }
        }
        return dp[0][n+1];
    }

    //LeeCode-301 删除无效的括号
    public boolean isValid(String s) {
        int n = s.length();
        int left = 0, right = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(')
                left++;
            if (s.charAt(i) == ')')
                right++;
            if (right > left)
                return false;
        }
        return left == right;
    }
    public List<String> removeInvalidParentheses(String s) {
        List<String> list = new ArrayList<>();
        Set<String> level = new HashSet<>();
        level.add(s);
        while (true) {
            Set<String> nextLevel = new TreeSet<>();
            for (String str : level) {
                if (str.length() == 0)
                    return list;
                if (isValid(str)) {
                    for (String str1 : level) {
                        if (isValid(str1))
                            list.add(str1);
                    }
                    return list;
                }
                for (int i = 0; i < str.length(); i++) {
                    if (str.charAt(i) == '(' || str.charAt(i) == ')') {
                        nextLevel.add(str.substring(0, i) + str.substring(i + 1, str.length()));
                    }
                }
            }
            level = nextLevel;
            return list;
        }
    }

    public static void main(String[] args) {
        BackTrack bc = new BackTrack();
        bc.letterCombinations("23");
    }
}
