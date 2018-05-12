package com.example.amoswei.tetris;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if( hasFocus ) {
            hideNavigationBar();
        }
    }
    private void hideNavigationBar() {
        // TODO Auto-generated method stub
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
