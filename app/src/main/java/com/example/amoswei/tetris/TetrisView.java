package com.example.amoswei.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import java.util.ArrayList;

public class TetrisView extends View implements Runnable {
    ArrayList<GameOver> observers = new ArrayList<>();
    Tetris game;

    public TetrisView(Context context) {
        super(context);
        game = new Tetris();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        game.draw(canvas);
    }

    @Override
    public void run() {
    }

    public boolean step() {
        game.step();
        // TODO judge if game over; if yes, notifyGameOver(); return false;
        this.invalidate(); // update GUI ie. call onDraw(c) again
        return true;
    }

    void registerGameOver(GameOver gameOver) {
        observers.add(gameOver);
    }

    private void notifyGameOver() {
        for (GameOver o : observers) o.gameOver();
    }
}
