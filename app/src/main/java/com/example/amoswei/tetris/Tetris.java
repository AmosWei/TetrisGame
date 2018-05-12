package com.example.amoswei.tetris;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.example.amoswei.tetris.Tetromino.drawGrid;

public class Tetris{
    // possible colors that can be randomly choose from
    private static ArrayList<Integer> colors = new ArrayList<>(Arrays.asList(Color.GREEN,
            Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED, Color.BLUE, Color.MAGENTA,
            Color.DKGRAY));

    private static Random rand = new Random();

    private int[] stoppedOnBoard = new int[200];  // element as color, -1 if nothing there
    private int[] topOfEachCol = new int[10];   // 20 if nothing in this column

    private Tetromino current;
    private Tetromino next;

    int score;

    private boolean over;

    private Canvas c;

    Tetris() {
        // initialize stoppedOnBoard and topOfEachCol
        for (int i = 0; i < 200; i++)
            stoppedOnBoard[i] = -1;
        for (int i = 0; i < 10; i++)
            topOfEachCol[i] = 20;
        next = new Tetromino(TetrominoType.values()[rand.nextInt(TetrominoType.values().length)],
                rand.nextInt(4), colors.get(rand.nextInt(colors.size())), this);
        triggerNextTetromino();
        score = 0;
        over = false;
    }

    // to be called on each step (through view.invalidate())
    // draw background of board
    // TODO score
    // draw stoppedOnBoard grids
    // draw current and next
    void draw(Canvas c) {
        this.c = c;
        drawBackGround();
        for (int i = 0; i < 200; i++) {
            if (stoppedOnBoard[i] == -1) continue;
            drawGrid(c, i, stoppedOnBoard[i]);
        }
        current.draw(c);
        next.draw(c);
    }

    private void drawBackGround() {
        int h = c.getHeight();
        int w = c.getWidth();

        Paint background = new Paint();
        Paint frame = new Paint();
        background.setColor(Color.rgb(211, 218, 229));
        frame.setColor(Color.rgb(255, 255, 255));
        c.drawRect(0, 0, w, h, frame);
        c.drawRect((float) 0, (float) 0,
                (float) (0.45 * h), (float) 0.9 * h, background);
    }

    void step() {
        current.moveDown();
        if (current.getStop())
            triggerNextTetromino();
    }

    private void triggerNextTetromino() {
        updateBoard();
        eliminate();
        updateTopOfEachCol();
        current = next;
        current.setCurrent();         // set the current attribute of the current tetromino to true
        next = new Tetromino(TetrominoType.values()[rand.nextInt(TetrominoType.values().length)],
                rand.nextInt(4), colors.get(rand.nextInt(colors.size())), this);
    }

    // event to be "rotate", "left", "right" or "faster" (0, 1, 2, 3)
    // TODO use this method
    void updateBoardByEvent(short event) {
        if (event == 0)
            current.setRotate();
        else if (event == 1)
            current.moveLeft();
        else if (event == 2)
            current.moveRight();
        else
            current.moveDownFast();
    }

    // update the stopped grid on board based on current Tetromino (which just stops)
    private void updateBoard() {
        if (current == null) return;
        int[] currentOccupied = current.getOccupied();
        for (int i: currentOccupied)
            if (i >= 0)
                stoppedOnBoard[i] = current.getColor();
    }

    // update the stopped grid on board if some line(s) need to be eliminated
    // otherwise keep it the same
    // add score = (lines to be eliminated) ^ 2
    private void eliminate() {
        int eliminatedCount = 0;
        for (int i = 0; i < 20; i++) {
            boolean allOccupied = true;
            for (int j = 0; j < 10; j++) {
                if (stoppedOnBoard[i*10+j] == -1) {
                    allOccupied = false;
                    break;
                }
            }
            if (allOccupied) {          // need to eliminate
                eliminatedCount++;
                for (int j = 0; j < 10; j++)        // eliminate line i
                    stoppedOnBoard[i*10+j] = -1;
                for (int j = 10; j < (i+1)*10; j++)         // move all above lines down
                    stoppedOnBoard[j] = stoppedOnBoard[j-10];
                for (int j = 0; j < 10; j++)                // fill the first line as -1
                    stoppedOnBoard[j] = -1;
            }
        }
        score += eliminatedCount*eliminatedCount;
    }

    // update the top of each column (from the stopped on Board)
    // set over attribute to true if game over
    // set to initial value if the column has no grid occupied
    private void updateTopOfEachCol() {
        if (current == null) return;
        for (int i = 0; i < 10; i++)
            topOfEachCol[i] = 20;
        for (int i = 0; i < 200; i++) {
            if (stoppedOnBoard[i] == -1) continue;
            int c = i%10;
            if (topOfEachCol[c] > i/10)
                topOfEachCol[c] = i/10;
        }
        for (int i: topOfEachCol)
            if (i == 0)
                over = true;
    }

    int[] getStoppedOnBoard() {
        return stoppedOnBoard;
    }

    int[] getTopOfEachCol() {
        return topOfEachCol;
    }

    boolean getOver() {
        return over;
    }
}
