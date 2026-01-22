DO $$
DECLARE
app RECORD;
    k INT;
BEGIN
FOR app IN
SELECT application_id
FROM application
         LOOP

    IF app.application_id <= 40 THEN
        k := 36;
ELSIF app.application_id <= 100 THEN
        k := 18;
    ELSIF app.application_id <= 160 THEN
        k := 9;
ELSE
        k := 4;
END IF;

INSERT INTO interview_available (
    application_id,
    interview_time_id,
    created_at,
    updated_at
)
SELECT
    app.application_id,
    it.interview_time_id,
    now(),
    now()
FROM interview_time it
ORDER BY random()
    LIMIT k;

END LOOP;
END $$;
