package org.inspirecenter.minesweeper.Util;

import org.inspirecenter.minesweeper.Model.BoardState;

public class StatePrinter {

    public static void print(BoardState state) {
        for (int x = 0; x < state.getCells().length; x++) {
            for (int y = 0; y < state.getCells()[x].length; y++) {
                if (state.getCells()[x][y].isMined()) System.out.print("*");
                else System.out.print("-");
                System.out.print(" ");
            }
            System.out.println();
        }
    }

}
