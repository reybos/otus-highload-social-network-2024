--liquibase formatted sql

--changeset reybos:create_dialog_table
CREATE TABLE IF NOT EXISTS dialog
(
    id         BIGSERIAL PRIMARY KEY,
    dialog_id  TEXT        NOT NULL,
    type       TEXT        NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

SELECT create_distributed_table('dialog', 'id');

--changeset reybos:create_indexes
CREATE INDEX IF NOT EXISTS dialog_dialog_id_idx ON dialog (dialog_id);

--changeset reybos:dialog_comments runOnChange:true
COMMENT ON TABLE dialog IS 'List of dialogs';
COMMENT ON COLUMN dialog.dialog_id IS 'The external ID of the dialog';
COMMENT ON COLUMN dialog.type IS 'Type of dialog';