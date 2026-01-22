package org.likelion.recruit.resource.application.service.command;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Question;
import org.likelion.recruit.resource.application.dto.command.ApplicationCreateCommand;
import org.likelion.recruit.resource.application.dto.command.ApplicationUpdateCommand;
import org.likelion.recruit.resource.application.repository.AnswerRepository;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.application.repository.QuestionRepository;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.likelion.recruit.resource.common.util.PhoneNumberUtils;
import org.likelion.recruit.resource.interview.repository.InterviewAvailableRepository;
import org.likelion.recruit.resource.verification.repository.VerificationRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.likelion.recruit.resource.common.domain.Part.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationCommandService {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationRepository applicationRepository;
    private final VerificationRepository verificationRepository;
    private final InterviewAvailableRepository interviewAvailableRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

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

    public void submitApplication(String publicId) {
        Application application = applicationRepository.findByPublicId(publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));

        validateNotAlreadySubmitted(application);
        validateInterviewTimeSelection(application);
        validateApplicationCompleteness(application);
        application.submit();
    }

    private void validateNotAlreadySubmitted(Application application) {
        if (application.isSubmitted()) {
            throw new BusinessException(ErrorCode.APPLICATION_ALREADY_EXISTS);
        }
    }

    private void validateInterviewTimeSelection(Application application) {
        if (!interviewAvailableRepository.existsByApplication(application)) {
            throw new BusinessException(ErrorCode.INTERVIEW_TIME_NOT_EXISTS);
        }
    }

    private void validateApplicationCompleteness(Application application) {
        Question.Type specificType = mapToQuestionType(application.getPart());
        long totalRequired = questionRepository.countByType(specificType)
                + questionRepository.countByType(Question.Type.COMMON);
        long totalAnswers = answerRepository.countByApplication(application);
        if (totalRequired != totalAnswers) {
            throw new BusinessException(ErrorCode.APPLICATION_INCOMPLETE);
        }
    }

    private Question.Type mapToQuestionType(Part part) {
        return switch (part) {
            case FRONTEND, BACKEND -> Question.Type.DEVELOPMENT;
            case PRODUCT_DESIGN -> Question.Type.PRODUCT_DESIGN;
        };
    }
}
