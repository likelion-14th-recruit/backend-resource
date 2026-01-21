package org.likelion.recruit.resource.application.repository.custom;

import org.likelion.recruit.resource.application.dto.result.ApplicationDetailResult;

public interface ApplicationRepositoryCustom {
    ApplicationDetailResult getDetail(String publicId, Integer passwordLength);
}
