package org.inspirecenter.minesweeper.api;

import org.inspirecenter.minesweeper.api.API.JoinBundle;
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
    public static int currentGameWidth;
    public static int currentGameHeight;

    static {
        SCANNER = new Scanner(System.in);
        MASTER_SERVICE = new LocalMasterService();
        USER_SERVICE = new LocalUserService();
    }

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            Player player1 = new Player("Alpha");

            System.out.println("~~~~~ CREATE GAMES ~~~~~");
            System.out.println(MASTER_SERVICE.createGame(1, 10, 10, Difficulty.EASY) + " created.");
            System.out.println(MASTER_SERVICE.createGame(2, 5, 5, Difficulty.MEDIUM) + " created.");

            System.out.println("~~~~~ LIST GAMES ~~~~~");
            ArrayList<Game> games = MASTER_SERVICE.listGames();
            for (Game g : games) {
                System.out.println("[ " + g.getGameSpecification().getToken() + " ]");
            }

            System.out.println("~~~~~ JOIN GAME ~~~~~");
            System.out.print("Enter game to join: ");
            String input = SCANNER.nextLine();

//            String sessionIDx = MASTER_SERVICE.join(input, player1.getName(), new PartialStatePreference(5, 5));
            JoinBundle bundle = MASTER_SERVICE.join(input, player1.getName(), new PartialStatePreference(5, 5));
            sessionID = bundle.getSessionID();
            currentGameWidth = bundle.getWidth();
            currentGameHeight = bundle.getHeight();

            if (sessionID == null) {
                System.out.println("Error joining game '" + input + "'.");
            }
            else {
                System.out.println("Player '" + player1.getName() + "' joined game '" + input + "'.");

                PartialBoardState state = USER_SERVICE.getPartialState(sessionID).getPartialGameState();

                System.out.println("Printing state... (CHEATER!!!)");
                StatePrinter.print(state);

                GameForm application = new GameForm(new PartialStatePreference(5,5));
                application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
