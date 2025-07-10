# Multi-stage build para otimizar o tamanho da imagem
FROM eclipse-temurin:21-jdk-alpine AS builder

# Instalar dependências necessárias
RUN apk add --no-cache gradle

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos de configuração do Gradle
COPY gradle/ gradle/
COPY gradlew gradlew.bat build.gradle.kts settings.gradle.kts gradle.properties ./

# Baixar dependências (cache layer)
RUN ./gradlew dependencies --no-daemon

# Copiar código fonte
COPY . .

# Build da aplicação
RUN ./gradlew :server:api:bootJar --no-daemon

# Imagem de produção
FROM eclipse-temurin:21-jre-alpine

# Instalar dependências de runtime
RUN apk add --no-cache tzdata

# Definir timezone
ENV TZ=America/Manaus

# Criar usuário não-root
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Definir diretório de trabalho
WORKDIR /app

# Copiar JAR da aplicação
COPY --from=builder /app/server/api/build/libs/*.jar app.jar

# Mudar propriedade do arquivo
RUN chown appuser:appgroup app.jar

# Mudar para usuário não-root
USER appuser

# Expor porta
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"] 