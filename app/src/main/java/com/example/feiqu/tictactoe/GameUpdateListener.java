package com.example.feiqu.tictactoe;

public interface GameUpdateListener {
    void won();
    void lost();
    void draw();
    void aiPlacedMark(int row, int col);
}
