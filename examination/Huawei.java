package examination;

import java.util.Scanner;

public class Huawei {
    public static void problem1() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            String[] strs = str.trim().split(",");
            int five = 0;
            int ten = 0;
            int i = 0;
            for (; i < strs.length; i++) {
                int money = Integer.parseInt(strs[i]);
                if (money!=5&&money!=10&&money!=20) {
                    System.out.println(false+" "+(i+1));
                    break;
                }
                if (money == 5) {
                    five++;
                } else if (money == 10) {
                    if (five <= 0) {
                        System.out.println(false+" "+(i+1));
                        break;
                    } else {
                        five--;
                    }
                    ten++;
                } else {
                    if (ten > 0 && five >0) {
                        ten--;
                        five--;
                    } else if (five > 3) {
                        five -= 3;
                    } else {
                        System.out.println(false+" "+(i+1));
                        break;
                    }
                }
            }
            if (i == strs.length)
                System.out.println(true+" "+strs.length);
        }
    }

    public static boolean dfs(int m, int n, int[][] matrix, boolean[][] visited, int x, int y, int s) {
        if (x == m - 1 && y == n - 1)
            return true;
        if (x >= 0 && x < m && y >= 0 && y < n && matrix[x][y] == 1 && !visited[x][y]) {
            visited[x][y] = true;
            if (dfs(m, n, matrix, visited, x + s, y, s))
                return true;
            if (dfs(m, n, matrix, visited, x, y - s, s))
                return true;
            if (dfs(m, n, matrix, visited, x - s, y, s))
                return true;
            if (dfs(m, n, matrix, visited, x, y + s, s))
                return true;
            visited[x][y] = false;
        }
        return false;
    }

    public static void problem2() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int s = scanner.nextInt();
            int m = scanner.nextInt();
            int n = scanner.nextInt();
            int[][] matrix = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = scanner.nextInt();
                }
            }
            boolean[][] visited = new boolean[m][n];
            if (dfs(m, n, matrix, visited, 0, 0, s)) {
                System.out.println(1);
            } else {
                System.out.println(0);
            }
        }
    }


    public static void problem3() {
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
                        res += string[p];
                        p += (2*n-3-(2*n-3-4*(i/2)));
                    }
                }
            }
            System.out.println(res);
        }
    }
}
