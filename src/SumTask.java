public class SumTask implements Runnable {

    private final int[] data;
    private final int start;
    private final int end;
    private long partialSum;

    public SumTask(int[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += data[i];
        }
        partialSum = sum;
        System.out.println(Thread.currentThread().getName() + " completed: sum = " + sum);
    }

    public long getPartialSum() {
        return partialSum;
    }
}
