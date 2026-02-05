package org.likelion.recruit.resource.project.dto.command;

import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.project.dto.request.ProjectSearchRequest;

@Getter
@Builder
public class ProjectSearchCommand {
    private Integer cohort;

    public static ProjectSearchCommand from(ProjectSearchRequest req){
        return ProjectSearchCommand.builder()
                .cohort(req.getCohort())
                .build();
    }
}
