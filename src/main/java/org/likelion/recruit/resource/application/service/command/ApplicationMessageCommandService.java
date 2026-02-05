package org.likelion.recruit.resource.application.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.dto.query.DocumentPassedMessageTarget;
import org.likelion.recruit.resource.application.dto.query.PassedMessageTarget;
import org.likelion.recruit.resource.application.dto.query.RejectedMessageTarget;
import org.likelion.recruit.resource.application.dto.result.SendMessageResultDto;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.likelion.recruit.resource.interview.repository.InterviewScheduleRepository;
import org.likelion.recruit.resource.message.service.command.MessageCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ApplicationMessageCommandService {

    private final ApplicationRepository applicationRepository;
    private final MessageCommandService messageCommandService;
    private final InterviewScheduleRepository interviewScheduleRepository;

    public SendMessageResultDto sendDocumentResultMessages() {
        Integer documentPassedCount = sendDocumentPassedMessages();
        Integer documentFailedCount = sendDocumentFailedMessages();
        log.info("서류 합격자 메시지 전송 완료. 서류 합격자 수 = {}, 서류 불합격자 수 = {}", documentPassedCount, documentFailedCount);
        return SendMessageResultDto.of(documentPassedCount, documentFailedCount);
    }

    public SendMessageResultDto sendInterviewResultMessages() {
        Integer interviewPassedCount = sendInterviewPassedMessages();
        Integer interviewFailedCount = sendInterviewFailedMessages();
        log.info("면접 합격자 메시지 전송 완료. 면접 합격자 수 = {}, 면접 불합격자 수 = {}", interviewPassedCount, interviewFailedCount);
        return SendMessageResultDto.of(interviewPassedCount, interviewFailedCount);
    }


    private Integer sendDocumentPassedMessages() {

        List<String> documentPassedPhones =
                applicationRepository.findPhoneNumbersByPassStatus(Application.PassStatus.DOCUMENT_PASSED);
        List<DocumentPassedMessageTarget> targets = interviewScheduleRepository.findDocumentPassedMessageTargets();

        if (documentPassedPhones.size() != targets.size()) {
            throw new BusinessException(ErrorCode.DOCUMENT_PASSED_NOT_ASSIGNED_INTERVIEW_SCHEDULE);
        }
        messageCommandService.sendDocumentPassedMessages(targets);

        return targets.size();
    }

    private Integer sendDocumentFailedMessages() {

        List<RejectedMessageTarget> targets =
                applicationRepository.findRejectTargetsByPassStatus(Application.PassStatus.DOCUMENT_FAILED);

        messageCommandService.sendDocumentFailedMessages(targets);

        return targets.size();
    }

    private Integer sendInterviewPassedMessages() {
        List<PassedMessageTarget> targets =
                applicationRepository.findPassTargetsByPassStatus(Application.PassStatus.INTERVIEW_PASSED);

        messageCommandService.sendInterviewPassedMessages(targets);

        return targets.size();
    }

    private Integer sendInterviewFailedMessages() {

        List<RejectedMessageTarget> targets =
                applicationRepository.findRejectTargetsByPassStatus(Application.PassStatus.INTERVIEW_FAILED);

        messageCommandService.sendInterviewFailedMessages(targets);

        return targets.size();
    }
}
