package phd.apps;

import phd.apps.config.ThreadsConfig;
import phd.apps.matrix.service.MatrixMultiplier;
import phd.apps.matrix.service.impl.SimpleMatrixMultiplier;
import phd.apps.matrix.service.impl.ThreadSafeMatrixMultiplier;
import phd.apps.matrix.util.MatrixUtil;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class MatrixMultiplication {
    private static int[][] A;
    private static int[][] B;
    private static int[][] C;
    private static int[][] D;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el tamaño de las matrices aleatorias: ");
        int size = scanner.nextInt();
        runStaticTests();
        runRandomTests(size);
    }

    private static void runStaticTests() throws ExecutionException, InterruptedException {
        System.out.println("Matrices estáticas");
        A = MatrixUtil.generateMatrixA();
        B = MatrixUtil.generateMatrixB();

        MatrixUtil.printMatrix("Matriz A:", A);
        MatrixUtil.printMatrix("Matriz B:", B);

        System.out.println("Multiplicando matrices sin paralelismo");
        MatrixMultiplier multiplier = new SimpleMatrixMultiplier();
        long startTime = System.currentTimeMillis();
        int[][] result = multiplier.multiply(A, B, 10);
        System.out.println("Tiempo de ejecución de A * B: " + (System.currentTimeMillis() - startTime) + " ms");
        MatrixUtil.printMatrix("Resultado de A * B", result);

        System.out.println("Multiplicando matrices estáticas en paralelo");
        MatrixMultiplier simpleMultiplier = new ThreadSafeMatrixMultiplier(ThreadsConfig.NUM_THREADS);
        startTime = System.currentTimeMillis();
        result = simpleMultiplier.multiply(A, B, 10);
        System.out.println("Tiempo de ejecución concurrente de A * B: " + (System.currentTimeMillis() - startTime) + " ms");
        MatrixUtil.printMatrix("Resultado de A * B", result);
    }

    private static void runRandomTests(int size) throws InterruptedException, ExecutionException {
        System.out.println("Matrices generadas aleatoriamente");
        A = MatrixUtil.generateMatrix(size);
        B = MatrixUtil.generateMatrix(size);
        C = MatrixUtil.generateMatrix(size);
        D = MatrixUtil.generateMatrix(size);

        System.out.println("Multiplicando matrices en paralelo...");
        MatrixMultiplier concurrentMultiplier = new ThreadSafeMatrixMultiplier(ThreadsConfig.NUM_THREADS);

        int[][] tmpResult1 = new int[size][size];
        long startTime = System.currentTimeMillis();
        System.out.println("Tiempo de ejecución concurrente individual de A * B: " + (System.currentTimeMillis() - startTime) + " ms");
        MatrixUtil.printMatrix("Resultado de A * B", tmpResult1);

        startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<?> future1 = executorService.submit(() -> {
            try {
                concurrentMultiplier.multiply(A, B, size);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Future<?> future2 = executorService.submit(() -> {
            try {
                concurrentMultiplier.multiply(C, D, size);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        future1.get();
        future2.get();

        executorService.shutdown();

        System.out.println("Tiempo de ejecución concurrente (A,B,C,D): " + (System.currentTimeMillis() - startTime) + " ms");

        startTime = System.currentTimeMillis();
        int[][] finalResult = concurrentMultiplier.multiply(concurrentMultiplier.multiply(A, B, size), C, size);

        System.out.println("Tiempo de ejecución para (A*B)*C: " + (System.currentTimeMillis() - startTime) + " ms");
        MatrixUtil.printMatrix("Resultado de (A * B) * C", finalResult);

        System.out.println("-----Comparación concurrente vs local-----");
        startTime = System.currentTimeMillis();
        MatrixMultiplier simpleMultiplier = new SimpleMatrixMultiplier();
        simpleMultiplier.multiply(A, B, size);
        System.out.println("Tiempo de ejecución local: " + (System.currentTimeMillis() - startTime) + " ms");
        System.out.println();

        startTime = System.currentTimeMillis();
        concurrentMultiplier.multiply(A, B, size);
        System.out.println("Tiempo de ejecución concurrente: " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
