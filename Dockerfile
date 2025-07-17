# Imagem base mais simples
FROM eclipse-temurin:21-jre-alpine

# Instalar dependências necessárias
RUN apk add --no-cache tzdata

# Definir timezone
ENV TZ=America/Manaus

# Criar usuário não-root
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Definir diretório de trabalho
WORKDIR /app

# Copiar apenas o JAR da aplicação (assumindo que foi buildado localmente)
COPY server/api/build/libs/*.jar app.jar

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