package org.likelion.recruit.resource.project.repository;

import org.likelion.recruit.resource.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long>, ProjectRepositoryCustom {
}
