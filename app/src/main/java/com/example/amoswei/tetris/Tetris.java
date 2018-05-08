package com.example.amoswei.tetris;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Tetris {
    // possible colors that can be randomly choose from
    private static ArrayList<Integer> colors = new ArrayList<>(Arrays.asList(Color.GREEN,
            Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED));

    private static Random rand = new Random();

    short[] stoppedOnBoard = new short[200];  // element as color

    private Tetromino current;
    private Tetromino next;

    Tetris() {
    }

    void triggerNextTetromino() {
        // TODO: do something in GUI using next
        updateBoard();
        next = new Tetromino(TetrominoType.values()[rand.nextInt(TetrominoType.values().length)],
                rand.nextInt(4), colors.get(rand.nextInt(colors.size())));
    }

    // update the stopped grid on board based on current Tetromino
    void updateBoard() {
        // TODO
    }
}
