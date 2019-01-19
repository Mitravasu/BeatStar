public class StopWatch {
    private long stopTime;
    private long startTime;
    private long timeElapsed;

    public void stop(){
        stopTime = System.nanoTime();
    }

    public void start() {
        startTime = System.nanoTime();
    }

    public long getTimeElapsed() {
        timeElapsed = stopTime - startTime;
        return timeElapsed;
    }

    public long getStopTime() {
        return stopTime;
    }

    public long getStartTime() {
        return startTime;
    }
}
