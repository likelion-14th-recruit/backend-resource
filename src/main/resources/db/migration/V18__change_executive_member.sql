DELETE FROM executive_member
WHERE name IN ('송명은', '천사은');

UPDATE executive_member
SET part = 'FRONTEND'
WHERE name = '오서현';

INSERT INTO executive_member(name, cohort, image_url, part, position, created_at, updated_at)
VALUES
('진현민', 14, 'https://likelion14th-recruit.s3.ap-northeast-2.amazonaws.com/executive-member/13.png','PRODUCT_DESIGN', 'MEMBER',NOW(), NOW()),
('문예담', 14, 'https://likelion14th-recruit.s3.ap-northeast-2.amazonaws.com/executive-member/14.png','PRODUCT_DESIGN', 'MEMBER',NOW(), NOW()),
('이현서', 14, 'https://likelion14th-recruit.s3.ap-northeast-2.amazonaws.com/executive-member/15.png','PRODUCT_DESIGN', 'PART_LEADER',NOW(), NOW());
