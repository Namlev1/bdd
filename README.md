## Testy wydajnościowe

### Testy przeciążeniowe

```sh
./mvnw spring-boot:run
./mvnw gatling:test
```

### Testy microbenchmark

```sh
./mvnw clean test-compile
./mvnw \
    -D"exec.mainClass=com.sevolutivo.demo.jmh.BenchmarkRunner" \
    -D"exec.classpathScope=test" \
    -D"exec.args=GetTaskById" \
    exec:java
```
