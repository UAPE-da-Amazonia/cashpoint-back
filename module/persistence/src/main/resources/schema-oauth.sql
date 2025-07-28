-- Adicionar colunas para suporte a OAuth2
ALTER TABLE users 
ADD COLUMN provider VARCHAR(50) NULL,
ADD COLUMN provider_id VARCHAR(100) NULL;

-- Criar índices para melhor performance
CREATE INDEX idx_users_provider_provider_id ON users(provider, provider_id);
CREATE INDEX idx_users_email_provider ON users(email, provider);

-- Comentários para documentação
COMMENT ON COLUMN users.provider IS 'OAuth provider name (e.g., google, facebook)';
COMMENT ON COLUMN users.provider_id IS 'Unique ID from the OAuth provider'; 