# Use a base image with JDK
FROM openjdk:17-jdk-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project file
COPY pom.xml .

# Build the application
RUN apt-get update && apt-get install -y maven
RUN mvn dependency:go-offline

# Copy the application source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Use a smaller base image for the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file from the build stage to the new image
COPY --from=build /app/target/demo-*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
