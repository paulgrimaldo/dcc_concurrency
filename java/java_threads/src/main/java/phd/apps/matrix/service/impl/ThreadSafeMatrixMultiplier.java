package phd.apps.matrix.service.impl;

import phd.apps.matrix.service.MatrixMultiplier;
import phd.apps.matrix.thread.MatrixTask;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeMatrixMultiplier implements MatrixMultiplier {
    private static final ReentrantLock lock = new ReentrantLock();
    private final int numThreads;
    private final int size;

    public ThreadSafeMatrixMultiplier(int numThreads, int size) {
        this.numThreads = numThreads;
        this.size = size;
    }

    @Override
    public void multiply(int[][] A, int[][] B, int[][] result) {
        Thread[] threads = new Thread[this.numThreads];
        for (int t = 0; t < this.numThreads; t++) {
            threads[t] = new Thread(new MatrixTask(A, B, result, t, this.numThreads, size, lock));
            threads[t].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
