import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToeGame {
    private char[][] board = new char[3][3];
    private char currentPlayer = 'X';

    public TicTacToeGame() {
        reset();
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean makeMove(int row, int col) {
        if (board[row][col] == ' ') {
            board[row][col] = currentPlayer;
            return true;
        }
        return false;
    }

    public boolean checkWin() {
        return checkWinFor(currentPlayer);
    }

    public boolean checkWinFor(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player &&
                board[i][1] == player &&
                board[i][2] == player) return true;
            if (board[0][i] == player &&
                board[1][i] == player &&
                board[2][i] == player) return true;
        }
        if (board[0][0] == player &&
            board[1][1] == player &&
            board[2][2] == player) return true;
        if (board[0][2] == player &&
            board[1][1] == player &&
            board[2][0] == player) return true;
        return false;
    }

    public boolean isDraw() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ')
                    return false;
        return !checkWin();
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public void reset() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = ' ';
        currentPlayer = 'X';
    }

    // IA Simples: jogada aleatória
    public int[] getAIMoveRandom() {
        List<int[]> empty = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ')
                    empty.add(new int[]{i, j});
        if (empty.isEmpty()) return null;
        return empty.get(new Random().nextInt(empty.size()));
    }

    // IA Minimax: jogada ótima
    public int[] getAIMoveMinimax() {
        int bestScore = Integer.MIN_VALUE;
        int[] move = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'O';
                    int score = minimax(false);
                    board[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        move = new int[]{i, j};
                    }
                }
            }
        }
        return move;
    }

    private int minimax(boolean isMaximizing) {
        if (checkWinFor('O')) return 1;
        if (checkWinFor('X')) return -1;
        if (isDraw()) return 0;

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = isMaximizing ? 'O' : 'X';
                    int score = minimax(!isMaximizing);
                    board[i][j] = ' ';
                    if (isMaximizing) bestScore = Math.max(score, bestScore);
                    else bestScore = Math.min(score, bestScore);
                }
            }
        }
        return bestScore;
    }
}
