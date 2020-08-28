package nowcoder;

import java.util.*;

public class Tencent {
    public static void problem1_2020() {
        /**
         * 1. 将"["一起入栈，用以括号匹配
         * 2. 字符串为空时，不如栈，因为可能有 "]]" 这种情况！
         * 3. 注意出栈顺序，以得到正确的字符串！
         */
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        Deque<String> stringDeque = new ArrayDeque<>();
        Deque<Integer> integerDeque = new ArrayDeque<>();
        int len = string.length();
        int i = 0;
        while (i < len) {
            if (string.charAt(i) == '[') {
                i++;
                String num = "";
                while (string.charAt(i) >= '0' && string.charAt(i) <= '9') {
                    num += string.charAt(i);
                    i++;
                }
                stringDeque.push("[");
                integerDeque.push(Integer.parseInt(num));
                i++;
            }
            if (string.charAt(i) == ']') {
                int num = integerDeque.pop();
                String res = "";
                String tmp = "";
                while (!stringDeque.isEmpty() && !stringDeque.peekFirst().equals("[")) {
                    tmp = stringDeque.pop() + tmp;
                }
                if (!stringDeque.isEmpty() && stringDeque.peekFirst().equals("["))
                    stringDeque.pop();
                for (int j = 0; j < num; j++) {
                    res += tmp;
                }
                stringDeque.push(res);
                i++;
            }
            String tmp = "";
            while (i < len && string.charAt(i) >= 'A' && string.charAt(i) <= 'Z') {
                tmp += string.charAt(i);
                i++;
            }
            if (!tmp.equals(""))
                stringDeque.push(tmp);
        }
        String res = "";
        while (!stringDeque.isEmpty()) {
            res += stringDeque.pollLast();
        }
        System.out.println(res);
    }

    // problem2
    public static void problem2_2020() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        int[] res = new int[n];
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            res[i] += deque.size();
            while (!deque.isEmpty() && deque.peek() <= nums[i])
                deque.pop();
            deque.push(nums[i]);
        }
        deque = new ArrayDeque<>();
        for (int i = n - 1; i >= 0; i--) {
            res[i] += (deque.size() + 1);
            while (!deque.isEmpty() && deque.peek() <= nums[i])
                deque.pop();
            deque.push(nums[i]);
        }
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                System.out.print(res[i]);
            } else {
                System.out.print(" " + res[i]);
            }
        }
    }

    // problem3
    public static int[] merge(long[] order, long[] reverse, int[] left, int[] right,int index) {
        int len = left.length + right.length;
        int[] res = new int[len];
        //int index = (int) (Math.log(len) / Math.log(2));
        int i = 0, j = 0;
        while (i < left.length && j < right.length) {
            /**
             * 1. 当left[i] < right[j] 时, 要把 left[i] 放入res，并且i++，所以 left[i]和right中 j 之后的所有元素呈顺序对！
             * 2. 当left[j] > right[j] 时， 要把right[j] 放入res，并且j++，所以right[j]和left中 i 之后的所有元素组成逆序对！
             * 3. 总之，将谁丢入 res， 就看谁和另外一个数组中的哪些元素能够组成顺序对或逆序对！ 这样就不会出现重复计数的问题了！！！！！
             * 4. 但是！顺序对和逆序对不能在一个循环里面同时运算得到，因为当left[i]==right[j]时，需要考虑究竟是i++还是j++
             * 5. 如果是求顺序对，就因该j++；如果是求逆序对，就应该是i++；
             * 6. 两个解决办法：1）将数组逆序，那么在该逆序后的数组上求逆序对就相当于在元素组上求顺序对！2）写两个mergeSort，分别用于求顺序对和逆序对！
             */
            if (left[i] < right[j]) {
                // left[i]和rilgt中j之后的元素组成顺序对！
                //order[index] += (right.length - j);
                res[i + j] = left[i];
                i++;
            } else if (left[i] > right[j]) {
                // right[j]和left中i之后的元素组成逆序对！
                reverse[index] += left.length - i;
                res[i + j] = right[j];
                j++;
            } else {
                //
                res[i + j] = left[i];
                i++;
            }
        }
        while (i < left.length) {
            res[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            res[i + j] = right[j];
            j++;
        }
        return res;
    }

    public static int[] mergeSort(long[] order, long[] reverse, int[] nums, int begin, int end,int index) {
        if (begin == end) {
            int[] res = {nums[begin]};
            return res;
        } else {
            int mid = (begin + end) / 2;
            int[] left = mergeSort(order, reverse, nums, begin, mid,index-1);
            int[] right = mergeSort(order, reverse, nums, mid + 1, end,index-1);
            return merge(order, reverse, left, right,index);
        }
    }

    public static void problem3_2020() {
        /**
         * 1. reverse 的本质
         * 2. reverse 对逆序对的影响
         * 3. 归并排序寻找逆序对的本质！
         */
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int len = 1 << n;
        int[] nums = new int[len];
        int[] nums1 = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = scanner.nextInt();
            nums1[len-1-i] = nums[i];
        }
        long[] order = new long[n + 1];
        long[] reverse = new long[n + 1];
        mergeSort(new long[5],order,  nums1, 0, len - 1,n);
        mergeSort(new long[5],reverse,  nums, 0, len - 1,n);
        int m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            int q = scanner.nextInt();
            for (int j = 1; j <= q; j++) {
                long tmp = order[j];
                order[j] = reverse[j];
                reverse[j] = tmp;
            }
            long res = 0;
            for (int j = 0; j < n + 1; j++) {
                res += reverse[j];
            }
            System.out.println(res);
        }
    }

    // problem4
    public static void problem4_2020() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] work = new int[n];
        int[] exercise = new int[n];
        for (int i = 0; i < n; i++) {
            work[i] = scanner.nextInt();
        }
        for (int i = 0; i < n; i++) {
            exercise[i] = scanner.nextInt();
        }
        int[][] dp = new int[n][3];
        for (int i = 0; i < n; i++) {
            // 用一个比较大的数字表示某种状态不可能存在！
            dp[i][0] = n;
            dp[i][1] = n;
            dp[i][2] = n;
        }
        dp[0][0] = 1;
        if (work[0] == 1)
            dp[0][1] = 0;
        if (exercise[0] == 1)
            dp[0][2] = 0;
        for (int i = 1; i < n; i++) {
            if (work[i] == 1)
                dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][2]);
            if (exercise[i] == 1) {
                dp[i][2] = Math.min(dp[i - 1][0], dp[i - 1][1]);
            }
            dp[i][0] = Math.min(dp[i - 1][0], Math.min(dp[i - 1][1], dp[i - 1][2])) + 1;
        }
        System.out.println(Math.min(dp[n - 1][0], Math.min(dp[n - 1][1], dp[n - 1][2])));
    }

    public static void problem5_2020() {
        class Pair {
            int x, y;

            public Pair(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int L = scanner.nextInt();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            map.put(x, Math.max(map.getOrDefault(x, 0), y));
        }
        ArrayList<Pair> list = new ArrayList<>();
        for (Integer key : map.keySet()) {
            list.add(new Pair(key, map.get(key)));
        }
        Collections.sort(list, (a, b) -> {
            return a.x - b.x;
        });
        int res = 0;
        int left = 0;
        int right = 0;
        int i = 0;
        while (true) {
            if (right >= L) {
                System.out.println(res);
                return;
            }
            int tmp = right;
            while (i < list.size()) {
                Pair p = list.get(i);
                if (p.x >= left && p.x <= right) {
                    tmp = Math.max(tmp, p.y);
                    i++;
                } else {
                    break;
                }
            }
            if (tmp <= right) {
                System.out.println(-1);
                return;
            } else {
                left = right + 1;
                right = tmp;
                res++;
            }
        }
    }

    public static void main(String[] args) {
//        problem3_2020();
        int b = 10000000;
        int i = 1;
        long a = 10000000000L;
        a += b-i;
        System.out.println(a);
    }
    public int[] smallestRange(List<List<Integer>> nums) {
        Collections.sort(nums,(x,y)->{
            return y.get(0)-x.get(0);
        });
        int right = nums.get(0).get(0);
        Collections.sort(nums,(x,y)->{
            return x.get(x.size()-1)-y.get(y.size()-1);
        });
        int left = nums.get(0).get(nums.get(0).size()-1);
        if (left <= right){
            int[] res = {left,right};
            return res;
        } else {
            
        }
        return null;
    }
}

