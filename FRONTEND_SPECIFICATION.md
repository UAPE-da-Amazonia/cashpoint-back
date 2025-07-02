# 🎯 Especificação Técnica - Frontend RealWorld

## 📋 **Resumo do Projeto**

### **Sistema:**
- **Backend**: Java 21 + Spring Boot 3 + MySQL
- **Autenticação**: JWT Token
- **Arquitetura**: Multi-tenant por Business Unit
- **Roles**: ADMIN e USER

### **Funcionalidades Principais:**
1. **Gestão de Usuários** (ADMIN)
2. **Gestão de Business Units** (ADMIN)
3. **Gestão de Cash Flow** (todos)
4. **Relatórios Financeiros** (todos)
5. **Blog/Artigos** (todos)

---

## 🔐 **Sistema de Autenticação**

### **Token JWT:**
```json
{
  "sub": "user-uuid",
  "businessUnitId": 1,
  "role": "ADMIN",
  "username": "joao",
  "iat": 1234567890,
  "exp": 1234568190
}
```

### **Fluxo de Autenticação:**
1. Usuário faz login/cadastro
2. Recebe token JWT
3. Armazena token no localStorage
4. Inclui token em todos os requests
5. Decodifica token para obter role e businessUnitId

---

## 🏗️ **Estrutura de Dados**

### **User:**
```typescript
interface User {
  id: string;
  name: string;
  email: string;
  username: string;
  businessUnit: BusinessUnit;
  role: 'ADMIN' | 'USER';
  bio?: string;
  image?: string;
  token: string;
}
```

### **BusinessUnit:**
```typescript
interface BusinessUnit {
  id: number;
  name: string;
  description?: string;
  createdAt: string;
  updatedAt?: string;
}
```

### **CashFlow:**
```typescript
interface CashFlow {
  id: number;
  amount: number;
  transactionDate: string;
  description: string;
  paymentMethod: PaymentMethod;
  transactionType: TransactionType;
  category: Category;
  accountType: AccountType;
  businessUnit: BusinessUnit;
  createdAt: string;
  updatedAt?: string;
  createdBy: string;
  updatedBy?: string;
  isActive: boolean;
  isChecked: boolean;
}
```

---

## 🎨 **Interface do Usuário**

### **Layout Responsivo:**
- **Desktop**: Dashboard completo com sidebar
- **Tablet**: Dashboard adaptado
- **Mobile**: Menu hambúrguer

### **Tema:**
- **Cores**: Azul corporativo (#2563eb)
- **Tipografia**: Inter ou Roboto
- **Ícones**: Lucide React ou Heroicons

---

## 📱 **Páginas e Rotas**

### **1. Autenticação:**
```
/login - Login e cadastro
```

### **2. Dashboard Principal:**
```
/dashboard - Dashboard principal
```

### **3. Gestão (ADMIN):**
```
/business-units - Gestão de unidades
/users - Gestão de usuários
```

### **4. Financeiro:**
```
/cash-flow - Lista de transações
/cash-flow/new - Nova transação
/cash-flow/:id - Detalhes/Edição
/reports - Relatórios
```

### **5. Blog:**
```
/blog - Lista de artigos
/blog/new - Novo artigo
/blog/:slug - Visualizar artigo
```

---

## 🔧 **Componentes Principais**

### **1. AuthProvider:**
```typescript
interface AuthContext {
  user: User | null;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
  isAuthenticated: boolean;
  isAdmin: boolean;
}
```

### **2. RoleGuard:**
```typescript
interface RoleGuardProps {
  children: React.ReactNode;
  allowedRoles: ('ADMIN' | 'USER')[];
  fallback?: React.ReactNode;
}
```

### **3. BusinessUnitSelector (ADMIN):**
```typescript
interface BusinessUnitSelectorProps {
  selectedId: number;
  onSelect: (id: number) => void;
  businessUnits: BusinessUnit[];
}
```

### **4. CashFlowTable:**
```typescript
interface CashFlowTableProps {
  cashFlows: CashFlow[];
  onEdit: (id: number) => void;
  onDelete: (id: number) => void;
  onCheck: (id: number) => void;
}
```

---

## 🎯 **Funcionalidades por Role**

### **ADMIN:**
- ✅ Ver todas as Business Units
- ✅ Criar/editar/deletar Business Units
- ✅ Ver todos os Cash Flows
- ✅ Criar/editar/deletar Cash Flows em qualquer unidade
- ✅ Gestão de usuários
- ✅ Relatórios globais

### **USER:**
- ✅ Ver apenas sua Business Unit
- ✅ Ver apenas Cash Flows da sua unidade
- ✅ Criar/editar/deletar Cash Flows da sua unidade
- ✅ Relatórios da sua unidade

---

## 📊 **Dashboard Layout**

### **ADMIN Dashboard:**
```
┌─────────────────────────────────────┐
│ Header (Logo + User Menu)           │
├─────────────────────────────────────┤
│ Sidebar:                            │
│ ├ Dashboard                         │
│ ├ Business Units                    │
│ ├ Users                             │
│ ├ Cash Flow                         │
│ ├ Reports                           │
│ └ Blog                              │
├─────────────────────────────────────┤
│ Main Content:                       │
│ ├ Business Unit Selector            │
│ ├ Quick Stats (Todas as unidades)   │
│ ├ Recent Cash Flows                 │
│ └ Charts                            │
└─────────────────────────────────────┘
```

### **USER Dashboard:**
```
┌─────────────────────────────────────┐
│ Header (Logo + User Menu)           │
├─────────────────────────────────────┤
│ Sidebar:                            │
│ ├ Dashboard                         │
│ ├ Cash Flow                         │
│ ├ Reports                           │
│ └ Blog                              │
├─────────────────────────────────────┤
│ Main Content:                       │
│ ├ Quick Stats (Sua unidade)         │
│ ├ Recent Cash Flows                 │
│ └ Charts                            │
└─────────────────────────────────────┘
```

---

## 🎨 **Design System**

### **Cores:**
```css
:root {
  --primary: #2563eb;
  --primary-dark: #1d4ed8;
  --secondary: #64748b;
  --success: #10b981;
  --warning: #f59e0b;
  --danger: #ef4444;
  --background: #f8fafc;
  --surface: #ffffff;
  --text: #1e293b;
  --text-light: #64748b;
}
```

### **Componentes Base:**
- Button (Primary, Secondary, Danger)
- Input (Text, Number, Date, Select)
- Card
- Table
- Modal
- Toast/Notification
- Loading Spinner

---

## 📱 **Responsividade**

### **Breakpoints:**
```css
/* Mobile */
@media (max-width: 768px) { }

/* Tablet */
@media (min-width: 769px) and (max-width: 1024px) { }

/* Desktop */
@media (min-width: 1025px) { }
```

### **Mobile First:**
- Menu hambúrguer
- Cards empilhados
- Botões touch-friendly
- Formulários otimizados

---

## 🚀 **Tecnologias Recomendadas**

### **Framework:**
- **React** + **TypeScript**
- **Next.js** (para SSR/SSG)

### **UI Library:**
- **Tailwind CSS** + **Headless UI**
- **Shadcn/ui** (componentes prontos)

### **State Management:**
- **Zustand** ou **Context API**

### **HTTP Client:**
- **Axios** ou **TanStack Query**

### **Formulários:**
- **React Hook Form** + **Zod**

### **Charts:**
- **Recharts** ou **Chart.js**

---

## 📦 **Estrutura de Pastas**

```
src/
├── components/
│   ├── ui/           # Componentes base
│   ├── layout/       # Layout components
│   ├── forms/        # Formulários
│   └── charts/       # Gráficos
├── hooks/
│   ├── useAuth.ts
│   ├── useApi.ts
│   └── useBusinessUnit.ts
├── services/
│   ├── api.ts
│   ├── auth.ts
│   └── cashFlow.ts
├── types/
│   ├── user.ts
│   ├── businessUnit.ts
│   └── cashFlow.ts
├── utils/
│   ├── auth.ts
│   ├── format.ts
│   └── validation.ts
├── pages/
│   ├── login/
│   ├── dashboard/
│   ├── business-units/
│   ├── cash-flow/
│   └── reports/
└── contexts/
    └── AuthContext.tsx
```

---

## 🔧 **Configuração Inicial**

### **1. Variáveis de Ambiente:**
```env
NEXT_PUBLIC_API_URL=http://localhost:8080
NEXT_PUBLIC_APP_NAME=RealWorld Business
```

### **2. Configuração do Axios:**
```typescript
// services/api.ts
import axios from 'axios';

const api = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL,
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
```

### **3. Context de Autenticação:**
```typescript
// contexts/AuthContext.tsx
export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  
  const login = async (email: string, password: string) => {
    const response = await api.post('/api/users/login', { user: { email, password } });
    const userData = response.data.user;
    setUser(userData);
    localStorage.setItem('token', userData.token);
  };
  
  const logout = () => {
    setUser(null);
    localStorage.removeItem('token');
  };
  
  return (
    <AuthContext.Provider value={{ user, login, logout, isAuthenticated: !!user }}>
      {children}
    </AuthContext.Provider>
  );
};
```

---

## 🎯 **Próximos Passos**

1. **Setup do Projeto**: Criar projeto Next.js com TypeScript
2. **Configuração**: Tailwind CSS, Axios, React Hook Form
3. **Autenticação**: Implementar login/logout
4. **Layout**: Header, Sidebar, Dashboard
5. **Páginas**: Business Units, Cash Flow, Reports
6. **Testes**: Testar integração com API
7. **Deploy**: Vercel ou similar

---

**📞 Suporte**: Esta especificação contém tudo necessário para desenvolver o frontend completo! 

# 📖 Especificação de Integração Frontend-Backend

Este documento descreve os principais endpoints da API para integração do frontend com o backend do sistema de gestão financeira multi-tenant.

---

## Autenticação

### Login
```http
POST /api/users/login
Content-Type: application/json

{
  "user": {
    "email": "john@example.com",
    "password": "password123"
  }
}
```

### Cadastro de Usuário
```http
POST /api/users
Content-Type: application/json

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

### Obter Usuário Logado
```http
GET /api/user
Authorization: Bearer {token}
```

### Atualizar Usuário
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

## Unidades de Negócio (BusinessUnit)

### Listar Unidades
```http
GET /api/business-units
Authorization: Bearer {token}
```

### Criar Unidade
```http
POST /api/business-units
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Empresa Nova",
  "description": "Descrição da empresa"
}
```

### Obter Unidade Específica
```http
GET /api/business-units/{id}
Authorization: Bearer {token}
```

### Atualizar Unidade
```http
PUT /api/business-units/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Empresa Atualizada",
  "description": "Nova descrição"
}
```

### Deletar Unidade
```http
DELETE /api/business-units/{id}
Authorization: Bearer {token}
```

---

## Categorias (Category)

### Listar Categorias
```http
GET /api/categories?businessUnit=1
Authorization: Bearer {token}
```

### Criar Categoria
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

### Obter Categoria Específica
```http
GET /api/categories/{id}
Authorization: Bearer {token}
```

### Atualizar Categoria
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

### Deletar Categoria
```http
DELETE /api/categories/{id}
Authorization: Bearer {token}
```

---

## Perfis (Profile - Cliente/Fornecedor)

### Criar Perfil
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

### Listar Perfis
```http
GET /api/profiles?businessUnitId=1&profileType=CLIENT&search=joao
Authorization: Bearer {token}
```

### Obter Perfil Específico
```http
GET /api/profiles/{id}
Authorization: Bearer {token}
```

### Atualizar Perfil
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

### Deletar Perfil
```http
DELETE /api/profiles/{id}
Authorization: Bearer {token}
```

### Ativar/Desativar Perfil
```http
PUT /api/profiles/{id}/activate
Authorization: Bearer {token}

PUT /api/profiles/{id}/deactivate
Authorization: Bearer {token}
```

---

## Fluxo de Caixa (CashFlow)

### Criar Movimentação
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

### Listar Movimentações
```http
GET /api/cash-flows?businessUnit=1
Authorization: Bearer {token}
```

### Obter Movimentação Específica
```http
GET /api/cash-flows/{id}
Authorization: Bearer {token}
```

### Atualizar Movimentação
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

### Deletar Movimentação
```http
DELETE /api/cash-flows/{id}
Authorization: Bearer {token}
```

---

## Observações Gerais
- Todos os endpoints que alteram dados exigem o header `Authorization: Bearer {token}`.
- Os IDs de entidades relacionadas (ex: `businessUnitId`, `categoryId`, `profileId`) devem ser obtidos previamente via os endpoints de listagem.
- Os campos obrigatórios e opcionais estão descritos nos exemplos acima.
- Para dúvidas sobre o formato de resposta, consulte a documentação da API ou peça exemplos específicos. 