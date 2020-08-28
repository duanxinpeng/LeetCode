import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            String[] strings = str.trim().split(",");
            char[] string = strings[0].toCharArray();
            int n = Integer.parseInt(strings[1]);
            String res = "";
            for (int i = 0; i < n; i+=2) {
                if (i == 0 || i == n-1) {
                    int p = i;
                    while (p < string.length) {
                        res += string[p];
                        p += (2*n-3);
                    }
                } else {
                    int p = i;
                    while (p < string.length) {
                        res += string[p];
                        p += (2*n-3-4*(i/2));
                        if (p >= string.length)
                            break;
                        res += string[p];
                        p += (2*n-3-(2*n-3-4*(i/2)));
                    }
                }
            }
            for (int i = n-2; i >= 1 ; i-=2) {
                if (i == 1) {
                    int p = i;
                    while (p < string.length) {
                        res += string[p];
                        p += (2*n-3);
                    }
                } else {
                    int p = i;
                    while (p < string.length) {
                        res += string[p];
                        p += (2*n-3-4*(i/2));
                        if (p >= string.length)
                            break;
                        res += string[p];
                        p += (2*n-3-(2*n-3-4*(i/2)));
                    }
                }
            }
            System.out.println(res);
        }
    }
}
