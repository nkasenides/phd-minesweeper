package org.inspirecenter.minesweeper.api;

import org.inspirecenter.minesweeper.api.API.LocalMasterService;
import org.inspirecenter.minesweeper.api.API.LocalUserService;
import org.inspirecenter.minesweeper.api.Model.*;
import org.inspirecenter.minesweeper.api.Util.StatePrinter;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static final Scanner SCANNER;

    static { SCANNER = new Scanner(System.in); }

    public static void main(String[] args) {

        try {

//            GameSpecification gameSpecification = new GameSpecification(1, 10, 10, Difficulty.EASY);
//            Game game = new Game(gameSpecification);
//            Player player1 = new Player("Alpha");
//            Player player2 = new Player("Bravo");
//            game.addPlayer(player1);
//            game.addPlayer(player2);
//
//            System.out.println("== FULL STATE ==");
//            StatePrinter.print(game.getFullGameState());
//
//            PartialGameState partialGameState = new PartialGameState(3, 3, 6, 6, game.getFullGameState());
//
//            System.out.println("== PARTIAL STATE ==");
//            StatePrinter.print(partialGameState);

            LocalMasterService masterService = new LocalMasterService();
            LocalUserService userService = new LocalUserService();

            Player player1 = new Player("Alpha");

            System.out.println("~~~~~ CREATE GAMES ~~~~~");
            System.out.println(masterService.createGame(1, 10, 10, Difficulty.EASY) + " created.");
            System.out.println(masterService.createGame(1, 10, 10, Difficulty.MEDIUM) + " created.");

            System.out.println("~~~~~ LIST GAMES ~~~~~");
            ArrayList<Game> games = masterService.listGames();
            for (Game g : games) {
                System.out.println("[ " + g.getGameSpecification().getToken() + " ]");
            }

            System.out.println("~~~~~ JOIN GAME ~~~~~");
            System.out.print("Enter game to join: ");
            String input = SCANNER.nextLine();
            //String sessionIDx = masterService.join(input, new Player("Bravo").getName(), new PartialStatePreference(5, 5));
            String sessionID = masterService.join(input, player1.getName(), new PartialStatePreference(5, 5));
            if (sessionID == null) {
                System.out.println("Error joining game '" + input + "'.");
            }
            else {
                System.out.println("Player '" + player1.getName() + "' joined game '" + input + "'.");
            }

            System.out.println("~~~~~ SHOW STATE (PARTIAL) AT INITIAL POSITION (0,0) ~~~~~");
            PartialGameState state = userService.getPartialState(sessionID);
            StatePrinter.print(state);

            System.out.println("~~~~~ SHIFT POSITION RIGHT (0,1) ~~~~~");
            state = userService.move(sessionID, Direction.RIGHT);
            StatePrinter.print(state);

            System.out.println("~~~~~ SHIFT POSITION DOWN (1,1) ~~~~~");
            state = userService.move(sessionID, Direction.DOWN);
            StatePrinter.print(state);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
