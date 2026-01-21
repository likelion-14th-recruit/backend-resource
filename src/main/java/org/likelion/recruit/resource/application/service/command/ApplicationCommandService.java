package org.likelion.recruit.resource.application.service.command;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.dto.command.ApplicationCreateCommand;
import org.likelion.recruit.resource.application.dto.command.ApplicationUpdateCommand;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.likelion.recruit.resource.common.util.PhoneNumberUtils;
import org.likelion.recruit.resource.verification.repository.VerificationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationCommandService {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationRepository applicationRepository;
    private final VerificationRepository verificationRepository;

    public String createApplication(ApplicationCreateCommand command) {
        String phoneNumber = PhoneNumberUtils.normalize(command.getPhoneNumber());
        String passwordHash = passwordEncoder.encode(command.getPassword());

        // 상황 1. 지원서가 있는데 새로 생성하는 경우  verified=true, application 존재
        // 상황 3. 지원서 찾기에서 인증번호 새로 받고 다시 생성 누른 경우 verified=false, application 존재
        if (applicationRepository.existsByPhoneNumber(phoneNumber)) {
            throw new BusinessException(ErrorCode.APPLICATION_ALREADY_EXISTS);
        }

        if (!verificationRepository.existsByPhoneNumberAndVerifiedTrue(phoneNumber)) {
            throw new BusinessException(ErrorCode.VERIFICATION_REQUIRED);
        }

        Application application = Application.create(command.getName(), command.getStudentNumber(),
                phoneNumber, passwordHash, command.getMajor(), command.getDoubleMajor(),
                command.getSemester(), command.getAcademicStatus(), command.getPart());
        applicationRepository.save(application);

        return application.getPublicId();
    }

    public String resetPassword(String reqPhoneNumber, String password) {
        String phoneNumber = PhoneNumberUtils.normalize(reqPhoneNumber);

        if (!verificationRepository.findVerifiedByPhoneNumber(phoneNumber)) {
            throw new BusinessException(ErrorCode.VERIFICATION_REQUIRED);
        }

        Application application = applicationRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));

        String passwordHash = passwordEncoder.encode(password);
        application.changePassword(passwordHash);

        return application.getPublicId();
    }

    public void updateApplication(String publicId, ApplicationUpdateCommand command) {
        Application application = applicationRepository.findByPublicId(publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));

        application.update(command);
    }
}
