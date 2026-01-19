package org.likelion.recruit.resource.project.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.project.domain.QProject;
import org.likelion.recruit.resource.project.dto.command.ProjectSearchCommand;
import org.likelion.recruit.resource.project.dto.result.ProjectSearchResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import static org.likelion.recruit.resource.project.domain.QProject.project;

@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<ProjectSearchResult> searchProjectsSlice(ProjectSearchCommand command, Pageable pageable) {
        queryFactory.select(Projections.constructor(ProjectSearchResult.class,
                        project.imageUrl,
                        project.description,))
                .from(project)
    }


}
