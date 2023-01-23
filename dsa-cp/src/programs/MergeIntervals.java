package programs;

import commons.Printer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeIntervals {
    private static int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(num -> num[0]));
        List<int[]> result = new ArrayList<>();
        int start = intervals[0][0];
        int end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= end) {
                end = Math.max(end, intervals[i][1]);
            } else {
                result.add(new int[] { start, end });
                start = intervals[i][0];
                end = intervals[i][1];
            }
        }
        result.add(new int[] { start, end });
        return result.toArray(new int[result.size()][]);
    }

    public static void main(String[] args) {
        int[][] res1 = merge(new int[][] {
          new int[] { 1, 4 }, new int[] { 2, 6 }, new int[] { 8, 10 }
        });
        Printer.print2DArray(res1);

        int[][] res2 = merge(new int[][] {
          new int[] { 1, 4 }, new int[] { 5, 7 }, new int[] { 8, 10 }
        });
        Printer.print2DArray(res2);


        int[][] res3 = merge(new int[][] {
          new int[] { 1, 4 }, new int[] { 2, 7 }, new int[] { 3, 10 }
        });
        Printer.print2DArray(res3);
    }
}
