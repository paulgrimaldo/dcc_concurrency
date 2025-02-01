package phd.apps.matrix.service;

import java.util.concurrent.ExecutionException;

public interface MatrixMultiplier {

    int[][] multiply(int[][] A, int[][] B, int m, int n, int q) throws ExecutionException, InterruptedException;
}