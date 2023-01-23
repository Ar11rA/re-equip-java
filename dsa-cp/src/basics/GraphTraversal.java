package basics;

import java.util.*;

public class GraphTraversal {

    /*
    Consider a graph like
    0 -> 1 -> 2
    0 -> 1 -> 3
    0 -> 1 -> 4 -> 3
    and always start from 0
     */

    // DFS Recursive
    private static void dfsRecursive(HashMap<Integer, List<Integer>> adjacencyMatrix) {
        dfsRecursiveHelper(adjacencyMatrix, 0);
    }

    private static void dfsRecursiveHelper(HashMap<Integer, List<Integer>> adjacencyMatrix, int index) {
        System.out.println("DFS current: " + index);
        if (!adjacencyMatrix.containsKey(index)) {
            return;
        }
        List<Integer> nodes = adjacencyMatrix.get(index);
        nodes.forEach(node -> {
            dfsRecursiveHelper(adjacencyMatrix, node);
        });

    }

    // DFS Iterative
    private static void dfsIterative(HashMap<Integer, List<Integer>> adjacencyMatrix) {
        Stack<Integer> stack = new Stack<>();
        stack.push((0));
        while (!stack.isEmpty()) {
            int curr = stack.pop();
            System.out.println("DFS current: " + curr);
            if (!adjacencyMatrix.containsKey(curr)) {
                continue;
            }
            List<Integer> nodes = adjacencyMatrix.get(curr);
            nodes.forEach(stack::push);
        }
    }

    // BFS Recursive
    private static void bfsRecursive(HashMap<Integer, List<Integer>> adjacencyMatrix) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        bfsRecursiveHelper(adjacencyMatrix, queue);
    }

    private static void bfsRecursiveHelper(HashMap<Integer, List<Integer>> adjacencyMatrix, Queue<Integer> queue) {
        if (queue.isEmpty()) {
            return;
        }
        int curr = queue.poll();
        System.out.println("BFS current: " + curr);
        if (adjacencyMatrix.containsKey(curr)) {
            List<Integer> nodes = adjacencyMatrix.get(curr);
            queue.addAll(nodes);
        }
        bfsRecursiveHelper(adjacencyMatrix, queue);
    }

    // BFS Iterative
    private static void bfsIterative(HashMap<Integer, List<Integer>> adjacencyMatrix) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            System.out.println("BFS current: " + curr);
            if (!adjacencyMatrix.containsKey(curr)) {
                continue;
            }
            List<Integer> nodes = adjacencyMatrix.get(curr);
            queue.addAll(nodes);
        }
    }

    public static void main(String[] args) {
        HashMap<Integer, List<Integer>> adjacencyMatrix = new HashMap<>();
        adjacencyMatrix.put(0, List.of(1));
        adjacencyMatrix.put(1, List.of(2, 3, 4));
        adjacencyMatrix.put(3, List.of(5));
        adjacencyMatrix.put(4, List.of(5));
        System.out.println("*********** DFS Iterative **********");
        dfsIterative(adjacencyMatrix);
        System.out.println("*************************************");
        System.out.println("*********** BFS Iterative **********");
        bfsIterative(adjacencyMatrix);
        System.out.println("*************************************");
        System.out.println("*********** DFS Recursive **********");
        dfsRecursive(adjacencyMatrix);
        System.out.println("*************************************");
        System.out.println("*********** BFS Recursive **********");
        bfsRecursive(adjacencyMatrix);
        System.out.println("*************************************");
    }
}
