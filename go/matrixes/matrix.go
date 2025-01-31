package matrixes

import (
	"fmt"
	"math/rand"
	"sync"
)

func PrintMatrix(matrix [][]int) {
	if len(matrix) >= 10 {
		size := len(matrix)
		fmt.Printf("Matriz de %dx%d \n", size, size)
		return
	}
	for i := 0; i < len(matrix); i++ {
		for j := 0; j < len(matrix[i]); j++ {
			fmt.Printf("%d ", matrix[i][j])
		}
		fmt.Println()
	}
}

func GenerateMatrix(size int) [][]int {
	matrix := make([][]int, size)
	for i := range matrix {
		matrix[i] = make([]int, size)
		for j := range matrix[i] {
			matrix[i][j] = rand.Intn(10)
		}
	}
	return matrix
}

func MultiplyMatricesConcurrent(A, B [][]int, size int) [][]int {
	result := make([][]int, size)
	for i := range result {
		result[i] = make([]int, size)
	}

	tasks := make(chan Task, size)
	var wg sync.WaitGroup

	for i := 0; i < NumWorkers; i++ {
		wg.Add(1)
		go worker(tasks, &wg)
	}

	for i := 0; i < size; i++ {
		tasks <- Task{Row: i, A: A, B: B, Result: result, Size: size}
	}
	close(tasks)

	wg.Wait()
	return result
}

func MultiplyMatrices(A, B [][]int, size int) [][]int {
	result := make([][]int, size)
	for i := range result {
		result[i] = make([]int, size)
		for j := range result[i] {
			sum := 0
			for k := 0; k < size; k++ {
				sum += A[i][k] * B[k][j]
			}
			result[i][j] = sum
		}
	}
	return result
}
