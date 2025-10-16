public class SumTask implements Runnable {

    private final String name;

    private final int[] data;
    private final int start;
    private final int end;
    private long partialSum;

    public SumTask(int[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
        this.partialSum = 0;

        this.name = "SumTask = " + Helpers.formatNumber(start) + " - " + Helpers.formatNumber(end);
    }

    @Override
    public void run() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += data[i];
        }
        partialSum = sum;
        System.out.printf("%-15s %-40s %-15s%n",
            Thread.currentThread().getName(),
            name,
            "sum = " + Helpers.formatNumber(sum));
    }

    public long getPartialSum() {
        return partialSum;
    }
}
