package phd.apps.matrix.util;

import java.util.Random;

public class MatrixUtil {
    private MatrixUtil() {
        //Do nothing on purpose
    }

    public static int[][] generateMatrix(int size) {
        Random rand = new Random();
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rand.nextInt(10);
            }
        }
        return matrix;
    }

    public static int[][] generateMatrixA() {
        return new int[][]{
                {12, 45, 78, 34, 89, 56, 23, 91, 67, 42},
                {88, 53, 76, 11, 95, 39, 28, 64, 79, 31},
                {47, 92, 55, 82, 16, 70, 37, 50, 20, 99},
                {61, 84, 13, 97, 40, 22, 68, 74, 10, 58},
                {33, 77, 85, 66, 25, 59, 81, 36, 44, 90},
                {14, 98, 63, 21, 83, 30, 60, 18, 46, 87},
                {72, 49, 93, 35, 26, 55, 80, 29, 96, 17},
                {62, 15, 71, 41, 48, 94, 19, 32, 88, 75},
                {51, 86, 24, 99, 69, 58, 43, 54, 27, 65},
                {31, 73, 90, 38, 20, 79, 34, 11, 92, 53},
        };
    }

    public static int[][] generateMatrixB() {
        return new int[][]{
                {-12, 45, -78, 34, -89, 56, -23, 91, -67, 42},
                {88, -53, 76, -11, 95, -39, 28, -64, 79, -31},
                {-47, 92, -55, 82, -16, 70, -37, 50, -20, 99},
                {61, -84, 13, -97, 40, -22, 68, -74, 10, -58},
                {-33, 77, -85, 66, -25, 59, -81, 36, -44, 90},
                {14, -98, 63, -21, 83, -30, 60, -18, 46, -87},
                {-72, 49, -93, 35, -26, 55, -80, 29, -96, 17},
                {62, -15, 71, -41, 48, -94, 19, -32, 88, -75},
                {-51, 86, -24, 99, -69, 58, -43, 54, -27, 65},
                {31, -73, 90, -38, 20, -79, 34, -11, 92, -53},
        };
    }

    public static void printMatrix(String message, int[][] matrix) {
        if (matrix[0].length >= 10) {
            System.out.printf("Matriz de %d x %d%n", matrix[0].length, matrix[0].length);
            return;
        }
        System.out.println(message);
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }
}
