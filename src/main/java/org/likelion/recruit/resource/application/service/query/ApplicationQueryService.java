package org.likelion.recruit.resource.application.service.query;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.dto.command.ApplicationSearchCommand;
import org.likelion.recruit.resource.application.dto.result.*;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.likelion.recruit.resource.common.util.PhoneNumberUtils;
import org.likelion.recruit.resource.interview.repository.InterviewAvailableRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationQueryService {

    private final ApplicationRepository applicationRepository;
    private final PasswordEncoder passwordEncoder;
    private final InterviewAvailableRepository interviewAvailableRepository;

    public PublicIdResult getPublicId(String publicId) {
        return PublicIdResult.from(publicId);
    }

    public LoginResult login(String phoneNumber, String password) {
        String normPhoneNumber = PhoneNumberUtils.normalize(phoneNumber);

        Application application = applicationRepository.findByPhoneNumber(normPhoneNumber)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));


        String storedHash = application.getPasswordHash();
        if (!passwordEncoder.matches(password, storedHash)) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        Integer passwordLength = password.length();

        return LoginResult.of(application.getPublicId(), phoneNumber, passwordLength);
    }

    public ApplicationDetailResult getApplicationDetail(String publicId, Integer passwordLength) {
        return applicationRepository.getDetail(publicId, passwordLength);
    }

    public Page<ApplicationSearchResult> searchApplications(ApplicationSearchCommand command, Pageable pageable){
        return applicationRepository.searchApplications(command, pageable);
    }

    public ApplicationAllDetailResult getApplicationAllDetail(String publicId){
        Application application = applicationRepository.findByPublicId(publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));
        return ApplicationAllDetailResult.from(application);
    }
}
