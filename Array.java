import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class Array {

    //LeeCode-456 132模式
    public boolean find132pattern(int[] nums) {
        return br_456(nums);
    }
    public boolean br_456(int[] nums) {
        /*
        i j k
        a[i] < a[k] < a[j]
        以 nums[j] 为最大值，并记录 j 前面的最小值 nums[i]，然后在 j 后面寻找中间值 nums[k]！
         */
        int n = nums.length;
        if (n < 3)
            return false;
        int min = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] < nums[min])
                min = i;
            if (nums[i] > nums[min]) {
                for (int j = i + 1; j < n; j++) {
                    if (nums[j] > nums[min] && nums[j] < nums[i])
                        return true;
                }
            }
        }
        return false;
    }

    // Leecode-189 旋转数组
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        if (k > n)
            k = k % n;
        if (n != 0) {
            for (int i = 0; i < n / 2; i++) {
                int tmp = nums[i];
                nums[i] = nums[n-1-i];
                nums[n-1-i] = tmp;
            }
            for (int i = 0; i < k / 2; i++) {
                int tmp = nums[i];
                nums[i] = nums[k-1-i];
                nums[k-1-i] = tmp;
            }
            for (int i = 0; i < (n-k)/2; i++) {
                int tmp = nums[k+i];
                nums[k+i] = nums[n-1-i];
                nums[n-1-i] = tmp;
            }
        }
    }
    // 下一个排列
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        if (n >= 2) {
            int i = n-1;
            while (i > 0 && nums[i-1] >= nums[i])
                i--;
            if (i == 0) {
                Arrays.sort(nums);
            } else {
                int j = i;
                while (j < n-1 && nums[j+1] > nums[i-1])
                    j++;
                int tmp = nums[i-1];
                nums[i-1] = nums[j];
                nums[j] = tmp;
                for (int k = 0; k < (n - i) / 2; k++) {
                    tmp = nums[i+k];
                    nums[i+k] = nums[n-1-k];
                    nums[n-1-k] = tmp;
                }
            }
        }
    }

    // LeeCode-最小差
    public int smallestDifference(int[] a, int[] b) {
        Arrays.sort(a);
        Arrays.sort(b);
        int m = a.length;
        int n = b.length;
        int i=0,j=0;
        // 有可能溢出！！
        int res = Integer.MAX_VALUE;
        while (i<m && j < n) {
            if (a[i] == b[j])
                return 0;
            if (a[i] > b[j]) {
                res = (int) Math.min(res,(long)a[i]-(long)b[j]);
                j++;
            } else {
                res = (int) Math.min(res,(long)b[j]-(long)a[i]);
                i++;
            }
        }
        while (i < m && a[i] < b[n-1]) {
            res = Math.min(res,b[n-1]-a[i]);
            i++;
        }
        while (j < n && b[j] < a[m-1]) {
            res = Math.min(res,a[m-1]-b[j]);
            j++;
        }
        return res;
    }



    public static void main(String[] args) {
        int[] a= {1,2,11,15};
        int[] b = {4,12,19,23,127,235};
        Array array = new Array();
        array.smallestDifference(a,b);
    }
}
