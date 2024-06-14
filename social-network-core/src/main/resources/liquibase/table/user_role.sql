--liquibase formatted sql

--changeset reybos:create_user_role_table
CREATE TABLE IF NOT EXISTS user_role
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users (id),
    role_id BIGINT NOT NULL REFERENCES roles (id)
);