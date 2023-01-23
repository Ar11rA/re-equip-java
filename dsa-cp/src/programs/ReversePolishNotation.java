package programs;

import java.util.Stack;

public class ReversePolishNotation {
    private static int evalRPN(String[] tokens) {
        Stack<Integer> numberStack = new Stack<>();
        int res = 0;
        for (String token : tokens) {
            try {
                numberStack.push(Integer.parseInt(token));
            } catch (NumberFormatException exception) {
                switch (token) {
                    case "*" -> {
                        res = numberStack.pop() * numberStack.pop();
                        numberStack.push(res);
                    }
                    case "+" -> {
                        res = numberStack.pop() + numberStack.pop();
                        numberStack.push(res);
                    }
                    case "/" -> {
                        int first = numberStack.pop();
                        int second = numberStack.pop();
                        res = second /first;
                        numberStack.push(res);
                    }
                    case "-" -> {
                        int first = numberStack.pop();
                        int second = numberStack.pop();
                        res = second - first;
                        numberStack.push(res);
                    }
                    default -> System.out.println("Unsupported operation!");
                }
            }
        }
        if (!numberStack.isEmpty()) {
            return numberStack.pop();
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(evalRPN(new String[] { "2", "1", "+", "3", "*" }));
        System.out.println(evalRPN(new String[] { "4", "13", "5", "/", "+" }));
    }
}
