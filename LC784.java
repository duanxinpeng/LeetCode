import java.util.ArrayList;
import java.util.List;

public class LC784 {
    public List<String> letterCasePermutation(String S) {
        List<StringBuilder> tmp = new ArrayList<>();
        tmp.add(new StringBuilder());
        for (char c :
                S.toCharArray()) {
            int n = tmp.size();
            if (Character.isLetter(c)) {
                for (int i = 0; i < n; i++) {
                    tmp.add(new StringBuilder((tmp.get(i))));
                    tmp.get(i).append(Character.toLowerCase(c));
                    tmp.get(i+n).append(Character.toUpperCase(c));
                }
            } else {
                for (int i = 0; i < n; i++) {
                    tmp.get(i).append(c);
                }
            }
        }
        List<String> ans = new ArrayList<>();
        for (StringBuilder sb :
                tmp) {
            ans.add(sb.toString());
        }
        return ans;
    }
}
