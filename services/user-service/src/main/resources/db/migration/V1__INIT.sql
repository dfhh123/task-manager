CREATE SCHEMA IF NOT EXISTS "user-service";

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "user-service".users
(
    id          UUID                        NOT NULL,
    keycloak_id VARCHAR(255)                NOT NULL,
    email       VARCHAR(255)                NOT NULL,
    language    VARCHAR(255)                NOT NULL,
    timezone    VARCHAR(255)                NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,

    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uc_users_email UNIQUE (email),
    CONSTRAINT uc_users_keycloak UNIQUE (keycloak_id)
);

CREATE OR REPLACE FUNCTION "user-service".set_updated_at()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_set_updated_at ON "user-service".users;

CREATE TRIGGER trg_set_updated_at
    BEFORE UPDATE ON "user-service".users
    FOR EACH ROW
EXECUTE FUNCTION "user-service".set_updated_at();