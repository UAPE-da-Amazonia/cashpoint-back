# 💰 Sistema de Gestão Financeira

Backend completo para sistema de gestão financeira multi-tenant desenvolvido em **Java 21** com **Spring Boot 3**.

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 3**
- **Spring Security + JWT**
- **Spring Data JPA**
- **MySQL**
- **Redis** (Cache)
- **Gradle**

## 📋 Pré-requisitos

- Java 21 ou superior
- MySQL 8.0+
- Redis (opcional, para cache)
- Gradle (opcional, o projeto usa Gradle Wrapper)

## ⚙️ Configuração

### 1. Clone o repositório
```bash
git clone <URL_DO_SEU_REPOSITORIO>
cd financial-management-backend
```

### 2. Configure o banco de dados
Crie um banco MySQL chamado `wai` e ajuste as configurações em:
- `module/persistence/src/main/resources/application.yaml`
- `server/api/src/main/resources/application.yaml`

### 3. Execute o schema do banco
```bash
mysql -u root -p wai < module/persistence/src/main/resources/schema-mysql.sql
```

## 🏃‍♂️ Executando o Projeto

### Build do projeto
```bash
./gradlew build
```

### Executar a aplicação
```bash
./gradlew bootRun
```

### Executar apenas o servidor API
```bash
./gradlew :server:api:bootRun
```

### Executar testes
```bash
./gradlew test
```

## 🌐 Acesso à API

- **URL Base**: `http://localhost:8080`
- **Swagger/OpenAPI**: `http://localhost:8080/swagger-ui.html`
- **Health Check**: `http://localhost:8080/actuator/health`

## 📚 Documentação

- **Documentação Técnica do Backend**: [BACKEND_DOCUMENTATION.md](BACKEND_DOCUMENTATION.md)
- **Documentação Completa da API**: [API_DOCUMENTATION.md](API_DOCUMENTATION.md)
- **Especificação Frontend**: [FRONTEND_SPECIFICATION.md](FRONTEND_SPECIFICATION.md)
- **Coleção Postman**: [api-docs/RealWorld-API.postman_collection.json](api-docs/RealWorld-API.postman_collection.json)

## 🏗️ Estrutura do Projeto

```
├── module/
│   ├── core/           # Entidades e serviços de negócio
│   └── persistence/    # Camada de persistência e configurações
├── server/
│   └── api/           # Controladores REST e configurações da API
├── api-docs/          # Documentação e coleções de teste
└── gradle/            # Configurações do Gradle
```

## 🔐 Autenticação

O sistema usa **JWT** para autenticação. Para obter um token:

```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "user": {
      "email": "admin@example.com",
      "password": "password123"
    }
  }'
```

## 👥 Usuários Padrão

Após executar o schema, você pode criar usuários via API ou usar os dados padrão:

- **Admin**: `admin@example.com` / `password123`
- **User**: `user@example.com` / `password123`

## 🧪 Testes

### Executar todos os testes
```bash
./gradlew test
```

### Executar testes de um módulo específico
```bash
./gradlew :module:core:test
./gradlew :server:api:test
```

## 📦 Build para Produção

### JAR executável
```bash
./gradlew :server:api:bootJar
```

### Docker (se configurado)
```bash
docker build -t financial-management .
docker run -p 8080:8080 financial-management
```

## 🔧 Configurações Avançadas

### Variáveis de Ambiente
- `SPRING_PROFILES_ACTIVE`: Define o perfil (dev, prod, test)
- `DB_HOST`: Host do banco de dados
- `DB_PORT`: Porta do banco de dados
- `DB_NAME`: Nome do banco de dados
- `DB_USER`: Usuário do banco
- `DB_PASS`: Senha do banco
- `REDIS_HOST`: Host do Redis
- `REDIS_PORT`: Porta do Redis

### Perfis Spring
- `dev`: Desenvolvimento (H2 em memória)
- `prod`: Produção (MySQL + Redis)
- `test`: Testes (H2 em memória)

## 🐛 Troubleshooting

### Problemas comuns

1. **Porta 8080 em uso**
   ```bash
   # Mude a porta no application.yaml
   server:
     port: 8081
   ```

2. **Erro de conexão com MySQL**
   - Verifique se o MySQL está rodando
   - Confirme as credenciais no `application.yaml`
   - Execute o schema: `mysql -u root -p wai < schema-mysql.sql`

3. **Erro de compilação**
   ```bash
   # Limpe o cache do Gradle
   ./gradlew clean
   ./gradlew build
   ```

## 📞 Suporte

Para dúvidas ou problemas:
1. Verifique a documentação completa
2. Execute os testes para verificar se tudo está funcionando
3. Consulte os logs da aplicação
