package com.example.amoswei.tetris;

public enum TetrominoType {
    I, J, L, O, S, T, Z;

    // the grid is 10*20
    int getOccupied(int orientation) {
        int initial = 0;
        switch (this) {
            case I: initial = 0b1111;
            case J: initial = 0b1000000000111;
            case L: initial = 0b10000000111;
            case O: return 0b110000000011;      // no need to rotate
            case S: initial = 0b110000000110;
            case T: initial = 0b100000000111;
            case Z: initial = 0b1100000000011;
        }
        return rotate(initial, orientation);
    }

    // return the index of center
    int getCenter() {
        switch (this) {
            case I: return 2;
            case J: return 2;
            case L: return 0;
            case O: return 0;
            case S: return 1;
            case T: return 1;
            case Z: return 1;
            default: return -1;
        }
    }

    private int rotateOnce(int initial) {
        int rotated = 0;
        for (int index = 0; initial != 0; index++) {
            if ((initial & 1) == 0)
                continue;
            int center = getCenter();
            int x = getX(center, index);
            int y = getY(center, index);
            int newX = -y;
            int newY = -x;
            int newIndex = (newX+center/10)*10 + (newY+center%10);
            rotated |= (1 << newIndex);
        }
        return rotated;
    }

    private int rotate(int initial, int orientation) {
        int rotated = initial;
        for (int i = 0; i < orientation; i++)
            rotated = rotateOnce(rotated);
        return rotated;
    }

    private int getX(int center, int i) {
        return i/10 - center/10;
    }

    private int getY(int center, int i) {
        return i%10 - center%10;
    }
}
