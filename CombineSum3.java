import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class CombineSum3 {
    public void dfs(List<List<Integer>> res, int first, int k, int n, Deque<Integer> tmp) {
        if (n==0 && k==0) {
            res.add(new ArrayList<>(tmp));
        } else {
            for (int i = first; i <= 9; i++) {
                if (n < i || k <= 0) {
                    break;
                }
                tmp.push(i);
                dfs(res,i+1,k-1,n-i,tmp);
                tmp.pop();
            }
        }
    }
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(res,1,k,n,new ArrayDeque<>());
        return res;
    }
}
