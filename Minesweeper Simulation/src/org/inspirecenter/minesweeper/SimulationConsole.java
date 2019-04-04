package org.inspirecenter.minesweeper;

import org.inspirecenter.minesweeper.API.LocalMasterService;
import org.inspirecenter.minesweeper.API.LocalUserService;
import org.inspirecenter.minesweeper.Model.Exception.InvalidGameSpecificationException;

import java.util.Scanner;

public class SimulationConsole {

    public static final Runtime RUNTIME = Runtime.getRuntime();
    public static final Scanner SCANNER = new Scanner(System.in);
    public static final String USE_INSTRUCTION = "Use: totalWidth totalHeight partialWidth partialHeight numOfPlayers (where all parameters are unsigned integers).";
    public static final LocalUserService USER_SERVICE;
    public static final LocalMasterService MASTER_SERVICE;

    static {
        USER_SERVICE = new LocalUserService();
        MASTER_SERVICE = new LocalMasterService();
    }

    public static final SimulationManager simulationManager = new SimulationManager();


    public static void main(String[] args) {

        printHeader();

        if (args.length != 0) {
            //TODO Args can be used later to carry out multiple simulations at once.
        }
        else {
            System.out.println(USE_INSTRUCTION);
            System.out.print("Enter input: ");
            String input = SCANNER.nextLine();
            String[] options = input.split(" ");

            int totalWidth = 0;
            int totalHeight = 0;
            int partialWidth = 0;
            int partialHeight = 0;
            int numOfPlayers = 0;

            if (options.length == 5) {

                //Add simulations:
                try {
                    totalWidth = Integer.parseInt(options[0]);
                    totalHeight = Integer.parseInt(options[1]);
                    partialWidth = Integer.parseInt(options[2]);
                    partialHeight = Integer.parseInt(options[3]);
                    numOfPlayers = Integer.parseInt(options[4]);
                    try {
                        LocalSimulation localSimulation = new LocalSimulation(totalWidth, totalHeight, partialWidth, partialHeight, numOfPlayers);
                        if (simulationManager.addSimulation(localSimulation)) {
                            System.out.println("org.inspirecenter.minesweeper.Simulation added: totalWidth=" + totalWidth + ", totalHeight=" + totalHeight + ", partialWidth=" + partialWidth + ", partialHeight=" + partialHeight + ", numOfPlayers=" + numOfPlayers);
                        }
                        else {
                            System.out.println("ERROR: Failed to add simulation.");
                        }
                    }
                    catch (InvalidGameSpecificationException e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                }
                catch (NumberFormatException e) {
                    System.out.println(USE_INSTRUCTION);
                }

                //Run simulations:
                System.out.println("Running simulations...");
                if (simulationManager.getSimulations().size() > 0) {
                    simulationManager.runAll();
                }
                else {
                    System.out.println("ERROR: No simulations found to run.");
                }

                //Present statistics:
                for (int i = 0; i < simulationManager.getSimulations().size(); i++) {
                    printSimulation(i);
                }
                printAllResults();

            }
            else {
                System.out.println(USE_INSTRUCTION);
            }

        }

    }

    public static String formatMemoryNumber(long memory) {
        return memory / 1048576 + " MB";
    }

    public static void printHeader() {
        System.out.println("~~~ STARTING MINESWEEPER SIMULATION ~~~");
        System.out.println("Total RAM (VM): " + formatMemoryNumber(RUNTIME.totalMemory()));
        System.out.println("Available RAM: " + formatMemoryNumber(RUNTIME.maxMemory()));
        System.out.println("Used RAM: " + formatMemoryNumber(RUNTIME.totalMemory() - RUNTIME.freeMemory()));
        System.out.println("Free RAM: " + formatMemoryNumber(RUNTIME.freeMemory()));
    }

    public static void printAllResults() {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("                                RESULTS (ALL)                                    ");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Simulations ran: " + simulationManager.getSimulationsRan());
        System.out.println("Started on: " + simulationManager.getTimeStarted());
        System.out.println("Ended on: " + simulationManager.getTimeEnded());
        System.out.println("Initialization time: " + simulationManager.getInitializationTimeTaken());
        System.out.println("Run time: " + simulationManager.getRunTimeTaken());
        System.out.println("Average latency: " + simulationManager.getAverageLatency() + "ms");
        System.out.println("Minimum latency: " + simulationManager.getMinLatency() + "ms");
        System.out.println("Maximum latency: " + simulationManager.getMaxLatency() + "ms");
        System.out.println("---------------------------------------------------------------------------------");
    }

    public static void printSimulation(int index) {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("        RESULTS (" + (index+1) + "/" + simulationManager.getSimulations().size() + ")");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("Started on: " + SimulationManager.formatAsSimpleDate(simulationManager.getSimulations().get(index).getSimulationStats().getRunStartTime()));
        System.out.println("Ended on: " + SimulationManager.formatAsSimpleDate(simulationManager.getSimulations().get(index).getSimulationStats().getRunEndTime()));
        System.out.println("Initialization time: " + (simulationManager.getSimulations().get(index).getSimulationStats().getInitEndTime() - simulationManager.getSimulations().get(index).getSimulationStats().getInitStartTime()) + "ms");
        System.out.println("Run time: " + (simulationManager.getSimulations().get(index).getSimulationStats().getRunEndTime() - simulationManager.getSimulations().get(index).getSimulationStats().getRunStartTime()) + "ms");
        System.out.println("Average latency: " + simulationManager.getSimulations().get(index).getSimulationStats().getAverageLatency() + "ms");
        System.out.println("Minimum latency: " + simulationManager.getSimulations().get(index).getSimulationStats().getMinimumLatency().getLatency() + "ms");
        System.out.println("Maximum latency: " + simulationManager.getSimulations().get(index).getSimulationStats().getMaximumLatency().getLatency() + "ms");
        System.out.println("---------------------------------------------------------------------------------");
    }

}
