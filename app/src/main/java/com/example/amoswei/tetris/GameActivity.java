package com.example.amoswei.tetris;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class GameActivity extends AppCompatActivity implements GameOver {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);
        TetrisView tetrisView = findViewById(R.id.tetrisview);
        tetrisView.registerGameOver(this);
        Log.d("game", "tetrisview created");

    }

    @Override
    public void gameOver() {
        setResult(AppCompatActivity.RESULT_OK);
        finish();
    }
}
