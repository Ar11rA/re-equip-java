package programs;


import java.util.Queue;
public class MinimumFuelCost {

    public static long minimumFuelCost(int[][] roads, int seats) {
        int [][] graph = new int[roads.length][roads.length];
        for (int i = 0; i < roads.length; i++) {
            for (int j = 0; j < roads.length; j++) {
                graph[i][j] = 0;
            }
        }
        // form adjacency matrix
        for (int i = 0; i < roads.length; i++) {
            graph[roads[i][0]][roads[i][1]] = 1;
            graph[roads[i][1]][roads[i][0]] = 1;
        }

        return 0;
    }

    public static void main(String[] args) {
        // Reverse the entire string and convert upper-case letters to lower-case

    }
}
