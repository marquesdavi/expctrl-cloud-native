-- V2__add_password_hash_to_app_user.sql
-- Adiciona coluna password_hash em app_user para armazenar hashes de senha

ALTER TABLE app_user
    ADD COLUMN password_hash VARCHAR(255) NOT NULL DEFAULT '';
