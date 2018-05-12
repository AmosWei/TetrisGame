package com.example.amoswei.tetris;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import static android.support.v4.app.ActivityCompat.startActivity;

public class TetrisView extends View implements Runnable{
    public static final int STEPDELAY = 100;
    Handler repaintHandler;
    Tetris game;
    ArrayList<GameOver> observers ;

    public TetrisView(Context context, AttributeSet attrs) {
        super(context, attrs);
        observers = new ArrayList<GameOver>();
        game = new Tetris();

        repaintHandler = new Handler();
        repaintHandler.postDelayed(this, STEPDELAY);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        game.draw(canvas);
    }

    // step the view forward by one step - true is returned if more steps to go
    public boolean step() {
        game.step();
        if (game.getOver()) {
            notifyGameOver();
            this.invalidate();
            return false;
        } return true;
    }

    private void notifyGameOver() {
        for (GameOver o : observers) o.gameOver();
    }


    @Override
    public void run() {
        if (step()) {
            repaintHandler.postDelayed(this, TetrisView.STEPDELAY);
        }
    }

    public void registerGameOver(GameOver gameover) {
        observers.add(gameover);
    }
}
