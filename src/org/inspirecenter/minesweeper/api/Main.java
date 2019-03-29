package org.inspirecenter.minesweeper.api;

import org.inspirecenter.minesweeper.api.API.LocalMasterService;
import org.inspirecenter.minesweeper.api.API.LocalUserService;
import org.inspirecenter.minesweeper.api.Model.*;
import org.inspirecenter.minesweeper.api.UI.GameForm;
import org.inspirecenter.minesweeper.api.Util.StatePrinter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static final Scanner SCANNER;
    public static final LocalUserService USER_SERVICE;
    public static final LocalMasterService MASTER_SERVICE;
    public static String sessionID;

    static {
        SCANNER = new Scanner(System.in);
        MASTER_SERVICE = new LocalMasterService();
        USER_SERVICE = new LocalUserService();
    }

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

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

            Player player1 = new Player("Alpha");

            System.out.println("~~~~~ CREATE GAMES ~~~~~");
            System.out.println(MASTER_SERVICE.createGame(1, 10, 10, Difficulty.EASY) + " created.");
            System.out.println(MASTER_SERVICE.createGame(2, 10, 10, Difficulty.MEDIUM) + " created.");

            System.out.println("~~~~~ LIST GAMES ~~~~~");
            ArrayList<Game> games = MASTER_SERVICE.listGames();
            for (Game g : games) {
                System.out.println("[ " + g.getGameSpecification().getToken() + " ]");
            }

            System.out.println("~~~~~ JOIN GAME ~~~~~");
            System.out.print("Enter game to join: ");
            String input = SCANNER.nextLine();
//            String sessionIDx = MASTER_SERVICE.join(input, player1.getName(), new PartialStatePreference(5, 5));
            sessionID = MASTER_SERVICE.join(input, player1.getName(), new PartialStatePreference(5, 5));
            if (sessionID == null) {
                System.out.println("Error joining game '" + input + "'.");
            }
            else {
                System.out.println("Player '" + player1.getName() + "' joined game '" + input + "'.");

                System.out.println("~~~~~ SHOW STATE (PARTIAL) AT INITIAL POSITION (0,0) ~~~~~");
                PartialGameState state = USER_SERVICE.getPartialState(sessionID);
                StatePrinter.print(state);

                System.out.println("~~~~~ SHIFT POSITION RIGHT (0,1) ~~~~~");
                state = USER_SERVICE.move(sessionID, Direction.RIGHT);
                StatePrinter.print(state);

                System.out.println("~~~~~ SHIFT POSITION DOWN (1,1) ~~~~~");
                state = USER_SERVICE.move(sessionID, Direction.DOWN);
                StatePrinter.print(state);

                System.out.println("~~~~~ SHIFT POSITION BACK TO NORMAL (0,0) ~~~~~");
                state = USER_SERVICE.move(sessionID, Direction.LEFT);
                state = USER_SERVICE.move(sessionID, Direction.UP);

                GameForm application = new GameForm(new PartialStatePreference(5,5), state);
                application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
