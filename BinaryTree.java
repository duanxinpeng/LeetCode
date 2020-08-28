import com.sun.source.tree.Tree;

import java.util.*;
import java.util.LinkedList;
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x){val = x;}
}

public class BinaryTree {

    // LeeCode-199 二叉树的右视图
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null)
            return res;
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            int n = queue.size();
            for (int i = 0; i < n; i++) {
                TreeNode right = queue.removeFirst();
                if (i == 0)
                    res.add(right.val);
                if (right.right != null)
                    queue.addLast(right.right);
                if (right.left != null)
                    queue.addLast(right.left);
            }
        }
        return res;
    }
    //二叉树的序列化与反序列化
    public void dfs_serial(TreeNode root, StringBuilder builder) {
        if (root == null) {
            builder.append("null,");
        } else {
            builder.append(root.val).append(",");
            dfs_serial(root.left,builder);
            dfs_serial(root.right,builder);
        }
    }
    public String serialize(TreeNode root) {
        StringBuilder builder = new StringBuilder();
        dfs_serial(root,builder);
        return builder.toString();
    }
    public TreeNode dfs_deserial(List<String> list) {
        if (!list.isEmpty()) {
            String tmp = list.remove(0);
            if (tmp.equals("null"))
                return null;
            TreeNode root = new TreeNode(Integer.parseInt(tmp));
            root.left = dfs_deserial(list);
            root.right = dfs_deserial(list);
            return root;
        }
        return null;
    }
    public TreeNode deserialize(String data) {
        String[] nodes = data.split(",");
        List<String> list = new LinkedList<>(Arrays.asList(nodes));
        return dfs_deserial(list);
    }

    // LeeCode-331 验证二叉树的前序序列化
    public boolean isValidSerialization(String preorder) {
        String[] nodes = preorder.split(",");
        int slot = 1;
        for (String node :
                nodes) {
            slot--;
            if (slot < 0)
                return false;
            if (!node.equals("#"))
                slot += 2;
        }
        return slot == 0;
    }

    // LeeCode-100 相同的树
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null)
            return true;
        if (p == null)
            return false;
        if (q == null)
            return false;
        return (p.val==q.val && isSameTree(p.left,q.left) && isSameTree(p.right,q.right));
    }
    //LeeCode-99 恢复二叉搜索树
    List<TreeNode> list = new ArrayList<>();
    public void dfs_99(TreeNode root) {
        if (root != null) {
            dfs_99(root.left);
            list.add(root);
            dfs_99(root.right);
        }
    }
    public TreeNode[] find_99() {
        TreeNode[] res = new TreeNode[2];
        for (int i = 0; i < list.size()-1; i++) {
            if (list.get(i).val > list.get(i+1).val) {
                res[0] = list.get(i);
                break;
            }
        }
        for (int i = list.size()-1; i >0 ; i--) {
            if (list.get(i).val < list.get(i-1).val) {
                res[1] = list.get(i);
                break;
            }
        }
        return res;
    }
    public void recoverTree(TreeNode root) {
        dfs_99(root);
        TreeNode[] res = find_99();
        int tmp = res[0].val;
        res[0].val = res[1].val;
        res[1].val = tmp;
    }

    // LeetCode 109 有序链表转平衡二叉搜索树
    public TreeNode dfs(int[] nums, int begin, int end) {
        if (begin > end)
            return null;
        int mid = (begin + end) / 2;
        TreeNode left = dfs(nums, begin, mid - 1);
        TreeNode right = dfs(nums, mid + 1, end);
        TreeNode res = new TreeNode(nums[mid]);
        return res;
    }

    public TreeNode sortedListToBST(ListNode head) {
        int size = 0;
        ListNode p = head;
        while (p != null) {
            size++;
            p = p.next;
        }
        int[] nums = new int[size];
        int i = 0;
        p = head;
        while (p != null) {
            nums[i] = p.val;
            i++;
            p = p.next;
        }
        return dfs(nums, 0, size - 1);
    }


    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);
        BinaryTree bt = new BinaryTree();
        String res = bt.serialize(root);
        bt.deserialize(res);
    }
}
