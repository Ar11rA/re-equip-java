package basics;

import commons.Printer;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {
    private static List<String> call(String input) {
        List<String> result = new ArrayList<>();
        backtrack(result, input, "",0);
        return result;
    }

    private static void backtrack(List<String> result, String input, String tmp, int index) {
        result.add(tmp);
        for (int i = index; i < input.length(); i++) {
            tmp = tmp + input.charAt(i);
            backtrack(result, input, tmp, i + 1);
            tmp = tmp.substring(0, tmp.length() - 1);
        }
    }

    public static void main(String[] args) {
        Printer.printList(call("abc"));
    }
}
