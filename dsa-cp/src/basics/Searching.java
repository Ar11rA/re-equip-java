package basics;

public class Searching {

    // binary search
    private static int binarySearch(int[] array, int search) {
        int left = 0, right = array.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] == search) {
                return mid;
            }
            if (array[mid] > search) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] array = { 1, 3, 6, 9, 12, 16 };
        System.out.println(binarySearch(array, 6));
        System.out.println(binarySearch(array, 5));
    }
}
