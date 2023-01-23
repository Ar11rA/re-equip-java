package programs;

import commons.MatrixPair;

import java.util.ArrayDeque;
import java.util.Queue;

public class NumberOfIslands {
    private static int numIslands(char[][] grid) {
        Queue<MatrixPair> queue = new ArrayDeque<>();
        int ctr = 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    queue.add(new MatrixPair(i, j));
                    visited[i][j] = true;
                    ctr += 1;
                }
                while (!queue.isEmpty()) {
                    MatrixPair curr = queue.poll();
                    if (curr.i - 1 >= 0 && grid[curr.i - 1][curr.j] == '1' && !visited[curr.i - 1][curr.j]) {
                        queue.add(new MatrixPair(curr.i - 1, curr.j));
                        visited[curr.i - 1][curr.j] = true;
                    }
                    if (curr.i + 1 < grid.length && grid[curr.i + 1][curr.j] == '1' && !visited[curr.i + 1][curr.j]) {
                        queue.add(new MatrixPair(curr.i + 1, curr.j));
                        visited[curr.i + 1][curr.j] = true;
                    }
                    if (curr.j - 1 >= 0 && grid[curr.i][curr.j - 1] == '1' && !visited[curr.i][curr.j - 1]) {
                        queue.add(new MatrixPair(curr.i, curr.j - 1));
                        visited[curr.i][curr.j - 1] = true;
                    }
                    if (curr.j + 1 < grid[0].length && grid[curr.i][curr.j + 1] == '1' && !visited[curr.i][curr.j + 1]) {
                        queue.add(new MatrixPair(curr.i, curr.j + 1));
                        visited[curr.i][curr.j + 1] = true;
                    }
                }
            }
        }

        return ctr;
    }

    public static void main(String[] args) {
        char[][] grid1 = {
          { '1', '1', '1', '1', '0' },
          { '1', '1', '0', '1', '0' },
          { '1', '1', '0', '0', '0' },
          { '0', '0', '0', '0', '1' }
        };
        System.out.println(numIslands(grid1));
    }
}
