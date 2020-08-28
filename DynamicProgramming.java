public class DynamicProgramming {
    // LeetCode-91 解码方法
    public int numDecodings(String s) {
        int n = s.length();
        int first=0, second;
        if (s.charAt(0)=='0'){
            second = 0;
        } else {
            second = 1;
        }
        for (int i = 1; i < n; i++) {
            int cur = s.charAt(i) - '0';
            int last = s.charAt(i - 1) - '0';
            last = last * 10 + cur;
            int tmp = 0;
            if (cur > 0)
                tmp = second;
            if (last >= 10 && last <= 26) {
                if (i == 1) {
                    tmp++;
                } else {
                    tmp += first;
                }
            }
            first = second;
            second = tmp;
        }
        return second;
    }
}
