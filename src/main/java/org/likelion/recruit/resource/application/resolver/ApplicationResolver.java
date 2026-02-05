package org.likelion.recruit.resource.application.resolver;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationResolver {

    private final ApplicationRepository applicationRepository;

    public Part resolvePart(String publicId){
        return applicationRepository.findTypeByPublicId(publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));
    }

    @Cacheable(
            value = "idByPublicId",
            key = "#publicId"
    )
    public Long resolveId(String publicId){
        return applicationRepository.findIdByPublicId(publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));
    }
}
