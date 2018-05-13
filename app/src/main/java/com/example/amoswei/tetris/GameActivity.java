package com.example.amoswei.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class GameActivity extends AppCompatActivity implements GameOver {
    TetrisView tetrisView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);
        tetrisView = findViewById(R.id.tetrisview);
        tetrisView.registerGameOver(this);
    }

    public void moveLeft(View view) {
        tetrisView.boardEvent((short)1);
    }

    public void moveRight(View view) {
        tetrisView.boardEvent((short)2);
    }

    public void moveDownFast(View view) {
        tetrisView.boardEvent((short)3);
    }

    public void rotate(View view) {
        tetrisView.boardEvent((short)0);
    }

    public void menu(View view) {
        Intent menuIntent = new Intent(this, WelcomeActivity.class);
        startActivity(menuIntent);
    }

    public void pause(View view) {
        tetrisView.game.pause();
    }

    public void restart(View v) {
        Intent restartIntent = new Intent(this, GameActivity.class);
        startActivity(restartIntent);
    }

    @Override
    public void gameOver() {
        setResult(AppCompatActivity.RESULT_OK);
        Intent gameOverIntent = new Intent(this, GameOverActivity.class);
        gameOverIntent.putExtra("Score", tetrisView.game.score);
        startActivity(gameOverIntent);
        finish();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if( hasFocus ) {
            hideNavigationBar();
        }
    }
    private void hideNavigationBar() {
        final View decorView = getWindow().getDecorView();
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(flags);
                }
            }
        });
    }
}
