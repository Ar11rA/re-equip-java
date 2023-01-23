package programs;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    private static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] result = new int[2];
        for (int index = 0; index < nums.length; index++) {
            if (map.containsKey(nums[index])) {
                result[1] = map.get(nums[index]);
                result[0] = index;
            }
            map.put(target - nums[index], index);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] result = twoSum(new int[] { 2, 7, 11, 15 }, 9);
        for (int res : result) {
            System.out.println(res);
        }
    }
}
