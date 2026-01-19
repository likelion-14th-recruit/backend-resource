package org.likelion.recruit.resource.project.service.query;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.project.dto.command.ProjectSearchCommand;
import org.likelion.recruit.resource.project.dto.result.ProjectSearchResult;
import org.likelion.recruit.resource.project.repository.ProjectRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectQueryService {

    private final ProjectRepository projectRepository;

    public Slice<ProjectSearchResult> searchProjects(ProjectSearchCommand command, Pageable pageable){
        return projectRepository.searchProjectsSlice(command, pageable);
    }
}
