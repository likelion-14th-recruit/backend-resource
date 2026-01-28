package org.likelion.recruit.resource.application.repository.custom;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.dto.command.ApplicationSearchCommand;
import org.likelion.recruit.resource.application.dto.result.ApplicationAllDetailResult;
import org.likelion.recruit.resource.application.dto.result.ApplicationDetailResult;
import org.likelion.recruit.resource.application.dto.result.ApplicationSearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface ApplicationRepositoryCustom {
    ApplicationDetailResult getDetail(String publicId, Integer passwordLength);
    Page<ApplicationSearchResult> searchApplications(ApplicationSearchCommand command, Pageable pageable);
    Set<Application> findInterviewTargets();
    ApplicationAllDetailResult getDetail(String publicId);

}
