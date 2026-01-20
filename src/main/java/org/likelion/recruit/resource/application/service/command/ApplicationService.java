package org.likelion.recruit.resource.application.service.command;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.dto.command.ApplicationCreateCommand;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.common.util.PhoneNumberUtils;
import org.likelion.recruit.resource.verification.domain.Verification;
import org.likelion.recruit.resource.verification.repository.VerificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final VerificationRepository verificationRepository;

    public String createApplication(ApplicationCreateCommand command) {
        PhoneNumberUtils.normalize(command.getPhoneNumber());

        if (verificationRepository.existsByPhoneNumber(command.getPhoneNumber())) {
            throw new
        }
    }
}
