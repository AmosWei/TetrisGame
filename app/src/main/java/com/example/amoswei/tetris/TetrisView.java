package com.example.amoswei.tetris;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    // step the view forward by one step - true is returned if more steps to go
    public boolean step() {
        game.step();
        if (game.getOver()) {
            notifyGameOver();
            this.invalidate();
            return true;
        } return false;
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
