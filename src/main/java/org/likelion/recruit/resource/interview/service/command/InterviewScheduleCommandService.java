package org.likelion.recruit.resource.interview.service.command;

import lombok.RequiredArgsConstructor;

import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;
import org.likelion.recruit.resource.interview.domain.InterviewSchedule;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.dto.command.InterviewScheduleCommand;
import org.likelion.recruit.resource.interview.repository.InterviewAvailableRepository;
import org.likelion.recruit.resource.interview.repository.InterviewScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.likelion.recruit.resource.common.exception.ErrorCode.NOT_AVAILABLE_INTERVIEW_TIME;

@Service
@RequiredArgsConstructor
@Transactional
public class InterviewScheduleCommandService {

    private final InterviewScheduleRepository interviewScheduleRepository;
    private final InterviewAvailableRepository interviewAvailableRepository;
    private final ApplicationRepository applicationRepository;

    public void upsertInterviewSchedule(InterviewScheduleCommand command) {
        Application application = findApplication(command.getApplicationPublicId());

        InterviewTime selectedTime = validateAndGetInterviewTime(application, command);

        saveOrUpdate(application, selectedTime, command.getPlace());
    }

    private void saveOrUpdate(Application application, InterviewTime time, String place) {
        interviewScheduleRepository.findByApplication(application)
                .ifPresentOrElse(
                        schedule -> {
                            schedule.assignPlace(place);
                            schedule.updateInterviewTime(time);
                        },
                        () -> interviewScheduleRepository.save(InterviewSchedule.create(place, application, time))
                );
    }

    private Application findApplication(String publicId) {
        return applicationRepository.findByPublicId(publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));
    }

    private InterviewTime validateAndGetInterviewTime(Application application, InterviewScheduleCommand command) {
        return interviewAvailableRepository
                .findByApplicationAndDateTime(
                        application,
                        command.getDate(),
                        command.getStartTime(),
                        command.getEndTime()
                )
                .map(InterviewAvailable::getInterviewTime)
                .orElseThrow(() -> new BusinessException(NOT_AVAILABLE_INTERVIEW_TIME));
    }
}