--liquibase formatted sql

--changeset reybos:create_role_authority_table
CREATE TABLE IF NOT EXISTS role_authority
(
    id           BIGSERIAL PRIMARY KEY,
    role_id      BIGINT NOT NULL REFERENCES roles (id),
    authority_id BIGINT NOT NULL REFERENCES authorities (id)
);