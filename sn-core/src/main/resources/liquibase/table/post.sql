--liquibase formatted sql

--changeset reybos:create_post_table
CREATE TABLE IF NOT EXISTS post
(
    id         BIGSERIAL PRIMARY KEY,
    post_id    TEXT        NOT NULL,
    user_id    BIGINT      NOT NULL REFERENCES users (id),
    deleted    BOOLEAN     NOT NULL,
    content    TEXT        NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    created_at TIMESTAMPTZ NOT NULL
);

--changeset reybos:post_comments runOnChange:true
COMMENT ON TABLE post IS 'List of users friends';
COMMENT ON COLUMN post.post_id IS 'The external ID of the post';
COMMENT ON COLUMN post.user_id IS 'The ID of the current user';
COMMENT ON COLUMN post.deleted IS 'Has the post been deleted';
COMMENT ON COLUMN post.content IS 'The text of the post';
COMMENT ON COLUMN post.updated_at IS 'Date of the post update';