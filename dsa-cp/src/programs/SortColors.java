package programs;

import commons.Printer;

import java.util.HashMap;

public class SortColors {
    private static void sortColors(int[] nums) {
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : nums) {
            if (frequencyMap.containsKey(num)) {
                frequencyMap.put(num, frequencyMap.get(num) + 1);
            } else {
                frequencyMap.put(num, 1);
            }
        }

        for (int index = 0; index < nums.length; index++) {
           if (frequencyMap.containsKey(0) && frequencyMap.get(0) > 0) {
               nums[index] = 0;
               frequencyMap.put(0, frequencyMap.get(0) - 1);
               continue;
           }
            if (frequencyMap.containsKey(1) && frequencyMap.get(1) > 0) {
                nums[index] = 1;
                frequencyMap.put(1, frequencyMap.get(1) - 1);
                continue;
            }
            if (frequencyMap.containsKey(2) && frequencyMap.get(2) > 0) {
                nums[index] = 2;
                frequencyMap.put(2, frequencyMap.get(2) - 1);
            }
        }

    }

    public static void main(String[] args) {
        int[] nums = new int[] {2,0,2,1,1,0};
        sortColors(nums);
        Printer.print1DArray(nums);
    }
}
