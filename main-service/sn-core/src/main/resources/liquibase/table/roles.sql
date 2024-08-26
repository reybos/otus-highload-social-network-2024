--liquibase formatted sql

--changeset reybos:create_roles_table
CREATE TABLE IF NOT EXISTS roles
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

--changeset reybos:roles_comments runOnChange:true
COMMENT ON TABLE roles IS 'Available roles in the system';