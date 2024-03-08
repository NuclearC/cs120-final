package game.experimental.utils;

import java.time.*;


/**
 * Represents a monotonic Clock. 
 */
public final class Clock {
    
    private static Clock INSTANCE = new Clock();

    private Instant steadyBegin;

    private Clock() {
        this.steadyBegin = Instant.now();
    }

    /**
     * Get the time point at the current Instant. 
     * @return the time point in seconds
     */
    public float getTimePoint() {
        Duration elapsedTime = Duration.between(steadyBegin, Instant.now());
        float elapsedTimeFl = (float)(elapsedTime.toNanos() / 1000) / 1.e6f;

        return elapsedTimeFl;
    }

    /**
     * Get the time point at the current Instant. 
     * @return the time point in seconds
     */
    public static float now() {
        return INSTANCE.getTimePoint();
    }

}
