package org.likelion.recruit.resource.verification.service.command;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.likelion.recruit.resource.common.util.PhoneNumberUtils;
import org.likelion.recruit.resource.message.service.command.MessageCommandService;
import org.likelion.recruit.resource.verification.domain.Verification;
import org.likelion.recruit.resource.verification.dto.command.VerifyConfirmCommand;
import org.likelion.recruit.resource.verification.dto.command.VerifyPhoneCommand;
import org.likelion.recruit.resource.verification.repository.VerificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VerificationCommandService {

    private final VerificationRepository verificationRepository;
    private final MessageCommandService messageCommandService;

    public void sendVerificationCode(VerifyPhoneCommand command) {
        String phoneNumber = PhoneNumberUtils.normalize(command.getPhoneNumber());

        // 이미 인증한 전적이 있다면
        if (verificationRepository.existsByPhoneNumber(phoneNumber)) {
            Verification verification = verificationRepository.findByPhoneNumber(phoneNumber)
                    .orElseThrow(() -> new BusinessException(ErrorCode.VERIFICATION_NOT_FOUND));

            Integer renewCode = verification.renewCode();
            messageCommandService.sendMessage(phoneNumber, renewCode);
            return;
        }

        Verification verification = Verification.create(phoneNumber);
        Integer code = verification.makeCode();
        verificationRepository.save(verification);

        messageCommandService.sendMessage(phoneNumber, code);
    }

    public void confirmVerificationCode(VerifyConfirmCommand command) {
        String phoneNumber = PhoneNumberUtils.normalize(command.getPhoneNumber());

        Verification verification = verificationRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new BusinessException(ErrorCode.VERIFICATION_NOT_FOUND));

        if (verification.isVerified()) {
            throw new BusinessException(ErrorCode.VERIFICATION_ALREADY_COMPLETED);
        }
        if (verification.getCode() == null ||
                !verification.getCode().equals(command.getCode())) {
            throw new BusinessException(ErrorCode.INVALID_VERIFICATION_CODE);
        }
        verification.verify();
    }
}
