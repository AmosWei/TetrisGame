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

    private short[] stoppedOnBoard = new short[200];  // element as color, -1 if nothing there
    private int[] topOfEachCol = new int[10];   // -1 if nothing in this column

    private Tetromino current;
    private Tetromino next;

    Tetris() {
        // initialize stoppedOnBoard and topOfEachCol
        for (int i = 0; i < 200; i++)
            stoppedOnBoard[i] = -1;
        for (int i = 0; i < 10; i++)
            topOfEachCol[i] = -1;
    }

    void triggerNextTetromino() {
        // TODO: do something in GUI using next
        updateBoard();
        eliminate();
        current = next;
        next = new Tetromino(TetrominoType.values()[rand.nextInt(TetrominoType.values().length)],
                rand.nextInt(4), colors.get(rand.nextInt(colors.size())));
    }

    // update the stopped grid on board based on current Tetromino
    void updateBoard() {
        // TODO
    }

    // update the stopped grid on board if some line(s) need to be eliminated
    // otherwise keep it the same
    void eliminate() {
        // TODO
    }

    // update the top of each column (from the stopped on Board)
    void updateTopOfEachCol() {
        for (int i = 0; i < 200; i++) {
            if (i == -1) continue;
            int c = i%10;
            if (topOfEachCol[c] > i/10)
                topOfEachCol[c] = i/10;
        }
    }

    public short[] getStoppedOnBoard() {
        return stoppedOnBoard;
    }

    public int[] getTopOfEachCol() {
        return topOfEachCol;
    }
}
