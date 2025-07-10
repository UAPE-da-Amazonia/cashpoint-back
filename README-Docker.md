# Docker para CashPoint Backend

## Visão Geral

Este projeto está configurado para rodar com Docker, oferecendo um ambiente isolado e reproduzível.

## Pré-requisitos

- Docker
- Docker Compose

## Estrutura Docker

### Arquivos Criados:
- `Dockerfile` - Imagem da aplicação Spring Boot
- `docker-compose.yml` - Ambiente de produção completo
- `docker-compose.dev.yml` - Ambiente de desenvolvimento
- `application-docker.yaml` - Configuração específica para Docker
- `.dockerignore` - Otimização do build

## Comandos Docker

### Desenvolvimento

```bash
# Iniciar apenas banco de dados e Redis para desenvolvimento
docker-compose -f docker-compose.dev.yml up -d

# Ver logs
docker-compose -f docker-compose.dev.yml logs -f

# Parar serviços
docker-compose -f docker-compose.dev.yml down
```

### Produção

```bash
# Build e iniciar todos os serviços
docker-compose up -d --build

# Ver logs
docker-compose logs -f api

# Parar todos os serviços
docker-compose down

# Parar e remover volumes
docker-compose down -v
```

### Comandos Úteis

```bash
# Rebuild da aplicação
docker-compose build api

# Executar apenas a aplicação (com banco externo)
docker-compose up api

# Ver status dos containers
docker-compose ps

# Acessar container da aplicação
docker-compose exec api sh

# Acessar MySQL
docker-compose exec mysql mysql -u admin -p wai
```

## Serviços Disponíveis

### Desenvolvimento:
- **MySQL**: `localhost:3306` (usuário: admin, senha: Info@1234)
- **Redis**: `localhost:6379`
- **Adminer**: `http://localhost:8081` (interface web para MySQL)

### Produção:
- **API**: `http://localhost:8080`
- **MySQL**: `localhost:3306`
- **Redis**: `localhost:6379`

## Configurações

### Variáveis de Ambiente

```yaml
# Docker Compose
SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/wai
SPRING_DATASOURCE_USERNAME: admin
SPRING_DATASOURCE_PASSWORD: Info@1234
JAVA_OPTS: "-Xmx512m -Xms256m"
```

### Volumes

- `mysql_data`: Dados persistentes do MySQL
- `redis_data`: Dados persistentes do Redis

## Benefícios do Docker

1. **Ambiente Reproduzível**: Mesmo ambiente em qualquer máquina
2. **Isolamento**: Cada serviço roda em container separado
3. **Facilidade de Deploy**: Um comando para subir toda a infraestrutura
4. **Versionamento**: Controle de versões das dependências
5. **Escalabilidade**: Fácil de escalar horizontalmente

## Troubleshooting

### Problemas Comuns:

1. **Porta já em uso**:
   ```bash
   # Verificar portas em uso
   netstat -tulpn | grep :3306
   ```

2. **Permissões de volume**:
   ```bash
   # Corrigir permissões
   sudo chown -R 1001:1001 mysql_data/
   ```

3. **Limpeza de containers**:
   ```bash
   # Remover containers parados
   docker container prune
   
   # Remover imagens não utilizadas
   docker image prune
   ```

## Monitoramento

### Health Checks:
- **API**: `http://localhost:8080/actuator/health`
- **MySQL**: Verificação automática no container
- **Redis**: Verificação automática no container

### Logs:
```bash
# Logs da aplicação
docker-compose logs -f api

# Logs do MySQL
docker-compose logs -f mysql

# Logs do Redis
docker-compose logs -f redis
```

## Performance

### Otimizações Implementadas:
- Multi-stage build para reduzir tamanho da imagem
- Usuário não-root para segurança
- Health checks para monitoramento
- Configurações otimizadas do HikariCP
- Cache Redis para melhor performance

### Recursos Recomendados:
- **CPU**: 2 cores
- **RAM**: 4GB mínimo
- **Disco**: 10GB livre 