package com.example.amoswei.tetris;

import android.graphics.Color;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.assertTrue;

public class TetrominoTest {
    @Test
    public void testMoveDown() {
        Tetris tetris = new Tetris();
        Tetromino tetromino = new Tetromino(TetrominoType.T, 1, Color.GREEN, tetris);
        tetris.setCurrent(tetromino);
        for (int i = 0; i < 3; i++)
            tetromino.moveDown();
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
    public void testMoveLeft() {
        Tetris tetris = new Tetris();
        Tetromino tetromino = new Tetromino(TetrominoType.T, 1, Color.GREEN, tetris);
        tetris.setCurrent(tetromino);
        for (int i = 0; i < 3; i++)
            tetromino.moveDown();
        for (int i = 0; i < 2; i++)
            tetromino.moveLeft();
        int[] before = tetris.getStoppedOnBoard().clone();
        tetris.updateBoard();
        int[] after = tetris.getStoppedOnBoard();
        for (int i = 0; i < before.length; i++)
            assertTrue(before[i] == -1 || before[i] == after[i]);
        int[] current = {12, 22, 23, 32};
        for (int aCurrent : current) assertTrue(after[aCurrent] == Color.GREEN);
    }

    @Test
    public void testMoveRight() {
        Tetris tetris = new Tetris();
        Tetromino tetromino = new Tetromino(TetrominoType.T, 1, Color.GREEN, tetris);
        tetris.setCurrent(tetromino);
        for (int i = 0; i < 3; i++)
            tetromino.moveDown();
        for (int i = 0; i < 2; i++)
            tetromino.moveRight();
        int[] before = tetris.getStoppedOnBoard().clone();
        tetris.updateBoard();
        int[] after = tetris.getStoppedOnBoard();
        for (int i = 0; i < before.length; i++)
            assertTrue(before[i] == -1 || before[i] == after[i]);
        int[] current = {16, 26, 27, 36};
        for (int aCurrent : current) assertTrue(after[aCurrent] == Color.GREEN);
    }

    @Test
    public void testMoveDownFast() {
        Tetris tetris = new Tetris();
        Tetromino tetromino = new Tetromino(TetrominoType.T, 0, Color.GREEN, tetris);
        for (int i = 0; i < 7; i++)
            tetromino.moveDownFast();
        ArrayList<Integer> correctOccupied = new ArrayList<>(Arrays.asList(183,184,185,194));
        for (int i: tetromino.getOccupied())
            assertTrue(correctOccupied.contains(i));
    }

    @Test
    public void testRotate() {
        Tetris tetris = new Tetris();
        Tetromino tetromino = new Tetromino(TetrominoType.T, 0, Color.GREEN, tetris);
        tetromino.moveDownFast();
        tetromino.setRotate();
        ArrayList<Integer> correctOccupied = new ArrayList<>(Arrays.asList(14, 24, 25, 34));
        for (int i: tetromino.getOccupied())
            assertTrue(correctOccupied.contains(i));
    }

    @Test
    public void testRotateL() {
        Tetris tetris = new Tetris();
        Tetromino tetromino = new Tetromino(TetrominoType.I, 1, Color.BLUE, tetris);
        for (int i = 0; i < 5; i++)
            tetromino.moveLeft();
        tetromino.moveDown();
        tetromino.setRotate();
        ArrayList<Integer> correctOccupied = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        for (int i: tetromino.getOccupied())
            assertTrue(correctOccupied.contains(i));
    }
}
