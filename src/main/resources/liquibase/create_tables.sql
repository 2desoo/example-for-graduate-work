-- formated sql

-- changeset dubrovsky:1
CREATE TABLE "users"
(
    ID         BIGSERIAL    NOT NULL PRIMARY KEY,
    EMAIL      VARCHAR(255) NOT NULL UNIQUE,
    PASSWORD   VARCHAR(255) NOT NULL,
    FIRST_NAME VARCHAR(255),
    LAST_NAME  VARCHAR(255),
    PHONE      VARCHAR(255),
    ROLE       VARCHAR(255) NOT NULL,
    IMAGE_ID   BIGINT
);

-- changeset dubrovsky:2
CREATE TABLE "ads"
(
    PK          BIGSERIAL        NOT NULL PRIMARY KEY,
    IMAGE_ID    BIGSERIAL           NOT NULL,
    PRICE       INTEGER NOT NULL,
    TITLE       VARCHAR(255)     NOT NULL,
    DESCRIPTION VARCHAR(255)     NOT NULL,
    USER_ID     BIGINT           NOT NULL
);

-- changeset dubrovsky:3
CREATE TABLE "comments"
(
    PK         BIGSERIAL    NOT NULL PRIMARY KEY,
    CREATED_AT TIMESTAMP    NOT NULL,
    TEXT       VARCHAR(255) NOT NULL,
    USER_ID    BIGINT       NOT NULL,
    AD_PK BIGINT NOT NULL
);

-- changeset dubrovsky:4
CREATE TABLE "images"
(
    ID         BIGSERIAL    NOT NULL PRIMARY KEY,
    FILE_SIZE  BIGINT       NOT NULL,
    FILE_PATH  VARCHAR(255) NOT NULL,
    MEDIA_TYPE VARCHAR(255) NOT NULL,
    DATA       BYTEA        NOT NULL
);

