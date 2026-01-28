DO $$
DECLARE
i INT;
    v_pass_status VARCHAR(255);
BEGIN
FOR i IN 1..200 LOOP

        IF i <= 50 THEN
            v_pass_status := 'DOCUMENT_PASSED';
        ELSIF i <= 100 THEN
            v_pass_status := 'DOCUMENT_FAILED';
        ELSIF i <= 150 THEN
            v_pass_status := 'INTERVIEW_PASSED';
ELSE
            v_pass_status := 'INTERVIEW_FAILED';
END IF;

INSERT INTO application (public_id, name, student_number, phone_number, password_hash, major, double_major,
    semester, submitted, submitted_at, academic_status, part, pass_status, created_at,updated_at)
VALUES (
             'app-' || i,
             '지원자' || i,
             '2023' || LPAD(i::text, 4, '0'),
             '0109000' || LPAD(i::text, 4, '0'),
             'pw',
             'CS',
             NULL,
             6,
             TRUE,
             now(),
             'ENROLLED',
             CASE
                 WHEN i % 3 = 0 THEN 'BACKEND'
                 WHEN i % 3 = 1 THEN 'FRONTEND'
                 ELSE 'DESIGN'
                 END,
             v_pass_status,
             now(),
             now()
         );

END LOOP;
END $$;
