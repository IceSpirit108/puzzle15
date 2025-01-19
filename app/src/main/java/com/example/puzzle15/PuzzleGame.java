package com.example.puzzle15;

import java.util.ArrayList;
import java.util.Collections;


public class PuzzleGame {
    private int size;
    private int[] board;
    private int emptyIndex;

    public PuzzleGame(int size) {
        this.size = size;
        generateSolvableBoard();
    }

    public int getSize() {
        return size;
    }

    public int[] getBoard() {
        return board;
    }

    public int getEmptyIndex() {
        return emptyIndex;
    }


    private boolean isSolvable(int[] arr) {
        int inversions = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) continue;
            for (int j = i+1; j < arr.length; j++) {
                if (arr[j] != 0 && arr[i] > arr[j]) inversions++;
            }
        }
        if (size % 2 != 0) {

            return (inversions % 2 == 0);
        } else {

            int blankRow = (emptyIndex / size) + 1;

            int rowFromBottom = size - (emptyIndex / size);
            if (rowFromBottom % 2 == 0) {
                return (inversions % 2 != 0);
            } else {
                return (inversions % 2 == 0);
            }
        }
    }

    private void generateSolvableBoard() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i < size*size; i++) {
            list.add(i);
        }
        list.add(0);

        do {
            Collections.shuffle(list);
            board = new int[size*size];
            for (int i = 0; i < size*size; i++) {
                board[i] = list.get(i);
                if (board[i] == 0) {
                    emptyIndex = i;
                }
            }
        } while (!isSolvable(board));
    }


    public boolean moveTile(int index) {
        if (canMove(index)) {

            int temp = board[index];
            board[index] = board[emptyIndex];
            board[emptyIndex] = temp;
            emptyIndex = index;
            return true;
        }
        return false;
    }

    private boolean canMove(int index) {

        int r1 = emptyIndex / size;
        int c1 = emptyIndex % size;
        int r2 = index / size;
        int c2 = index % size;

        if (r1 == r2 && Math.abs(c1 - c2) == 1) return true;
        if (c1 == c2 && Math.abs(r1 - r2) == 1) return true;
        return false;
    }


    public boolean isSolved() {
        for (int i = 0; i < size*size-1; i++) {
            if (board[i] != i+1) return false;
        }
        return board[size*size-1] == 0;
    }


    public void restart() {
        generateSolvableBoard();
    }
}