package org.likelion.recruit.resource.interview.service.command;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.repository.ApplicationRepository;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.likelion.recruit.resource.interview.domain.InterviewSchedule;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.dto.command.InterviewScheduleCommand;
import org.likelion.recruit.resource.interview.repository.InterviewScheduleRepository;
import org.likelion.recruit.resource.interview.repository.InterviewTimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Transactional
public class InterviewScheduleCommandService {

    private final InterviewScheduleRepository interviewScheduleRepository;
    private final InterviewTimeRepository interviewTimeRepository;
    private final ApplicationRepository applicationRepository;

    public void upsertInterviewSchedule(InterviewScheduleCommand command) {
        Application application = findApplication(command.getApplicationPublicId());

        InterviewTime selectedTime = getInterviewTime(command.getDate(), command.getStartTime());

        saveOrUpdate(application, selectedTime, command.getPlace());
    }
    private Application findApplication(String publicId) {
        return applicationRepository.findByPublicId(publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.APPLICATION_NOT_EXISTS));
    }

    private InterviewTime getInterviewTime(LocalDate date, LocalTime startTime) {
        return interviewTimeRepository.findByDateAndStartTime(date, startTime)
                .orElseThrow(() -> new BusinessException(ErrorCode.INTERVIEW_TIME_NOT_EXISTS));
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
}