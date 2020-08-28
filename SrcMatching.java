import java.util.Arrays;
import java.util.stream.Collectors;

public class SrcMatching {
    // 在text中对pattern进行匹配！
    //BR 算法
    public static int BR(String text, String pattern) {
        int tLen = text.length();
        int pLen = pattern.length();
        for (int i = 0; i < tLen-pLen; i++) {
            if (text.charAt(i) == pattern.charAt(0)) {
                boolean flag = true;
                for (int j = 0; j < pLen; j++) {
                    if (text.charAt(i+j) != pattern.charAt(j)) {
                        flag = false;
                        break;
                    }
                }
                if (flag)
                    return i;
            }
        }
        return -1;
    }
    //BM 算法
    public static void preBc(int[] bmBc, String pattern) {
        int m = pattern.length();
        int num = bmBc.length;
        for (int i = 0; i < num; i++) {
            bmBc[i] = m;
        }
        for (int i = 0; i < m; i++) {
            bmBc[pattern.charAt(i)] = m - 1 - i;
        }
    }

    public static void suffiOld(String pattern, int[] suff) {
        int m = pattern.length();
        suff[m - 1] = m;
        for (int i = m - 2; i >= 0; i--) {
            int j = i;
            while (j >= 0 && pattern.charAt(j) == pattern.charAt(m - 1 - i + j))
                j--;
            suff[i] = i - j;
        }
    }

    public static void suffix(String pattern, int[] suff) {
        int m = pattern.length();
        suff[m - 1] = m;
        int g = m - 1, f = 0;
        for (int i = m - 2; i >= 0; i--) {
            if (i > g && suff[i + m - 1 - f] < i - g) {
                suff[i] = suff[i + m - 1 - f];
            } else {
                if (i < g)
                    g = i;
                f = i;
                while (g >= 0 && pattern.charAt(g) == pattern.charAt(g + m - 1 - f))
                    g--;
                suff[i] = f - g;
            }
        }
    }

    public static void preBmGs(String pattern, int[] bmGs) {
        int m = pattern.length();
        int[] suff = new int[m];
        suffix(pattern, suff);
        //case 3
        for (int i = 0; i < m; i++) {
            bmGs[i] = m;
        }
        //case 2
        for (int i = m - 1; i >= 0; i--) {
            if (suff[i] == i + 1) {
                for (int j = 0; j < m - 1; j++) {
                    if (bmGs[j] == m)
                        bmGs[j] = m - 1 - j;
                }
            }
        }
        //case 1
        for (int i = 0; i <= m - 2; i++) {
            bmGs[m - 1 - suff[i]] = m - 1 - i;
        }
    }

    public static int BoyerMoore(String text, String pattern) {
        int m = pattern.length();
        int n = text.length();
        int[] bmBc = new int[256];
        int[] bmGs = new int[m];
        preBc(bmBc, pattern);
        preBmGs(pattern, bmGs);
        int j = 0;
        while (j <= n - m) {
            int i;
            for (i = m - 1; i >= 0 && pattern.charAt(i) == text.charAt(i + j); i--) ;
            if (i < 0) {
                return j;
            } else {
                j += Math.max(bmBc[text.charAt(i + j)] - m + 1 + i, bmGs[i]);
            }
        }
        return -1;
    }

    //Rabin-Karp
    final static int BASE = 256;
    final static int MODULES = 101;

    public static int rabinKarp(String text, String pattern) {
        int tLen = text.length();
        int pLen = pattern.length();
        int tHash = 0;
        int pHash = 0;
        for (int i = 0; i < pLen; i++) {
            tHash = (BASE * tHash + text.charAt(i)) % MODULES;
            pHash = (BASE * pHash + pattern.charAt(i)) % MODULES;
        }
        // 作滚动哈希用
        int h = 1;
        for (int i = 0; i < pLen - 1; i++) {
            h = (h * BASE) % MODULES;
        }
        int i = 0;
        while (i <= tLen - pLen) {
            //先通过哈希值比较，再通过 equals 方法进行比较
            if (tHash == pHash && pattern.equals(text.substring(i, i + pLen)))
                return i;
            //比较失败，生成下一个哈希值
            if (i < tLen - pLen)
                tHash = (BASE * (tHash + MODULES - text.charAt(i) * h) + text.charAt(i + pLen)) % MODULES;

            //防止出现负数？？？？？
            if (tHash < 0) {
                tHash = tHash + MODULES;
            }
            i++;
        }
        return -1;
    }

    //KMP算法
    public static void preNext(int[] next, String p) {
        // 准备被匹配串的next数组
        // next数组表示以被匹配串的某个元素为结尾时，和前缀相同的最长的后缀的长度。
        int pLen = p.length();
        int i = 0, j = -1;
        next[0] = -1;
        while (i < pLen) {
            // 前一个字符时候和j对应的字符相等？
            if (j == -1 || p.charAt(i) == p.charAt(j)) {
                i++;
                j++;
                if (i < pLen) {
                    // 因为字符串最后一位都是'\0' 所以这里不会出现越界错误
                    if (p.charAt(i)!=p.charAt(j))
                        next[i] = j;
                    else
                        next[i] = next[j];
                }
            } else {
                j = next[j];
            }
        }
    }

    public static int KMP(String text, String pattern) {
        int pLen = pattern.length();
        int tLen = text.length();
        int[] next = new int[pLen];
        preNext(next, pattern);
        int i = 0, j = 0;
        while (i < tLen && j < pLen) {
            if (j == -1 || pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == pLen)
            return i - j;
        return -1;
    }

    //Sunday 算法
    public static void preNextSunday(String p, int[] next) {
        int len = next.length;
        int pLen = p.length();
        for (int i = 0; i < len; i++) {
            next[i] = -1;
        }
        for (int i = 0; i < pLen; i++) {
            next[p.charAt(i)] = i;
        }
    }

    public static int sunday(String text, String pattern) {
        int[] next = new int[256];
        preNextSunday(pattern, next);
        int tLen = text.length();
        int pLen = pattern.length();
        int i = 0;
        while (i < tLen - pLen) {
            int j = i, k = 0;
            while (j < tLen && k < pLen && text.charAt(j) == pattern.charAt(k)) {
                j++;
                k++;
            }
            if (k == pLen)
                return i;
            if (i + pLen < tLen)
                i += (pLen - next[text.charAt(i + pLen)]);
            else
                break;
            ;
        }
        return -1;
    }


    public static void main(String[] args) {
        String pattern = "ABCDABD";
        int[] next = new int[pattern.length()];
        //System.out.println(KMP("skldjfklsjfklshglkjksljfdklsjfd",pattern));
        preNext(next,pattern);
        System.out.println(Arrays.stream(next).boxed().map(i -> i.toString()).collect(Collectors.joining(" ")));
    }
}
