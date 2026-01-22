package org.likelion.recruit.resource.recommend.service;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.likelion.recruit.resource.interview.repository.InterviewAvailableRepository;
import org.likelion.recruit.resource.interview.repository.InterviewTimeRepository;
import org.likelion.recruit.resource.recommend.dto.common.InterviewTimeAssignmentResponse;
import org.likelion.recruit.resource.recommend.dto.request.InterviewAssignmentValidationRequest;
import org.likelion.recruit.resource.recommend.dto.request.InterviewTimeAssignmentRequest;
import org.likelion.recruit.resource.recommend.dto.result.AvailabilityViolation;
import org.likelion.recruit.resource.recommend.dto.response.InterviewAssignmentResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewAssignmentValidationService {

    private final InterviewAvailableRepository interviewAvailableRepository;
    private final InterviewTimeRepository interviewTimeRepository;

    public List<AvailabilityViolation> validate(InterviewAssignmentValidationRequest request) {
        List<AvailabilityViolation> violations = new ArrayList<>();

        for (InterviewTimeAssignmentRequest assignment : request.getAssignments()) {

            LocalDate date = assignment.getDate();
            LocalTime start = assignment.getStartTime();
            LocalTime end = assignment.getEndTime();

            if (assignment.getAssignedApplications() == null) continue;

            assignment.getAssignedApplications().forEach(app -> {
                Long interviewTimeId = interviewTimeRepository.findIdByDateAndStartTime(date, start)
                        .orElseThrow(() -> new BusinessException(ErrorCode.INTERVIEW_TIME_NOT_EXISTS));
                boolean ok = interviewAvailableRepository.existsAvailable(app.getPublicId(), interviewTimeId);

                if (!ok) {
                    violations.add(
                            new AvailabilityViolation(app.getPublicId(), date, start, end)
                    );
                }
            });
        }
        return violations;
    }

}

