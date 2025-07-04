# API ENDPOINTS - DOCUMENTAÇÃO PARA FRONTEND

## 🔐 AUTENTICAÇÃO

### Login
```http
POST /api/users/login
Content-Type: application/json
```

**Request Body:**
```json
{
  "user": {
    "email": "davi@teste.com",
    "password": "111111"
  }
}
```

**Response:**
```json
{
  "user": {
    "name": "daviConde",
    "email": "davi@teste.com",
    "token": "eyJhbGciOiJSUzI1NiJ9...",
    "username": "daviTeste",
    "businessUnit": {
      "id": 1,
      "name": "Manaus",
      "description": "Unidade sede"
    },
    "role": "ADMIN",
    "bio": null,
    "image": null
  }
}
```

---

## 👥 USERS - CRUD COMPLETO ✅

### GET All Users
```http
GET /api/users
Authorization: Bearer {token}
```

**Response:**
```json
{
  "users": [
    {
      "name": "Admin",
      "email": "daviconde8@gmail.com",
      "token": null,
      "username": "admin",
      "businessUnit": {
        "id": 1,
        "name": "Manaus",
        "description": "Unidade sede"
      },
      "role": "ADMIN",
      "bio": "Administrador do sistema",
      "image": null
    }
  ]
}
```

### GET User by ID
```http
GET /api/users/{id}
Authorization: Bearer {token}
```

### CREATE User
```http
POST /api/users
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "user": {
    "name": "Novo Usuário",
    "email": "novo@email.com",
    "username": "novousuario",
    "password": "123456",
    "businessUnitId": 1,
    "role": "user"
  }
}
```

### UPDATE User
```http
PUT /api/users/{id}
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "user": {
    "name": "Usuário Atualizado",
    "email": "atualizado@email.com",
    "username": "atualizado",
    "password": "123456",
    "businessUnitId": 1,
    "role": "admin",
    "bio": "Bio atualizada",
    "image": "https://example.com/image.jpg"
  }
}
```

### DELETE User
```http
DELETE /api/users/{id}
Authorization: Bearer {token}
```

---

## 💰 CASHFLOW - CRUD COMPLETO ✅

### GET All CashFlows
```http
GET /api/cash-flows
Authorization: Bearer {token}
```

### GET CashFlow by ID
```http
GET /api/cash-flows/{id}
Authorization: Bearer {token}
```

### CREATE CashFlow
```http
POST /api/cash-flows
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
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

### UPDATE CashFlow
```http
PUT /api/cash-flows/{id}
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "cashFlow": {
    "amount": 2000.00,
    "transactionDate": "2024-01-16",
    "description": "Venda atualizada",
    "paymentMethodId": 1,
    "transactionTypeId": 1,
    "categoryId": 1,
    "accountTypeId": 1,
    "profileId": 1
  }
}
```

### DELETE CashFlow
```http
DELETE /api/cash-flows/{id}
Authorization: Bearer {token}
```

### SEARCH CashFlows by Date Range
```http
GET /api/cash-flows/search?startDate=2024-01-01&endDate=2024-01-31
Authorization: Bearer {token}
```

### MARK AS CHECKED/UNCHECKED
```http
PUT /api/cash-flows/{id}/check
PUT /api/cash-flows/{id}/uncheck
Authorization: Bearer {token}
```

### ACTIVATE/DEACTIVATE
```http
PUT /api/cash-flows/{id}/activate
PUT /api/cash-flows/{id}/deactivate
Authorization: Bearer {token}
```

---

## 👤 PROFILES - CRUD COMPLETO ✅

### GET All Profiles
```http
GET /api/profiles?businessUnitId=1
Authorization: Bearer {token}
```

**Response:**
```json
{
  "profiles": [
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
      "created_at": "2025-07-04T00:59:01.000Z",
      "updated_at": null
    }
  ]
}
```

### GET Profile by ID
```http
GET /api/profiles/{id}
Authorization: Bearer {token}
```

### CREATE Profile
```http
POST /api/profiles
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "Novo Cliente",
  "email": "cliente@email.com",
  "phone": "(11) 12345-6789",
  "document": "123.456.789-00",
  "address": "Rua Nova, 123",
  "profile_type": "CLIENT"
}
```

### UPDATE Profile
```http
PUT /api/profiles/{id}
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "Cliente Atualizado",
  "email": "atualizado@email.com",
  "phone": "(11) 98765-4321",
  "document": "987.654.321-00",
  "address": "Rua Atualizada, 456",
  "profile_type": "SUPPLIER"
}
```

### DELETE Profile
```http
DELETE /api/profiles/{id}
Authorization: Bearer {token}
```

### ACTIVATE/DEACTIVATE Profile
```http
PUT /api/profiles/{id}/activate
PUT /api/profiles/{id}/deactivate
Authorization: Bearer {token}
```

---

## 🏢 BUSINESS UNIT - CRUD COMPLETO ✅

### GET All Business Units
```http
GET /api/businessunit
Authorization: Bearer {token}
```

**Response:**
```json
{
  "businessUnits": [
    {
      "id": 1,
      "name": "Manaus",
      "description": "Unidade sede",
      "createdAt": "2025-07-02T15:57:22",
      "updatedAt": "2025-07-02T15:57:22"
    }
  ]
}
```

### GET Business Unit by ID
```http
GET /api/businessunit/{id}
Authorization: Bearer {token}
```

### CREATE Business Unit
```http
POST /api/businessunit
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "Nova Unidade",
  "description": "Descrição da nova unidade"
}
```

### UPDATE Business Unit
```http
PUT /api/businessunit/{id}
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "Unidade Atualizada",
  "description": "Descrição atualizada"
}
```

### DELETE Business Unit
```http
DELETE /api/businessunit/{id}
Authorization: Bearer {token}
```

---

## 📂 CATEGORIES - CRUD COMPLETO ✅

### GET All Categories
```http
GET /api/categories
Authorization: Bearer {token}
```

### GET Category by ID
```http
GET /api/categories/{id}
Authorization: Bearer {token}
```

### GET Categories by Transaction Type
```http
GET /api/categories/by-transaction-type?transactionTypeId=1
Authorization: Bearer {token}
```

### CREATE Category
```http
POST /api/categories
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "Nova Categoria",
  "transactionTypeId": 1,
  "businessUnitId": 1
}
```

### UPDATE Category
```http
PUT /api/categories/{id}
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "Categoria Atualizada",
  "transactionTypeId": 1,
  "businessUnitId": 1
}
```

### DELETE Category
```http
DELETE /api/categories/{id}
Authorization: Bearer {token}
```

---

## 💳 PAYMENT METHODS - APENAS GET ✅

### GET All Payment Methods
```http
GET /api/payment-methods
Authorization: Bearer {token}
```

**Response:**
```json
{
  "paymentMethods": [
    {
      "id": 1,
      "name": "PIX",
      "description": "Pagamento instantâneo via PIX",
      "businessUnitId": 1,
      "isActive": true
    }
  ]
}
```

---

## 🏦 ACCOUNT TYPES - APENAS GET ❌ (ERRO 500)

### GET All Account Types
```http
GET /api/account-types
Authorization: Bearer {token}
```

**Status:** Retorna erro 500 - Internal Server Error

---

## 💸 TRANSACTION TYPES - CRUD COMPLETO ✅

### GET All Transaction Types
```http
GET /api/transaction-types
Authorization: Bearer {token}
```

**Response:**
```json
{
  "transactionTypes": [
    {
      "id": 1,
      "name": "Income"
    },
    {
      "id": 2,
      "name": "Expense"
    }
  ]
}
```

### GET Transaction Type by ID
```http
GET /api/transaction-types/{id}
Authorization: Bearer {token}
```

### CREATE Transaction Type
```http
POST /api/transaction-types
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "Novo Tipo"
}
```

### UPDATE Transaction Type
```http
PUT /api/transaction-types/{id}
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "Tipo Atualizado"
}
```

### DELETE Transaction Type
```http
DELETE /api/transaction-types/{id}
Authorization: Bearer {token}
```

---

## 📊 RESUMO DOS ENDPOINTS

### ✅ CRUD COMPLETO (CREATE, READ, UPDATE, DELETE):
1. **USERS** - ✅ Funcionando
2. **CASHFLOW** - ✅ Funcionando (com operações extras)
3. **PROFILES** - ✅ Funcionando
4. **BUSINESS UNIT** - ✅ Funcionando
5. **CATEGORIES** - ✅ Funcionando

### ✅ APENAS GET:
1. **PAYMENT METHODS** - ✅ Funcionando

### ✅ CRUD COMPLETO (CREATE, READ, UPDATE, DELETE):
1. **USERS** - ✅ Funcionando
2. **CASHFLOW** - ✅ Funcionando (com operações extras)
3. **PROFILES** - ✅ Funcionando
4. **BUSINESS UNIT** - ✅ Funcionando
5. **CATEGORIES** - ✅ Funcionando
6. **TRANSACTION TYPES** - ✅ Funcionando (CRUD completo adicionado)

### ❌ COM PROBLEMAS:
1. **ACCOUNT TYPES** - ❌ Erro 500

---

## 🔧 OBSERVAÇÕES IMPORTANTES

1. **Autenticação:** Todos os endpoints requerem token Bearer no header Authorization
2. **Permissões:** Alguns endpoints só funcionam para usuários ADMIN
3. **Business Unit:** A maioria dos endpoints está vinculada à unidade de negócio do usuário
4. **IDs:** Os IDs são numéricos (Integer) para a maioria das entidades, exceto Users que usa UUID
5. **Datas:** Use formato ISO 8601 (YYYY-MM-DD) para datas
6. **Valores:** Use BigDecimal para valores monetários (ex: 1500.00)

---

## 🚀 PRÓXIMOS PASSOS PARA O FRONTEND

1. Implementar autenticação com login/logout
2. Criar componentes para cada entidade (Users, CashFlow, Profiles, etc.)
3. Implementar formulários de criação/edição
4. Criar listagens com paginação
5. Implementar filtros e busca
6. Adicionar confirmações para operações de DELETE
7. Tratar erros de API adequadamente 