DELETE FROM executive_member
WHERE name = '한서정';

INSERT INTO executive_member(name, cohort, image_url, part, position, created_at, updated_at)
VALUES
    ('천사은', 14, 'https://likelion14th-recruit.s3.ap-northeast-2.amazonaws.com/executive-member/6.png','PRODUCT_DESIGN', 'MEMBER',NOW(), NOW());
