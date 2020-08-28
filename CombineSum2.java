import java.util.*;

public class CombineSum2 {
    public void dfs(List<List<Integer>> res, int[] nums, int first, int target, Deque<Integer> tmp) {
        if (target==0) {
            res.add(new ArrayList<>(tmp));
        } else {
            for (int i = first; i < nums.length; i++) {
                //first之前的元素已经全部被删掉了！所以应该是i>first!!而不是i>0
                if (i > first && nums[i-1]==nums[i]) {
                    continue;
                }
                if (target < nums[i]) {
                    break;
                }
                tmp.push(nums[i]);
                dfs(res,nums,i+1,target-nums[i],tmp);
                tmp.pop();
            }
        }
    }
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(res,candidates,0,target,new ArrayDeque<>());
        return res;
    }
}
