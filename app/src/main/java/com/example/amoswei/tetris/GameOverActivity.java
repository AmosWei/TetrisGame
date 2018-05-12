package com.example.amoswei.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {
    public final static String GAMEOVER_MESSAGE = "Gameover!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        TextView gameOverText = (TextView) findViewById(R.id.gameovermessage);
        gameOverText.setText(getIntent().getStringExtra(GAMEOVER_MESSAGE));

        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);

        int score = getIntent().getIntExtra("Score", 0);
        scoreLabel.setText(score + "");
    }

    public void playAgain(View v) {
        Intent playAgainIntent = new Intent(this, GameActivity.class);
        startActivity(playAgainIntent);
    }}
