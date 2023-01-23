package programs;

public class Citation {

    private static int find(int[] citations, int threshold) {
        int ctr = 0;
        for (int citation : citations) {
            if (citation >= threshold) {
                ctr++;
            }
        }
        return ctr;
    }

    private static int hIndex(int[] citations) {
        int low = 1, high = citations.length, ans = 0;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int count = find(citations, mid);
            if (count >= mid) {
                ans = count;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(hIndex(new int[] { 3, 0, 6, 1, 5 }));
        System.out.println(hIndex(new int[] { 1, 3, 1 }));
    }
}
