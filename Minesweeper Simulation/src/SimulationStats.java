import Measurements.LatencyMeasurement;

public class SimulationStats {

    private long startTime;
    private long endTime;
    private LatencyMeasurement maximumLatency;
    private LatencyMeasurement minimumLatency;
    private long averageLatency;
    private long setupStartTime;
    private long setupEndTime;

    public SimulationStats(long startTime, long endTime, LatencyMeasurement maximumLatency, LatencyMeasurement minimumLatency, long averageLatency, long setupStartTime, long setupEndTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.maximumLatency = maximumLatency;
        this.minimumLatency = minimumLatency;
        this.averageLatency = averageLatency;
        this.setupStartTime = setupStartTime;
        this.setupEndTime = setupEndTime;
    }

    public SimulationStats() {
        this(0, 0, new LatencyMeasurement(0, 0), new LatencyMeasurement(0, 0), 0, 0, 0);
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public LatencyMeasurement getMaximumLatency() {
        return maximumLatency;
    }

    public void setMaximumLatency(LatencyMeasurement maximumLatency) {
        this.maximumLatency = maximumLatency;
    }

    public LatencyMeasurement getMinimumLatency() {
        return minimumLatency;
    }

    public void setMinimumLatency(LatencyMeasurement minimumLatency) {
        this.minimumLatency = minimumLatency;
    }

    public long getAverageLatency() {
        return averageLatency;
    }

    public void setAverageLatency(long averageLatency) {
        this.averageLatency = averageLatency;
    }

    public long getTimeTaken() {
        return endTime - startTime;
    }

    public long getSetupStartTime() {
        return setupStartTime;
    }

    public void setSetupStartTime(long setupStartTime) {
        this.setupStartTime = setupStartTime;
    }

    public long getSetupEndTime() {
        return setupEndTime;
    }

    public void setSetupEndTime(long setupEndTime) {
        this.setupEndTime = setupEndTime;
    }
}
