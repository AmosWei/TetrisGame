package com.example.amoswei.tetris;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;

public class TetrisView extends View implements Runnable {
    ArrayList<GameOver> observers = new ArrayList<>();

    public TetrisView(Context context) {
        super(context);
    }

    @Override
    public void run() {

    }

    void registerGameOver(GameOver gameOver) {
        observers.add(gameOver);
    }
}
