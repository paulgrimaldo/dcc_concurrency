package main

import (
	"fmt"
	"sync"
	"time"

	mt "github.com/paulgrimaldo/concurrency_examples/go/matrixes"
)

func main() {
	fmt.Println("Calculo para la multiplicación de matrices A(mxn) * B (nxq)")
	var m, n, q int
	fmt.Print("Ingrese el número de filas de la matriz A (m):")
	fmt.Scan(&m)
	fmt.Print("Ingrese el número de columnas de la matriz A y filas de la matriz B (n):")
	fmt.Scan(&n)
	fmt.Print("Ingrese el número de columnas de la matriz B (q):")
	fmt.Scan(&q)
	runStaticTests()
	runRandomTests(m, n, q)
}

func runStaticTests() {
	fmt.Println("Matrices estáticas")
	A := mt.GenerateStaticMatrixA()
	B := mt.GenerateStaticMatrixB()

	fmt.Println("Matriz A:")
	mt.PrintMatrix(A)

	fmt.Println("Matriz B:")
	mt.PrintMatrix(B)

	fmt.Println("Multiplicando matrices sin paralelismo")
	start := time.Now()
	var singleResult = mt.MultiplyMatrices(A, B, 10, 10, 10)
	fmt.Println("Tiempo de ejecución de A * B:", time.Since(start))
	mt.PrintMatrix(singleResult)

	fmt.Println("Multiplicando matrices estáticas en paralelo...")
	start = time.Now()
	var concurrentResult = mt.MultiplyMatricesConcurrent(A, B, 10, 10, 10)
	fmt.Println("Tiempo de ejecución concurrente de A * B:", time.Since(start))
	mt.PrintMatrix(concurrentResult)
}

func runRandomTests(m, n, q int) {
	fmt.Println("Matrices generadas aleatoriamente")

	A := mt.GenerateMatrix(m, n)
	B := mt.GenerateMatrix(n, q)
	C := mt.GenerateMatrix(q, n)
	D := mt.GenerateMatrix(n, q)

	fmt.Println("Multiplicando matrices en paralelo...")

	start := time.Now()
	var singleResult = mt.MultiplyMatricesConcurrent(A, B, m, n, q)
	fmt.Printf("Tiempo de ejecución concurrente individual de A * B: %d ms \n", time.Since(start).Milliseconds())
	mt.PrintMatrix(singleResult)

	var wg sync.WaitGroup
	results := make([][][]int, 2)

	start = time.Now()

	wg.Add(2)
	go func() {
		defer wg.Done()
		results[0] = mt.MultiplyMatricesConcurrent(A, B, m, n, q)
	}()
	go func() {
		defer wg.Done()
		results[1] = mt.MultiplyMatricesConcurrent(C, D, m, n, q)
	}()

	wg.Wait()
	fmt.Printf("Tiempo de ejecución concurrente (A,B,C,D): %d ms \n", time.Since(start).Milliseconds())

	start = time.Now()
	finalResult := mt.MultiplyMatricesConcurrent(mt.MultiplyMatricesConcurrent(A, B, m, n, q), C, m, q, n)
	fmt.Printf("Tiempo de ejecución para (A*B)*C: %d ms \n", time.Since(start).Milliseconds())

	fmt.Println("Resultado de (A * B) * C:")
	mt.PrintMatrix(finalResult)

	fmt.Println("-----Comparación concurrente vs local-----")
	start = time.Now()
	mt.MultiplyMatrices(A, B, m, n, q)
	fmt.Printf("Tiempo de ejecución local: %d ms \n", time.Since(start).Milliseconds())

	start = time.Now()
	mt.MultiplyMatricesConcurrent(A, B, m, n, q)
	fmt.Printf("Tiempo de ejecución concurrente: %d ms \n", time.Since(start).Milliseconds())
}
