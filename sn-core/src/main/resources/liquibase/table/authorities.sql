--liquibase formatted sql

--changeset reybos:create_authorities_table
CREATE TABLE IF NOT EXISTS authorities
(
    id   BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

--changeset reybos:authorities_comments runOnChange:true
COMMENT ON TABLE authorities IS 'Available authorities in the system';