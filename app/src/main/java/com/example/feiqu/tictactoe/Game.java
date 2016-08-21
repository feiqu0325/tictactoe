package com.example.feiqu.tictactoe;

public class Game {
    private Mark[][] board = new Mark[3][3];
    private int numMoves;
    private GameUpdateListener listener;
    private AIPlayer ai;
    private boolean gameEnded;
    private boolean markPlaced;

    public Game(GameUpdateListener listener) {
        ai = new AIPlayer();
        this.listener = listener;
        initGame();
    }

    public void initGame() {
        markPlaced = false;
        gameEnded = false;
        numMoves = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = Mark.EMPTY;
            }
        }
    }

    public void placeMark(int row, int col) {
        if (!markPlaced) {
            markPlaced = true;
        }
        if (board[row][col] == Mark.EMPTY) {
            board[row][col] = Mark.CROSS;
            numMoves++;
            if (won()) {
                gameEnded = true;
                if (listener != null)
                    listener.won();
            } else if (numMoves >= 9) {
                gameEnded = true;
                if (listener != null)
                    listener.draw();
            } else {
                int[] aiMove = ai.updateGameState(board);
                board[aiMove[0]][aiMove[1]] = Mark.ZERO;
                numMoves++;
                if (listener != null)
                    listener.aiPlacedMark(aiMove[0], aiMove[1]);
                if (won()) {
                    gameEnded = true;
                    if (listener != null)
                        listener.lost();
                } else if (numMoves >= 9) {
                    gameEnded = true;
                    if (listener != null)
                        listener.draw();
                }
            }
        }
    }

    public Mark[][] getBoard() {
        return board;
    }

    public void setListener(GameUpdateListener listener) {
        this.listener = listener;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public boolean isMarkPlaced() {
        return markPlaced;
    }

    private boolean won() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][2] != Mark.EMPTY) {
                return true;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[2][i] != Mark.EMPTY) {
                return true;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[2][2] != Mark.EMPTY) {
            return true;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[2][0] != Mark.EMPTY) {
            return true;
        }
        return false;
    }
}
