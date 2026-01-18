/* ===========================
    RESOURCE SERVER
   =========================== */

CREATE TABLE projects
(
    project_id      BIGSERIAL       PRIMARY KEY,
    cohort          INTEGER         NOT NULL,
    category        VARCHAR(255) NOT NULL,

    image_url VARCHAR(255) NOT NULL,

    description TEXT NOT NULL,

    instagram_url VARCHAR(255) NOT NULL,

    created_at TIMESTAMP,

    updated_at TIMESTAMP
);