import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private final int poolSize;
    private final List<Worker> workers;
    private final LinkedList<Runnable> taskQueue;
    private boolean isStopped = false;

    public ThreadPool(int poolSize) {
        this.poolSize = poolSize;
        this.taskQueue = new LinkedList<>();
        this.workers = new LinkedList<>();
    }

    public void startWorkers() {
        for (int i = 0; i < poolSize; i++) {
            Worker worker = new Worker();
            Thread thread = new Thread(worker, "Worker-" + i);
            thread.start();
            workers.add(worker);
        }
    }

    public synchronized void execute(Runnable task) {
        if (isStopped) {
            throw new IllegalStateException("ThreadPool has been stopped");
        }
        taskQueue.addLast(task);
        notify(); // Wake up a worker
    }

    public synchronized Runnable getNextTask() throws InterruptedException {
        while (taskQueue.isEmpty() && !isStopped) {
            wait();
        }
        if (isStopped && taskQueue.isEmpty()) {
            return null;
        }
        return taskQueue.removeFirst();
    }

    public synchronized void stop() {
        isStopped = true;
        notifyAll(); // Wake up all workers to exit
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Runnable task = getNextTask();
                    if (task == null) break; // No more tasks and pool stopped
                    task.run();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
