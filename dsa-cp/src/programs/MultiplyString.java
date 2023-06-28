package programs;

import commons.Printer;

public class MultiplyString {

    public static String multiply(String num1, String num2) {
        int m = num1.length();
        int n = num2.length();
        // base cases
        if (m == 0 || n == 0 || "0".equals(num1) || "0".equals(num2)) {
            return "0";
        }
        if ("1".equals(num1)) {
            return num2;
        }
        if ("1".equals(num2)) {
            return num1;
        }

        // logic
        StringBuilder sb = new StringBuilder();
        int[] result = new int[m + n];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int mulRes = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                System.out.println(mulRes);
                mulRes += result[i + j + 1];
                System.out.println(mulRes);
                Printer.print1DArray(result);
                result[i + j + 1] = mulRes % 10;
                result[i + j] += mulRes / 10;
            }
        }

        for (int i = 0; i < result.length; i++) {
            if (result[i] == 0 && sb.isEmpty()) {
                continue;
            }
            sb.append(result[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(multiply("12", "13"));
    }
}
