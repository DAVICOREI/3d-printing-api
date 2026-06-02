# Usa uma imagem oficial do Java 17 (padrão do Spring Boot 3)
FROM eclipse-temurin:17-jdk-jammy

# Define a pasta de trabalho dentro do servidor
WORKDIR /app

# Copia todos os arquivos do seu computador para o servidor
COPY . .

# Garante que o script do Maven tenha permissão e constrói o projeto
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Expõe a porta que o Spring Boot usa
EXPOSE 8080

# O comando que o servidor vai rodar para ligar a API
CMD ["sh", "-c", "java -jar target/*.jar"]