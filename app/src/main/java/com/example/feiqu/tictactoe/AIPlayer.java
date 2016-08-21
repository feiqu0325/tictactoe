package com.example.feiqu.tictactoe;

import java.util.ArrayList;
import java.util.List;

public class AIPlayer {

    private Mark[][] board;

    public int[] updateGameState(Mark[][] board) {
        this.board = board;
        int[] result = minimax(2, Mark.ZERO);
        return new int[] {result[1], result[2]};
    }

    private int[] minimax(int depth, Mark player) {
        List<int[]> nextMoves = generateNextMoves(board);

        int bestScore = (player == Mark.ZERO) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            bestScore = evaluate();
        } else {
            for (int[] move : nextMoves) {
                board[move[0]][move[1]] = player;
                if (player == Mark.ZERO) {
                    currentScore = minimax(depth - 1, Mark.CROSS)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {
                    currentScore = minimax(depth - 1, Mark.ZERO)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                board[move[0]][move[1]] = Mark.EMPTY;
            }
        }
        return new int[] {bestScore, bestRow, bestCol};
    }

    private List<int[]> generateNextMoves(Mark[][] board) {
        List<int[]> nextMoves = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == Mark.EMPTY) {
                    nextMoves.add(new int[] { row, col });
                }
            }
        }
        return nextMoves;
    }

    private int evaluate() {
        int score = 0;
        score += evaluateLine(0, 0, 0, 1, 0, 2);  // row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2);  // row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2);  // row 2
        score += evaluateLine(0, 0, 1, 0, 2, 0);  // col 0
        score += evaluateLine(0, 1, 1, 1, 2, 1);  // col 1
        score += evaluateLine(0, 2, 1, 2, 2, 2);  // col 2
        score += evaluateLine(0, 0, 1, 1, 2, 2);  // diagonal
        score += evaluateLine(0, 2, 1, 1, 2, 0);  // alternate diagonal
        return score;
    }

    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;

        if (board[row1][col1] == Mark.ZERO) {
            score = 1;
        } else if (board[row1][col1] == Mark.CROSS) {
            score = -1;
        }

        if (board[row2][col2] == Mark.ZERO) {
            if (score == 1) {
                score = 10;
            } else if (score == -1) {
                return 0;
            } else {
                score = 1;
            }
        } else if (board[row2][col2] == Mark.CROSS) {
            if (score == -1) {
                score = -10;
            } else if (score == 1) {
                return 0;
            } else {
                score = -1;
            }
        }

        if (board[row3][col3] == Mark.ZERO) {
            if (score > 0) {
                score *= 10;
            } else if (score < 0) {
                return 0;
            } else {
                score = 1;
            }
        } else if (board[row3][col3] == Mark.CROSS) {
            if (score < 0) {
                score *= 10;
            } else if (score > 1) {
                return 0;
            } else {
                score = -1;
            }
        }
        return score;
    }
}
