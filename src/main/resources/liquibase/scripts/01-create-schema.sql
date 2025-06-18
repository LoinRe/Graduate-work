-- liquibase formatted sql

-- changeset LoinRe:1

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(64) NOT NULL UNIQUE,
    username VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(16) NOT NULL,
    last_name VARCHAR(16) NOT NULL,
    phone VARCHAR(32) NOT NULL,
    role VARCHAR(16) NOT NULL,
    image VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS ads (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    price INTEGER,
    image VARCHAR(255),
    author_id INTEGER NOT NULL REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS comments (
    id SERIAL PRIMARY KEY,
    text VARCHAR(64),
    created_at BIGINT,
    author_id INTEGER NOT NULL REFERENCES users(id),
    ad_id INTEGER NOT NULL REFERENCES ads(id)
);
