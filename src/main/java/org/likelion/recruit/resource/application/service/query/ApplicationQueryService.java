package org.likelion.recruit.resource.application.service.query;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.dto.result.PublicIdResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationQueryService {

    public PublicIdResult getPublicId(String publicId){
        return PublicIdResult.from(publicId);
    }
}
