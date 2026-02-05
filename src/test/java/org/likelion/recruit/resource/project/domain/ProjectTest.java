package org.likelion.recruit.resource.project.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.likelion.recruit.resource.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectTest {

    @Test
    void createProject() {
        Integer cohort = 14;
        String name = "프로젝트 이름";
        String imageUrl = "http://likelion14th/image-url";
        String description = "테스트 프로젝트";
        String instagramUrl = "http://likelion14th/instagram-url";

        Project project = Project.create(cohort,name, imageUrl,description,instagramUrl);

        assertThat(project.getCohort()).isEqualTo(cohort);
        assertThat(project.getName()).isEqualTo(name);
        assertThat(project.getImageUrl()).isEqualTo(imageUrl);
        assertThat(project.getDescription()).isEqualTo(description);
        assertThat(project.getInstagramUrl()).isEqualTo(instagramUrl);
    }
}