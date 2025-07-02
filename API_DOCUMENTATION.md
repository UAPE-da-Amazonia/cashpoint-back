# 📚 **Documentação da API - Sistema de Gestão Financeira**

## 🏗️ **Estrutura do Sistema**

### **Entidades Principais:**

1. **`users`** - Usuários do sistema (ADMIN/USER)
2. **`business_unit`** - Unidades de negócio
3. **`profiles`** - Clientes/Fornecedores cadastrados
4. **`cash_flow`** - Movimentações financeiras
5. **`category`** - Categorias de despesa/receita
6. **`payment_method`** - Formas de pagamento
7. **`transaction_type`** - Tipos de transação (Income/Expense)
8. **`account_type`** - Tipos de conta

### **Relacionamentos:**
- **CashFlow** → **Profile** (opcional) - Cada movimentação pode estar associada a um cliente/fornecedor
- **Profile** → **BusinessUnit** - Cada perfil pertence a uma unidade de negócio
- **CashFlow** → **BusinessUnit** - Cada movimentação pertence a uma unidade de negócio

---

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
- **ADMIN**: Pode acessar todas as unidades de negócio e seus dados
- **USER**: Pode acessar apenas sua própria unidade de negócio

---

## 📊 **Endpoints da API**

### **🔑 Autenticação**

#### **POST /api/users/login**
```json
{
  "user": {
    "email": "user@example.com",
    "password": "password123"
  }
}
```

#### **POST /api/users**
```json
{
  "user": {
    "username": "johndoe",
    "email": "john@example.com",
    "password": "password123",
    "name": "John Doe",
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
    "name": "Novo Nome",
    "email": "novo@email.com",
    "username": "novouser",
    "password": "novasenha",
    "businessUnitId": 1,
    "role": "ADMIN",
    "bio": "Nova bio",
    "imageUrl": "https://imagem.com/foto.png"
  }
}
```

---

### **🏢 Unidades de Negócio (BusinessUnit)**

#### **GET /api/business-units** - Listar unidades
```http
GET /api/business-units
Authorization: Bearer {token}
```

#### **POST /api/business-units** - Criar unidade
```http
POST /api/business-units
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Empresa Nova",
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

### **🏷️ Categorias (Category)**

#### **GET /api/categories** - Listar categorias
```http
GET /api/categories?businessUnit=1
Authorization: Bearer {token}
```

#### **POST /api/categories** - Criar categoria
```http
POST /api/categories
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Nova Categoria",
  "transactionTypeId": 1,
  "businessUnitId": 1
}
```

#### **GET /api/categories/{id}** - Obter categoria específica
```http
GET /api/categories/{id}
Authorization: Bearer {token}
```

#### **PUT /api/categories/{id}** - Atualizar categoria
```http
PUT /api/categories/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Categoria Atualizada",
  "transactionTypeId": 2,
  "businessUnitId": 1
}
```

#### **DELETE /api/categories/{id}** - Deletar categoria
```http
DELETE /api/categories/{id}
Authorization: Bearer {token}
```

---

### **👥 Perfis (Profile - Cliente/Fornecedor)**

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

**Tipos de Perfil:**
- `CLIENT` - Cliente
- `SUPPLIER` - Fornecedor  
- `BOTH` - Cliente e Fornecedor

#### **GET /api/profiles** - Listar perfis
```http
GET /api/profiles?businessUnitId=1&profileType=CLIENT&search=joão
Authorization: Bearer {token}
```

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

### **💰 Fluxo de Caixa (CashFlow)**

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

## 🔧 **Configuração do Ambiente**

### **Base URL:**
```
http://localhost:8080
```

### **Configuração do Banco:**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/wai
    username: root
    password: root
```

### **Porta Padrão:**
```
8080
```

---

## 🎯 **Fluxo de Desenvolvimento Frontend**

### **1. Autenticação:**
1. Usuário faz cadastro/login
2. Recebe token JWT
3. Armazena token no localStorage/sessionStorage
4. Inclui token em todos os requests

### **2. Controle de Acesso:**
1. Decodifica token para obter role e businessUnitId
2. Mostra/esconde funcionalidades baseado no role
3. Filtra dados baseado no businessUnitId

### **3. Interface:**
1. **ADMIN**: Dashboard completo com todas as unidades
2. **USER**: Dashboard limitado à sua unidade
3. **Responsivo**: Adaptar para mobile/desktop

### **4. Funcionalidades Principais:**
1. **Gestão de Usuários** (ADMIN)
2. **Gestão de Business Units** (ADMIN)
3. **Gestão de Perfis** (Clientes/Fornecedores)
4. **Gestão de Cash Flow** (todos)
5. **Relatórios Financeiros** (todos)

---

## 📱 **Estrutura Sugerida para Frontend**

### **Páginas Principais:**
- `/login` - Login/Cadastro
- `/dashboard` - Dashboard principal
- `/business-units` - Gestão de unidades (ADMIN)
- `/users` - Gestão de usuários (ADMIN)
- `/profiles` - Gestão de clientes/fornecedores
- `/cash-flow` - Gestão financeira
- `/reports` - Relatórios

### **Componentes Sugeridos:**
- `AuthProvider` - Gerenciar autenticação
- `RoleGuard` - Proteger rotas por role
- `BusinessUnitContext` - Contexto da unidade atual
- `CashFlowTable` - Tabela de fluxo de caixa
- `BusinessUnitSelector` - Seletor de unidade (ADMIN)
- `ProfileSelector` - Seletor de cliente/fornecedor

---

## 🔄 **Fluxo de Integração Frontend-Backend**

### **1. Autenticação:**
1. Frontend envia credenciais para `/api/users/login`
2. Backend retorna JWT token
3. Frontend armazena token e inclui no header `Authorization: Bearer {token}`

### **2. Criação de Movimentação:**
1. Frontend busca perfis disponíveis via `/api/profiles`
2. Frontend busca categorias, métodos de pagamento, etc.
3. Frontend envia dados para `/api/cash-flows` incluindo `profileId` (opcional)
4. Backend valida acesso e cria movimentação
5. Backend retorna movimentação criada com dados do perfil

### **3. Listagem de Movimentações:**
1. Frontend busca movimentações via `/api/cash-flows`
2. Backend aplica filtros de acesso baseado no token
3. Backend retorna lista com informações completas incluindo perfis

---

## 🚀 **Próximos Passos**

1. **Escolher Framework**: React, Vue, Angular, etc.
2. **Configurar Autenticação**: JWT token management
3. **Implementar Controle de Acesso**: Role-based UI
4. **Criar Componentes**: Reutilizáveis e responsivos
5. **Testar Integração**: Conectar com a API
6. **Deploy**: Produção

---

## 📝 **Observações Importantes**

- Todos os endpoints que alteram dados exigem o header `Authorization: Bearer {token}`
- Os IDs de entidades relacionadas (ex: `businessUnitId`, `categoryId`, `profileId`) devem ser obtidos previamente via os endpoints de listagem
- Os campos obrigatórios e opcionais estão descritos nos exemplos acima
- O sistema é multi-tenant, então cada usuário só acessa dados da sua unidade de negócio (exceto ADMIN)
- Para dúvidas sobre o formato de resposta, consulte a documentação da API ou peça exemplos específicos

---

**📞 Suporte:** Esta documentação contém todas as informações necessárias para desenvolver o frontend completo do sistema de gestão financeira! 