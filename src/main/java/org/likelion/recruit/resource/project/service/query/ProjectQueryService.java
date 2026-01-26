package org.likelion.recruit.resource.project.service.query;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.project.dto.command.ProjectSearchCommand;
import org.likelion.recruit.resource.project.dto.result.ProjectSearchResult;
import org.likelion.recruit.resource.project.repository.ProjectRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectQueryService {

    private final ProjectRepository projectRepository;

    @Cacheable(
            value = "projects",
            key = "#command.cohort",
            condition = "#pageable.pageNumber == 0 && #pageable.pageSize == 15"
    )
    public Slice<ProjectSearchResult> searchProjects(ProjectSearchCommand command, Pageable pageable){
        return projectRepository.searchProjectsSlice(command, pageable);
    }
}
