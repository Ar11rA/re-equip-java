package programs;

import commons.Printer;

public class RangeBinary {

    private enum SearchType {
        MIN,
        MAX,
        IMMEDIATE
    }

    public static int getIndexBinarySearch(int[] nums, int target) {
        return getIndexBinarySearch(nums, target, SearchType.IMMEDIATE);
    }

    public static int getIndexBinarySearch(int[] nums, int target,
                                           SearchType type) {
        int low = 0, high = nums.length - 1, index = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] == target) {
                index = mid;
                switch(type) {
                    case MAX -> low = mid + 1;
                    case MIN -> high = mid - 1;
                    default -> {
                        return index;
                    }
                }
            } else if (nums[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return index;
    }

    public static int[] searchRange(int[] nums, int target) {
        int[] result = new int[2];
        result[0] = getIndexBinarySearch(nums, target, SearchType.MIN);
        result[1] = getIndexBinarySearch(nums, target, SearchType.MAX);
        return result;
    }

    public static void main(String[] args) {
        int[] result = searchRange(new int[] { 1, 2, 2, 4, 4, 4, 6, 6, 6 }, 4);
        Printer.print1DArray(result);
    }
}
