package programs;

import java.util.Stack;

public class SimplifyPath {
    private static String simplifyPath(String path) {
        Stack<String> stack = new Stack<>();
        String[] paths = path.split("/");
        for (String s : paths) {
            if (!s.equals(".") && !s.equals("") && !s.equals("..")) {
                stack.push(s);
            }
            if (!stack.isEmpty() && s.equals("..")) {
                stack.pop();
            }
        }
        if (stack.isEmpty()) {
            return "/";
        }
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result
              .insert(0, stack.pop())
              .insert(0, "/");
        }
        return result.toString();
    }
    public static void main(String[] args) {
        System.out.println(simplifyPath("/home//foo/"));
        System.out.println(simplifyPath("/../home//foo/"));
    }
}
