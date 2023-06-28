package programs;

import java.util.*;

public class DeliveryManagementSystem {

    // 1-d graph
    private static Map<Integer, SortedSet<Integer>> createAdjacencyMatrix(List<Integer> cityFrom,
                                                                          List<Integer> cityTo) {
        Map<Integer, SortedSet<Integer>> adjacencyMatrix = new HashMap<>();
        for (int index = 0; index < cityFrom.size(); index++) {
            adjacencyMatrix
              .computeIfAbsent(cityFrom.get(index), k -> new TreeSet<Integer>())
              .add(cityTo.get(index));
        }
        return adjacencyMatrix;
    }

    private static List<Integer> traverse(Map<Integer, SortedSet<Integer>> adjacencyMatrix,
                                          int start, int cityNodes) {
        List<Integer> result = new ArrayList<>();
        int[] visited = new int[cityNodes + 1];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);
        visited[start] = 1;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (!adjacencyMatrix.containsKey(current)) {
                continue;
            }
            SortedSet<Integer> next = adjacencyMatrix.get(current);
            next.forEach(n -> {
                if (visited[n] != 1) {
                    result.add(n);
                    queue.add(n);
                    visited[n] = 1;
                }
            });
        }
        return result;
    }

    private static List<Integer> order(int cityNodes,
                                       List<Integer> cityFrom,
                                       List<Integer> cityTo,
                                       int company) {
        Map<Integer, SortedSet<Integer>> adjacencyMatrix =
          createAdjacencyMatrix(cityFrom, cityTo);
        return traverse(adjacencyMatrix, company, cityNodes);
    }

    public static void main(String[] args) {
        List<Integer> res1 = order(4,
          Arrays.asList(1, 2, 2),
          Arrays.asList(2, 3, 4),
          1);
        System.out.println(res1.size());
        List<Integer> res2 = order(5,
          Arrays.asList(1, 1, 2, 3, 1),
          Arrays.asList(2, 3, 4, 5, 5),
          1);
        System.out.println(res2.size());
        List<Integer> res3 = order(2,
          Arrays.asList(3, 1),
          Arrays.asList(1, 2),
          2);
        System.out.println(res3.size());

        // 1 2 2
        // 2 3 2

        //
    }
}
