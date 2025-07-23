# 💰 Sistema de Gestão Financeira

Backend completo para sistema de gestão financeira multi-tenant desenvolvido em **Java 21** com **Spring Boot 3**.

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 3**
- **Spring Security + JWT**
- **Spring Data JPA**
- **MySQL**
- **Gradle**

## 📋 Pré-requisitos

- Java 21 ou superior
- Docker e Docker Compose (recomendado)
- Gradle (opcional, o projeto usa Gradle Wrapper)

## ⚙️ Configuração

### 1. Clone o repositório
```bash
git clone <URL_DO_SEU_REPOSITORIO>
cd cashpoint-back
```

### 2. Configure as variáveis de ambiente
Crie um arquivo `.env` na raiz do projeto com o seguinte conteúdo (ajuste conforme seu ambiente):

```
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/wai
MYSQL_USER=seu user sem aspas
MYSQL_PASSWORD=sua senha sem aspas
```

Cada desenvolvedor pode ter seu próprio `.env` com usuário e senha diferentes.

## 🏃‍♂️ Executando o Projeto

### Build do projeto
```bash
./gradlew build
```

### Checar e corrigir formatação (Spotless)
```bash
./gradlew :uape:spotlessCheck     # Verifica formatação
./gradlew :uape:spotlessApply     # Corrige formatação automaticamente
```

### Executar a aplicação localmente
```bash
./gradlew :uape:bootRun
```

### Executar com Docker Compose (recomendado)
```bash
docker-compose up --build
```
- A aplicação estará disponível em `http://localhost:8082`
- O banco MySQL estará disponível em `localhost:3308`

### Executar testes
```bash
./gradlew test
```

## 🌐 Acesso à API

- **URL Base**: `http://localhost:8082`
- **Health Check**: `http://localhost:8082/actuator/health`

## 🏗️ Estrutura do Projeto

```
├── module/
│   ├── core/           # Entidades e serviços de negócio
│   └── persistence/    # Camada de persistência e configurações
├── server/
│   └── api/           # Controladores REST e configurações da API
├── gradle/            # Configurações do Gradle
```

## 🔐 Autenticação

O sistema usa **JWT** para autenticação. Para obter um token:

```bash
curl -X POST http://localhost:8082/api/users/login \
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

### Docker Compose
```bash
docker compose up --build -d
```

## 🔧 Configurações Avançadas

### Variáveis de Ambiente
- `SPRING_DATASOURCE_URL`: URL JDBC do banco de dados
- `MYSQL_USER`: Usuário do banco
- `MYSQL_PASSWORD`: Senha do banco
- `SPRING_PROFILES_ACTIVE`: Define o perfil (dev, prod, test

### Perfis Spring
- `dev`: Desenvolvimento (H2 em memória)
- `prod`: Produção (MySQL)
- `test`: Testes (H2 em memória)

## 🐛 Troubleshooting

### Problemas comuns

1. **Porta 8082 em uso**
   ```bash
   # Mude a porta no application.yaml
   server:
     port: 8083
   ```

2. **Erro de conexão com MySQL**
   - Verifique se o MySQL está rodando (ou o serviço mysql do Docker Compose)
   - Confirme as credenciais no `.env`
   - O host do banco, no Docker Compose, deve ser `mysql`

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
