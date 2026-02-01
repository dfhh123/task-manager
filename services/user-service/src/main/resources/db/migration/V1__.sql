CREATE TABLE "user-service".users
(
    id          UUID                        NOT NULL,
    keycloak_id VARCHAR(255)                NOT NULL,
    email       VARCHAR(255)                NOT NULL,
    language    VARCHAR(255)                NOT NULL,
    timezone    VARCHAR(255)                NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE "user-service".users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE "user-service".users
    ADD CONSTRAINT uc_users_keycloak UNIQUE (keycloak_id);