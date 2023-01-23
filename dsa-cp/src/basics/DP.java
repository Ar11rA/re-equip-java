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
        Arrays.fill(dp,-1);
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

    public static void main(String[] args) {
        System.out.println(rodCut(new int[] { 1, 5, 8, 9, 10, 17, 17, 20 }, 4));
        System.out.println(frogJump(new int[] { 10, 20, 30, 10 }));
        System.out.println(frogJump(new int[] { 30, 10, 60, 10, 60, 50 }));
    }
}
