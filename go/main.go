package main

import (
	"fmt"
	"sync"
	"time"

	m "github.com/paulgrimaldo/concurrency_examples/go/matrixes"
)

func main() {
	var size int
	fmt.Print("Ingrese el tamaño de las matrices: ")
	fmt.Scan(&size)

	A := m.GenerateMatrix(size)
	B := m.GenerateMatrix(size)
	C := m.GenerateMatrix(size)
	D := m.GenerateMatrix(size)
	fmt.Println("Matriz A:")
	m.PrintMatrix(A)

	fmt.Println("Matriz B:")
	m.PrintMatrix(B)

	fmt.Println("Matriz C:")
	m.PrintMatrix(C)

	fmt.Println("Matriz D:")
	m.PrintMatrix(D)

	fmt.Println("Multiplicando matrices en paralelo...")

	start := time.Now()
	var singleResult = m.MultiplyMatricesConcurrent(A, B, size)
	fmt.Printf("Tiempo de ejecución concurrente individual de A * B: %d ms \n", time.Since(start).Milliseconds())
	m.PrintMatrix(singleResult)

	var wg sync.WaitGroup
	results := make([][][]int, 2)

	start = time.Now()

	wg.Add(2)
	go func() {
		defer wg.Done()
		results[0] = m.MultiplyMatricesConcurrent(A, B, size)
	}()
	go func() {
		defer wg.Done()
		results[1] = m.MultiplyMatricesConcurrent(C, D, size)
	}()

	wg.Wait()
	fmt.Printf("Tiempo de ejecución concurrente: %d ms \n", time.Since(start).Milliseconds())

	fmt.Println("Resultado de A * B:")
	m.PrintMatrix(results[0])

	fmt.Println("Resultado de C * D:")
	m.PrintMatrix(results[1])

	start = time.Now()
	finalResult := m.MultiplyMatricesConcurrent(m.MultiplyMatricesConcurrent(A, B, size), C, size)
	fmt.Printf("Tiempo de ejecución para (A*B)*C: %d ms \n", time.Since(start).Milliseconds())

	fmt.Println("Resultado de (A * B) * C:")
	m.PrintMatrix(finalResult)
}
