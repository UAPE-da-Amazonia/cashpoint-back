# 🔧 Documentação Técnica - Backend

## 📋 **Visão Geral**

Sistema de gestão financeira multi-tenant desenvolvido em **Java 21** com **Spring Boot 3**, focado em controle de fluxo de caixa, gestão de clientes/fornecedores e controle de acesso baseado em roles.

## 🏗️ **Arquitetura**

### **Estrutura Modular:**
```
├── module/
│   ├── core/                    # Camada de domínio
│   │   ├── model/              # Entidades de negócio
│   │   └── service/            # Serviços de negócio
│   └── persistence/            # Camada de persistência
│       ├── config/             # Configurações (JPA, Cache, etc.)
│       └── persistence/        # Repositórios e adaptadores
├── server/
│   └── api/                    # Camada de apresentação
│       ├── api/                # Controladores REST
│       ├── config/             # Configurações da API
│       ├── request/            # DTOs de entrada
│       └── response/           # DTOs de saída
```

### **Padrões Utilizados:**
- **Clean Architecture** (Separação de camadas)
- **Repository Pattern** (Abstração de persistência)
- **Adapter Pattern** (Adaptadores para JPA)
- **Strategy Pattern** (Diferentes tipos de perfil)
- **Factory Pattern** (Criação de entidades)

## 🔐 **Autenticação e Autorização**

### **JWT Token Structure:**
```json
{
  "sub": "user-uuid",
  "businessUnitId": 1,
  "role": "ADMIN",
  "iat": 1234567890,
  "exp": 1234567890
}
```

### **Controle de Acesso:**
- **ADMIN**: Acesso total a todas as unidades de negócio
- **USER**: Acesso limitado à sua própria unidade de negócio

### **Configuração de Segurança:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    // Configurações de CORS, JWT, etc.
}
```

## 📊 **Entidades do Sistema**

### **1. User (Usuário)**
```java
@Entity
@Table(name = "users")
public class User {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String name;
    private String bio;
    private String imageUrl;
    private UserRole role;
    private Long businessUnitId;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

### **2. BusinessUnit (Unidade de Negócio)**
```java
@Entity
@Table(name = "business_unit")
public class BusinessUnit {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

### **3. Profile (Cliente/Fornecedor)**
```java
@Entity
@Table(name = "profiles")
public class Profile {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String document;
    private String address;
    private ProfileType profileType;
    private Long businessUnitId;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

### **4. CashFlow (Movimentação Financeira)**
```java
@Entity
@Table(name = "cash_flow")
public class CashFlow {
    private Long id;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private String description;
    private Long paymentMethodId;
    private Long transactionTypeId;
    private Long categoryId;
    private Long accountTypeId;
    private Long businessUnitId;
    private Long profileId; // Opcional
    private UUID createdBy;
    private UUID updatedBy;
    private boolean isActive;
    private boolean isChecked;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

## 🌐 **Endpoints da API**

### **🔑 Autenticação**

#### **POST /api/users/login**
```http
POST /api/users/login
Content-Type: application/json

{
  "user": {
    "email": "user@example.com",
    "password": "password123"
  }
}
```

**Resposta:**
```json
{
  "user": {
    "id": "uuid",
    "username": "user",
    "email": "user@example.com",
    "name": "User Name",
    "role": "ADMIN",
    "businessUnitId": 1,
    "token": "jwt-token-here"
  }
}
```

#### **POST /api/users** - Cadastro
```http
POST /api/users
Content-Type: application/json

{
  "user": {
    "username": "newuser",
    "email": "new@email.com",
    "password": "password123",
    "name": "New User",
    "businessUnitId": 1,
    "role": "USER"
  }
}
```

#### **GET /api/user** - Obter usuário logado
```http
GET /api/user
Authorization: Bearer {token}
```

#### **PUT /api/user** - Atualizar usuário
```http
PUT /api/user
Authorization: Bearer {token}
Content-Type: application/json

{
  "user": {
    "name": "Updated Name",
    "email": "updated@email.com",
    "username": "updateduser",
    "password": "newpassword",
    "businessUnitId": 1,
    "role": "ADMIN",
    "bio": "New bio",
    "imageUrl": "https://image.com/photo.jpg"
  }
}
```

---

### **🏢 BusinessUnit**

#### **GET /api/business-units** - Listar unidades
```http
GET /api/business-units
Authorization: Bearer {token}
```

**Resposta:**
```json
{
  "businessUnits": [
    {
      "id": 1,
      "name": "Empresa A",
      "description": "Empresa principal",
      "createdAt": "2024-01-15T10:30:00",
      "updatedAt": null
    }
  ]
}
```

#### **POST /api/business-units** - Criar unidade
```http
POST /api/business-units
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Nova Empresa",
  "description": "Descrição da empresa"
}
```

#### **GET /api/business-units/{id}** - Obter unidade específica
```http
GET /api/business-units/{id}
Authorization: Bearer {token}
```

#### **PUT /api/business-units/{id}** - Atualizar unidade
```http
PUT /api/business-units/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Empresa Atualizada",
  "description": "Nova descrição"
}
```

#### **DELETE /api/business-units/{id}** - Deletar unidade
```http
DELETE /api/business-units/{id}
Authorization: Bearer {token}
```

---

### **👥 Profile (Cliente/Fornecedor)**

#### **POST /api/profiles** - Criar perfil
```http
POST /api/profiles
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@email.com",
  "phone": "(11) 99999-9999",
  "document": "123.456.789-00",
  "address": "Rua A, 123 - São Paulo",
  "profile_type": "CLIENT"
}
```

#### **GET /api/profiles** - Listar perfis
```http
GET /api/profiles?businessUnitId=1&profileType=CLIENT&search=joão
Authorization: Bearer {token}
```

**Parâmetros:**
- `businessUnitId` (opcional): Filtrar por unidade
- `profileType` (opcional): CLIENT, SUPPLIER, BOTH
- `search` (opcional): Busca por nome/email

#### **GET /api/profiles/{id}** - Obter perfil específico
```http
GET /api/profiles/{id}
Authorization: Bearer {token}
```

#### **PUT /api/profiles/{id}** - Atualizar perfil
```http
PUT /api/profiles/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "João Atualizado",
  "email": "joao@email.com",
  "phone": "(11) 99999-9999",
  "document": "123.456.789-00",
  "address": "Rua Nova, 456 - SP",
  "profile_type": "SUPPLIER"
}
```

#### **DELETE /api/profiles/{id}** - Deletar perfil
```http
DELETE /api/profiles/{id}
Authorization: Bearer {token}
```

#### **PUT /api/profiles/{id}/activate** - Ativar perfil
```http
PUT /api/profiles/{id}/activate
Authorization: Bearer {token}
```

#### **PUT /api/profiles/{id}/deactivate** - Desativar perfil
```http
PUT /api/profiles/{id}/deactivate
Authorization: Bearer {token}
```

---

### **💰 CashFlow**

#### **POST /api/cash-flows** - Criar movimentação
```http
POST /api/cash-flows
Authorization: Bearer {token}
Content-Type: application/json

{
  "cashFlow": {
    "amount": 1500.00,
    "transactionDate": "2024-01-15",
    "description": "Venda de produtos",
    "paymentMethodId": 1,
    "transactionTypeId": 1,
    "categoryId": 1,
    "accountTypeId": 1,
    "businessUnitId": 1,
    "profileId": 1
  }
}
```

#### **GET /api/cash-flows** - Listar movimentações
```http
GET /api/cash-flows?businessUnit=1
Authorization: Bearer {token}
```

**Parâmetros:**
- `businessUnit` (opcional): ID da unidade de negócio

#### **GET /api/cash-flows/{id}** - Obter movimentação específica
```http
GET /api/cash-flows/{id}
Authorization: Bearer {token}
```

#### **PUT /api/cash-flows/{id}** - Atualizar movimentação
```http
PUT /api/cash-flows/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "cashFlow": {
    "amount": 2000.00,
    "transactionDate": "2024-01-20",
    "description": "Venda atualizada",
    "paymentMethodId": 2,
    "transactionTypeId": 1,
    "categoryId": 2,
    "accountTypeId": 1,
    "profileId": 2
  }
}
```

#### **DELETE /api/cash-flows/{id}** - Deletar movimentação
```http
DELETE /api/cash-flows/{id}
Authorization: Bearer {token}
```

#### **GET /api/cash-flows/search** - Buscar por período
```http
GET /api/cash-flows/search?businessUnit=1&startDate=2024-01-01&endDate=2024-01-31
Authorization: Bearer {token}
```

#### **PUT /api/cash-flows/{id}/check** - Marcar como revisado
```http
PUT /api/cash-flows/{id}/check
Authorization: Bearer {token}
```

#### **PUT /api/cash-flows/{id}/uncheck** - Desmarcar como revisado
```http
PUT /api/cash-flows/{id}/uncheck
Authorization: Bearer {token}
```

#### **PUT /api/cash-flows/{id}/activate** - Ativar movimentação
```http
PUT /api/cash-flows/{id}/activate
Authorization: Bearer {token}
```

#### **PUT /api/cash-flows/{id}/deactivate** - Desativar movimentação
```http
PUT /api/cash-flows/{id}/deactivate
Authorization: Bearer {token}
```

---

## 📋 **Exemplos de Resposta**

### **CashFlow Response:**
```json
{
  "cashFlow": {
    "id": 1,
    "amount": 1500.00,
    "transactionDate": "2024-01-15",
    "description": "Venda de produtos",
    "paymentMethod": "PIX",
    "transactionType": "Income",
    "category": "Vendas",
    "accountType": "BASA - Corrente",
    "businessUnit": "Empresa A",
    "profile": {
      "id": 1,
      "name": "João Silva",
      "email": "joao@email.com",
      "phone": "(11) 99999-9999",
      "document": "123.456.789-00"
    },
    "createdAt": "2024-01-15T10:30:00",
    "updatedAt": "2024-01-15T10:30:00",
    "createdBy": "user-uuid",
    "updatedBy": null,
    "isActive": true,
    "isChecked": false
  }
}
```

### **Profile Response:**
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@email.com",
  "phone": "(11) 99999-9999",
  "document": "123.456.789-00",
  "address": "Rua A, 123 - São Paulo",
  "business_unit_id": 1,
  "profile_type": "CLIENT",
  "is_active": true,
  "created_at": "2024-01-15T10:30:00",
  "updated_at": "2024-01-15T10:30:00"
}
```

### **BusinessUnit Response:**
```json
{
  "id": 1,
  "name": "Empresa A",
  "description": "Empresa principal",
  "createdAt": "2024-01-15T10:30:00",
  "updatedAt": null
}
```

---

## ⚠️ **Códigos de Erro**

### **401 Unauthorized**
```json
{
  "errors": {
    "body": ["invalid email or password"]
  }
}
```

### **403 Forbidden**
```json
{
  "errors": {
    "body": ["Access denied. You can only access cash flows from your business unit."]
  }
}
```

### **404 Not Found**
```json
{
  "errors": {
    "body": ["Business unit not found"]
  }
}
```

### **422 Unprocessable Entity**
```json
{
  "errors": {
    "body": ["email or username is already exists"]
  }
}
```

---

## 🔧 **Configurações**

### **application.yaml (Persistence)**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/wai
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQLDialect
        format_sql: true
  
  redis:
    host: localhost
    port: 6379
    timeout: 2000ms
    lettuce:
      pool:
        max-active: 8
        max-idle: 8
        min-idle: 0

logging:
  level:
    io.zhc1.realworld: DEBUG
    org.springframework.security: DEBUG
```

### **application.yaml (API)**
```yaml
server:
  port: 8080

spring:
  application:
    name: financial-management-api

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: always

jwt:
  secret: your-secret-key-here-make-it-long-and-secure
  expiration: 86400000 # 24 horas em ms
```

---

## 🧪 **Testes**

### **Executar Testes:**
```bash
# Todos os testes
./gradlew test

# Testes específicos
./gradlew :module:core:test
./gradlew :server:api:test

# Testes com cobertura
./gradlew test jacocoTestReport
```

### **Estrutura de Testes:**
```
├── module/core/src/test/
│   ├── java/io/zhc1/realworld/
│   │   ├── model/           # Testes de entidades
│   │   └── service/         # Testes de serviços
│   └── testFixtures/        # Fixtures para testes
├── module/persistence/src/test/
│   └── java/io/zhc1/realworld/
│       └── config/          # Testes de configuração
└── server/api/src/test/
    └── java/io/zhc1/realworld/
        ├── api/             # Testes de controladores
        └── config/          # Testes de configuração
```

---

## 📦 **Build e Deploy**

### **Build JAR:**
```bash
./gradlew :server:api:bootJar
```

### **Docker (Dockerfile exemplo):**
```dockerfile
FROM openjdk:21-jdk-slim
COPY build/libs/api-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### **Docker Compose (exemplo):**
```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - DB_HOST=mysql
      - REDIS_HOST=redis
    depends_on:
      - mysql
      - redis
  
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: wai
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
  
  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"

volumes:
  mysql_data:
```

---

## 🔍 **Monitoramento e Logs**

### **Health Checks:**
- `GET /actuator/health` - Status geral da aplicação
- `GET /actuator/health/db` - Status do banco de dados
- `GET /actuator/health/redis` - Status do Redis

### **Métricas:**
- `GET /actuator/metrics` - Lista de métricas disponíveis
- `GET /actuator/metrics/http.server.requests` - Métricas de requisições HTTP

### **Logs:**
```yaml
logging:
  level:
    io.zhc1.realworld: INFO
    org.springframework.security: WARN
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/application.log
```

---

## 🚀 **Performance e Otimização**

### **Cache Redis:**
- Cache de usuários por 30 minutos
- Cache de business units por 1 hora
- Cache de perfis por 15 minutos

### **Configurações JPA:**
- Lazy loading habilitado
- Batch size otimizado
- Query cache desabilitado (usa Redis)

### **Configurações de Pool:**
- HikariCP para conexões de banco
- Pool de threads para Redis
- Timeout de conexão configurado

---

## 📝 **Observações Importantes**

1. **Multi-tenancy**: Cada usuário só acessa dados da sua unidade de negócio (exceto ADMIN)
2. **JWT**: Tokens expiram em 24 horas por padrão
3. **Validação**: Todos os inputs são validados com Bean Validation
4. **Auditoria**: Todas as entidades têm campos de auditoria (createdAt, updatedAt, createdBy, updatedBy)
5. **Soft Delete**: Entidades não são deletadas fisicamente, apenas marcadas como inativas
6. **Cache**: Redis é usado para cache de dados frequentemente acessados
7. **Segurança**: CORS configurado, CSRF desabilitado para API REST

---

**🎯 Esta documentação contém todas as informações técnicas necessárias para desenvolvimento e manutenção do backend!** 