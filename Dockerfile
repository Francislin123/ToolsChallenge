# Estágio de Build (Builder)
# Atualizado para Maven com Java 21 para suportar as novas funcionalidades do projeto
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app
COPY . .
# Otimização: mvn clean package sem rodar os testes para agilizar o pipeline
RUN mvn clean package -DskipTests

# Estágio de Runtime (Execução)
# Usamos o JRE em vez do JDK para reduzir o tamanho da imagem e a superfície de ataque (Segurança)
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copia apenas o artefato necessário do estágio anterior
COPY --from=builder /app/target/*.jar app.jar

# Boa prática: Rodar como usuário não-root (opcional, mas recomendado para bancos/fintechs)
# RUN useradd -m myuser
# USER myuser

EXPOSE 8080

# Adicionamos flags para otimizar o uso de memória em containers
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]