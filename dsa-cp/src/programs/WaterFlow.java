package programs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WaterFlow {
    int[][] directions = new int[][] { { 0, 1 }, { 1, 0 }, { -1, 0 }, { 0, -1 } };

    public List<List<Integer>> pacificAtlantic(int[][] heights) {

        int m = heights.length;
        int n = heights[0].length;

        Queue<int[]> pacOcean = new LinkedList<>();
        Queue<int[]> atlOcean = new LinkedList<>();

        // Add all the left wall cells will be accessible by pacific ocean
        // Add all the right wall cells will be accessible by atlantic ocean
        for (int i = 0; i < m; i++) {
            pacOcean.offer(new int[] { i, 0 });
            atlOcean.offer(new int[] { i, n - 1 });
        }

        // Add all the top wall cells will be accessible by pacific ocean
        // Add all the bottom wall cells will be accessible by atlantic ocean
        for (int i = 0; i < n; i++) {
            pacOcean.offer(new int[] { 0, i });
            atlOcean.offer(new int[] { m - 1, i });
        }

        // Do BFS on available cells and do BFS till we have next cell's
        // height >= current cell's height
        boolean[][] pacCells = bfs(pacOcean, heights, m, n);
        boolean[][] atlCells = bfs(atlOcean, heights, m, n);

        // find common cells which have access to both pacific ocean & atlantic ocean
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacCells[i][j] && atlCells[i][j]) {
                    result.add(List.of(i, j));
                }
            }
        }

        return result;
    }

    private boolean[][] bfs(Queue<int[]> ocean, int[][] heights, int m, int n) {
        boolean[][] visited = new boolean[m][n];
        while (!ocean.isEmpty()) {

            int[] curr = ocean.poll();
            visited[curr[0]][curr[1]] = true;

            for (int[] dir : directions) {
                int newX = curr[0] + dir[0];
                int newY = curr[1] + dir[1];

                // New Coordinates should be within the matrix
                if (newX < 0 || newX >= m || newY < 0 || newY >= n || visited[newX][newY]) {
                    continue;
                }

                // New Coordinates should have height >= curr cell's height else ignore and continue;
                if (heights[newX][newY] < heights[curr[0]][curr[1]]) {
                    continue;
                }

                ocean.offer(new int[] { newX, newY });
            }
        }

        return visited;
    }

    public static void main(String[] args) {
        int[][] heights = {
          { 1, 2, 2, 3, 5 },
          { 3, 2, 3, 4, 4 },
          { 2, 4, 5, 3, 1 },
          { 6, 7, 1, 4, 5 },
          { 5, 1, 1, 2, 4 }
        };
        WaterFlow wf = new WaterFlow();
        List<List<Integer>> answer = wf.pacificAtlantic(heights);
        System.out.println("here");
    }
}
