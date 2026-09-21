package Test;

import java.util.HashSet;
import java.util.Set;

public class BoggleCount {
    private static final int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

    public static void main(String[] args) {
        char[][] board = {
            {'A', 'B', 'C', 'D', 'E'},
            {'F', 'G', 'H', 'I', 'J'},
            {'K', 'L', 'M', 'N', 'O'},
            {'P', 'Q', 'R', 'S', 'T'},
            {'U', 'V', 'W', 'X', 'Y'}
        };

        Set<String> words = findAllWords(board);
        System.out.println("Pass1");
        for (String word : words) {
            System.out.println(word);
        }
        System.out.println("Pass2");

    }

    public static Set<String> findAllWords(char[][] board) {
        Set<String> words = new HashSet<>();
        int rows = board.length;
        int cols = board[0].length;

        boolean[][] visited = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                findWords(board, visited, row, col, "", words);
            }
        }

        return words;
    }

    private static void findWords(char[][] board, boolean[][] visited, int row, int col, String currentWord, Set<String> words) {
        // Ensure within bounds and not visited
        if (row < 0 || col < 0 || row >= board.length || col >= board[0].length || visited[row][col]) {
            return;
        }

        // Append current character to word and mark cell as visited
        currentWord += board[row][col];
        visited[row][col] = true;

        // Add to the set if word length is 3 or more
        if (currentWord.length() >= 3) {
            words.add(currentWord);
        }

        // Explore all 8 directions
        for (int i = 0; i < 8; i++) {
            findWords(board, visited, row + rowOffsets[i], col + colOffsets[i], currentWord, words);
        }

        // Backtrack: unmark the current cell
        visited[row][col] = false;
    }
}

