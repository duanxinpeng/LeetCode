import java.util.*;

public class Permute2 {
    public void p(List<List<Integer>> res, int[] nums, Deque<Integer> path,boolean[] visited) {
        //nums和visited结合起来，以达成从nums中删除掉某些元素加入到path中，并且还能撤销的效果！
        //visited[i]==false 表示还没有从nums中删除，如果visited[i]==true表示已经从nums中删除了
        //当path中的元素个数和nums中的元素个数相等时，其实就表示nums中的元素全被删掉了
        if (path.size()==nums.length) {
            res.add(new ArrayList<>(path));
        } else {
            int n = nums.length;
            //分别以每个元素作为排列头
            for (int i = 0; i < n; i++) {
                //visited[i]==true表示已经从nums中删除掉了
                if (visited[i]) continue;
                //表示将以同样的元素作为排列头的分支剪掉！
                //这里!visited[i-1]表示visited[i-1]还没有被删除，表明nums[i]和nums[i-1]是在回溯的同一个层次的；
                //但是这里写visited[i-1]也是对的，这里是倒序索引剪枝，没有！visited[i-1]好理解
                if (i>0 && nums[i-1]==nums[i] && !visited[i-1])
                    continue;
                //从nums中将nums[i]删掉加入path中！
                visited[i] = true;
                path.push(nums[i]);
                //递归
                p(res,nums,path,visited);
                //撤销
                visited[i] = false;
                path.pop();
            }
        }
    }
    public List<List<Integer>> permuteUnique(int[] nums) {
        //存放结果
        List<List<Integer>> res = new ArrayList<>();
        //排序
        Arrays.sort(nums);
        p(res,nums,new ArrayDeque<>(),new boolean[nums.length]);
        return res;
    }
}
