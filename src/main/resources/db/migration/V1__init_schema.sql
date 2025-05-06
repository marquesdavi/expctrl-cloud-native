-- Tabela Bank
CREATE TABLE bank (
                      bank_id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      code VARCHAR(10) UNIQUE NOT NULL
);
-- Tabela User (opcional se existir)
CREATE TABLE app_user (
                          user_id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          email VARCHAR(150) UNIQUE NOT NULL,
                          created_at TIMESTAMP DEFAULT now()
);
-- Tabela Account
CREATE TABLE account (
                         account_id SERIAL PRIMARY KEY,
                         bank_id INTEGER NOT NULL REFERENCES bank(bank_id),
                         user_id INTEGER REFERENCES app_user(user_id),
                         account_number VARCHAR(20) NOT NULL,
                         branch VARCHAR(20),
                         currency CHAR(3) DEFAULT 'BRL'
);
-- Tabela Category
CREATE TABLE category (
                          category_id SERIAL PRIMARY KEY,
                          user_id INTEGER REFERENCES app_user(user_id),
                          name VARCHAR(100) NOT NULL,
                          type VARCHAR(20) NOT NULL,
                          parent_category_id INTEGER REFERENCES category(category_id)
);
-- Tabela Payee
CREATE TABLE payee (
                       payee_id SERIAL PRIMARY KEY,
                       user_id INTEGER REFERENCES app_user(user_id),
                       name VARCHAR(150) NOT NULL,
                       details VARCHAR(255)
);
-- Tabela ImportBatch
CREATE TABLE import_batch (
                              import_batch_id SERIAL PRIMARY KEY,
                              user_id INTEGER REFERENCES app_user(user_id),
                              source VARCHAR(50),
                              file_name VARCHAR(255),
                              imported_at TIMESTAMP DEFAULT now()
);
-- Tabela Transaction
CREATE TABLE transaction (
                             transaction_id SERIAL PRIMARY KEY,
                             account_id INTEGER NOT NULL REFERENCES account(account_id),
                             category_id INTEGER REFERENCES category(category_id),
                             payee_id INTEGER REFERENCES payee(payee_id),
                             import_batch_id INTEGER REFERENCES import_batch(import_batch_id),
                             date DATE NOT NULL,
                             type VARCHAR(50),
                             amount DECIMAL(12,2) NOT NULL,
                             description VARCHAR(255),
                             details VARCHAR(255),
                             document_number VARCHAR(50),
                             running_balance DECIMAL(12,2)
);
-- Tabela Tag
CREATE TABLE tag (
                     tag_id SERIAL PRIMARY KEY,
                     user_id INTEGER REFERENCES app_user(user_id),
                     name VARCHAR(50) NOT NULL
);
-- Tabela TransactionTag
CREATE TABLE transaction_tag (
                                 id SERIAL PRIMARY KEY,
                                 transaction_id INTEGER NOT NULL REFERENCES transaction(transaction_id),
                                 tag_id INTEGER NOT NULL REFERENCES tag(tag_id)
);
-- Tabela Budget
CREATE TABLE budget (
                        budget_id SERIAL PRIMARY KEY,
                        user_id INTEGER NOT NULL REFERENCES app_user(user_id),
                        category_id INTEGER NOT NULL REFERENCES category(category_id),
                        period_start DATE NOT NULL,
                        period_end DATE NOT NULL,
                        amount DECIMAL(12,2) NOT NULL
);
