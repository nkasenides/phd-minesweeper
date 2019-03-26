package org.inspirecenter.minesweeper.api;

import org.inspirecenter.minesweeper.api.Exception.InvalidGameSpecificationException;
import org.inspirecenter.minesweeper.api.Model.Difficulty;
import org.inspirecenter.minesweeper.api.Model.Game;
import org.inspirecenter.minesweeper.api.Model.GameSpecification;
import org.inspirecenter.minesweeper.api.Model.Player;

public class Main {

    public static void main(String[] args) {

        try {

            GameSpecification gameSpecification = new GameSpecification(1, 10, 10, Difficulty.EASY);
            Game game = new Game(gameSpecification);
            Player player1 = new Player("Alpha");
            Player player2 = new Player("Bravo");
            game.addPlayer(player1);
            game.addPlayer(player2);
            game.printAsMatrix();

        } catch (InvalidGameSpecificationException e) {
            e.printStackTrace();
        }


    }

}
