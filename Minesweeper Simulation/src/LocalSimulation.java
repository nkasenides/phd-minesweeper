import Measurements.SimulationMeasurementBundle;
import org.inspirecenter.minesweeper.api.Exception.InvalidGameSpecificationException;

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
        //TODO
        return bundle;
    }

}
