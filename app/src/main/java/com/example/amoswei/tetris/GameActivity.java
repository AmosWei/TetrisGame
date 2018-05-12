package com.example.amoswei.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import static com.example.amoswei.tetris.GameOverActivity.GAMEOVER_MESSAGE;

public class GameActivity extends AppCompatActivity implements GameOver {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);
        TetrisView tetrisView = (TetrisView) findViewById(R.id.tetrisview);
        tetrisView.registerGameOver(this);
        Log.d("game", "tetrisview created");

    }

    @Override
    public void gameOver() {
        setResult(AppCompatActivity.RESULT_OK);
        Intent gameOverIntent = new Intent(this, GameOverActivity.class);
        startActivity(gameOverIntent);
        finish();
    }
}
