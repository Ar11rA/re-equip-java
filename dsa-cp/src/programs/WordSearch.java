package programs;

import commons.MatrixPair;

import java.util.Stack;

// stack approach passes 70/85 cases but has limitation, hence backtracking is required
public class WordSearch {
    private static boolean exist(char[][] board, String word) {
        Stack<MatrixPair> stack = new Stack<>();
        boolean[][] visited = new boolean[board.length][board[0].length];

        // edge case
        if (word.length() == 0) {
            return false;
        }

        // find starting points
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word.charAt(0)) {
                    stack.push(new MatrixPair(i, j, String.valueOf(board[i][j]), 1));
                    if (traverse(stack, visited, board, word)) {
                        return true;
                    } else {
                        visited = new boolean[board.length][board[0].length];
                    }
                }
            }
        }

        return false;
    }

    private static boolean traverse(Stack<MatrixPair> stack, boolean[][] visited, char[][] board, String word) {
        while (!stack.isEmpty()) {
            MatrixPair curr = stack.pop();
            if (curr.start == word.length() && word.equals(curr.word)) {
                return true;
            }
            //up
            if (curr.i - 1 >= 0 && board[curr.i - 1][curr.j] == word.charAt(curr.start) && !visited[curr.i - 1][curr.j]) {
                stack.push(new MatrixPair(curr.i - 1, curr.j, curr.word + board[curr.i - 1][curr.j], curr.start + 1));
                visited[curr.i - 1][curr.j] = true;
            }
            //down
            if (curr.i + 1 < board.length && board[curr.i + 1][curr.j] == word.charAt(curr.start) && !visited[curr.i + 1][curr.j]) {
                stack.push(new MatrixPair(curr.i + 1, curr.j, curr.word + board[curr.i + 1][curr.j], curr.start + 1));
                visited[curr.i + 1][curr.j] = true;
            }
            //left
            if (curr.j - 1 >= 0 && board[curr.i][curr.j - 1] == word.charAt(curr.start) && !visited[curr.i][curr.j - 1]) {
                stack.push(new MatrixPair(curr.i, curr.j - 1, curr.word + board[curr.i][curr.j - 1], curr.start + 1));
                visited[curr.i][curr.j - 1] = true;
            }
            //right
            if (curr.j + 1 < board[0].length && board[curr.i][curr.j + 1] == word.charAt(curr.start) && !visited[curr.i][curr.j + 1]) {
                stack.push(new MatrixPair(curr.i, curr.j + 1, curr.word + board[curr.i][curr.j + 1], curr.start + 1));
                visited[curr.i][curr.j + 1] = true;
            }
            if(!stack.isEmpty()) {
                visited[curr.i][curr.j] = false;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        char[][] grid0 = {
          { 'A', 'B', 'C', 'E' },
          { 'S', 'F', 'C', 'S' },
          { 'A', 'D', 'E', 'E' }
        };
        System.out.println(exist(grid0, "ABCB"));

        char[][] grid1 = {
          { 'A', 'B', 'C', 'E' },
          { 'S', 'F', 'C', 'S' },
          { 'A', 'D', 'E', 'E' }
        };
        System.out.println(exist(grid1, "ABCCED"));
        System.out.println(exist(grid1, "ABZ"));

        char[][] grid2 = {
          { 'C', 'A', 'A' },
          { 'A', 'A', 'A' },
          { 'B', 'C', 'D' }
        };
        System.out.println(exist(grid2, "AAB"));

        char[][] grid3 = {
          { 'A', 'B', 'C', 'E' },
          { 'S', 'F', 'E', 'S' },
          { 'A', 'D', 'E', 'E' }
        };
        System.out.println(exist(grid3, "ABCESEEEFS"));

    }
}
