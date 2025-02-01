package matrixes

import (
	"sync"
)

func worker(tasks <-chan Task, wg *sync.WaitGroup) {
	defer wg.Done()
	for task := range tasks {
		for j := 0; j < task.Cols; j++ {
			for k := 0; k < task.Size; k++ {
				task.Result[task.Row][j] += task.A[task.Row][k] * task.B[k][j]
			}
		}
	}
}
