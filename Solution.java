import java.util.*;
import java.util.LinkedList;

class ListNode {
    int val;
    ListNode next;

    public ListNode(int val) {
        this.val = val;
    }
}

public class Solution {
    //Leecode-96

    public int numTrees(int n) {
        if (n <= 1) {
            return 1;
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            int left = numTrees(i - 1);
            int right = numTrees(n - i);
            res += left * right;
        }
        return res;
    }

    public boolean isValidBST(TreeNode root) {
        if (root == null)
            return true;
        boolean left = isValidBST(root.left);
        boolean right = isValidBST(root.right);
        if (!left || !right)
            return false;
        TreeNode p = root.left;
        int num = 0;
        if (p != null) {
            while (p.right != null) {
                p = p.right;
            }
            if (p.val >= root.val)
                return false;
        }
        p = root.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            if (p.val <= root.val)
                return false;
        }
        return true;
    }

    public boolean isSymmetric(TreeNode root) {
        return check(root, root);
    }

    public boolean check(TreeNode p, TreeNode q) {
//        if (p==null && q == null)
//            return true;
//        if (p==null || q==null)
//            return false;
//        return p.val==q.val && check(p.left,q.right) && check(p.right,q.left);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(p);
        queue.offer(q);
        while (!queue.isEmpty()) {
            TreeNode u = queue.poll();
            TreeNode v = queue.poll();
            if (u == null && v == null)
                continue;
            if (u == null || v == null || u.val != v.val)
                return false;
            queue.offer(u.left);
            queue.offer(v.right);

            queue.offer(u.right);
            queue.offer(v.left);
        }
        return true;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        if (root == null)
            return res;
        queue.offer(root);
        while (!queue.isEmpty()) {
            int n = queue.size();
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                TreeNode tn = queue.poll();
                tmp.add(tn.val);
                if (tn.left != null)
                    queue.offer(tn.left);
                if (tn.right != null)
                    queue.offer(tn.right);
            }
            res.add(tmp);
        }
        return res;
    }

    public int maxDepth(TreeNode root) {
        int depth = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        if (root == null)
            return 0;
        queue.offer(root);
        while (!queue.isEmpty()) {
            int n = queue.size();
            depth++;
            for (int i = 0; i < n; i++) {
                TreeNode tmp = queue.poll();
                if (tmp.left != null)
                    queue.offer(tmp.left);
                if (tmp.right != null)
                    queue.offer(tmp.right);
            }
        }
        return depth;
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        TreeNode root = build(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
        return root;
    }

    public TreeNode build(int[] preorder, int begin1, int end1, int[] inorder, int begin2, int end2) {
        //这种null的情况尤其需要注意！
        if (begin1 > end1 || begin2 > end2) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[begin1]);
        int left_num = 0;
        for (int i = begin2; i < end2; i++) {
            if (inorder[i] == preorder[begin1]) {
                break;
            }
            left_num++;
        }
        TreeNode left = build(preorder, begin1 + 1, begin1 + left_num, inorder, begin2, begin2 + left_num - 1);
        TreeNode right = build(preorder, begin1 + 1 + left_num, end1, inorder, begin2 + left_num + 1, end2);
        root.left = left;
        root.right = right;
        return root;
    }

    public void flatten(TreeNode root) {
        TreeNode p = root;
        while (p != null) {
            if (p.left == null) {
                p = p.right;
            } else {
                TreeNode pre = p.left;
                while (pre.right != null)
                    pre = pre.right;
                pre.right = p.right;
                p.right = p.left;
                p.left = null;
                p = p.right;
            }
        }
    }

    //LeeCode-124
    int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxPath(root);
        return maxSum;
    }

    public int maxPath(TreeNode root) {
        if (root == null)
            return 0;
        int left = Math.max(maxPath(root.left), 0);
        int right = Math.max(maxPath(root.right), 0);

        //这里寻找局部最优，局部最优不包括空节点的！
        int tmp = root.val + right + left;
        if (tmp > maxSum) {
            maxSum = tmp;
        }

        return root.val + Math.max(left, right);
    }

    //Leecode-15
    public List<List<Integer>> threeSum(int nums[]) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i - 1] == nums[i]) continue;
            int b = i + 1, c = nums.length - 1;
            while (b < c) {
                System.out.println(b + "" + c);
                if (b > i && nums[b - 1] == nums[b]) {
                    b++;
                    continue;
                }
                if (c < nums.length - 1 && nums[c + 1] == nums[c]) {
                    c--;
                    continue;
                }
                if (nums[i] + nums[b] + nums[c] == 0) {
                    ArrayList tmp = new ArrayList();
                    tmp.add(nums[i]);
                    tmp.add(nums[b]);
                    tmp.add(nums[c]);
                    res.add(tmp);
                    b++;
                    c--;
                }
                if (nums[i] + nums[b] + nums[c] > 0) c--;
                if (nums[i] + nums[b] + nums[c] < 0) b++;
            }
        }
        return res;
    }

    //LeeCode-22
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        parenthesis(res, n, n, new StringBuilder());
        return res;
    }

    public void parenthesis(List<String> res, int left, int right, StringBuilder str) {
        if (left == 0 && right == 0) {
            res.add(str.toString());
        } else {
            if (left == right) {
                str.append('(');
                parenthesis(res, left - 1, right, str);
                str.deleteCharAt(str.length() - 1);
            } else {
                if (left == 0) {
                    str.append(')');
                    parenthesis(res, left, right - 1, str);
                    str.deleteCharAt(str.length() - 1);
                } else {
                    str.append('(');
                    parenthesis(res, left - 1, right, str);
                    str.deleteCharAt(str.length() - 1);
                    str.append(')');
                    parenthesis(res, left, right - 1, str);
                    str.deleteCharAt(str.length() - 1);
                }
            }
        }
    }

    //LeeCode-23
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0)
            return null;
        return merge(lists, 0, lists.length - 1);
    }

    public ListNode merge(ListNode[] lists, int begin, int end) {
        if (begin == end)
            return lists[begin];
        int mid = (begin + end) / 2;
        return mergeTwoLists(merge(lists, begin, mid), merge(lists, mid + 1, end));
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null)
            return list2;
        if (list2 == null)
            return list1;
        ListNode res = new ListNode(-1);
        ListNode p = res;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                p.next = list1;
                list1 = list1.next;
            } else {
                p.next = list2;
                list2 = list2.next;
            }
            p = p.next;
        }
        if (list1 != null)
            p.next = list1;
        if (list2 != null)
            p.next = list2;
        return res.next;
    }

    //Leecode-33
    public int search(int[] nums, int target) {
        return binarySearch(nums, 0, nums.length - 1, target);
    }

    public int binarySearch(int[] nums, int begin, int end, int target) {
        if (begin > end)
            return -1;
        int mid = (begin + end) / 2;
        if (nums[mid] == target)
            return mid;
        int low = begin;
        int high = end;
        if (nums[begin] <= nums[mid]) {
            if (target >= nums[begin] && target <= nums[mid]) {
                low = begin;
                high = mid - 1;
            } else {
                low = mid + 1;
                high = end;
            }
        } else {
            if (target >= nums[mid] && target <= nums[end]) {
                low = mid + 1;
                high = end;
            } else {
                low = begin;
                high = mid - 1;
            }
        }
        return binarySearch(nums, low, high, target);
    }

    //LeeCode-34
    public int[] searchRange(int[] nums, int target) {
        int[] res = {-1, -1};
        int left = findBoundary(nums, target, true);
        if (left == nums.length || nums[left] != target) {
            return res;
        }
        res[0] = left;
        res[1] = findBoundary(nums, target, false);
        return res;
    }

    public int findBoundary(int[] nums, int target, boolean left) {
        int low = 0;
        int high = nums.length;
        while (low < high) {
            int mid = (low + high) / 2;
            if (nums[mid] > target || left && nums[mid] == target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    //LeeCode-48
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        //矩阵转置
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
        //数组反转
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[i][n - j - 1];
                matrix[i][n - j - 1] = tmp;
            }
        }
    }

    //LeeCode-49
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            char[] tmp = strs[i].toCharArray();
            Arrays.sort(tmp);
            String key = String.valueOf(tmp);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(strs[i]);
        }
        return new ArrayList<>(map.values());
    }

    //LeeCode-55
    public boolean canJump(int[] nums) {
        int n = nums.length;
        int rightMost = 0;
        for (int i = 0; i < n; i++) {
            if (i <= rightMost) {
                if (i + nums[i] > rightMost)
                    rightMost = i + nums[i];
                if (rightMost >= n - 1)
                    return true;
            }
        }
        return false;
    }

    //LeeCode-56
    public int[][] merge(int[][] intervals) {
        //先按照区间起始位置排序
        Arrays.sort(intervals, (v1, v2) -> v1[0] - v2[0]);
        int[][] res = new int[intervals.length][2];
        //res的指针！
        int idx = -1;
        for (int[] interval :
                intervals) {
            //如果res为空，或者区间不重叠，则直接将区间加入res
            if (idx == -1 || interval[0] > res[idx][1])
                res[++idx] = interval;
                // 进行区间的合并；
            else {
                res[idx][1] = Math.max(res[idx][1], interval[1]);
            }
        }

        //截取数组，第二个元素表示长度。
        return Arrays.copyOf(res, idx + 1);
    }

    //LeeCode-75
    public void sortColors(int[] nums) {
        int n = nums.length;
        int p0 = 0, p = 0;
        int p2 = n - 1;
        int tmp;
        while (p <= p2) {
            if (nums[p] == 0) {
                tmp = nums[p0];
                nums[p0++] = nums[p];
                nums[p++] = tmp;
            } else if (nums[p] == 2) {
                tmp = nums[p2];
                nums[p2--] = nums[p];
                nums[p] = tmp;
            } else {
                p++;
            }
        }
    }

    //LeeCode-78
    List<List<Integer>> res = new ArrayList<>();
    int n, k;

    public List<List<Integer>> subsets(int[] nums) {
        //二进制实现
//        List<List<Integer>> res = new ArrayList<>();
//        int n = nums.length;
//        int m = 1 << n;
//        for (int i = 0; i < m; i++) {
//            ArrayList<Integer> tmp = new ArrayList<>();
//            int j = 0;
//            int p = i;
//            while (p != 0) {
//                if ((p&1)==1)
//                    tmp.add(nums[j]);
//                j++;
//                p = p >> 1;
//            }
//            res.add(tmp);
//        }
//        return res;
        //递归实现
//        List<List<Integer>> res = new ArrayList<>();
//        res.add(new ArrayList<>());
//        for (int num :
//                nums) {
//            List<List<Integer>> newSets = new ArrayList<>();
//            for (List<Integer> set:
//                 res) {
//                ArrayList<Integer> tmp = new ArrayList<>(set);
//                tmp.add(num);
//                newSets.add(tmp);
//            }
//            for (List<Integer> set:
//                newSets){
//                res.add(set);
//            }
//        }
//        return res;

        //回溯实现
        n = nums.length;
        for (k = 0; k < n + 1; k++) {
            dfs(0, new ArrayList<Integer>(), nums);
        }
        return res;
    }

    public void dfs(int first, ArrayList<Integer> curr, int[] nums) {
        if (curr.size() == k) {
            res.add(new ArrayList<>(curr));
            return;
        }
        for (int i = first; i < n; i++) {
            curr.add(nums[i]);
            dfs(i + 1, curr, nums);
            curr.remove(curr.size() - 1);
        }
    }

    //LeeCode-79 单词搜索
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        int w = word.length();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    boolean[][] flags = new boolean[m][n];
                    flags[i][j] = true;
                    if (dfs(board, flags, i, j, word, 1))
                        return true;
                }
            }
        }
        return false;
    }

    public boolean dfs(char[][] board, boolean[][] flag, int x, int y, String word, int index) {
        if (index == word.length())
            return true;
        if (x - 1 >= 0 && !flag[x - 1][y] && board[x - 1][y] == word.charAt(index)) {
            flag[x - 1][y] = true;
            if (dfs(board, flag, x - 1, y, word, index + 1))
                return true;
            flag[x - 1][y] = false;
        }
        if (x + 1 < board.length && !flag[x + 1][y] && board[x + 1][y] == word.charAt(index)) {
            flag[x + 1][y] = true;
            if (dfs(board, flag, x + 1, y, word, index + 1))
                return true;
            flag[x + 1][y] = false;
        }
        if (y - 1 >= 0 && !flag[x][y - 1] && board[x][y - 1] == word.charAt(index)) {
            flag[x][y - 1] = true;
            if (dfs(board, flag, x, y - 1, word, index + 1))
                return true;
            flag[x][y - 1] = false;
        }
        if (y + 1 < board[0].length && !flag[x][y + 1] && board[x][y + 1] == word.charAt(index)) {
            flag[x][y + 1] = true;
            if (dfs(board, flag, x, y + 1, word, index + 1))
                return true;
            flag[x][y + 1] = false;
        }
        return false;
    }

    //LeeCode-136 只出现一次的数字
    public int singleNumber(int[] nums) {
        int single = 0;
        for (int num :
                nums) {
            single ^= num;
        }
        return single;
    }

    //LeeCode-139 单词拆分
    public boolean wordBreak(String word, List<String> wordDict) {
        int n = word.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j <= i; j++) {
                if (dp[i - j] && wordDict.contains(word.substring(i - j, j))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }

    //LeeCode-142环形链表2
    public ListNode detectCycle(ListNode head) {
        HashMap<ListNode, Integer> map = new HashMap<>();
        ListNode p = head;
        while (p != null) {
            if (map.containsKey(p))
                break;
            map.put(p, 0);
            p = p.next;
        }
        if (p == null) {
            //System.out.println("no cycle");
            return null;
        }
        return p;
    }

    //LeeCode-160 相交链表
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null)
            return null;
        ListNode pA = headA;
        ListNode pB = headB;
        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }
        return pA;
    }

    //Leecode-169 多数元素
    public int majorityElement(int[] nums) {
        int n = nums.length;
        Random r = new Random();
        while (true) {
            int rand = r.nextInt(n);
            if (isMajority(nums, rand))
                return nums[rand];
        }
    }

    public boolean isMajority(int[] nums, int index) {
        int n = nums.length;
        int num = 0;
        for (int i = 0; i < n; i++) {
            if (nums[index] == nums[i])
                num++;
        }
        if (num > n / 2)
            return true;
        else
            return false;
    }

    //LeeCode-200 岛屿数量
    public int numIslands(char[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    res++;
                    dfs(grid, i, j);
                }
            }
        }
        return res;
    }

    public void dfs(char[][] grid, int x, int y) {
        int m = grid.length;
        int n = grid[0].length;
        //把所有的判断条件都放在这里！
        if (x < 0 || y < 0 || x >= m || y >= n || grid[x][y] == '0')
            return;
        grid[x][y] = '0';
        dfs(grid, x - 1, y);
        dfs(grid, x + 1, y);
        dfs(grid, x, y - 1);
        dfs(grid, x, y + 1);
    }

    //LeeCode-206 反转链表
    public ListNode reverseList(ListNode head) {
        ListNode p = head;
        ListNode dnode = new ListNode(-1);
        while (p != null) {
            ListNode tmp = p;
            p = p.next;
            tmp.next = dnode.next;
            dnode.next = tmp;
        }
        return dnode.next;
    }

    //LeeCode-207 课程表
    private boolean dfs(List<List<Integer>> adjacency, int[] flags, int i) {
        if (flags[i] == 1) return false;
        if (flags[i] == -1) return true;
        flags[i] = 1;
        for (int j :
                adjacency.get(i)) {
            if (!dfs(adjacency, flags, j))
                return false;
        }
        flags[i] = -1;
        return true;
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //DFS
        List<List<Integer>> adjacency = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adjacency.add(new ArrayList<>());
        }
        int[] flags = new int[numCourses];
        for (int[] cp :
                prerequisites) {
            adjacency.get(cp[1]).add(cp[0]);
        }
        for (int i = 0; i < numCourses; i++) {
            if (!dfs(adjacency, flags, i)) return false;
        }
        return true;
        //BFS
//        int[] indegrees = new int[numCourses];
//        List<List<Integer>> adjacent = new ArrayList<>();
//        for (int i = 0; i < numCourses; i++) {
//            adjacent.add(new ArrayList<>());
//        }
//        for (int[] pre :
//                prerequisites) {
//            adjacent.get(pre[1]).add(pre[0]);
//            indegrees[pre[0]]++;
//        }
//        Queue<Integer> queue = new LinkedList<>();
//        for (int i = 0; i < indegrees.length; i++) {
//            if (indegrees[i]==0) {
//                queue.offer(i);
//            }
//        }
//        while (!queue.isEmpty()) {
//            int course = queue.poll();
//            numCourses--;
//            for (int adj :
//                    adjacent.get(course)) {
//                if (--indegrees[adj] == 0) {
//                    queue.add(adj);
//                }
//            }
//        }
//        return numCourses==0;
    }

    // LeeCode-215 数组中第k个最大的元素。
    Random random = new Random();

    public int findKthLargest(int[] nums, int k) {
        //快速选择
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
        //PriorityQueues实现
        //return priorityQueue(nums,k);
    }

    public int priorityQueue(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>(nums.length, (a, b) -> {
            return b - a;
        });
        for (int i = 0; i < nums.length; i++) {
            heap.add(nums[i]);
        }
        int res = 0;
        for (int i = 0; i < k; i++) {
            res = heap.poll();
        }
        return res;
    }

    public int quickSelect(int[] nums, int low, int high, int index) {
        int tmp = randomPartion(nums, low, high);
        if (tmp == index) {
            return nums[index];
        } else if (tmp < index) {
            return quickSelect(nums, tmp + 1, high, index);
        } else {
            return quickSelect(nums, low, tmp - 1, index);
        }
    }

    public int randomPartion(int[] nums, int low, int high) {
        int randIndex = random.nextInt(high - low + 1) + low;
        int tmp = nums[low];
        nums[low] = nums[randIndex];
        nums[randIndex] = tmp;
        int l = low, h = high;
        tmp = nums[low];
        while (true) {
            while (h > l && nums[h] >= tmp)
                h--;
            if (h == l) {
                nums[h] = tmp;
                break;
            } else {
                nums[l] = nums[h];
            }
            while (l < h && nums[l] <= tmp)
                l++;
            if (h == l) {
                nums[l] = tmp;
                break;
            } else {
                nums[h] = nums[l];
            }
        }
        return l;
    }

    //LeeCode-221 最大正方形
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        if (m == 0)
            return 0;
        int n = matrix[0].length;
        int[][] dp = new int[m][n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '0') {
                    dp[i][j] = 0;
                } else {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j - 1])) + 1;
                    }
                    int area = dp[i][j] * dp[i][j];
                    if (area > res)
                        res = area;
                }
            }
        }
        return res;
    }

    //LeeCode-226 反转二叉树
    public TreeNode invertTree(TreeNode root) {
        if (root == null)
            return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    //LeeCode-234 回文链表
    public boolean isPalindrome(ListNode head) {
        ListNode p = head;
        List<Integer> list = new ArrayList<>();
        while (p != null) {
            list.add(p.val);
            p = p.next;
        }
        int l = 0, h = list.size() - 1;
        while (l < h) {
            if (!list.get(l).equals(list.get(h)))
                return false;
            l++;
            h--;
        }
        return true;
    }

    //LeeCode-236 二叉树的最近公共祖先
    TreeNode res236 = null;

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root, p, q);
        return res236;
    }

    private boolean dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null)
            return false;
        boolean lson = dfs(root.left, p, q);
        boolean rson = dfs(root.right, p, q);
        if ((lson && rson) || ((root == p || root == q) && (lson || rson))) {
            res236 = root;
        }
        return lson || rson || root == p || root == q;
    }

    // LeeCode-238 除自身以外数组的乘积
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        int tmp = 1;
        res[0] = 1;
        for (int i = 1; i < n; i++) {
            res[i] = tmp * nums[i - 1];
            tmp = res[i];
        }
        tmp = 1;
        for (int i = n - 2; i >= 0; i--) {
            tmp = nums[i + 1] * tmp;
            res[i] = res[i] * tmp;
        }
        return res;
    }

    //LeeCode-739 每日温度
    public int[] dailyTemperatures(int[] T) {
        //暴力法
        //return violence739(T);
        //单调栈
        //return mstack739(T);
        //KMP
        return kmp739(T);
    }

    public int[] kmp739(int[] T) {
        int n = T.length;
        int[] res = new int[n];
        for (int i = n - 2; i >= 0; i--) {
            int now = i + 1;
            while (T[now] <= T[i]) {
                if (res[now] != 0) {
                    now += res[now];
                } else {
                    break;
                }
            }
            if (T[now] > T[i])
                res[i] = now - i;
        }
        return res;
    }

    public int[] mstack739(int[] T) {
        int n = T.length;
        int[] res = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && T[i] > T[stack.peek()]) {
                int pre = stack.poll();
                res[pre] = i - pre;
            }
            stack.push(i);
        }
        return res;
    }

    public int[] violence739(int[] T) {
        int n = T.length;
        int[] res = new int[n];
        int[] next = new int[71];
        for (int i = 0; i < 71; i++) {
            next[i] = Integer.MAX_VALUE;
        }
        for (int i = n - 1; i >= 0; i--) {
            int minIndex = Integer.MAX_VALUE;
            for (int j = T[i] + 1; j <= 100; j++) {
                if (next[j - 30] < minIndex)
                    minIndex = next[j - 30];
            }
            if (minIndex != Integer.MAX_VALUE) {
                res[i] = minIndex - i;
            } else {
                res[i] = 0;
            }
            next[T[i] - 30] = i;
        }
        return res;
    }

    //LeeCode-621 任务调度器
    public int leastInterval(char[] tasks, int n) {
        int[] taskNums = new int[26];
        for (char c :
                tasks) {
            taskNums[c - 'A']++;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>(26, Collections.reverseOrder());
        for (int nums :
                taskNums) {
            if (nums > 0)
                queue.add(nums);
        }
        int res = 0;
        while (!queue.isEmpty()) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < n + 1; i++) {
                if (queue.isEmpty() && tmp.isEmpty())
                    break;
                if (!queue.isEmpty()) {
                    if (queue.peek() > 1)
                        tmp.add(queue.poll() - 1);
                    else
                        queue.poll();
                }
                res++;
            }
            for (int t :
                    tmp) {
                queue.add(t);
            }
        }
        return res;
    }

    //LeeCode-560 和为K的子数组
    public int subarraySum(int[] nums, int k) {
        int res = 0, pre = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            pre += nums[i];
            if (map.containsKey(pre - k)) {
                res += map.get(pre - k);
            }
            map.put(pre, map.getOrDefault(pre, 0) + 1);
        }
        return res;
    }

    //LeeCode-543 二叉树的直径
    int res543 = 0;

    public int dfs543(TreeNode root) {
        if (root == null)
            return 0;
        int left = dfs543(root.left);
        int right = dfs543(root.right);
        if (left + right > res543)
            res543 = left + right;
        if (left > right)
            return left + 1;
        else
            return right + 1;
    }

    public int diameterOfBinaryTree(TreeNode root) {
        dfs543(root);
        return res543;
    }

    //LeeCode-538 把二叉搜索树转换为累加树
    int tmp538 = 0;

    public void dfs538(TreeNode root) {
        if (root != null) {
            dfs538(root.right);
            tmp538 += root.val;
            root.val = tmp538;
            dfs538(root.left);
        }
    }

    public TreeNode convertBST(TreeNode root) {
        dfs538(root);
        return root;
    }

    //LeeCode-494 目标和
    public int findTargetSumWays(int[] nums, int S) {
        //递归实现，回溯实现
//        recursion494(nums,0,0,S);
//        return res494;
        return dp494(nums, S);
    }

    public int dp4942(int[] nums, int S) {
        int[][] dp = new int[nums.length][2001];
        dp[0][nums[0] + 1000] = 1;
        dp[0][-nums[0] + 1000] += 1;
        for (int i = 1; i < nums.length; i++) {
            for (int j = -1000; j <= 1000; j++) {
                if (j + nums[i] < 1000 && j - nums[i] >= -1000)
                    dp[i][j + 1000] = dp[i - 1][j + nums[i] + 1000] + dp[i - 1][j - nums[i] + 1000];
                else if (j + nums[i] < 1000)
                    dp[i][j + 1000] = dp[i - 1][j + nums[i] + 1000];
                else
                    dp[i][j + 1000] = dp[i - 1][j - nums[i] + 1000];
            }
        }
        return dp[nums.length - 1][S + 1000];
    }

    public int dp494(int[] nums, int S) {
        int[][] dp = new int[nums.length][2001];
        dp[0][nums[0] + 1000] = 1;
        //+= 很巧妙，把nums[0]==0的情况也考虑了进去
        dp[0][-nums[0] + 1000] += 1;
        for (int i = 1; i < nums.length; i++) {
            for (int j = -1000; j <= 1000; j++) {
                //因为有数组和不大于1000的条件限制，所以不需要考虑数组越界的情况！！！
                //这个判断也很巧妙啊
                if (dp[i - 1][j + 1000] > 0) {
                    dp[i][j + nums[i] + 1000] += dp[i - 1][j + 1000];
                    dp[i][j - nums[i] + 1000] += dp[i - 1][j + 1000];
                }
            }
        }
        return S > 1000 ? 0 : dp[nums.length - 1][S + 1000];
    }

    int res494 = 0;

    public void recursion494(int[] nums, int i, int sum, int S) {
        if (i == nums.length) {
            if (sum == S)
                res494++;
        } else {
            recursion494(nums, i + 1, sum + nums[i], S);
            recursion494(nums, i + 1, sum - nums[i], S);
        }
    }

    //LeeCode-461 汉明距离
    public int hanmingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }

    //LeeCode-448 找到数组中消失的数字
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]) - 1;
            if (nums[index] > 0)
                nums[index] = -nums[index];
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0)
                res.add(i + 1);
        }
        return res;
    }

    //LeeCode-437 路经总和III
    int res437 = 0;
    HashMap<Integer, Integer> map437;
    int sum437;

    public int pathSum(TreeNode root, int sum) {
        sum437 = sum;
        map437 = new HashMap<>();
        map437.put(0, 1);
        return dfs437(root, 0);
    }

    public int dfs437(TreeNode root, int s) {
        if (root == null)
            return 0;
        s += root.val;
        int cnt = map437.getOrDefault(s - sum437, 0);
        map437.put(s, map437.getOrDefault(s, 0) + 1);
        int left = dfs437(root.left, s);
        int right = dfs437(root.right, s);
        map437.put(s, map437.get(s) - 1);
        return cnt + left + right;
    }

    //LeeCOde-283 移动零
    public void moveZeros(int[] nums) {
        int n = nums.length;
        if (n <= 1)
            return;
        int p1 = 0;
        while (p1 < n && nums[p1] != 0)
            p1++;
        int p2 = p1;
        while (p1 < n) {
            if (nums[p1] == 0)
                p1++;
            else {
                nums[p2] = nums[p1];
                nums[p1] = 0;
                p1++;
                p2++;
            }
        }
    }

    //LeeCode-406 根据身高重建队列
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (x1, x2) -> {
            return x1[0] == x2[0] ? x1[1] - x2[1] : x2[0] - x1[0];
        });
        List<int[]> list = new ArrayList<>();
        for (int[] p :
                people) {
            list.add(p[1], p);
        }
        int n = people.length;
        return list.toArray(new int[n][2]);
    }

    //LeeCode-347 前K个高频元素
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num :
                nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        Queue<Integer> queue = new PriorityQueue<>((x1, x2) -> map.get(x1) - map.get(x2));
        for (int num :
                map.keySet()) {
            queue.offer(num);
            if (queue.size() > k)
                queue.poll();
        }

        return queue.stream().mapToInt(Integer::valueOf).toArray();
    }

    //LeeCode-287 寻找重复数
    public int findDuplicate(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int i = 0, j = 1;
        while (j < nums.length) {
            if (nums[i] == nums[j])
                return nums[i];
            i++;
            j++;
        }
        return nums[0];
    }

    public int findDuplicate2(int[] nums) {
        int slow = 0, fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    //300 最长上升子序列
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0)
            return 0;
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int res = 1;
        for (int i = 1; i < nums.length; i++) {
            int tmp = 1;
            for (int j = i - 1; j >= 0; j--) {
                if (nums[i] > nums[j]) {
                    if (dp[j] + 1 > tmp)
                        tmp = dp[j] + 1;
                }
            }
            dp[i] = tmp;
            if (dp[i] > res)
                res = dp[i];
        }
        return res;
    }


    //LeeCLeeCode-279 完全平方数
    public int numSquares(int n) {
        return mathMethod279(n);
    }

    public boolean isSquare279(int n) {
        int tmp = (int) Math.sqrt(n);
        return tmp * tmp == n;
    }

    public int mathMethod279(int n) {
        while (n % 4 == 0)
            n = n / 4;
        if (n % 8 == 7)
            return 4;
        if (isSquare279(n))
            return 1;
        for (int i = 1; i * i < n; i++) {
            if (isSquare279(n - i * i))
                return 2;
        }
        return 3;
    }

    //LeeCode-394 字符串解码
    public String decodeString(String s) {
        StringBuilder tmpStr = new StringBuilder();
        int retNum = 0;
        Deque<String> strStack = new ArrayDeque<>();
        Deque<Integer> numStack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '[') {
                numStack.addFirst(retNum);
                retNum = 0;
                strStack.addFirst(tmpStr.toString());
                tmpStr = new StringBuilder();
            } else if (c == ']') {
                StringBuilder tmp = new StringBuilder();
                int num = numStack.poll();
                for (int i = 0; i < num; i++) {
                    tmp.append(tmpStr);
                }
                tmpStr = new StringBuilder(strStack.poll() + tmp);
            } else if (Character.isDigit(c)) retNum = retNum * 10 + c - '0';
            else tmpStr.append(c);
        }
        return tmpStr.toString();
    }

    // LeeCode 240 搜索二维矩阵II
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        if (m == 0)
            return false;
        int n = matrix[0].length;
        int i = m - 1, j = 0;
        while (i >= 0 && j < n) {
            if (matrix[i][j] == target) {
                return true;
            } else if (matrix[i][j] > target) {
                i--;
            } else {
                j++;
            }
        }
        return false;
    }

    // LeeCode-322 零钱兑换
    public int coinChange(int[] coins, int amount) {
        int n = coins.length;
        int[] dp = new int[amount + 1];
        for (int i = 1; i <= amount; i++) {
            dp[i] = amount + 1;
        }
        for (int i = 0; i < n; i++) {
            for (int j = coins[i]; j <= amount; j++) {
                dp[j] = Math.min(dp[j], dp[j - coins[i]] + 1);
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    //打家劫舍I
    public int rob(int[] nums) {
        int len = nums.length;
        if (len == 0)
            return 0;
        int dp_0 = 0, dp_1 = nums[0];
        for (int i = 1; i < len; i++) {
            int tmp = dp_0;
            dp_0 = Math.max(dp_0, dp_1);
            dp_1 = tmp + nums[i];
        }
        return Math.max(dp_0, dp_1);
    }

    //打家劫舍II
    public int robII(int[] nums) {
        int n = nums.length;
        if (n == 0)
            return 0;
        if (n == 1)
            return nums[0];
        int res1 = rob(Arrays.copyOfRange(nums, 1, nums.length));
        int res2 = rob(Arrays.copyOfRange(nums, 0, n - 1));
        return Math.max(res1, res2);
    }

    //打家劫舍Ⅲ
    public int robIII(TreeNode root) {
        return method_1_337(root);
    }

    public int method_1_337(TreeNode root) {
        if (root == null)
            return 0;
        int sons = 0;
        int grandsons = root.val;
        if (root.left != null) {
            grandsons = grandsons + method_1_337(root.left.right) + method_1_337(root.left.left);
        }
        if (root.right != null) {
            sons += root.right.val;
            grandsons = grandsons + method_1_337(root.right.left) + method_1_337(root.right.right);
        }
        sons = method_1_337(root.left) + method_1_337(root.right);
        return Math.max(sons, grandsons);
    }

    Map<TreeNode, Integer> map = new HashMap<>();

    public int method_2_337(TreeNode root) {
        if (map.containsKey(root))
            return map.get(root);
        int sons = 0;
        int grandsons = root.val;
        if (root.left != null) {
            grandsons = grandsons + method_2_337(root.left.right) + method_2_337(root.left.left);
        }
        if (root.right != null) {
            sons += root.right.val;
            grandsons = grandsons + method_2_337(root.right.left) + method_2_337(root.right.right);
        }
        sons = method_2_337(root.left) + method_2_337(root.right);
        int res = Math.max(sons, grandsons);
        map.put(root, res);
        return res;
    }

    public int[] method_3_337(TreeNode root) {
        int[] res = new int[2];
        if (root == null)
            return res;
        int[] left = method_3_337(root.left);
        int[] right = method_3_337(root.right);
        res[1] = left[0] + right[0] + root.val;
        res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        return res;
    }

    //LeeCode-376 摆动序列
    public int wiggleMaxLength(int[] nums) {
        int n = nums.length;
        if (n < 2)
            return n;
        int[][] dp = new int[n][2];
        dp[0][0] = 1;
        dp[0][1] = 1;
        for (int i = 1; i < n; i++) {
            int tmp0 = 1;
            int tmp1 = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] - nums[j] > 0) {
                    tmp1 = Math.max(tmp1, dp[j][0] + 1);
                } else if (nums[i] - nums[j] < 0) {
                    tmp0 = Math.max(tmp0, dp[j][1] + 1);
                }
            }
            dp[i][0] = tmp0;
            dp[i][1] = tmp1;
        }
        return Math.max(dp[n - 1][0], dp[n - 1][1]);
    }

    //LeeCode 416 分割等和子集
    public boolean canPartition(int[] nums) {
        return method_2_416(nums);
    }
    public boolean method_1_416(int[] nums) {
        int n = nums.length;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        if (sum % 2 != 0) {
            return false;
        }
        int half = sum / 2;
        int[] dp = new int[half + 1];
        for (int i = 1; i < half + 1; i++) {
            dp[i] = Integer.MIN_VALUE;
        }
        for (int i = 0; i < n; i++) {
            for (int j = half; j >= nums[i]; j--) {
                dp[j] = Math.max(dp[j],dp[j-nums[i]]+1);
            }
            if (dp[half] > 0) {
                return true;
            }
        }
        return false;
    }
    public boolean method_2_416(int[] nums) {
        int n = nums.length;
        //int sum = (int)Arrays.stream(nums).summaryStatistics().getSum();
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
        }
        if (sum%2 != 0)
            return false;
        sum /= 2;
        Arrays.sort(nums);
        return dfs_416(nums,sum,0);
    }
    public boolean dfs_416(int[] nums, int sum, int begin) {
        if (begin >= nums.length)
            return false;
        if (sum == 0)
            return true;
        for (int i = begin; i < nums.length; i++) {
            if (i > begin && nums[i]==nums[i-1])
                continue;
            if (nums[i] > sum)
                return false;
            if (dfs_416(nums,sum-nums[i],i+1))
                return true;
        }
        return false;
    }

    //接雨水
    public int trap(int[] height) {
        return method_1_42(height);
    }
    public int method_1_42(int[] height) {
        int res = 0;
        int n = height.length;
        for (int i = 0; i < n; i++) {
            int left = height[i];
            for (int j = 0; j < i; j++) {
                if (height[j] > left)
                    left = height[j];
            }
            int right = height[i];
            for (int j = i+1; j < n; j++) {
                if (height[j] > right) {
                    right = height[j];
                }
            }
            res += (Math.max(left,right)-height[i]);
        }
        return res;
    }
    public int method_2_42(int[] height) {
        int n = height.length;
        int[] left = new int[n];
        int[] right = new int[n];
        int max = height[0];
        for (int i = 0; i < n; i++) {
            if (height[i] > max)
                max = height[i];
            left[i] = max;
        }
        max = height[n-1];
        for (int i = n-1; i >=0 ; i--) {
            if (height[i] > max) {
                max = height[i];
            }
            right[i] = max;
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            res += (Math.min(left[i],right[i])-height[i]);
        }
        return res;
    }
    public int method_3_42(int[] height) {
        int n = height.length;
        if (n == 0) {
            return 0;
        }
        int res = 0;
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!queue.isEmpty() && height[i] > height[queue.peek()]) {
                int top = queue.poll();
                if (queue.isEmpty())
                    break;
                res += (i-queue.peek()-1)*(Math.min(height[i],height[queue.peek()]));
            }
            queue.offer(i);
        }
        return res;
    }
    public int method_4_42(int[] height) {
        int n = height.length;
        if (n == 0)
            return 0;
        int left = 0, right = n-1;
        int i = 0, j = n-1;
        int res = 0;
        while (i < j) {
            if (height[i] < height[j]) {
                if (height[i] > height[left])
                    left = i;
                res = res + height[left] - height[i];
                i++;
            } else {
                if (height[j] > height[right])
                    right = j;
                res = res + height[right] - height[j];
                j--;
            }
        }
        return res;
    }

    // 最长公共子串
    public void maxCommonSubString() {
        Scanner scanner = new Scanner(System.in);
        String str1 = scanner.nextLine();
        String str2 = scanner.nextLine();
        int n1 = str1.length() + 1, n2 = str2.length() + 1;
        if (n1 == 1 || n2 == 1) {
            System.out.println("");
            return;
        }
        int[][] dp = new int[n1][n2];
        int low = 0, high = 0;
        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < n2; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else {
                    if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                        if (dp[i][j] > high - low) {
                            low = i - dp[i][j];
                            high = i;
                        }
                    } else {
                        dp[i][j] = 0;
                    }
                }
            }
        }
        System.out.println(str1.substring(low, high));
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] test = {1,2,5};
        Queue<Integer> queue = new LinkedList<>();
        solution.canPartition(test);
    }
}
