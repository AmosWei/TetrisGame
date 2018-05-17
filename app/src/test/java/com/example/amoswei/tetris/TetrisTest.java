package com.example.amoswei.tetris;

import android.graphics.Color;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class TetrisTest {
    @Test
    public void testUpdateBoard() {
        Tetris tetris = new Tetris();
        Tetromino tetromino = new Tetromino(TetrominoType.T, 1, Color.GREEN, tetris);
        tetromino.moveDownFast();
        tetris.setCurrent(tetromino);
        int[] before = tetris.getStoppedOnBoard().clone();
        tetris.updateBoard();
        int[] after = tetris.getStoppedOnBoard();
        for (int i = 0; i < before.length; i++)
            assertTrue(before[i] == -1 || before[i] == after[i]);
        int[] current = {14, 24, 25, 34};
        for (int i = 0; i < current.length; i++)
            assertTrue(after[current[i]] == Color.GREEN);
    }

    @Test
    public void testEliminateFalsePositive() {
        Tetris tetris = new Tetris();
        Tetromino tetromino = new Tetromino(TetrominoType.T, 1, Color.GREEN, tetris);
        tetromino.moveDownFast();
        tetris.setCurrent(tetromino);
        int[] before = tetris.getStoppedOnBoard();
        tetris.eliminate();
        int[] after = tetris.getStoppedOnBoard();
        for (int i = 0; i < before.length; i++)
            assertTrue(before[i] == after[i]);
        tetromino.moveDownFast();
    }

    @Test
    public void testEliminateTruePositive() {
        Tetris tetris = new Tetris();
        Tetromino tetromino1 = new Tetromino(TetrominoType.I, 0, Color.CYAN, tetris);
        tetris.setCurrent(tetromino1);
        for (int i = 0; i < 3; i++)
            tetromino1.moveLeft();
        for (int i = 0; i < 7; i++)
            tetromino1.moveDownFast();
        tetris.updateBoard();
        Tetromino tetromino2 = new Tetromino(TetrominoType.I, 0, Color.CYAN, tetris);
        tetris.setCurrent(tetromino2);
        tetromino2.moveRight();
        for (int i = 0; i < 7; i++)
            tetromino2.moveDownFast();
        tetris.updateBoard();
        Tetromino tetromino3 = new Tetromino(TetrominoType.I, 1, Color.CYAN, tetris);
        tetris.setCurrent(tetromino3);
        for (int i = 0; i < 3; i++)
            tetromino3.moveRight();
        for (int i = 0; i < 7; i++)
            tetromino3.moveDownFast();
        tetris.updateBoard();
        Tetromino tetromino4 = new Tetromino(TetrominoType.I, 1, Color.CYAN, tetris);
        tetris.setCurrent(tetromino4);
        for (int i = 0; i < 4; i++)
            tetromino4.moveRight();
        for (int i = 0; i < 7; i++)
            tetromino4.moveDownFast();
        tetris.updateBoard();
        int[] before = tetris.getStoppedOnBoard().clone();
        tetris.eliminate();
        int[] after = tetris.getStoppedOnBoard();
        for (int i = 190; i < 198; i++)
            assertTrue(before[i] == Color.CYAN && after[i] == -1);
        assertTrue(tetris.score == 1);
    }
}
