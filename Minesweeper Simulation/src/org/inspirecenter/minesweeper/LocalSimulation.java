package org.inspirecenter.minesweeper;

import org.inspirecenter.minesweeper.Measurements.SimulationMeasurementBundle;
import org.inspirecenter.minesweeper.Model.Exception.InvalidGameSpecificationException;

public class LocalSimulation extends Simulation {

    public LocalSimulation(int totalWidth, int totalHeight, int partialWidth, int partialHeight, int numOfPlayers) throws InvalidGameSpecificationException {
        super(totalWidth, totalHeight, partialWidth, partialHeight, numOfPlayers);
    }

    @Override
    public void initializeGame() {
        //DO NOTHING ELSE HERE...
    }

    @Override
    public SimulationMeasurementBundle runGame() {
        SimulationMeasurementBundle bundle = new SimulationMeasurementBundle();
        long lastTime = System.currentTimeMillis();

//        //TODO - Just sleep for now, this will implement the solver later.
//        for (int i = 100; i < 1000; i+= 100) {
//            try {
//                Thread.sleep(i);
//                bundle.increaseTicks();
//                bundle.addLatencyMeasurement(new LatencyMeasurement(System.currentTimeMillis(), System.currentTimeMillis() - lastTime));
//                lastTime = System.currentTimeMillis();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }


        return bundle;
    }

}
