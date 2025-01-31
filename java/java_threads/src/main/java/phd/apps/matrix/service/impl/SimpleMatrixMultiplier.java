package phd.apps.matrix.service.impl;

import phd.apps.matrix.service.MatrixMultiplier;

public class SimpleMatrixMultiplier implements MatrixMultiplier {
    @Override
    public int[][] multiply(int[][] A, int[][] B, int size) {
        int[][] result = new int[size][size];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                for (int k = 0; k < B.length; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return result;
    }
}
