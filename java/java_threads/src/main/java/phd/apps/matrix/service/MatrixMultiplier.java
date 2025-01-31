package phd.apps.matrix.service;

import java.util.concurrent.ExecutionException;

public interface MatrixMultiplier {

    int[][] multiply(int[][] A, int[][] B, int size) throws ExecutionException, InterruptedException;
}

