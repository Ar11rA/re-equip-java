package commons;

import java.util.List;

public class Printer {
    public static void print2DArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.println("Element at i: " + i + ", j: " + j + " = " + arr[i][j]);
            }
        }
    }

    public static void print1DArray(int[] arr) {
        for (int a : arr) System.out.println("Element: " + a);
    }

    public static void printList(List<String> arr) {
        arr.forEach(System.out::println);
    }
}

