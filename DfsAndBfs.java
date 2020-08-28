public class DfsAndBfs {
    //LeeCode 733 图像渲染
    int[][] next_733 = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    public void dfs(int[][] image, int x, int y, int newColor, boolean[][] isVisited) {
        int m = image.length;
        int n = image[0].length;
        isVisited[x][y] = true;
        for (int i = 0; i < 4; i++) {
            int tx = x + next_733[i][0];
            int ty = y + next_733[i][1];
            if (tx >= 0 && tx < m && ty >= 0 && ty < n && !isVisited[tx][ty] && image[tx][ty] == image[x][y])
                dfs(image, tx, ty, newColor, isVisited);
        }
        image[x][y] = newColor;
    }

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        boolean[][] isVisited = new boolean[image.length][image[0].length];
        dfs(image, sr, sc, newColor, isVisited);
        return image;
    }
}
