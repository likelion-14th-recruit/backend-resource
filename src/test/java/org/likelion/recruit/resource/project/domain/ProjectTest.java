package org.likelion.recruit.resource.project.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.likelion.recruit.resource.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProjectTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void createProject() {
        Project project = Project.create(14, "http://likelion14th/image-url",
                "테스트 프로젝트", "http://likelion14th/instagram-url");

        Project save = projectRepository.save(project);

        assertThat(save).isNotNull();
        assertThat(save.getId()).isEqualTo(project.getId());
        assertThat(save.getCohort()).isEqualTo(project.getCohort());
        assertThat(save.getDescription()).isEqualTo(project.getDescription());
        assertThat(save.getImageUrl()).isEqualTo(project.getImageUrl());
        assertThat(save.getInstagramUrl()).isEqualTo(project.getInstagramUrl());
        assertThat(save.getCreatedAt()).isNotNull();
        assertThat(save.getUpdatedAt()).isNotNull();
        System.out.println("생성 시각 : " + save.getCreatedAt());
        System.out.println("업데이트 시각 : " + save.getCreatedAt());
    }
}