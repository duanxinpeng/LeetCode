import java.util.*;

public class CombinationSum {
    public void dfs(List<List<Integer>> res,int[] nums, int first, int target, Deque<Integer> tmp) {
        if (target==0) {
            res.add(new ArrayList<>(tmp));
        } else {
            for (int i = first; i < nums.length; i++) {
                if (target < nums[i]) break;
                tmp.push(nums[i]);
                dfs(res,nums,i,target-nums[i],tmp);
                tmp.pop();
            }
        }
    }
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(res,candidates,0,target,new ArrayDeque<Integer>());
        return res;
    }
}
