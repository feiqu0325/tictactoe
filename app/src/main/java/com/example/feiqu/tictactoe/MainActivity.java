package com.example.feiqu.tictactoe;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GameUpdateListener {

    private Game game;
    private TextView restartBtn;
    private GameFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restartBtn = (TextView) findViewById(R.id.restart_btn);
        FragmentManager fm = getFragmentManager();
        fragment = (GameFragment) fm.findFragmentByTag("game");
        if (fragment == null) {
            fragment = new GameFragment();
            fm.beginTransaction().add(fragment, "game").commit();
            game = new Game(this);
        } else {
            game = fragment.getGame();
            game.setListener(this);
            if (game.isMarkPlaced()) {
                restartBtn.setVisibility(View.VISIBLE);
                Mark[][] board = game.getBoard();
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        TextView cell = getCell(i, j);
                        if (cell != null) {
                            if (board[i][j] == Mark.CROSS)
                                cell.setText("X");
                            else if (board[i][j] == Mark.ZERO)
                                cell.setText("0");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        game.setListener(null);
        fragment.setGame(game);
    }

    public void cellClicked(View v) {
        if (restartBtn.getVisibility() == View.GONE) {
            restartBtn.setVisibility(View.VISIBLE);
        }
        if (!game.isGameEnded()) {
            TextView cell = (TextView) findViewById(v.getId());
            if (cell != null) {
                String content = cell.getText().toString();
                if (content.isEmpty()) {
                    switch (cell.getId()) {
                        case R.id.cell0:
                            game.placeMark(0, 0);
                            break;
                        case R.id.cell1:
                            game.placeMark(0, 1);
                            break;
                        case R.id.cell2:
                            game.placeMark(0, 2);
                            break;
                        case R.id.cell3:
                            game.placeMark(1, 0);
                            break;
                        case R.id.cell4:
                            game.placeMark(1, 1);
                            break;
                        case R.id.cell5:
                            game.placeMark(1, 2);
                            break;
                        case R.id.cell6:
                            game.placeMark(2, 0);
                            break;
                        case R.id.cell7:
                            game.placeMark(2, 1);
                            break;
                        case R.id.cell8:
                            game.placeMark(2, 2);
                            break;
                    }
                    cell.setText("X");
                }
            }
        }
    }

    public void restartClicked(View v) {
        Mark[][] board = game.getBoard();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != Mark.EMPTY) {
                    TextView cell = getCell(i, j);
                    if (cell != null) {
                        cell.setText("");
                    }
                }
            }
        }
        game.initGame();
        restartBtn.setVisibility(View.GONE);
    }

    @Override
    public void won() {
        showResult(R.string.won);
    }

    @Override
    public void lost() {
        showResult(R.string.lost);
    }

    @Override
    public void draw() {
        showResult(R.string.draw);
    }

    @Override
    public void aiPlacedMark(int row, int col) {
        TextView cell = getCell(row, col);
        if (cell != null) {
            cell.setText("0");
        }
    }

    private void showResult(int contentResId) {
        Toast toast = Toast.makeText(this, contentResId, Toast.LENGTH_LONG);
        toast.show();
    }

    private TextView getCell(int row, int col) {
        TextView cell = null;
        switch (row) {
            case 0:
                switch (col) {
                    case 0:
                        cell = (TextView)findViewById(R.id.cell0);
                        break;
                    case 1:
                        cell = (TextView)findViewById(R.id.cell1);
                        break;
                    case 2:
                        cell = (TextView)findViewById(R.id.cell2);
                        break;
                }
                break;
            case 1:
                switch (col) {
                    case 0:
                        cell = (TextView)findViewById(R.id.cell3);
                        break;
                    case 1:
                        cell = (TextView)findViewById(R.id.cell4);
                        break;
                    case 2:
                        cell = (TextView)findViewById(R.id.cell5);
                        break;
                }
                break;
            case 2:
                switch (col) {
                    case 0:
                        cell = (TextView)findViewById(R.id.cell6);
                        break;
                    case 1:
                        cell = (TextView)findViewById(R.id.cell7);
                        break;
                    case 2:
                        cell = (TextView)findViewById(R.id.cell8);
                        break;
                }
                break;
        }
        return cell;
    }
}
