package phd.apps.matrix.service.impl;

import phd.apps.matrix.service.MatrixMultiplier;

public class SimpleMatrixMultiplier implements MatrixMultiplier {
    @Override
    public int[][] multiply(int[][] A, int[][] B, int m, int n, int q) {
        int[][] result = new int[m][q];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < q; j++) {
                for (int k = 0; k < n; k++) {
                    result[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return result;
    }
}