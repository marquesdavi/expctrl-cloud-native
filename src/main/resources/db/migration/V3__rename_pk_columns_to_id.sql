-- V3__rename_pk_columns_to_id.sql

-- Renomeia colunas PK para "id"
ALTER TABLE bank           RENAME COLUMN bank_id         TO id;
ALTER TABLE app_user       RENAME COLUMN user_id         TO id;
ALTER TABLE account        RENAME COLUMN account_id      TO id;
ALTER TABLE category       RENAME COLUMN category_id     TO id;
ALTER TABLE payee          RENAME COLUMN payee_id        TO id;
ALTER TABLE import_batch   RENAME COLUMN import_batch_id TO id;
ALTER TABLE transaction    RENAME COLUMN transaction_id  TO id;
ALTER TABLE tag            RENAME COLUMN tag_id          TO id;
ALTER TABLE budget         RENAME COLUMN budget_id       TO id;

-- (transaction_tag já usa "id" como nome de PK, não precisa renomear)
