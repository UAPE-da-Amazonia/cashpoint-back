CREATE SCHEMA wai;
USE wai;
-- ==========================================
-- TABLE: business_unit
-- ==========================================
CREATE TABLE IF NOT EXISTS business_unit (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL UNIQUE,
  description TEXT,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==========================================
-- TABLE: transaction_type (ex-tipomovimento)
-- ==========================================
CREATE TABLE IF NOT EXISTS transaction_type (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL, -- "Income" or "Expense"
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==========================================
-- TABLE: payment_method (ex-tipopagamento)
-- ==========================================
CREATE TABLE IF NOT EXISTS payment_method (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==========================================
-- TABLE: account_type (ex-tipoconta)
-- ==========================================
CREATE TABLE IF NOT EXISTS account_type (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL COMMENT 'Bank + Type (e.g., BASA - Checking)',
  business_unit_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (business_unit_id) REFERENCES business_unit(id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==========================================
-- TABLE: category (ex-categoria)
-- ==========================================
CREATE TABLE IF NOT EXISTS category (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  transaction_type_id INT NOT NULL,
  business_unit_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (transaction_type_id) REFERENCES transaction_type(id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (business_unit_id) REFERENCES business_unit(id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==========================================
-- TABLE: cash_flow (ex-caixa)
-- ==========================================
CREATE TABLE IF NOT EXISTS cash_flow (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  amount DECIMAL(16,2) NOT NULL,
  transaction_date DATE NOT NULL,
  description TEXT NOT NULL,
  payment_method_id INT NOT NULL,
  transaction_type_id INT NOT NULL,
  category_id INT NOT NULL,
  account_type_id INT NOT NULL,
  business_unit_id INT NOT NULL,
  created_at DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  created_by VARCHAR(45),
  updated_at DATETIME NULL ON UPDATE CURRENT_TIMESTAMP,
  updated_by VARCHAR(45),
  is_active BOOLEAN DEFAULT TRUE,
  is_checked BOOLEAN DEFAULT FALSE COMMENT 'Reviewed or not',
  PRIMARY KEY (id),
  FOREIGN KEY (payment_method_id) REFERENCES payment_method(id),
  FOREIGN KEY (transaction_type_id) REFERENCES transaction_type(id),
  FOREIGN KEY (category_id) REFERENCES category(id),
  FOREIGN KEY (account_type_id) REFERENCES account_type(id),
  FOREIGN KEY (business_unit_id) REFERENCES business_unit(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==========================================
-- TABLE: role (ex-tab_perfil)
-- ==========================================
CREATE TABLE IF NOT EXISTS role (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(60) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_role_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==========================================
-- TABLE: screen_access (ex-tab_acesso_perfil)
-- ==========================================
CREATE TABLE IF NOT EXISTS screen_access (
  id INT NOT NULL AUTO_INCREMENT,
  screen_name VARCHAR(60) NOT NULL,
  role_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (role_id) REFERENCES role(id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==========================================
-- TABLE: user_account (ex-tab_usuario)
-- ==========================================
CREATE TABLE IF NOT EXISTS user_account (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(60) NOT NULL,
  username VARCHAR(45) NOT NULL UNIQUE,
  password_hash VARCHAR(128) NOT NULL,
  gender VARCHAR(20),
  google_auth_enabled BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS user_role_unit (
  user_id INT NOT NULL,
  role_id INT NOT NULL,
  business_unit_id INT NOT NULL,
  PRIMARY KEY (user_id, role_id, business_unit_id),
  FOREIGN KEY (user_id) REFERENCES user_account(id)
    ON DELETE CASCADE ON UPDATE CASCADE,
  FOREIGN KEY (role_id) REFERENCES role(id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (business_unit_id) REFERENCES business_unit(id)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- business_unit
ALTER TABLE business_unit
  MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único da unidade de negócios',
  MODIFY COLUMN name VARCHAR(100) NOT NULL UNIQUE COMMENT 'Nome da unidade de negócios',
  MODIFY COLUMN description TEXT COMMENT 'Descrição opcional da unidade de negócios',
  MODIFY COLUMN created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT 'Data de criação do registro',
  MODIFY COLUMN updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Data da última atualização';

-- transaction_type
ALTER TABLE transaction_type
  MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do tipo de transação',
  MODIFY COLUMN name VARCHAR(45) NOT NULL COMMENT 'Nome do tipo de transação: Entrada ou Saída';

-- payment_method
ALTER TABLE payment_method
  MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do método de pagamento',
  MODIFY COLUMN name VARCHAR(45) NOT NULL COMMENT 'Nome do método de pagamento, ex: dinheiro, pix, transferência';

-- account_type
ALTER TABLE account_type
  MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do tipo de conta',
  MODIFY COLUMN name VARCHAR(100) NOT NULL COMMENT 'Nome do tipo de conta (ex: banco + tipo, como BASA - Corrente)',
  MODIFY COLUMN business_unit_id INT NOT NULL COMMENT 'Identificador da unidade de negócios associada';

-- category
ALTER TABLE category
  MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único da categoria',
  MODIFY COLUMN name VARCHAR(100) NOT NULL COMMENT 'Nome da categoria, ex: impostos, taxas',
  MODIFY COLUMN transaction_type_id INT NOT NULL COMMENT 'Identificador do tipo de transação (Entrada/Saída)',
  MODIFY COLUMN business_unit_id INT NOT NULL COMMENT 'Identificador da unidade de negócios associada';

-- cash_flow
ALTER TABLE cash_flow
  MODIFY COLUMN id INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do fluxo de caixa',
  MODIFY COLUMN amount DECIMAL(16,2) NOT NULL COMMENT 'Valor da transação',
  MODIFY COLUMN transaction_date DATE NOT NULL COMMENT 'Data da transação',
  MODIFY COLUMN description TEXT NOT NULL COMMENT 'Descrição detalhada da transação',
  MODIFY COLUMN payment_method_id INT NOT NULL COMMENT 'Método de pagamento usado',
  MODIFY COLUMN transaction_type_id INT NOT NULL COMMENT 'Tipo de transação (Receita/Despesa)',
  MODIFY COLUMN category_id INT NOT NULL COMMENT 'Categoria da transação',
  MODIFY COLUMN account_type_id INT NOT NULL COMMENT 'Conta relacionada à transação',
  MODIFY COLUMN business_unit_id INT NOT NULL COMMENT 'Unidade de negócios relacionada',
  MODIFY COLUMN created_at DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Data de criação do registro',
  MODIFY COLUMN created_by VARCHAR(45) COMMENT 'Usuário que criou o registro',
  MODIFY COLUMN updated_at DATETIME NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Data da última atualização',
  MODIFY COLUMN updated_by VARCHAR(45) COMMENT 'Usuário que atualizou o registro',
  MODIFY COLUMN is_active BOOLEAN DEFAULT TRUE COMMENT 'Indica se o registro está ativo',
  MODIFY COLUMN is_checked BOOLEAN DEFAULT FALSE COMMENT 'Indica se a transação foi revisada';

-- role
ALTER TABLE role
  MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do perfil/role',
  MODIFY COLUMN name VARCHAR(60) NOT NULL COMMENT 'Nome do perfil (ex: Administrador, Consultor)';

-- screen_access
ALTER TABLE screen_access
  MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do acesso a tela',
  MODIFY COLUMN screen_name VARCHAR(60) NOT NULL COMMENT 'Nome da tela ou funcionalidade',
  MODIFY COLUMN role_id INT NOT NULL COMMENT 'Perfil relacionado a este acesso';

-- user_account
ALTER TABLE user_account
  MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador único do usuário',
  MODIFY COLUMN name VARCHAR(60) NOT NULL COMMENT 'Nome completo do usuário',
  MODIFY COLUMN username VARCHAR(45) NOT NULL UNIQUE COMMENT 'Nome de usuário para login',
  MODIFY COLUMN password_hash VARCHAR(128) NOT NULL COMMENT 'Hash da senha (sha512)',
  MODIFY COLUMN gender VARCHAR(20) COMMENT 'Sexo do usuário',
  MODIFY COLUMN google_auth_enabled BOOLEAN DEFAULT FALSE COMMENT 'Indica se autenticação Google está ativada';

-- user_role_unit
ALTER TABLE user_role_unit
  MODIFY COLUMN user_id INT NOT NULL COMMENT 'Identificador do usuário',
  MODIFY COLUMN role_id INT NOT NULL COMMENT 'Identificador do perfil/role do usuário',
  MODIFY COLUMN business_unit_id INT NOT NULL COMMENT 'Identificador da unidade de negócios associada';

