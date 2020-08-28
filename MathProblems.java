public class MathProblems {
    // 快速幂
    public static int quickPow(int a, int b) {
        /*
        计算log(n)时间复杂度 计算 a^b
         */
        int res = 1, base = a;
        while (b > 0) {
            if ((b & 1) == 1) {
                res *= base;
            }
            base *= base;
            b >>= 1;
        }
        return res;
    }
    // 寻找中位数
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m>n) {
            int[] tmp = nums1;
            nums1 = nums2;
            nums2 = tmp;
            m = nums1.length;
            n = nums2.length;
        }
        int low = 0, high = m;
        while (low <= high) {
            int i = (low+high)/2;
            int j = (m+n)/2-  i;
            if (i > low && nums1[i-1] > nums2[j]) {
                high = i - 1;
                continue;
            }
            if (i < high && nums2[j-1] > nums1[i]) {
                low = i + 1;
                continue;
            }
            int minRight=0;
            if (i==m) {
                minRight = nums2[j];
            } else {
                if (j==n) {
                    minRight = nums1[i];
                } else {
                    minRight = Math.min(nums1[i],nums2[j]);
                }
            }
            if ((m+n)%2==1) {
                return minRight;
            }

            int maxLeft = 0;
            if (i==0) {
                maxLeft = nums2[j-1];
            } else {
                if (j==0) {
                    maxLeft = nums1[i-1];
                }else {
                    maxLeft = Math.max(nums1[i-1],nums2[j-1]);
                }
            }
            return (maxLeft+minRight)/2.0;
        }
        return 0.0;
    }

    public static void main(String[] args) {
        System.out.println(quickPow(4,2));
    }
}
