package basics;

import java.util.Arrays;

public class DP {

    // Rod cut problem to find best among set of combinations
    private static int rodCut(int[] price, int num) {
        int max = Integer.MIN_VALUE;
        if (num == 0) {
            return 0;
        }
        for (int i = 1; i <= num; i++) {
            int res = price[i - 1] + rodCut(price, num - i);
            if (res > max) {
                max = res;
            }
        }
        return max;
    }

    // Frog Jump with find best among 2 options type
    private static int frogJump(int[] height) {
        int[] dp = new int[height.length];
        Arrays.fill(dp, -1);
        return frogJumpHelper(height.length - 1, height, dp);
    }

    private static int frogJumpHelper(int ind, int[] height, int[] dp) {
        if (ind == 0) return 0;
        if (dp[ind] != -1) return dp[ind];
        int jumpTwo = Integer.MAX_VALUE;
        int jumpOne = frogJumpHelper(ind - 1, height, dp) + Math.abs(height[ind] - height[ind - 1]);
        if (ind > 1)
            jumpTwo = frogJumpHelper(ind - 2, height, dp) + Math.abs(height[ind] - height[ind - 2]);

        return dp[ind] = Math.min(jumpOne, jumpTwo);
    }


    // knapsack
    private static int findBestWeight(int[] weights, int[] values, int capacity) {
        int len = weights.length;
        int[][] dp = new int[len + 1][capacity + 1];
        for (int i = 1; i <= len; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (weights[i - 1] <= j) {
                    dp[i][j] = Math.max(dp[i - 1][j], values[i - 1] + dp[i - 1][j - weights[i - 1]]);
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[len][capacity];
    }

    public static void main(String[] args) {
        System.out.println(rodCut(new int[] { 1, 5, 8, 9, 10, 17, 17, 20 }, 4));
        System.out.println(frogJump(new int[] { 10, 20, 30, 10 }));
        System.out.println(frogJump(new int[] { 30, 10, 60, 10, 60, 50 }));
        System.out.println(findBestWeight(new int[] { 1, 2, 3 }, new int[] { 6, 10, 12 }, 5));
        System.out.println(findBestWeight(new int[] { 1, 56, 42, 78, 12 }, new int[] { 50, 30, 20, 10, 50 }, 150));
    }
}
