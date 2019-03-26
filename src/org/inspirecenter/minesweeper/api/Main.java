package org.inspirecenter.minesweeper.api;

import org.inspirecenter.minesweeper.api.Model.*;
import org.inspirecenter.minesweeper.api.Util.StatePrinter;

public class Main {

    public static void main(String[] args) {

        try {

            GameSpecification gameSpecification = new GameSpecification(1, 10, 10, Difficulty.EASY);
            Game game = new Game(gameSpecification);
            Player player1 = new Player("Alpha");
            Player player2 = new Player("Bravo");
            game.addPlayer(player1);
            game.addPlayer(player2);

            System.out.println("== FULL STATE ==");
            StatePrinter.print(game.getFullGameState());

            PartialGameState partialGameState = new PartialGameState(3, 3, 6, 6, game.getFullGameState());

            System.out.println("== PARTIAL STATE ==");
            StatePrinter.print(partialGameState);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
