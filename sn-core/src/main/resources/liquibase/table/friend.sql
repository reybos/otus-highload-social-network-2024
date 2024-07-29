--liquibase formatted sql

--changeset reybos:create_friend_table
CREATE TABLE IF NOT EXISTS friend
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT      NOT NULL REFERENCES users (id),
    friend_id  BIGINT      NOT NULL REFERENCES users (id),
    deleted    BOOLEAN     NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX user_id_friend_id_not_deleted_idx
    ON friend (user_id, friend_id, deleted) WHERE NOT deleted;

--changeset reybos:friend_comments runOnChange:true
COMMENT ON TABLE friend IS 'List of users friends';
COMMENT ON COLUMN friend.user_id IS 'The ID of the current user';
COMMENT ON COLUMN friend.friend_id IS 'The ID of the user who was added as a friend';
COMMENT ON COLUMN friend.deleted IS 'Has the user been removed from friends';