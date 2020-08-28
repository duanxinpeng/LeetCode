public class UglyNumber {
    // 丑数
    public boolean isUgly(int num) {
        int[] divisor = {2, 3, 5};
        for (int i = 0; i < 3; i++) {
            while (num / divisor[i] != 0)
                num = num % divisor[i];
        }
        if (num == 1)
            return true;
        else
            return false;
    }

    //丑数Ⅱ
    static class Ugly {
        public int[] nums = new int[1690];

        Ugly() {
            nums[0] = 1;
            int ugly, p2 = 0, p3 = 0, p5 = 0;
            for (int i = 1; i < 1690; i++) {
                ugly = Math.min(Math.min(nums[p2] * 2, nums[p3] * 3), nums[p5] * 5);
                nums[i] = ugly;
                if (ugly == nums[p2] * 2) p2++;
                if (ugly == nums[p3] * 3) p3++;
                if (ugly == nums[p5] * 5) p5++;
            }
        }
    }

    static int[] nums = new int[1690];

    static {
        nums[0] = 1;
        int ugly, p2 = 0, p3 = 0, p5 = 0;
        for (int i = 1; i < 1690; i++) {
            ugly = Math.min(Math.min(nums[p2] * 2, nums[p3] * 3), nums[p5] * 5);
            nums[i] = ugly;
            if (ugly == nums[p2] * 2) p2++;
            if (ugly == nums[p3] * 3) p3++;
            if (ugly == nums[p5] * 5) p5++;
        }
    }

    public int nthUglyNumber(int n) {
        return nums[n - 1];
    }

    //超级丑数
    public int nthSuperUglyNumber(int n, int[] prims) {
        int[] dp = new int[n];
        dp[0] = 1;
        int[] pointer = new int[prims.length];
        for (int i = 1; i < n; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < prims.length; j++) {
                if (dp[pointer[j]] * prims[j] < min)
                    min = dp[pointer[j]] * prims[j];
            }
            dp[i] = min;
            for (int j = 0; j < prims.length; j++) {
                if (dp[pointer[j]] * prims[j] == min)
                    pointer[j]++;
            }
        }
        return dp[n - 1];
    }

    //丑数Ⅲ
    //这里的丑数的概念被改了！
    long hcd(long a, long b) {
        long res = a * b;
        //辗转相除法，又被称为除余法！
        while (b != 0) {
            long tmp = a % b;
            a = b;
            b = tmp;
        }
        return (res / a);
    }

    public long binarySearch(long low, long high, int a, int b, int c, int n) {
        long mid = (low + high) / 2;
        long sum = mid / a + mid / b + mid / c - mid / hcd(a, b) - mid / hcd(a, c) - mid / hcd(b, c) + mid / hcd(a, hcd(b, c));
        if (sum == n)
            return mid;
        else if (sum > n)
            return binarySearch(low, mid - 1, a, b, c, n);
        else
            return binarySearch(mid + 1, high, a, b, c, n);
    }

    public int nthUglyNumber(int n, int a, int b, int c) {
        long low = Math.min(a, Math.min(b, c));
        long high = low * n;
        long res = binarySearch(low, high, a, b, c, n);
        long r1 = res % a;
        long r2 = res % b;
        long r3 = res % c;
        return (int) (res - Math.min(r1, Math.min(r2, r3)));
    }

    public int exhaustion(int n, int a, int b, int c) {
        int i = 0;
        int num = 1;
        while (true) {
            if (num % a == 0 || num % b == 0 || num % c == 0)
                i++;
            if (i == n)
                return num;
            num++;
        }
    }

    public static void main(String[] args) {
        UglyNumber un = new UglyNumber();
        System.out.println(un.hcd(12, 18));
    }
}
