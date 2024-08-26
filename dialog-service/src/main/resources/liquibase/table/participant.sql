--liquibase formatted sql

--changeset reybos:create_participant_table
CREATE TABLE IF NOT EXISTS participant
(
    id         BIGSERIAL,
    dialog_id  BIGINT      NOT NULL REFERENCES dialog (id),
    user_id    TEXT        NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (dialog_id, id)
);

SELECT create_distributed_table('participant', 'dialog_id');

--changeset reybos:create_indexes
CREATE INDEX IF NOT EXISTS participant_dialog_id_idx ON participant (dialog_id);

--changeset reybos:participant_comments runOnChange:true
COMMENT ON TABLE participant IS 'List of users friends';
COMMENT ON COLUMN participant.dialog_id IS 'The ID of the dialog';
COMMENT ON COLUMN participant.user_id IS 'Type of dialog';