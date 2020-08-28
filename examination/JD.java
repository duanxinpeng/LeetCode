package examination;

import java.util.Scanner;

public class JD {

    public static void getN(int n) {
        int count = 0;
        int i = 1;
        for (; i <= n; i++) {
            if (count >= n)
                break;
            else {
                count = count + (int)Math.pow(3,i);
            }
        }
        i = i-1;
        int j = i;
        while (j > 1) {
            n = n - (int)Math.pow(3,j-1);
            j--;
        }
        String res = "";
        while (i > 0) {
            if (n <= (int)Math.pow(3,i-1)) {
                res += 2;
               // n -= Math.pow(3,i-1);
            }
            else if (n <= 2*(int)Math.pow(3,i-1)) {
                res += 3;
                n -= Math.pow(3,i-1);
            } else {
                res += 5;
                n -= 2*Math.pow(3,i-1);
            }
            i--;
        }
        System.out.println(res);
    }
    public static void problem1() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            getN(scanner.nextInt());
        }
    }

    public static void problem2() {
        int[][] next = {{-1,0},{-1,-1},{-1,1}};
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] nums = new int[n][2*n-1];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[0].length; j++) {
                nums[i][j] = Integer.MIN_VALUE;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2 * i + 1; j++) {
                int index = (2*n-1-2*i-1)/2+j;
                nums[i][index] = scanner.nextInt();
            }
        }
        int[][] dp = new int[nums.length][nums[0].length];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j] = Integer.MIN_VALUE;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[0].length; j++) {
                if (nums[i][j] != Integer.MIN_VALUE){
                    dp[i][j] = nums[i][j];
                    int tmp = dp[i][j];
                    for (int k = 0; k < 3; k++) {
                        int lastx = i + next[k][0];
                        int lasty = j +next[k][1];
                        if (lastx >= 0 && lastx < nums.length && lasty>=0 && lasty < nums[0].length && nums[lastx][lasty]!= Integer.MIN_VALUE) {
                            tmp = Math.max(tmp,dp[i][j]+dp[lastx][lasty]);
                        }
                    }
                    dp[i][j] = tmp;
                }
            }
        }
        int res = dp[n-1][0];
        for (int i = 0; i < nums[0].length; i++) {
            if (dp[n-1][i] > res)
                res = dp[n-1][i];
        }
        System.out.println(res);
    }
    public static void main(String[] args) {
        problem2();
    }
}
