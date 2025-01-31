# Ejemplos de Concurrencia en Go

Este directorio contiene ejemplos de concurrencia en Go.

```
├── go/
│   ├── matrixes/
│   │   ├── config.go
│   │   ├── matrix.go
│   │   ├── matrix_util.go
│   │   ├── task.go
│   │   ├── worker.go
│   │   ├── go.mod
│   ├── main.go
│   └── README.md
```
- El archivo `main.go` es el punto de entrada de la aplicación, solicita el número de filas y columnas para las matrices aleatorias, mediante un solo número, dado que se realizan los ejemplos con matrices cuadradas. 
- El modulo `matrixes` es un módulo de go que contiene los elementos escenciales para las operaciones con matrices. 
    - `config.go` contiene la configuración para `workers` de `goroutines`. 
    - `matrix_util` contiene la generación de matrices estáticas para los ejemplos estáticos. 
    - `task.go` declaración de un `struct` a modo de apoyo para los `worker`. 
    - `matrix.go` contiene las operaciones de multiplicación concurrente y no concurrente así como utilitarios para mostrar en consola. 

## Instrucciones para Compilar y Ejecutar (Primer enfoque)

1. Asegúrate de tener Go instalado en tu sistema. Puedes descargarlo desde [golang.org](https://golang.org/).
2. Navega a este directorio en tu terminal.
    ```
    cd go
    ```
3. Compila el archivo fuente usando el comando:
   ```sh
   go build -o output main.go
   ```
4. Ejecuta el archivo compilado:
   ```sh
   ./output
   ```

## Instrucciones para ejecutar (Segundo enfoque)

1. Asegúrate de tener Go instalado en tu sistema. Puedes descargarlo desde [golang.org](https://golang.org/).
2. Navega a este directorio en tu terminal.
    ```
    cd go
    ```
3. Corre el archivo main utilizando los comandos de go:
   ```sh
   go run main.go
   ```
