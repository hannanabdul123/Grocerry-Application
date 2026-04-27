# Stage 1: Build the Spring Boot application
# Uses a JDK 21 image from Eclipse Temurin for compilation along with Maven.
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder


# Set the working directory inside the container for the build stage.
WORKDIR /app

# Copy the build configuration files.
# pom.xml: Main Maven configuration script.
COPY pom.xml .

RUN chmod +x mvnw
# Fetch all dependencies to be cached for faster consecutive builds
RUN mvn dependency:go-offline -B

# Copy the source code.
# The src directory contains your Java source files, resources, etc.
COPY src ./src

# Build the Spring Boot application into an executable JAR.
# mvn clean package creates the executable JAR.
# -DskipTests skips running tests during the Docker build, which speeds up the build process.

RUN mvn clean package 

# Stage 2: Create the final, lightweight runtime image
# Uses a JRE 21 image from Eclipse Temurin, which is much smaller than a JDK image.
FROM eclipse-temurin:21-jre-alpine

# Set the working directory inside the container for the runtime stage.
WORKDIR /app

# Copy the executable JAR from the builder stage to the final image.
# The JAR is copied from /app/target/grocery-app-0.0.1-SNAPSHOT.jar in the builder stage
# and renamed to app.jar in the current stage for simplicity.
COPY --from=builder app/target/grocery-app-0.0.1-SNAPSHOT.jar app.jar

# Expose the port on which your Spring Boot application will listen.
# 8080 is the standard default for Spring Boot web applications.
EXPOSE 8080

# Define the command to run your application when the container starts.
# java -jar app.jar executes the Spring Boot application.
ENTRYPOINT ["java", "-jar", "app.jar"]