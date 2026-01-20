package org.likelion.recruit.resource.interview.service.query;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.interview.dto.query.InterviewTimeDto;
import org.likelion.recruit.resource.interview.dto.query.InterviewTimeQueryDto;
import org.likelion.recruit.resource.interview.dto.result.InterviewTimesResult;
import org.likelion.recruit.resource.interview.repository.InterviewTimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewTimeQueryService {

    private final InterviewTimeRepository interviewTimeRepository;

    public List<InterviewTimesResult> findAll(){
        List<InterviewTimeQueryDto> allInterviewTimes = interviewTimeRepository.findAllInterviewTimes();

        Map<LocalDate, List<InterviewTimeQueryDto>> grouped =
                allInterviewTimes.stream()
                        .collect(Collectors.groupingBy(InterviewTimeQueryDto::getDate));

        return grouped.entrySet().stream()
                .map(entry -> new InterviewTimesResult(
                        entry.getKey(),
                        entry.getValue().stream()
                                .map(q -> new InterviewTimeDto(
                                        q.getInterviewTimeId(),
                                        q.getStartTime(),
                                        q.getEndTime()
                                ))
                                .toList()
                ))
                .toList();
    }
}
