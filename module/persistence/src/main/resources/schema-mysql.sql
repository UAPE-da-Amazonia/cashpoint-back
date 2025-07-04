-- ==========================================
-- SCHEMA: wai (Workflow Accounting Interface)
-- ==========================================

CREATE SCHEMA IF NOT EXISTS wai;
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
-- TABLE: transaction_type
-- ==========================================
CREATE TABLE IF NOT EXISTS transaction_type (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==========================================
-- TABLE: payment_method
-- ==========================================
CREATE TABLE IF NOT EXISTS payment_method (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  description TEXT,
  business_unit_id INT NOT NULL,
  is_active BOOLEAN DEFAULT TRUE,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (business_unit_id) REFERENCES business_unit(id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==========================================
-- TABLE: account_type
-- ==========================================
CREATE TABLE IF NOT EXISTS account_type (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  business_unit_id INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (business_unit_id) REFERENCES business_unit(id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==========================================
-- TABLE: category
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
-- TABLE: profiles (Clientes/Fornecedores)
-- ==========================================
CREATE TABLE IF NOT EXISTS profiles (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(100),
  phone VARCHAR(20),
  document VARCHAR(20), -- CPF/CNPJ
  address TEXT,
  business_unit_id INT NOT NULL,
  profile_type ENUM('CLIENT', 'SUPPLIER', 'BOTH') DEFAULT 'CLIENT',
  is_active BOOLEAN DEFAULT TRUE,
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (business_unit_id) REFERENCES business_unit(id)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  business_unit_id INT NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ==========================================
-- DADOS INICIAIS
-- ==========================================

-- Business Units
INSERT INTO business_unit (name, description) VALUES 
('Empresa A', 'Empresa principal'),
('Empresa B', 'Empresa secundária');

-- Transaction Types
INSERT INTO transaction_type (name) VALUES 
('Income'),
('Expense');

-- Payment Methods
INSERT INTO payment_method (name, description, business_unit_id) VALUES 
('PIX', 'Pagamento instantâneo via PIX', 1),
('Dinheiro', 'Pagamento em dinheiro', 1),
('Cartão de Crédito', 'Pagamento com cartão de crédito', 1),
('Cartão de Débito', 'Pagamento com cartão de débito', 1),
('Transferência Bancária', 'Transferência entre contas bancárias', 1),
('PIX', 'Pagamento instantâneo via PIX', 2),
('Dinheiro', 'Pagamento em dinheiro', 2),
('Cartão de Crédito', 'Pagamento com cartão de crédito', 2),
('Cartão de Débito', 'Pagamento com cartão de débito', 2),
('Transferência Bancária', 'Transferência entre contas bancárias', 2);

-- Account Types
INSERT INTO account_type (name, business_unit_id) VALUES 
('BASA - Corrente', 1),
('BASA - Poupança', 1),
('Itaú - Corrente', 2),
('Itaú - Poupança', 2);

-- Categories
INSERT INTO category (name, transaction_type_id, business_unit_id) VALUES 
('Vendas', 1, 1),
('Serviços', 1, 1),
('Impostos', 2, 1),
('Fornecedores', 2, 1),
('Vendas', 1, 2),
('Serviços', 1, 2),
('Impostos', 2, 2),
('Fornecedores', 2, 2);

-- Profiles (Clientes/Fornecedores)
INSERT INTO profiles (name, email, phone, document, address, business_unit_id, profile_type) VALUES 
('João Silva', 'joao@email.com', '(11) 99999-9999', '123.456.789-00', 'Rua A, 123 - São Paulo', 1, 'CLIENT'),
('Maria Santos', 'maria@email.com', '(11) 88888-8888', '987.654.321-00', 'Rua B, 456 - São Paulo', 1, 'CLIENT'),
('Fornecedor ABC', 'contato@abc.com', '(11) 77777-7777', '12.345.678/0001-90', 'Av. C, 789 - São Paulo', 1, 'SUPPLIER'),
('Cliente XYZ', 'contato@xyz.com', '(11) 66666-6666', '98.765.432/0001-10', 'Rua D, 321 - Rio de Janeiro', 2, 'BOTH'); 