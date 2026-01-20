//package org.likelion.recruit.resource.verification.service.command;
//
//import lombok.RequiredArgsConstructor;
//import org.likelion.recruit.resource.common.exception.BusinessException;
//import org.likelion.recruit.resource.common.exception.ErrorCode;
//import org.likelion.recruit.resource.verification.domain.Verification;
//import org.likelion.recruit.resource.verification.repository.VerificationRepository;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class VerificationCommandService {
//
//    private final VerificationRepository verificationRepository;
//
//    public void verify(String phoneNumber) {
//        if(verificationRepository.existsByPhoneNumber(phoneNumber)){
//            Verification verification = verificationRepository.findByPhoneNumber(phoneNumber)
//                    .orElseThrow(() -> new BusinessException(ErrorCode.VERIFICATION_NOT_FOUND));
//
//            Integer renewCode = verification.renewCode();
//            messageCommandService.sendMessage(renewCode);
//        }
//    }
//}
