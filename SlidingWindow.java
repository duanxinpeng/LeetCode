import java.lang.reflect.Array;
import java.util.*;

public class SlidingWindow {
    public double getMedian(ArrayList<Integer> arrayList) {
        int k = arrayList.size();
        if (k % 2 == 0) {
            return ((long) arrayList.get(k / 2) + arrayList.get(k / 2 - 1)) / 2.0;
        } else {
            return (double) arrayList.get(k / 2);
        }
    }
    public void slide(ArrayList<Integer> arrayList, int remove, int add) {
        arrayList.remove((Integer) remove);
        int n = arrayList.size();
        int i = 0;
        for (i = 0; i < n; i++) {
            if (arrayList.get(i) >= add)
                break;
        }
        arrayList.add(i, add);
    }
    //Leecode 480 滑动窗口中位数
    public double[] medianSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n < k)
            return new double[0];
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            arrayList.add(nums[i]);
        }
        Collections.sort(arrayList);
        double[] res = new double[n - k + 1];
        res[0] = getMedian(arrayList);
        int i = 0, j = k;
        int p = 1;
        while (j < n) {
            slide(arrayList, nums[i], nums[j]);
            i++;
            j++;
            res[p] = getMedian(arrayList);
            p++;
        }
        return res;
    }

    //LeeCode 239 滑动窗口最大值
    public void push(int num, Deque<Integer> queue) {
        while (!queue.isEmpty() && queue.getLast() < num)
            queue.removeLast();
        queue.addLast(num);
    }
    public void pop(int num, Deque<Integer> queue) {
        if (!queue.isEmpty() && num == queue.getFirst())
            queue.removeFirst();
    }
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (k > n) {
            return new int[0];
        }
        Deque<Integer> queue = new ArrayDeque<>();
        int left = 0, right = k;
        int[] res = new int[n - k + 1];
        for (int i = 0; i < k; i++) {
            push(nums[i], queue);
        }
        int i = 1;
        res[0] = queue.getFirst();
        while (right < n) {
            push(nums[right], queue);
            right++;
            pop(nums[left], queue);
            left++;
            res[i] = queue.getFirst();
            i++;
        }
        return res;
    }


    // LeeCode-209 长度最小的子数组
    public int minSubArrayLen(int s, int[] nums) {
        int n = nums.length;
        int left = 0, right = 0;
        int res = n + 1;
        int sum = 0;
        while (right < n) {
            sum += nums[right];
            while (sum >= s) {
                if (right - left + 1 < res)
                    res = right - left + 1;
                sum -= nums[left];
                left++;
            }
            right++;
        }
        return res > n ? 0 : res;
    }
    // LeeCode-76最小覆盖子串
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length())
            return "";
        int[] need = new int[128];
        int[] window = new int[128];
        for (int i = 0; i < t.length(); i++) {
            need[t.charAt(i)]++;
        }
        int count = 0;
        int left = 0, right = 0;
        String res = "";
        int min = s.length() + 1;
        while (right < s.length()) {
            char ch = s.charAt(right);
            window[ch]++;
            if (need[ch] > 0 && need[ch] >= window[ch]) {
                count++;
            }
            while (count == t.length()) {
                ch = s.charAt(left);
                if (need[ch] > 0 && need[ch] >= window[ch])
                    count--;
                if (right - left + 1 < min) {
                    min = right - left + 1;
                    res = s.substring(left, right + 1);
                }
                window[ch]--;
                left++;
            }
            right++;
        }
        return res;
    }
    // LeeCode 438 找到字符串中所有字母异位词
    public List<Integer> findAnagrams(String s, String p) {
        int slen = s.length();
        int plen = p.length();
        if(slen < plen) {
            return new ArrayList<>();
        }
        ArrayList<Integer> res = new ArrayList<>();
        int[] windows = new int[26];
        int[] pchars = new int[26];
        for (int i = 0; i < plen; i++) {
            pchars[p.charAt(i)-'a']++;
        }
        int count = 0;
        int right = 0, left = 0;
        while (right < slen) {
            int cur = s.charAt(right)-'a';
            if (windows[cur] < pchars[cur]) {
                count++;
            }
            windows[cur]++;
            while (count == plen) {
                if (right-left+1 == plen)
                    res.add(left);
                int leftchar = s.charAt(left)-'a';
                if (windows[leftchar] <= pchars[leftchar])
                    count--;
                windows[leftchar]--;
                left++;
            }
            right++;
        }
        return res;
    }

    public static void main(String[] args) {
        String s = "duanxinpengxin";
        String p = "xin";
        System.out.println(s.indexOf(p));
    }
}
