package programs;

import commons.MatrixPair;

import java.util.ArrayDeque;
import java.util.Queue;

public class SurroundedRegions {
    private static void solve(char[][] board) {
        Queue<MatrixPair> queue = new ArrayDeque<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if ((i == 0 || j == 0 || i == board.length - 1 || j == board[0].length - 1) && board[i][j] == 'O') {
                    queue.add(new MatrixPair(i, j));
                }
            }
        }
        while (!queue.isEmpty()) {
            MatrixPair curr = queue.poll();
            board[curr.i][curr.j] = 'D';

            if (curr.i > 0 && board[curr.i - 1][curr.j] == 'O') {
                queue.add(new MatrixPair(curr.i - 1, curr.j));
            }
            if (curr.i < board.length - 1 && board[curr.i + 1][curr.j] == 'O') {
                queue.add(new MatrixPair(curr.i + 1, curr.j));
            }
            if (curr.j > 0 && board[curr.i][curr.j - 1] == 'O') {
                queue.add(new MatrixPair(curr.i, curr.j - 1));
            }
            if (curr.j < board[0].length - 1 && board[curr.i][curr.j + 1] == 'O') {
                queue.add(new MatrixPair(curr.i, curr.j + 1));
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != 'D' && board[i][j] != 'X') {
                    board[i][j] = 'X';
                }
                if (board[i][j] == 'D') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    public static void main(String[] args) {
        char[][] grid1 = {
          { 'X', 'X', 'X', 'X' },
          { 'X', 'O', 'O', 'X' },
          { 'X', 'X', 'O', 'X' },
          { 'X', 'O', 'X', 'X' }
        };
        solve(grid1);
        System.out.println("here!");
    }

}
