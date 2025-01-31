# Ejemplos de Concurrencia en Java

Este directorio contiene ejemplos de concurrencia en Java.

```
java_threads/
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
```

- El paquete `config/` define la configuración del número de threads a utilizar para en el pool. 
- El paquete `matrix/` contiene utilitarios para metrices, definición de tareas, e interfaces para el calculo de multiplicación de matrices. 
    - `MatrixMultiplier` es la interfaz que define el comportamiento para la multiplicación de matriz. 
    - Existen 2 implementaciones de `MatrixMultiplier`, la primera `SimpleMatrixMultiplier` que es una multiplicación convencional. Y la segunda `ThreadSafeMatrixMultiplier` que separa la operación utilizando `ExecutorService` de acuerdo al número de hilos configurados en `ThreadsConfig`.
- `MatrixMultiplication` es el punto de entrada de la aplicación, solicita por consola el número de filas y columnas para las matrices aleatorias, mediante un solo número, dado que se realizan los ejemplos con matrices cuadradas. 

## Instrucciones para Compilar y Ejecutar (Primer enfoque)

1. Asegúrate de tener Java Development Kit (JDK) instalado en tu sistema. Puedes descargarlo desde [oracle.com](https://www.oracle.com/java/technologies/javase-downloads.html).
2. Navega a este directorio en tu terminal.
   ```sh
   cd java/java_threads
   ``` 
3. Compila los archivos fuente usando el comando:
   ```sh
   javac -d target/classes -sourcepath src/main/java $(find src/main/java -name "*.java")
   ```
4. Ejecuta el main compilado:
   ```sh
   java -cp target/classes phd.apps.MatrixMultiplication
   ```

## Instrucciones para Compilar y Ejecutar (Segundo enfoque)

1. Asegúrate de tener Java Development Kit (JDK) instalado en tu sistema. Puedes descargarlo desde [oracle.com](https://www.oracle.com/java/technologies/javase-downloads.html). También es necesario tener instalado Maven, que se puede instalar desde [maven.apache.org](https://maven.apache.org).
2. Navega a este directorio en tu terminal.
   ```sh
   cd java/java_threads
   ``` 
3. Compila los archivos fuente usando el comando:
   ```sh
   mvn clean package
   ```
4. Ejecuta el main compilado:
   ```sh
   java -jar target/java_threads-1.0-SNAPSHOT.jar
   ```