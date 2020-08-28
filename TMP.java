import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class TMP {
    public boolean repeatedSubstringPattern(String s) {
        return (s+s).indexOf(s,1) != s.length();
    }
}
