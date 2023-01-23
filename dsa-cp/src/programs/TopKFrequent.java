package programs;

import commons.Printer;

import java.util.HashMap;
import java.util.PriorityQueue;

public class TopKFrequent {

    private static int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums)
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);

        PriorityQueue<Integer> pq = new PriorityQueue<>(frequencyMap.size(),
          (a, b) -> frequencyMap.get(b) - frequencyMap.get(a));
        pq.addAll(frequencyMap.keySet());

        int[] result = new int[k];

        for (int i = 0; i < k; i++) {
            if (pq.isEmpty()) {
                continue;
            }
            result[i] = pq.poll();
        }

        return result;
    }

    public static void main(String[] args) {
        int[] res1 = topKFrequent(new int[] { 1, 1, 1, 2, 2, 3 }, 2);
        Printer.print1DArray(res1);
    }
}
