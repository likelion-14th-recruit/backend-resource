CREATE TABLE message_log (
                             id BIGSERIAL PRIMARY KEY,

                             message_type VARCHAR(50) NOT NULL,
                             message_status VARCHAR(20) NOT NULL,

                             sender_phone_number VARCHAR(30) NOT NULL,
                             receiver_phone_number VARCHAR(30) NOT NULL,

                             created_at TIMESTAMP NOT NULL,
                             updated_at TIMESTAMP NOT NULL
);