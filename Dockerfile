FROM eclipse-temurin:21 AS builder
WORKDIR /fruit-salad-rest-api
COPY . .
RUN ./mvnw package -Dgroups=unit

FROM eclipse-temurin:21
WORKDIR /fruit-salad-rest-api
COPY --from=builder /fruit-salad-rest-api/target/*.jar fruit-salad-rest-api-1.0.0.jar
ENTRYPOINT ["java","-jar","fruit-salad-rest-api-1.0.0.jar"]