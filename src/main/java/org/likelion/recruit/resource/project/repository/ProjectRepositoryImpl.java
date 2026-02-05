package org.likelion.recruit.resource.project.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.project.domain.QProject;
import org.likelion.recruit.resource.project.dto.command.ProjectSearchCommand;
import org.likelion.recruit.resource.project.dto.result.ProjectSearchResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static org.likelion.recruit.resource.project.domain.QProject.project;

@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<ProjectSearchResult> searchProjectsSlice(ProjectSearchCommand command, Pageable pageable) {
        List<ProjectSearchResult> mainQuery = queryFactory.select(Projections.constructor(ProjectSearchResult.class,
                        project.imageUrl,
                        project.name,
                        project.description,
                        project.instagramUrl)
                )
                .from(project)
                .where(
                        cohortEq(command.getCohort())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

                return toSlice(mainQuery, pageable);
    }

    private static <T> Slice<T> toSlice(List<T> results, Pageable pageable) {
        // 가져온 개수가 limit보다 많다면 hasNext는 true
        boolean hasNext = results.size() > pageable.getPageSize();

        if (hasNext) {
            results.remove(results.size() - 1);
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }

    private BooleanExpression cohortEq(Integer cohort){
        return cohort == null ? null : project.cohort.eq(cohort);
    }


}
