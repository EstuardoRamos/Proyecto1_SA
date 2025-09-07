# syntax=docker/dockerfile:1.7
# Etapa de build (maven + JDK 21)
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copiamos POMs necesarios del monorepo
COPY pom.xml .
COPY auth.microservice/pom.xml auth.microservice/pom.xml
# si tienes módulo common, inclúyelo:
# COPY common/pom.xml common/pom.xml

# Copiamos el código fuente del módulo (y el common si aplica)
COPY auth.microservice/src auth.microservice/src
# COPY common/src common/src

# Construimos solo el módulo auth (y dependencias con -am)
RUN mvn -q -pl auth.microservice -am -DskipTests package

# Etapa runtime (JRE 21 liviano)
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/auth.microservice/target/*.jar app.jar

# Puedes ajustar por variables/flags de JVM si quieres
ENV JAVA_OPTS=""

EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
