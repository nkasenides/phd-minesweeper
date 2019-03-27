package org.inspirecenter.minesweeper.api.Util;

import org.inspirecenter.minesweeper.api.Model.GameState;

public class StatePrinter {

    public static void print(GameState state) {
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
