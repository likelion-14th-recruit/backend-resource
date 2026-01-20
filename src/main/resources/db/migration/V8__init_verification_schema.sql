CREATE TABLE verification
(
       verification_id          BIGSERIAL       PRIMARY KEY,
       phone_number             VARCHAR(20)     NOT NULL UNIQUE,
       code                     VARCHAR(8)      NOT NULL,
       verified                 BOOLEAN         NOT NULL DEFAULT FALSE,
       created_at               TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
       updated_at               TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_verification_phone ON verification(phone_number);