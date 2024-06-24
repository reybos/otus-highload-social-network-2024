--liquibase formatted sql

--changeset reybos:create_users_table
CREATE TABLE IF NOT EXISTS users
(
    id                 BIGSERIAL PRIMARY KEY,
    user_id            TEXT        NOT NULL,
    first_name         TEXT        NOT NULL,
    second_name        TEXT,
    birthdate          DATE,
    biography          TEXT,
    city               TEXT,
    encrypted_password TEXT        NOT NULL,
    created_at         TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

--changeset reybos:users_set_not_null_second_name
UPDATE users SET second_name = '' WHERE second_name IS NULL;
ALTER TABLE users
    ALTER COLUMN second_name SET NOT NULL;

--changeset reybos:users_idxs_for_search
CREATE INDEX IF NOT EXISTS idx_first_name_second_name
    ON users (first_name, second_name);
CREATE INDEX IF NOT EXISTS idx_user_id
    ON users (user_id);

--changeset reybos:users_comments runOnChange:true
COMMENT ON TABLE users IS 'List of all users';
COMMENT ON COLUMN users.user_id IS 'User ID for external use';
COMMENT ON COLUMN users.biography IS 'Hobbies, interests, etc.';