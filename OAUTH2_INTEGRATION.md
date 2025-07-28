# 🔐 Integração OAuth2 com Google

Este documento explica como configurar e usar a autenticação OAuth2 com Google no seu backend.

## 📋 Pré-requisitos

1. **Conta Google Cloud Platform**
2. **Projeto configurado no Google Cloud Console**
3. **Credenciais OAuth2 configuradas**

## ⚙️ Configuração do Google Cloud Console

### 1. Criar um projeto no Google Cloud Console
1. Acesse [Google Cloud Console](https://console.cloud.google.com/)
2. Crie um novo projeto ou selecione um existente
3. Ative a API do Google+ (se necessário)

### 2. Configurar credenciais OAuth2
1. Vá para **APIs & Services** > **Credentials**
2. Clique em **Create Credentials** > **OAuth 2.0 Client IDs**
3. Configure o tipo de aplicação (Web application)
4. Adicione as URLs autorizadas:
   - `http://localhost:3000` (para desenvolvimento)
   - `https://seu-dominio.com` (para produção)
5. Anote o **Client ID** e **Client Secret**

### 3. Configurar URIs de redirecionamento
Adicione as seguintes URIs de redirecionamento:
- `http://localhost:3000/auth/google/callback`
- `https://seu-dominio.com/auth/google/callback`

## 🔧 Configuração do Backend

### 1. Variáveis de Ambiente

Crie um arquivo `.env` na raiz do projeto:

```env
# Google OAuth2 Configuration
GOOGLE_CLIENT_ID=seu-google-client-id
GOOGLE_CLIENT_SECRET=seu-google-client-secret

# Outras configurações existentes
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/wai
MYSQL_USER=seu_user_sem_aspas
MYSQL_PASSWORD=sua_senha_sem_aspas
```

### 2. Atualizar o Banco de Dados

Execute o script SQL para adicionar as colunas necessárias:

```sql
-- Executar o arquivo: module/persistence/src/main/resources/schema-oauth.sql
```

Ou execute manualmente:

```sql
ALTER TABLE users 
ADD COLUMN provider VARCHAR(50) NULL,
ADD COLUMN provider_id VARCHAR(100) NULL;

CREATE INDEX idx_users_provider_provider_id ON users(provider, provider_id);
CREATE INDEX idx_users_email_provider ON users(email, provider);
```

## 🚀 Como Usar

### 1. Frontend - Obter Token do Google

```javascript
// Exemplo usando Google Sign-In API
function signInWithGoogle(businessUnitId) {
  google.accounts.oauth2.initTokenClient({
    client_id: 'SEU_GOOGLE_CLIENT_ID',
    scope: 'email profile',
    callback: (response) => {
      if (response.access_token) {
        // Enviar token para o backend
        loginWithGoogle(response.access_token, businessUnitId);
      }
    },
  }).requestAccessToken();
}

async function loginWithGoogle(accessToken, businessUnitId) {
  try {
    const response = await fetch('http://localhost:8082/api/oauth/google/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        accessToken: accessToken,
        businessUnitId: businessUnitId
      })
    });

    const data = await response.json();
    
    if (response.ok) {
      // Salvar token JWT
      localStorage.setItem('token', data.user.token);
      // Redirecionar ou atualizar UI
    } else {
      console.error('Erro no login:', data);
    }
  } catch (error) {
    console.error('Erro na requisição:', error);
  }
}

// Exemplo de uso
document.getElementById('googleLoginBtn').addEventListener('click', () => {
  // businessUnitId deve ser obtido do contexto da aplicação
  const businessUnitId = getCurrentBusinessUnitId(); // Implemente conforme sua lógica
  signInWithGoogle(businessUnitId);
});
```

### 2. Backend - Endpoint de Login

O endpoint `/api/oauth/google/login` está disponível e:

1. **Recebe** o token de acesso do Google e businessUnitId
2. **Valida** o token com a API do Google
3. **Extrai** informações do usuário (email, nome, foto)
4. **Cria ou atualiza** o usuário no banco de dados na business unit especificada
5. **Retorna** um token JWT para autenticação

### 3. Resposta do Endpoint

```json
{
  "user": {
    "id": "uuid-do-usuario",
    "name": "Nome do Usuário",
    "email": "usuario@gmail.com",
    "username": "usuario",
    "role": "USER",
    "businessUnit": {
      "id": 1,
      "name": "Unidade de Negócio"
    },
    "bio": null,
    "imageUrl": "https://lh3.googleusercontent.com/...",
    "provider": "google",
    "providerId": "123456789",
    "createdAt": "2024-01-01T10:00:00",
    "token": "jwt-token-aqui"
  }
}
```

## 🔄 Fluxo de Autenticação

### 1. Primeiro Login (Novo Usuário)
1. Frontend obtém businessUnitId do contexto da aplicação
2. Usuário faz login com Google no frontend
3. Frontend envia token + businessUnitId para `/api/oauth/google/login`
4. Backend valida token com Google
5. Backend cria novo usuário na business unit especificada
6. Backend retorna JWT token
7. Frontend salva token e autentica usuário

### 2. Login Subsequente (Usuário Existente)
1. Frontend obtém businessUnitId do contexto da aplicação
2. Usuário faz login com Google no frontend
3. Frontend envia token + businessUnitId para `/api/oauth/google/login`
4. Backend valida token com Google
5. Backend encontra usuário existente por `provider` + `providerId`
6. Backend retorna JWT token
7. Frontend salva token e autentica usuário

### 3. Usuário com Email Existente na Mesma Business Unit
Se um usuário já existe com o mesmo email na mesma business unit:
1. Backend encontra usuário por email
2. Backend verifica se está na mesma business unit
3. Backend atualiza usuário com informações OAuth
4. Backend retorna JWT token

### 4. Usuário com Email Existente em Business Unit Diferente
Se um usuário já existe com o mesmo email em business unit diferente:
1. Backend encontra usuário por email
2. Backend verifica business unit e rejeita se for diferente
3. Backend retorna erro: "User already exists in a different business unit"

## 🛡️ Segurança

### 1. Validação de Token
- Todos os tokens do Google são validados com a API oficial
- Tokens inválidos são rejeitados
- Informações do usuário são extraídas apenas de tokens válidos

### 2. Isolamento por Business Unit
- Usuários são sempre criados na business unit especificada
- Usuários existentes só podem ser atualizados se estiverem na mesma business unit
- Tentativas de login em business unit diferente são rejeitadas

### 3. Armazenamento Seguro
- Senhas não são armazenadas para usuários OAuth
- Provider ID é armazenado para identificação única
- Tokens JWT têm expiração configurável

### 4. CORS
- Configure CORS adequadamente para produção
- Restrinja origens permitidas

## 🔧 Configurações Avançadas

### 1. Obter Business Unit ID no Frontend

```javascript
// Exemplo de como obter businessUnitId no frontend
function getCurrentBusinessUnitId() {
  // Pode vir de:
  // 1. URL parameters
  // 2. Local storage
  // 3. Context da aplicação
  // 4. Seleção do usuário
  
  const urlParams = new URLSearchParams(window.location.search);
  const businessUnitId = urlParams.get('businessUnitId');
  
  if (!businessUnitId) {
    throw new Error('Business Unit ID é obrigatório');
  }
  
  return parseInt(businessUnitId);
}
```

### 2. Escopos Adicionais
Para solicitar escopos adicionais do Google:

```yaml
# application.yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            scope:
              - email
              - profile
              - https://www.googleapis.com/auth/calendar.readonly
```

### 3. Expiração de Token JWT
Para configurar a expiração do token JWT:

```java
// Em AuthTokenProvider.java
.expiresAt(now.plusSeconds(86400)) // 24 horas
```

## 🐛 Troubleshooting

### 1. Erro: "Invalid Google OAuth2 token"
- Verifique se o token está sendo enviado corretamente
- Confirme se as credenciais do Google estão corretas
- Verifique se o token não expirou

### 2. Erro: "BusinessUnit não encontrada"
- Verifique se a businessUnitId está sendo enviada
- Confirme se a business unit existe no banco
- Verifique se o ID está correto

### 3. Erro: "User already exists in a different business unit"
- O usuário já existe em outra business unit
- Use o login tradicional ou entre em contato com o administrador

### 4. Erro de CORS
- Configure CORS adequadamente no SecurityConfiguration
- Verifique se as origens estão permitidas

### 5. Usuário não encontrado
- Verifique se o usuário existe no banco
- Confirme se o provider e providerId estão corretos

## 📝 Notas Importantes

1. **Sempre use HTTPS em produção**
2. **Configure CORS adequadamente**
3. **Monitore logs para detectar problemas**
4. **Teste com diferentes contas Google**
5. **Mantenha as credenciais seguras**
6. **Business Unit ID é obrigatório** - o frontend deve sempre enviar
7. **Usuários são isolados por business unit** - não é possível usar a mesma conta Google em business units diferentes

## 🔗 Links Úteis

- [Google OAuth2 Documentation](https://developers.google.com/identity/protocols/oauth2)
- [Spring Security OAuth2](https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html)
- [Google Cloud Console](https://console.cloud.google.com/) 