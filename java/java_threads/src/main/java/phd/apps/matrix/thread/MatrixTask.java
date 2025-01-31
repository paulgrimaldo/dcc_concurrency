package phd.apps.matrix.thread;

import java.util.concurrent.locks.ReentrantLock;

public class MatrixTask implements Runnable {
    private final int[][] A;
    private final int[][] B;
    private final int[][] result;
    private final int threadID;
    private final int numThreads;
    private final int size;
    private final ReentrantLock lock;

    public MatrixTask(int[][] A, int[][] B, int[][] result, int threadID, int numThreads, int size, ReentrantLock lock) {
        this.A = A;
        this.B = B;
        this.result = result;
        this.threadID = threadID;
        this.numThreads = numThreads;
        this.size = size;
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = threadID * size / numThreads; i < (threadID + 1) * size / numThreads; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    lock.lock();
                    try {
                        result[i][j] += A[i][k] * B[k][j];
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
    }
}
