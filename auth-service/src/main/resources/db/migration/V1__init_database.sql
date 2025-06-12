-- V1__init_database.sql

-- 1) Crear la tabla users
CREATE TABLE IF NOT EXISTS users (
    id       INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT    NOT NULL UNIQUE,
    password TEXT    NOT NULL,
    enabled  BOOLEAN NOT NULL
);

-- 2) Insertar usuario admin (reemplaza <your_bcrypt_hash> por el hash real)
INSERT INTO users (username, password, enabled)
VALUES ('admin', '$2a$10$F5rzmMmNJcgwqTXmcro1eOeATecEUDsPM8WjKtF8Qx46RFDjlmCSW', 1);
