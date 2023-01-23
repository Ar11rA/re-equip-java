package programs;

import java.util.HashSet;

public class LongestSubsequence {

    private static int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        if (nums.length == 0) {
            return 0;
        }
        for (int num : nums) {
            set.add(num);
        }
        int maxStreak = 1, maxRes = 1;
        for (int num : nums) {
            if (!set.contains(num - 1)) {
                int curr = num;
                while (set.contains(curr + 1)) {
                    maxStreak += 1;
                    curr = curr + 1;
                }
                if (maxStreak >= maxRes) {
                    maxRes = maxStreak;
                }
                maxStreak = 1;
            }
        }
        return maxRes;
    }

    public static void main(String[] args) {
        System.out.println(longestConsecutive(new int[] { 100, 4, 200, 1, 3, 2 }));
    }
}
