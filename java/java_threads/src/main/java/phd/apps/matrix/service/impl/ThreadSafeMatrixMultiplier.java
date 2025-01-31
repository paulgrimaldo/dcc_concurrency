package phd.apps.matrix.service.impl;

import phd.apps.matrix.service.MatrixMultiplier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeMatrixMultiplier implements MatrixMultiplier {
    private static final ReentrantLock lock = new ReentrantLock();
    private final int numThreads;

    public ThreadSafeMatrixMultiplier(int numThreads) {
        this.numThreads = numThreads;
    }

    @Override
    public int[][] multiply(int[][] A, int[][] B, int size) throws ExecutionException, InterruptedException {
        int[][] result = new int[size][size];
        ExecutorService executor = Executors.newFixedThreadPool(this.numThreads);
        List<Future<Void>> futures = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            final int row = i;
            futures.add(executor.submit(() -> {
                for (int j = 0; j < size; j++) {
                    for (int k = 0; k < size; k++) {
                        result[row][j] += A[row][k] * B[k][j];
                    }
                }
                return null;
            }));
        }

        for (Future<Void> future : futures) {
            future.get();
        }

        executor.shutdown();
        return result;
    }
}
