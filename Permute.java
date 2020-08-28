import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Permute {
    public void p(List<List<Integer>> res, ArrayList tmp, int length, int first) {
        if (first == length) {
            res.add(new ArrayList<>(tmp));
        } else {
            for (int i = first; i < length; i++) {
                Collections.swap(tmp,i,first);
                p(res,tmp,length,first+1);
                Collections.swap(tmp,i,first);
            }
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        ArrayList<Integer> tmp = new ArrayList<>();
        for (int n :
                nums) {
            tmp.add(n);
        }
        p(res,tmp,nums.length,0);
        return res;
    }
}
