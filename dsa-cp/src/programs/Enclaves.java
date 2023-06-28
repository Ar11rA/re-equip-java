package programs;

import java.util.ArrayDeque;
import java.util.Queue;

public class Enclaves {

    private int[] xDirs = { 1, -1, 0, 0 };
    private int[] yDirs = { 0, 0, 1, -1 };

    class Pair {
        public int x;
        public int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private boolean checkBoundary(int i, int j, int[][] grid) {
        int m = grid.length, n = grid[0].length;
        return (i >= 0 && j >= 0 && i < m && j < n) && grid[i][j] == 1;
    }

    public int numEnclaves(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<Pair> queue = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == 0 || j == 0 || i == m - 1 || j == n - 1) && grid[i][j] == 1) {
                    queue.add(new Pair(i, j));
                }
            }
        }
        while (!queue.isEmpty()) {
            Pair current = queue.poll();
            grid[current.x][current.y] = 0;
            for (int i = 0; i < 4; i++) {
                int newX = current.x + xDirs[i];
                int newY = current.y + yDirs[i];
                if (checkBoundary(newX, newY, grid)) {
                    queue.add(new Pair(newX, newY));
                }
            }
        }
        int ctr = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    ctr++;
                }
            }
        }
        return ctr;
    }

    public static void main(String[] args) {
        int[][] graph = {
          { 0, 0, 0, 0 },
          { 1, 0, 1, 0 },
          { 0, 1, 1, 0 },
          { 0, 0, 0, 0 }
        };
        Enclaves e = new Enclaves();
        System.out.println(e.numEnclaves(graph));
    }
}
