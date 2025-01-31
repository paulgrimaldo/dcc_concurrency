package phd.apps.matrix.util;

import java.util.Random;

public class MatrixUtil {
    private MatrixUtil (){
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

    public static void printMatrix(String message, int[][] matrix) {
        System.out.println(message);
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }
}
