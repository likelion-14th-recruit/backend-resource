package org.likelion.recruit.resource.application.service.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.dto.query.DocumentPassedMessageTarget;
import org.likelion.recruit.resource.application.dto.result.SendDocumentResultDto;
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

    public SendDocumentResultDto sendDocumentResultMessages() {
        Integer DocumentPassedCount = sendDocumentPassedMessages();
        Integer DocumentFailedCount = sendDocumentFailedMessages();
        log.info("서류 합격자 메시지 전송 완료. 서류 합격자 수 = {}, 서류 불합격자 수 = {}", DocumentPassedCount, DocumentFailedCount);
        return SendDocumentResultDto.of(DocumentPassedCount, DocumentFailedCount);
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

        List<String> documentFailedPhones =
                applicationRepository.findPhoneNumbersByPassStatus(Application.PassStatus.DOCUMENT_FAILED);

        messageCommandService.sendDocumentFailedMessages(documentFailedPhones);

        return documentFailedPhones.size();
    }
}
