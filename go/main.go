package main

import (
	"fmt"
	"sync"
	"time"

	m "github.com/paulgrimaldo/concurrency_examples/go/matrixes"
)

func main() {
	var size int
	fmt.Print("Ingrese el tamaño de las matrices aleatorias: ")
	fmt.Scan(&size)
	runStaticTests()
	runRandomTests(size)
}

func runStaticTests() {
	fmt.Println("Matrices estáticas")
	A := m.GenerateStaticMatrixA()
	B := m.GenerateStaticMatrixB()

	fmt.Println("Matriz A:")
	m.PrintMatrix(A)

	fmt.Println("Matriz B:")
	m.PrintMatrix(B)

	fmt.Println("Multiplicando matrices sin paralelismo")
	start := time.Now()
	var singleResult = m.MultiplyMatrices(A, B, 10)
	fmt.Println("Tiempo de ejecución de A * B:", time.Since(start))
	m.PrintMatrix(singleResult)

	fmt.Println("Multiplicando matrices estáticas en paralelo...")
	start = time.Now()
	var concurrentResult = m.MultiplyMatricesConcurrent(A, B, 10)
	fmt.Println("Tiempo de ejecución concurrente de A * B:", time.Since(start))
	m.PrintMatrix(concurrentResult)
}

func runRandomTests(size int) {
	fmt.Println("Matrices generadas aleatoriamente")

	A := m.GenerateMatrix(size)
	B := m.GenerateMatrix(size)
	C := m.GenerateMatrix(size)
	D := m.GenerateMatrix(size)

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
	fmt.Printf("Tiempo de ejecución concurrente (A,B,C,D): %d ms \n", time.Since(start).Milliseconds())

	start = time.Now()
	finalResult := m.MultiplyMatricesConcurrent(m.MultiplyMatricesConcurrent(A, B, size), C, size)
	fmt.Printf("Tiempo de ejecución para (A*B)*C: %d ms \n", time.Since(start).Milliseconds())

	fmt.Println("Resultado de (A * B) * C:")
	m.PrintMatrix(finalResult)

	fmt.Println("-----Comparación concurrente vs local-----")
	start = time.Now()
	m.MultiplyMatrices(A, B, size)
	fmt.Printf("Tiempo de ejecución local: %d ms \n", time.Since(start).Milliseconds())

	start = time.Now()
	m.MultiplyMatricesConcurrent(A, B, size)
	fmt.Printf("Tiempo de ejecución concurrente: %d ms \n", time.Since(start).Milliseconds())
}
