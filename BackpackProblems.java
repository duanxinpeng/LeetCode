import java.util.Scanner;

public class BackpackProblems {
    //0/1背包
    public static int ZeroOnePack(int N, int V, int[] c, int[] w) {
        int[] dp = new int[V+1];
        for (int i = 0; i < N; i++) {
            for (int j = V; j >= c[i]; j--) { //必须是>=
                dp[j] = Math.max(dp[j], dp[j-c[i]] + w[i]);
            }
        }
        return dp[V];
    }
    //完全背包
    public static int CompletePack(int N, int V, int[] c, int[] w) {
        int[] dp = new int[V+1];
        for (int i = 0; i < N; i++) {
            //注意范围是闭包 [c[i],V]
            for (int j = c[i]; j <= V; j++) {
                dp[j] = Math.max(dp[j], dp[j-c[i]] + w[i]);
            }
        }
        return dp[V];
    }
    //多重背包
    public static boolean canTransform(int[] c, int[] m, int V) {
        for (int i = 0; i < c.length; i++) {
            if (c[i]*m[i] < V)
                return false;
        }
        return true;
    }
    public static int MultiPack(int N, int V, int[] c, int[] w, int[] m) {
        //如果每种物品拥有的数量都大于V/c[i]的话，就可以转为CompletePack
        if (canTransform(c,m,V))
            return CompletePack(N,V,c,w);
        //全部初始化为0
        int[] dp = new int[V+1];
        for (int i = 0; i < N; i++) {
            int k = 1;
            while (k < m[i]) {
                for (int j = V; j >= k*c[i]; j--) {
                    dp[j] = Math.max(dp[j], dp[j-k*c[i]] + k*w[i]);
                }
                //顺序不能错！
                m[i] -= k;
                k *= 2;
            }
            for (int j = V; j >= m[i]*c[i]; j--) {
                dp[j] = Math.max(dp[j], dp[j-m[i]*c[i]] + m[i]*w[i]);
            }
        }
        return dp[V];
    }
    public static void main(String[] args) {
        int N = 5, V = 10;
        int[] c = {2,2,6,5,4};
        int[] w = {6,3,5,4,6};
        CompletePack(N,V,c,w);
//        Scanner scanner = new Scanner(System.in);
//        int V = scanner.nextInt();
//        int N = scanner.nextInt();
//        int[] m = new int[N];
//        int[] c = new int[N];
//        int[] w = new int[N];
//        for (int i = 0; i < N; i++) {
//            m[i] = scanner.nextInt();
//            c[i] = scanner.nextInt();
//            w[i] = scanner.nextInt();
//        }
//        int res = MultiPack(N,V,c,w,m);
//        System.out.println(res);
    }
}
