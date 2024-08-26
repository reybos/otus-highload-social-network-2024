--liquibase formatted sql

--changeset reybos:create_message_table
CREATE TABLE IF NOT EXISTS message
(
    id             BIGSERIAL,
    dialog_id      BIGINT      NOT NULL REFERENCES dialog (id),
    participant_id BIGINT      NOT NULL,
    message        TEXT        NOT NULL,
    updated_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_at     TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    PRIMARY KEY (dialog_id, id),
    FOREIGN KEY (dialog_id, participant_id) REFERENCES participant (dialog_id, id)
);

SELECT create_distributed_table('message', 'dialog_id');

--changeset reybos:message_comments runOnChange:true
COMMENT ON TABLE message IS 'List of messages from a single dialog';
COMMENT ON COLUMN message.dialog_id IS 'The ID of the dialog';
COMMENT ON COLUMN message.participant_id IS 'The ID of the participant';
COMMENT ON COLUMN message.message IS 'The text of the message';