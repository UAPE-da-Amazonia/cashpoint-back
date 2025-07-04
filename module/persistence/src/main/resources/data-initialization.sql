-- ==========================================
-- DADOS INICIAIS PARA O SISTEMA
-- ==========================================

-- Business Units
INSERT INTO business_unit (name, description) VALUES 
('Empresa A', 'Empresa principal'),
('Empresa B', 'Empresa secundária')
ON DUPLICATE KEY UPDATE name = name;

-- Transaction Types
INSERT INTO transaction_type (name) VALUES 
('Income'),
('Expense')
ON DUPLICATE KEY UPDATE name = name;

-- Payment Methods para Empresa A
INSERT INTO payment_method (name, description, business_unit_id, created_at, is_active) VALUES 
('PIX', 'Pagamento instantâneo via PIX', 1, NOW(), 1),
('Dinheiro', 'Pagamento em dinheiro', 1, NOW(), 1),
('Cartão de Crédito', 'Pagamento com cartão de crédito', 1, NOW(), 1),
('Cartão de Débito', 'Pagamento com cartão de débito', 1, NOW(), 1),
('Transferência Bancária', 'Transferência entre contas bancárias', 1, NOW(), 1)
ON DUPLICATE KEY UPDATE name = name;

-- Payment Methods para Empresa B
INSERT INTO payment_method (name, description, business_unit_id, created_at, is_active) VALUES 
('PIX', 'Pagamento instantâneo via PIX', 2, NOW(), 1),
('Dinheiro', 'Pagamento em dinheiro', 2, NOW(), 1),
('Cartão de Crédito', 'Pagamento com cartão de crédito', 2, NOW(), 1),
('Cartão de Débito', 'Pagamento com cartão de débito', 2, NOW(), 1),
('Transferência Bancária', 'Transferência entre contas bancárias', 2, NOW(), 1)
ON DUPLICATE KEY UPDATE name = name;

-- Account Types para Empresa A
INSERT INTO account_type (name, business_unit_id) VALUES 
('BASA - Corrente', 1),
('BASA - Poupança', 1),
('Caixa - Corrente', 1),
('Caixa - Poupança', 1)
ON DUPLICATE KEY UPDATE name = name;

-- Account Types para Empresa B
INSERT INTO account_type (name, business_unit_id) VALUES 
('Itaú - Corrente', 2),
('Itaú - Poupança', 2),
('Santander - Corrente', 2),
('Santander - Poupança', 2)
ON DUPLICATE KEY UPDATE name = name;

-- Categories para Empresa A
INSERT INTO category (name, transaction_type_id, business_unit_id) VALUES 
('Vendas', 1, 1),
('Serviços', 1, 1),
('Juros e Rendimentos', 1, 1),
('Impostos', 2, 1),
('Fornecedores', 2, 1),
('Salários', 2, 1),
('Aluguel', 2, 1),
('Energia Elétrica', 2, 1),
('Água', 2, 1),
('Internet/Telefone', 2, 1)
ON DUPLICATE KEY UPDATE name = name;

-- Categories para Empresa B
INSERT INTO category (name, transaction_type_id, business_unit_id) VALUES 
('Vendas', 1, 2),
('Serviços', 1, 2),
('Juros e Rendimentos', 1, 2),
('Impostos', 2, 2),
('Fornecedores', 2, 2),
('Salários', 2, 2),
('Aluguel', 2, 2),
('Energia Elétrica', 2, 2),
('Água', 2, 2),
('Internet/Telefone', 2, 2)
ON DUPLICATE KEY UPDATE name = name;

-- Profiles (Clientes/Fornecedores) para Empresa A
INSERT INTO profiles (name, email, phone, document, address, business_unit_id, profile_type, created_at, is_active) VALUES 
('João Silva', 'joao@email.com', '(11) 99999-9999', '123.456.789-00', 'Rua A, 123 - São Paulo', 1, 'CLIENT', NOW(), 1),
('Maria Santos', 'maria@email.com', '(11) 88888-8888', '987.654.321-00', 'Rua B, 456 - São Paulo', 1, 'CLIENT', NOW(), 1),
('Fornecedor ABC', 'contato@abc.com', '(11) 77777-7777', '12.345.678/0001-90', 'Av. C, 789 - São Paulo', 1, 'SUPPLIER', NOW(), 1),
('Cliente XYZ', 'contato@xyz.com', '(11) 66666-6666', '98.765.432/0001-10', 'Rua D, 321 - Rio de Janeiro', 1, 'BOTH', NOW(), 1)
ON DUPLICATE KEY UPDATE name = name;

-- Profiles (Clientes/Fornecedores) para Empresa B
INSERT INTO profiles (name, email, phone, document, address, business_unit_id, profile_type, created_at, is_active) VALUES 
('Pedro Costa', 'pedro@email.com', '(21) 99999-9999', '111.222.333-44', 'Rua E, 555 - Rio de Janeiro', 2, 'CLIENT', NOW(), 1),
('Ana Oliveira', 'ana@email.com', '(21) 88888-8888', '555.666.777-88', 'Rua F, 666 - Rio de Janeiro', 2, 'CLIENT', NOW(), 1),
('Fornecedor DEF', 'contato@def.com', '(21) 77777-7777', '11.222.333/0001-44', 'Av. G, 777 - Rio de Janeiro', 2, 'SUPPLIER', NOW(), 1),
('Cliente UVW', 'contato@uvw.com', '(21) 66666-6666', '99.888.777/0001-66', 'Rua H, 888 - São Paulo', 2, 'BOTH', NOW(), 1)
ON DUPLICATE KEY UPDATE name = name;

-- Cash Flows de exemplo para Empresa A
INSERT INTO cash_flow (amount, transaction_date, description, payment_method_id, transaction_type_id, category_id, account_type_id, business_unit_id, profile_id, created_by, is_active, is_checked) VALUES 
(1500.00, '2024-01-15', 'Venda de produtos para João Silva', 1, 1, 1, 1, 1, 1, 'system', true, false),
(800.00, '2024-01-16', 'Prestação de serviços para Maria Santos', 3, 1, 2, 1, 1, 2, 'system', true, false),
(50.00, '2024-01-17', 'Juros da poupança', 5, 1, 3, 2, 1, NULL, 'system', true, false),
(200.00, '2024-01-18', 'Compra de matéria-prima do Fornecedor ABC', 1, 2, 5, 1, 1, 3, 'system', true, false),
(150.00, '2024-01-19', 'Pagamento de energia elétrica', 2, 2, 8, 1, 1, NULL, 'system', true, false)
ON DUPLICATE KEY UPDATE amount = amount;

-- Cash Flows de exemplo para Empresa B
INSERT INTO cash_flow (amount, transaction_date, description, payment_method_id, transaction_type_id, category_id, account_type_id, business_unit_id, profile_id, created_by, is_active, is_checked) VALUES 
(2000.00, '2024-01-15', 'Venda de produtos para Pedro Costa', 6, 1, 11, 5, 2, 5, 'system', true, false),
(1200.00, '2024-01-16', 'Prestação de serviços para Ana Oliveira', 8, 1, 12, 5, 2, 6, 'system', true, false),
(75.00, '2024-01-17', 'Juros da poupança', 10, 1, 13, 6, 2, NULL, 'system', true, false),
(300.00, '2024-01-18', 'Compra de matéria-prima do Fornecedor DEF', 6, 2, 15, 5, 2, 7, 'system', true, false),
(180.00, '2024-01-19', 'Pagamento de energia elétrica', 7, 2, 18, 5, 2, NULL, 'system', true, false)
ON DUPLICATE KEY UPDATE amount = amount; 