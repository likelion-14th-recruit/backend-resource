package org.likelion.recruit.resource.project.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    @Test
    void createProject() {
        Project project = Project.create(14, "http://likelion14th/image-url",
                "테스트 프로젝트", "http://likelion14th/instagram-url");

    }
}