package com.example.amoswei.tetris;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Tetromino {
    private int orientation;
    private int position = 4;
    private int color;
    private boolean moving = true;
    private int[] occupied = new int[4];
    private boolean stop = false;
    private Tetris game;

    Tetromino(TetrominoType type, int orientation, int color, Tetris game) {
        if (type == TetrominoType.I && orientation == 1) orientation = 3;
        this.orientation = orientation;
        this.color = color;
        this.game = game;
        occupied = type.getInitialOccupied();
        for (int i = 0; i < this.orientation; i++)
            occupied = rotate(occupied);
        occupied = setStart(occupied);
    }

    // draw the Tetromino
    public void draw(Canvas c) {
        Paint p = new Paint();
        p.setColor(color);
        p.setStyle(Paint.Style.FILL);

        int h = c.getHeight();
        int w = c.getWidth();

        float topEdge = (float) (h%0.2);
        float leftEdge = (float) (w*0.1);
        float hBoard = (float) (h*0.7);
        float wBoard = hBoard/2;
        float side = wBoard/10;

        for (int i: occupied) {
            double x = i%10;
            double y = i/10;
            c.drawRect((float)x*side+leftEdge, (float)y*side+topEdge,
                    (float)x*side+leftEdge+side, (float)y*side+topEdge+side, p);
        }
    }

    // set "orientation" field as well
    // this is to be called by other class
    public void setRotate() {
        orientation = (orientation+1)%3;
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
            int iX = (occupied[i]/10-centerY)+centerX; // iX and iY here are new positions (rotated)
            int iY = -(occupied[i]%10-centerX)+centerY;
            if (iX - centerX >= 6) move = 2;
            if (centerX - iX >= 6) move = 1;
            newOccupied[i] = iX + iY*10;
        }
        if (move == 2) for (int i = 0; i < 4; i++) newOccupied[i] = newOccupied[i]+1;
        if (move == 1) for (int i = 0; i < 4; i++) newOccupied[i] = newOccupied[i]-1;
        boolean canRotate = true;
        for (int i: newOccupied)
            if (game.getStoppedOnBoard()[i] != -1)
                canRotate = false;
        return canRotate ? newOccupied : rotate(newOccupied);
    }

    // make sure when the tetromino firstly comes out, only the bottem line is on board
    // note: in GUI, all negative indices shoudn't be shown
    public int[] setStart(int[] occupied) {
        int lineTOReduce = occupied[0]/10;
        for (int i: occupied) if (i/10>lineTOReduce) lineTOReduce = i/10;
        for (int i = 0; i < occupied.length; i++)
            occupied[i] = occupied[i] - lineTOReduce*10;
        return occupied;
    }

    // when users press move left button, tetromino should be moved left
    // need to check whether it's already left most
    // need to check whether obstructed by other stopped position
    public void moveLeft() {
        int[] newOccupied = new int[4];
        boolean dontmove = false;
        for (int i: occupied) {
            int iX = i%10;
            int iY = i/10;
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
    public void moveRight() {
        int[] newOccupied = new int[4];
        boolean dontmove = false;
        for (int i: occupied) {
            int iX = i%10;
            int iY = i/10;
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
    public void moveDown() {
        checkStop();
        for (int i = 0; i < 4 && !stop; i++)
            occupied[i] += 10;
    }

    // move down once to the most bottom position it can be in
    public void moveDownFast() {
        while (!stop)
            moveDown();
    }

    private void checkStop() {
        for (int i: occupied) {
            if (game.getTopOfEachCol()[i&10] == i/10+1) {
                stop = true;
                return;
            }
        }
    }

    public int[] getOccupied() {
        return occupied;
    }

    public boolean getStop() {
        return stop;
    }

    public int getColor() {
        return color;
    }
}
