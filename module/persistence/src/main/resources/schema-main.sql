-- ==========================================
-- SCHEMA: wai (Workflow Accounting Interface)
-- ==========================================

CREATE SCHEMA IF NOT EXISTS wai;
USE wai;

-- ==========================================
-- TABLE: business_unit
-- ==========================================
CREATE TABLE IF NOT EXISTS business_unit (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL UNIQUE,
  description TEXT,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================
-- TABLE: transaction_type
-- ==========================================
CREATE TABLE IF NOT EXISTS transaction_type (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================
-- TABLE: payment_method
-- ==========================================
CREATE TABLE IF NOT EXISTS payment_method (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  description TEXT,
  business_unit_id BIGINT NOT NULL,
  is_active BOOLEAN DEFAULT TRUE,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (business_unit_id) REFERENCES business_unit(id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================
-- TABLE: account_type
-- ==========================================
CREATE TABLE IF NOT EXISTS account_type (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  business_unit_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (business_unit_id) REFERENCES business_unit(id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================
-- TABLE: category
-- ==========================================
CREATE TABLE IF NOT EXISTS category (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  transaction_type_id INT NOT NULL,
  business_unit_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (transaction_type_id) REFERENCES transaction_type(id)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  FOREIGN KEY (business_unit_id) REFERENCES business_unit(id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================
-- TABLE: profiles (Clientes/Fornecedores)
-- ==========================================
CREATE TABLE IF NOT EXISTS profiles (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100),
  phone VARCHAR(20),
  document VARCHAR(20), -- CPF/CNPJ
  address TEXT,
  business_unit_id BIGINT NOT NULL,
  profile_type ENUM('CLIENT', 'SUPPLIER', 'BOTH') DEFAULT 'CLIENT',
  is_active BOOLEAN DEFAULT TRUE,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (business_unit_id) REFERENCES business_unit(id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================
-- TABLE: cash_flow (Atualizada com profile_id)
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
  business_unit_id BIGINT NOT NULL,
  profile_id INT, -- Relacionamento com profile (opcional)
  created_at DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  created_by VARCHAR(45),
  updated_at DATETIME NULL ON UPDATE CURRENT_TIMESTAMP,
  updated_by VARCHAR(45),
  is_active BOOLEAN DEFAULT TRUE,
  is_checked BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (id),
  FOREIGN KEY (payment_method_id) REFERENCES payment_method(id),
  FOREIGN KEY (transaction_type_id) REFERENCES transaction_type(id),
  FOREIGN KEY (category_id) REFERENCES category(id),
  FOREIGN KEY (account_type_id) REFERENCES account_type(id),
  FOREIGN KEY (business_unit_id) REFERENCES business_unit(id),
  FOREIGN KEY (profile_id) REFERENCES profiles(id)
    ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================
-- TABLE: users
-- ==========================================
CREATE TABLE IF NOT EXISTS users (
  id CHAR(36) NOT NULL,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(30) NOT NULL UNIQUE,
  username VARCHAR(30) NOT NULL UNIQUE,
  image_url VARCHAR(200),
  password VARCHAR(200) NOT NULL,
  bio VARCHAR(500),
  business_unit_id BIGINT NOT NULL,
  role VARCHAR(10) NOT NULL,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (business_unit_id) REFERENCES business_unit(id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ==========================================
-- DADOS INICIAIS
-- ==========================================

-- Business Units
INSERT INTO business_unit (name, description) VALUES 
('Manaus', 'Unidade Sede'),
('Belém', 'Unidade Belém'),
('Brasília', 'Unidade Brasília');

-- Transaction Types
INSERT INTO transaction_type (name) VALUES 
('Saída'),
('Entrada');
