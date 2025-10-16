import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int dataSize = 10_000_000;
        int numThreads = 10;
        int[] data = new int[dataSize];
        Random rand = new Random();

        // Fill with random integers
        for (int i = 0; i < dataSize; i++) {
            data[i] = rand.nextInt(100);
        }

        ThreadPool pool = new ThreadPool(numThreads);
        pool.startWorkers();
        List<SumTask> tasks = new ArrayList<>();

        // Divide work into 100 chunks (so more tasks than threads)
        int chunkSize = dataSize / 100;
        for (int i = 0; i < dataSize; i += chunkSize) {
            int end = Math.min(i + chunkSize, dataSize);
            SumTask task = new SumTask(data, i, end);
            tasks.add(task);
            pool.execute(task);
        }

        // Wait for all tasks to finish (simple delay for demo)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Stop the pool gracefully
        pool.stop();

        // Compute total sum from partial results
        long total = 0;
        for (SumTask task : tasks) {
            total += task.getPartialSum();
        }

        System.out.println("\nFinal total sum = " + Helpers.formatNumber(total));
    }
}
