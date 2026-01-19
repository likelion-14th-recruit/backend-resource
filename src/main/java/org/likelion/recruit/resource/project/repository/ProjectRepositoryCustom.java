package org.likelion.recruit.resource.project.repository;

import org.likelion.recruit.resource.project.dto.command.ProjectSearchCommand;
import org.likelion.recruit.resource.project.dto.result.ProjectSearchResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProjectRepositoryCustom {

    Slice<ProjectSearchResult> searchProjectsSlice(ProjectSearchCommand command, Pageable pageable);
}
