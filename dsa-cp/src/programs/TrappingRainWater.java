package programs;

import java.util.Stack;

public class TrappingRainWater {
    private static int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int curr = 0, ans = 0;
        while (curr < height.length) {
            while (!stack.isEmpty() && height[curr] > height[stack.peek()]) {
                int top = stack.peek();
                stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                int boundedHeight = (curr - stack.peek() - 1) * Math.min(height[curr], height[stack.peek()]) - height[top];
                ans += boundedHeight;
            }
            stack.push(height[curr]);
            curr++;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(trap(new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 }));
    }
}
