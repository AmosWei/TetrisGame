package com.example.amoswei.tetris;

import android.graphics.Color;

public class Tetromino {
    private TetrominoType type;
    private int orientation;
    private int position = 4;
    private int color;
    private boolean moving = true;
    private int[] occupied = new int[4];

    Tetromino(TetrominoType type, int orientation, int color) {
        if (type == TetrominoType.I && orientation == 1) orientation = 3;
        this.type = type;
        this.orientation = orientation;
        this.color = color;
        occupied = type.getInitialOccupied();
        for (int i = 0; i < orientation; i++)
            occupied = rotate(occupied);
        occupied = setStart(occupied);
    }

    // rotate once
    // need to check bound (and move left/right to adjust)
    public int[] rotate(int[] occupied) {
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
        return newOccupied;
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
    public int[] moveLeft(int[] occupied) {
        int[] newOccupied = new int[4];
        boolean dontmove = false;
        for (int i: occupied) {
            int iX = i%10;
            int iY = i/10;
            if (iX == 0) {
                dontmove = true;
                break;
            }
        }
        if (dontmove) return occupied;
        for (int i = 0; i < 4; i++) newOccupied[i] = occupied[i]-1;
        return newOccupied;
    }

    // similar to the above method
    public int[] moveRight(int[] occupied) {
        int[] newOccupied = new int[4];
        boolean dontmove = false;
        for (int i: occupied) {
            int iX = i%10;
            int iY = i/10;
            if (iX == 9) {
                dontmove = true;
                break;
            }
        }
        if (dontmove) return occupied;
        for (int i = 0; i < 4; i++) newOccupied[i] = occupied[i]+1;
        return newOccupied;
    }

    public int[] moveDown(int[] occupied, Tetris game) {
        int[] newOccupied = new int[4];
        boolean stop = false;
        // TODO use game.stoppedOnBoard to judge if it should stop
        return null;
    }
}
