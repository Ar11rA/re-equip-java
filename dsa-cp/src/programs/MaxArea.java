package programs;

import java.util.ArrayDeque;
import java.util.Queue;

public class MaxArea {

    class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private final int[][] directions;

    private MaxArea() {
        this.directions = new int[][] {
          { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }
        };
    }


    private boolean checkBoundary(int i, int j, int m, int n, int[][] visited) {
        return i >= 0 && j >= 0 && i < m && j < n && visited[i][j] == 0;
    }

    private int bfs(int[][] grid, Point start, int m, int n, int[][] visited) {
        Queue<Point> queue = new ArrayDeque<>();
        queue.add(start);
        visited[start.x][start.y] = 1;
        int area = 1;
        while (!queue.isEmpty()) {
            Point curr = queue.poll();
            for (int[] direction : this.directions) {
                Point newPoint = new Point(
                  curr.x + direction[0],
                  curr.y + direction[1]
                );
                if (checkBoundary(newPoint.x, newPoint.y, m, n, visited)
                  && grid[newPoint.x][newPoint.y] == 1) {
                    visited[newPoint.x][newPoint.y] = 1;
                    area++;
                    queue.add(newPoint);
                }
            }
        }
        return area;
    }

    public int maxAreaOfIsland(int[][] grid) {
        int area = 0;
        int m = grid.length, n = grid[0].length;
        int[][] visited = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && visited[i][j] == 0) {
                    int currArea = bfs(grid, new Point(i, j), m, n, visited);
                    if (currArea > area) {
                        area = currArea;
                    }
                }
            }
        }
        return area;
    }

    public static void main(String[] args) {
        int[][] graph = {
          { 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
          { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 },
          { 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
          { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0 },
          { 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0 },
          { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
          { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0 },
          { 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 }
        };
        MaxArea maxArea = new MaxArea();
        System.out.println(maxArea.maxAreaOfIsland(graph));
    }
}
