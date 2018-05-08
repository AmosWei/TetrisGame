package com.example.amoswei.tetris;

public enum TetrominoType {
    I, J, L, O, S, T, Z;

    // the grid is 10*20
    int[] getInitialOccupied() {
        int[] initialWithPos;
        switch (this) {
            case I: initialWithPos = new int[] {0, 1, 2, 3}; break;
            case J: initialWithPos = new int[] {0, 1, 2,
                                                      12}; break;
            case L: initialWithPos = new int[] {0, 1, 2,
                                                10}; break;
            case O: initialWithPos = new int[] {0, 1,
                                                10, 11}; break;
            case S: initialWithPos = new int[] {1, 2,
                                            10, 11}; break;
            case T: initialWithPos = new int[] {0, 1, 2,
                                                   11}; break;
            case Z: initialWithPos = new int[] {0, 1,
                                                   11, 12}; break;
            default: initialWithPos = null;
        }
        return initialWithPos;
    }
}
