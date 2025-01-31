# Concurrency Examples

Este proyecto contiene ejemplos de concurrencia en diferentes lenguajes de programación. La estructura de carpetas del proyecto es la siguiente:

```
concurrency_examples/
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
├── java/
│   ├── src/main/java/phd/apps/
│   │   ├── MatrixMultiplication.java
│   │   ├── config/
│   │   │   └── ThreadsConfig.java
│   │   ├── matrix/
│   │   │   ├── service/
│   │   │   │   ├── impl/
│   │   │   │   │   ├── SimpleMatrixMultiplier.java
│   │   │   │   │   ├── ThreadSafeMatrixMultiplier.java
│   │   │   │   ├── MatrixMultiplier.java
│   │   │   ├── util/
│   │   │   │   └── MatrixUtil.java
│   │   │   ├── thread/
│   │   │   │   └── MatrixTask.java
│   ├── pom.xml
│   ├── .gitignore
│   └── README.md
└── README.md
```

## Estructura de Carpetas

- `go/`: Contiene ejemplos de concurrencia en Go.
- `java/`: Contiene ejemplos de concurrencia en Java.

Cada carpeta contiene un archivo `README.md` con instrucciones específicas sobre cómo compilar y ejecutar los ejemplos en ese lenguaje.
