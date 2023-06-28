package programs;

import commons.Printer;

import java.util.*;

public class CourseSchedule {

    private Map<Integer, List<Integer>> adjacencyGraph;
    private int[] indegree;

    private int[] traversal;

    public CourseSchedule() {
        this.adjacencyGraph = new HashMap<>();
    }

    private void createAdjacencyGraph(int numCourses, int[][] prerequisites) {
        this.indegree = new int[numCourses];
        for (int[] prerequisite : prerequisites) {
            this.adjacencyGraph.computeIfAbsent(
              prerequisite[1], k -> new ArrayList<>()
            ).add(prerequisite[0]);
            this.indegree[prerequisite[0]]++;
        }
    }

    public void bfs(Queue<Integer> queue, int numCourses)  {
        this.traversal = new int[numCourses];
        int ctr = 0;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            traversal[ctr] = node;
            List<Integer> next = this.adjacencyGraph.get(node);
            if (next == null) {
                ctr++;
                continue;
            }
            next.forEach(item -> {
                this.indegree[item]--;
                if (this.indegree[item] == 0) {
                    queue.add(item);
                }
            });
            ctr++;
        }
        if (ctr != numCourses) {
            traversal = new int[0];
        }
    }

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        this.createAdjacencyGraph(numCourses, prerequisites);
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < this.indegree.length; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }
        bfs(queue, numCourses);
        return this.traversal;
    }

    public static void main(String[] args) {
        int[][] prerequisites = {
          { 1, 0 },
          { 2, 0 },
          { 3, 1 },
          { 3, 2 }
        };
        CourseSchedule cs = new CourseSchedule();
        Printer.print1DArray(cs.findOrder(4, prerequisites));
    }
}
