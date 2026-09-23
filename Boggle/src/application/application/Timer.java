package application; 
/**
 * author: Yao Xiao
 * 2024-01-06
 */
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * referenced from  https://github.com/c05mic/pause-resume-timer/blob/master/src/com/c05mic/timer/Timer.java
 */



public class Timer {
    private long timeLimit;
    private ScheduledExecutorService executorService;
    private boolean remaining;
    private long remainingTime; // to store remaining time if paused
    private long startTime; // to store the start time
    private TimerListener listener;


    public Timer(long timeLimit, Timer TimerListener) {
        this.timeLimit = timeLimit;
        this.remaining = false;
        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    // Start the timer
    public void start() {
        startTime = System.currentTimeMillis();
        remaining = true;
        executorService.schedule(() -> {
            remaining = false;
            executorService.shutdown(); // Shutdown the executor after the time limit
        }, timeLimit, TimeUnit.MILLISECONDS);
    }

    // Pause the timer
    public void pause() {
        remaining = false;
        executorService.shutdownNow(); // Interrupt the timer thread to pause it
        remainingTime = timeLimit - (System.currentTimeMillis() - startTime);
    }

    // Resume the timer
    public void resume() {
        if (!remaining) {
            // Start the timer with the remaining time
            executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(() -> {
                remaining = false;
                executorService.shutdown(); // Shutdown the executor after the time limit
            }, remainingTime, TimeUnit.MILLISECONDS);
            remaining = true;
            startTime = System.currentTimeMillis(); // Update the start time
        }
    }

    // Check if time is up
    public boolean isTimeUp() {
        return !remaining;
    }
}