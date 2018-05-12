package com.example.amoswei.tetris;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Tetromino {
    private TetrominoType tetrominoType;
    private int orientation;
    private int color;
    private int[] occupied = new int[4];
    private boolean stop = false;
    private Tetris game;
    private boolean current = false;

    Tetromino(TetrominoType type, int orientation, int color, Tetris game) {
        this.tetrominoType = type;
        if (type == TetrominoType.I && orientation == 3) orientation = 1;
        if (type == TetrominoType.O) orientation = 0;
        this.orientation = orientation;
        this.color = color;
        this.game = game;
        occupied = type.getInitialOccupied();
        for (int i = 0; i < this.orientation; i++) {
            occupied = rotate(occupied);
        }
        occupied = setStart(occupied);
    }

    // draw the Tetromino
    // on-board if current == true; off-board otherwise
    void draw(Canvas c) {
        Paint p = new Paint();
        p.setColor(color);
        p.setStyle(Paint.Style.FILL);

        int h = c.getHeight();
        int w = c.getWidth();

        float topEdge = (float) 0;
        float leftEdge = (float) 0;
        float hBoard = (float) (h * 0.9);
        float wBoard = hBoard / 2;
        float side = wBoard / 10;

        if (current) {
            for (int i : occupied)
                drawGrid(c, i, side, leftEdge, topEdge, p);
        }
        else {
            int[] next = new int[4];
            for (int i = 0; i < 4; i++)
                next[i] = occupied[i]+30;
            float rightPadding = w - leftEdge - wBoard;
            float centerNextX = w-rightPadding/2;
            float centerNextY = (float) (0.3*h);
            side *= 0.7;
            float hside = side/2;
            int center = next[1];
            int centerX = center%10;
            int centerY = center/10;
            for (int i: next) {
                int iX = i%10;
                int iY = i/10;
                c.drawRect((iX-centerX)*side+centerNextX-hside,
                        (iY-centerY)*side+centerNextY-hside,
                        (iX-centerX)*side+centerNextX+hside,
                        (iY-centerY)*side+centerNextY+hside, p);
            }
        }
    }

    static void drawGrid(Canvas c, int i, int color) {
        Paint p = new Paint();
        p.setColor(color);
        p.setStyle(Paint.Style.FILL);

        int h = c.getHeight();
        int w = c.getWidth();

        float topEdge = (float) 0;
        float leftEdge = (float) 0;
        float hBoard = (float) 0.9;
        float wBoard = hBoard/2;
        float side = wBoard/10;

        drawGrid(c, i, side, leftEdge, topEdge, p);
    }


    // pass in more arguments (actually can be inferred from c, but can reduce duplicate)
    private static void drawGrid(Canvas c, int i, float side, float leftEdge, float topEdge, Paint p) {
        if (i < 0) return;
        double x = i%10;
        double y = i/10;
        c.drawRect((float)x*side+leftEdge, (float)y*side+topEdge,
                (float)x*side+leftEdge+side, (float)y*side+topEdge+side, p);
    }

    // set "orientation" field as well
    // this is to be called by other class
    void setRotate() {
        if (tetrominoType == TetrominoType.O) return;
        orientation = (orientation+1)%4;
        occupied = rotate(occupied);
    }

    // rotate once
    // need to check bound (and move left/right to adjust)
    // need to check whether obstructed by other stopped position (if not, rotate once more)
    private int[] rotate(int[] occupied) {
        int[] newOccupied = new int[4];
        int centerX = occupied[1]%10;
        int centerY = occupied[1]/10;
        int move = 0;  // 0 if not move; 1 if move left; 2 if move right
        for (int i = 0; i < 4; i++) {
            int iX = ((occupied[i]+30)/10-3-centerY)+centerX; // iX and iY here are new positions (rotated)
            int iY = -((occupied[i]+30)%10-centerX)+centerY;
            if (iX - centerX >= 6) move = 2;
            if (centerX - iX >= 6) move = 1;
            newOccupied[i] = iX + iY*10;
        }
        if (move == 2) for (int i = 0; i < 4; i++) newOccupied[i] = newOccupied[i]+1;
        if (move == 1) for (int i = 0; i < 4; i++) newOccupied[i] = newOccupied[i]-1;
        boolean canRotate = true;
        for (int i: newOccupied)
            if (i >= 0 && game.getStoppedOnBoard()[i] != -1)
                canRotate = false;
        return canRotate ? newOccupied : rotate(newOccupied);
    }

    // make sure when the tetromino firstly comes out, only the bottem line is on board
    // make sure they are in the center, not on the left
    // note: in GUI, all negative indices shoudn't be shown
    // TODO adjust to center
    private int[] setStart(int[] occupied) {
        int lineTOReduce = occupied[0]/10;
        for (int i: occupied) if (i/10>lineTOReduce) lineTOReduce = i/10;
        boolean has4 = false;
        boolean has5 = false;
        for (int i = 0; i < 4; i++) {
            if ((occupied[i]+30)%10 == 4) has4 = true;
            if ((occupied[i]+30)%10 == 5) has5 = true;
            occupied[i] = occupied[i] - lineTOReduce * 10 + 3;
        }
        if (has4 && !has5) for (int i = 0; i < 4; i++) occupied[i] += 1;
        if (!has4 && has5) for (int i = 0; i < 4; i++) occupied[i] -= 1;
        return occupied;
    }

    // when users press move left button, tetromino should be moved left
    // need to check whether it's already left most
    // need to check whether obstructed by other stopped position
    void moveLeft() {
        int[] newOccupied = new int[4];
        boolean dontmove = false;
        for (int i: occupied) {
            int iX = i%10;
            if (iX == 0 || game.getStoppedOnBoard()[i-1]!=-1) {
                dontmove = true;
                break;
            }
        }
        if (dontmove) return;
        for (int i = 0; i < 4; i++) newOccupied[i] = occupied[i]-1;
        occupied = newOccupied;
    }

    // similar to the above method
    void moveRight() {
        int[] newOccupied = new int[4];
        boolean dontmove = false;
        for (int i: occupied) {
            int iX = i%10;
            if (iX == 9 || game.getStoppedOnBoard()[i+1]!=-1) {
                dontmove = true;
                break;
            }
        }
        if (dontmove) return;
        for (int i = 0; i < 4; i++) newOccupied[i] = occupied[i]+1;
        occupied = newOccupied;
    }

    // move down (need to check if it needs to stop)
    // return the moved position array
    void moveDown() {
        checkStop();
        for (int i = 0; i < 4 && !stop; i++)
            occupied[i] += 10;
    }

    // move down once to the most bottom position it can be in
    void moveDownFast() {
        while (!stop)
            moveDown();
    }

    private void checkStop() {
        for (int i: occupied) {
            if (i >= 0 && game.getTopOfEachCol()[i%10] == i/10+1) {
                stop = true;
            }
        }
    }

    int[] getOccupied() {
        return occupied;
    }

    boolean getStop() {
        return stop;
    }

    public int getColor() {
        return color;
    }

    // set current to true (when "current = next;" in game)
    void setCurrent() {
        current = true;
    }

    public String toString() {
        return "Tetromino: " + tetrominoType.name() + " " + Integer.toString(orientation) + " " +
                Integer.toString(color) + " " + occupied[0] + " " + occupied[1] + " " + occupied[2]
                + " " + occupied[3] + " " + stop;
    }
}
