package phd.apps;

import phd.apps.config.ThreadsConfig;
import phd.apps.matrix.service.MatrixMultiplier;
import phd.apps.matrix.service.impl.ThreadSafeMatrixMultiplier;
import phd.apps.matrix.util.MatrixUtil;

import java.util.Scanner;

class MatrixMultiplication {
    private static int[][] A;
    private static int[][] B;
    private static int[][] C;
    private static int[][] D;
    private static int[][] result1;
    private static int[][] result2;

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el tamaño de las matrices: ");
        int size = scanner.nextInt();

        A = MatrixUtil.generateMatrix(size);
        B = MatrixUtil.generateMatrix(size);
        C = MatrixUtil.generateMatrix(size);
        D = MatrixUtil.generateMatrix(size);
        result1 = new int[size][size];
        result2 = new int[size][size];

        System.out.println("Multiplicando matrices en paralelo...");
        MatrixMultiplier multiplier = new ThreadSafeMatrixMultiplier(ThreadsConfig.NUM_THREADS, size);

        int[][] tmpResult1 = new int[size][size];

        long startTime = System.currentTimeMillis();
        multiplier.multiply(A, B, tmpResult1);

        System.out.println("Tiempo de ejecución concurrente individual de A * B: " + (System.currentTimeMillis() - startTime) + " ms");
        MatrixUtil.printMatrix("Resultado de A * B", tmpResult1);

        startTime = System.currentTimeMillis();
        Thread t1 = new Thread(() -> multiplier.multiply(A, B, result1));
        Thread t2 = new Thread(() -> multiplier.multiply(C, D, result2));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Tiempo de ejecución concurrente: " + (System.currentTimeMillis() - startTime) + " ms");
        MatrixUtil.printMatrix("Resultado de A * B", result1);
        MatrixUtil.printMatrix("Resultado de C * D", result2);

        int[][] finalResult = new int[size][size];
        startTime = System.currentTimeMillis();

        multiplier.multiply(A, B, result1);
        multiplier.multiply(result1, C, finalResult);

        System.out.println("Tiempo de ejecución para (A*B)*C: " + (System.currentTimeMillis() - startTime) + " ms");
        MatrixUtil.printMatrix("Resultado de (A * B) * C", finalResult);
    }
}
