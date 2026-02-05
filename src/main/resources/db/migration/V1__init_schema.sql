/* ===========================
    RESOURCE SERVER
   =========================== */

CREATE TABLE project
(
    project_id              BIGSERIAL          PRIMARY KEY,
    cohort                  INTEGER         NOT NULL,
    image_url               VARCHAR(255)    NOT NULL,
    description             VARCHAR(255)    NOT NULL,
    instagram_url           VARCHAR(255)    NOT NULL,
    created_at              TIMESTAMP,
    updated_at              TIMESTAMP
);

CREATE TABLE executive_member
(
    executive_member_id     BIGSERIAL       PRIMARY KEY,
    name                    VARCHAR(255)    NOT NULL,
    cohort                  INTEGER         NOT NULL,
    image_url               VARCHAR(255)    NOT NULL,
    part                    VARCHAR(255)    NOT NULL,
    position                VARCHAR(255)    NOT NULL,
    created_at              TIMESTAMP,
    updated_at              TIMESTAMP
);

CREATE TABLE application
(
    application_id          BIGSERIAL       PRIMARY KEY,
    public_id               VARCHAR(40)     NOT NULL UNIQUE,
    name                    VARCHAR(255)    NOT NULL,
    student_number          VARCHAR(255)    NOT NULL UNIQUE,
    phone_number            VARCHAR(255)    NOT NULL UNIQUE,
    password_hash           VARCHAR(255)    NOT NULL,
    major                   VARCHAR(255)    NOT NULL,
    double_major            VARCHAR(255),
    semester                INTEGER         NOT NULL,
    submitted               BOOLEAN         NOT NULL DEFAULT FALSE,
    submitted_at            TIMESTAMP,
    academic_status         VARCHAR(255),
    part                    VARCHAR(255) NOT NULL,
    pass_status             VARCHAR(255) NOT NULL,
    created_at              TIMESTAMP,
    updated_at              TIMESTAMP

);

CREATE INDEX idx_application_public_id ON application(public_id);
CREATE INDEX idx_application_phone ON application(phone_number);
CREATE INDEX idx_application_pass_status ON application(pass_status);

CREATE TABLE question
(
    question_id             BIGSERIAL       PRIMARY KEY,
    question_number         INTEGER         NOT NULL,
    content                 VARCHAR(255)    NOT NULL,
    type                    VARCHAR(255)    NOT NULL,
    created_at              TIMESTAMP,
    updated_at              TIMESTAMP
);

CREATE TABLE answer
(
    answer_id               BIGSERIAL       PRIMARY KEY,
    content                 VARCHAR(500),
    question_id             BIGINT          NOT NULL,
    application_id          BIGINT          NOT NULL,
    created_at              TIMESTAMP,
    updated_at              TIMESTAMP,

    CONSTRAINT fk_answer_question
        FOREIGN KEY (question_id)
            REFERENCES question(question_id),

    CONSTRAINT fk_answer_application
        FOREIGN KEY (application_id)
            REFERENCES application(application_id),

    CONSTRAINT uq_answer_app_question
        UNIQUE (application_id, question_id)
);

CREATE INDEX idx_answer_application ON answer(application_id);
CREATE INDEX idx_answer_question ON answer(question_id);

CREATE TABLE interview_time
(
    interview_time_id       BIGSERIAL       PRIMARY KEY,
    date                    DATE            NOT NULL,
    start_time              TIME            NOT NULL,
    end_time                TIME            NOT NULL,
    created_at              TIMESTAMP,
    updated_at              TIMESTAMP
);

CREATE TABLE interview_available
(
    interview_available_id  BIGSERIAL       PRIMARY KEY,
    interview_time_id       BIGINT          NOT NULL,
    application_id          BIGINT          NOT NULL,
    created_at              TIMESTAMP,
    updated_at              TIMESTAMP,

    CONSTRAINT fk_interview_available_time
        FOREIGN KEY (interview_time_id)
            REFERENCES interview_time(interview_time_id),

    CONSTRAINT fk_interview_available_application
        FOREIGN KEY (application_id)
            REFERENCES application(application_id),

    CONSTRAINT uq_interview_available
        UNIQUE (interview_time_id, application_id)
);

CREATE INDEX idx_interview_available_app ON interview_available(application_id);
CREATE INDEX idx_interview_available_time ON interview_available(interview_time_id);

CREATE TABLE interview_schedule
(
    interview_schedule_id   BIGSERIAL       PRIMARY KEY,
    place                   VARCHAR(255),
    application_id          BIGINT          NOT NULL,
    interview_time_id       BIGINT          NOT NULL,
    created_at              TIMESTAMP,
    updated_at              TIMESTAMP,

    CONSTRAINT fk_interview_schedule_application
        FOREIGN KEY (application_id)
            REFERENCES application(application_id),

    CONSTRAINT fk_interview_schedule_time
        FOREIGN KEY (interview_time_id)
            REFERENCES interview_time(interview_time_id),

    CONSTRAINT uq_interview_schedule_application
        UNIQUE (application_id)
);

CREATE INDEX idx_interview_schedule_time ON interview_schedule(interview_time_id);
