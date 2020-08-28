import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Combine {
    int n,k;
    public void p(List<List<Integer>> res, int first, Deque<Integer> tmp) {
        if (tmp.size()==k) {
            res.add(new ArrayList<>(tmp));
        } else {
            for (int i = first; i <= n; i++) {
                tmp.push(i);
                p(res,i+1,tmp);
                tmp.pop();
            }
        }
    }

    public List<List<Integer>> combine(int n, int k) {
        this.n = n;
        this.k = k;
        List<List<Integer>> res = new ArrayList<>();
        p(res,1,new ArrayDeque<>());
        return res;
    }
}
