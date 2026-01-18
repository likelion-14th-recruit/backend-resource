package org.likelion.recruit.resource.executiveMember.repository;

import org.likelion.recruit.resource.executiveMember.dto.command.MemberSearchCommand;
import org.likelion.recruit.resource.executiveMember.dto.result.MemberSearchResult;

import java.util.List;

public interface ExecutiveMemberCustom {

    List<MemberSearchResult> findByCondition(MemberSearchCommand command);
}
