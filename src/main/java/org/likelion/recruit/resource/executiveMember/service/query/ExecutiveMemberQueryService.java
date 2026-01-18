package org.likelion.recruit.resource.executiveMember.service.query;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.executiveMember.dto.command.MemberSearchCommand;
import org.likelion.recruit.resource.executiveMember.dto.result.MemberSearchResult;
import org.likelion.recruit.resource.executiveMember.repository.ExecutiveMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExecutiveMemberQueryService {

    private final ExecutiveMemberRepository executiveMemberRepository;

    public List<MemberSearchResult> searchMembers(MemberSearchCommand command){
        return executiveMemberRepository.findByCondition(command);
    }
}
