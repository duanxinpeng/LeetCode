import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class InorderTraversal {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {val =x;}
    }
//    public List<Integer> inorderTraversal(TreeNode root) {
//        List<Integer> res = new ArrayList<>();
//        Stack<TreeNode> stack = new Stack<>();
//        TreeNode intoStack = root;
//        while (intoStack!=null || !stack.isEmpty()) {
//            while (intoStack!=null) {
//                stack.push(intoStack);
//                intoStack = intoStack.left;
//            }
//            TreeNode outStack = stack.pop();
//            res.add(outStack.val);
//            intoStack = outStack.right;
//        }
//        return res;
//    }
    List<Integer> res = new ArrayList<>();
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root != null) {
            inorderTraversal(root.left);
            res.add(root.val);
            inorderTraversal(root.right);
        }
        return res;
    }
}
