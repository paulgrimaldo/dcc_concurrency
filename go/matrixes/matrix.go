package matrixes

import (
	"fmt"
	"math/rand"
	"sync"
)

func PrintMatrix(matrix [][]int) {
	if len(matrix) >= 10 {
		m := len(matrix)
		n := len(matrix[0])
		fmt.Printf("Matriz de %dx%d \n", m, n)
		return
	}
	for i := 0; i < len(matrix); i++ {
		for j := 0; j < len(matrix[i]); j++ {
			fmt.Printf("%d ", matrix[i][j])
		}
		fmt.Println()
	}
}

func GenerateMatrix(rows, cols int) [][]int {
	matrix := make([][]int, rows)
	for i := range matrix {
		matrix[i] = make([]int, cols)
		for j := range matrix[i] {
			matrix[i][j] = rand.Intn(10)
		}
	}
	return matrix
}

func MultiplyMatricesConcurrent(A, B [][]int, m, n, q int) [][]int {
	result := make([][]int, m)
	for i := range result {
		result[i] = make([]int, q)
	}

	tasks := make(chan Task, m)
	var wg sync.WaitGroup

	for i := 0; i < NumWorkers; i++ {
		wg.Add(1)
		go worker(tasks, &wg)
	}

	for i := 0; i < m; i++ {
		tasks <- Task{Row: i, A: A, B: B, Result: result, Size: n, Cols: q}
	}
	close(tasks)

	wg.Wait()
	return result
}

func MultiplyMatrices(A, B [][]int, m, n, q int) [][]int {
	result := make([][]int, m)
	for i := range result {
		result[i] = make([]int, q)
		for j := range result[i] {
			sum := 0
			for k := 0; k < n; k++ {
				sum += A[i][k] * B[k][j]
			}
			result[i][j] = sum
		}
	}
	return result
}
