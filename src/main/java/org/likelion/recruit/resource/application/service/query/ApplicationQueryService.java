package org.likelion.recruit.resource.application.service.query;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.dto.result.ApplicationDetailResult;
import org.likelion.recruit.resource.application.dto.result.LoginResult;
import org.likelion.recruit.resource.application.dto.result.PublicIdResult;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.likelion.recruit.resource.common.util.PhoneNumberUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationQueryService {

    private final ApplicationRepository applicationRepository;
    private final PasswordEncoder passwordEncoder;

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
}
