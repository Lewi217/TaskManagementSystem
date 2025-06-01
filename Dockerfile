# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make Maven wrapper executable
RUN chmod +x ./mvnw

# Download dependencies (for better layer caching)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Copy keys.properties if it exists (for local builds)
COPY src/main/resources/keys.properties* src/main/resources/

# Build the application with verbose output
RUN ./mvnw clean package -DskipTests -X

# List files to verify JAR was created
RUN ls -la target/

# Expose port
EXPOSE 8087

# Run the application
CMD ["java", "-jar", "target/TaskManagement-0.0.1-SNAPSHOT.jar"]