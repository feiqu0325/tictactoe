package com.example.feiqu.tictactoe;

import android.app.Fragment;
import android.os.Bundle;

public class GameFragment extends Fragment {
    private Game game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
