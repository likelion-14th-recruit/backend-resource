package org.likelion.recruit.resource.executiveMember.repository;

import org.likelion.recruit.resource.executiveMember.domain.ExecutiveMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecutiveMemberRepository extends JpaRepository<ExecutiveMember, Long>, ExecutiveMemberCustom {
}
