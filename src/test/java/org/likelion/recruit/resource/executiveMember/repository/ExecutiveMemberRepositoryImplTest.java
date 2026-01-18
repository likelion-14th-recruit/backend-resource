package org.likelion.recruit.resource.executiveMember.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.common.querydsl.TestQuerydslConfig;
import org.likelion.recruit.resource.executiveMember.domain.ExecutiveMember;
import org.likelion.recruit.resource.executiveMember.domain.Position;
import org.likelion.recruit.resource.executiveMember.dto.command.MemberSearchCommand;
import org.likelion.recruit.resource.executiveMember.dto.result.MemberSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({
        ExecutiveMemberRepositoryImpl.class,
        TestQuerydslConfig.class})
public class ExecutiveMemberRepositoryImplTest {

    @Autowired
    private ExecutiveMemberRepository executiveMemberRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {
        ExecutiveMember member1 = ExecutiveMember.create("회장", 14,
                "imageUrl", Part.BACKEND, Position.PRESIDENT);
        ExecutiveMember member2 = ExecutiveMember.create("부회장1", 14,
                "imageUrl", Part.FRONTEND, Position.VICE_PRESIDENT);
        ExecutiveMember member3 = ExecutiveMember.create("부회장2", 14,
                "imageUrl", Part.PRODUCT_DESIGN, Position.VICE_PRESIDENT);
        ExecutiveMember member4 = ExecutiveMember.create("파트장1", 14,
                "imageUrl", Part.BACKEND, Position.PART_LEADER);
        ExecutiveMember member5 = ExecutiveMember.create("파트장2", 14,
                "imageUrl", Part.FRONTEND, Position.PART_LEADER);
        ExecutiveMember member6 = ExecutiveMember.create("파트장3", 14,
                "imageUrl", Part.PRODUCT_DESIGN, Position.PART_LEADER);
        ExecutiveMember member7 = ExecutiveMember.create("멤버", 14,
                "imageUrl", Part.BACKEND, Position.MEMBER);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
        em.persist(member5);
        em.persist(member6);
        em.persist(member7);

        em.flush();
        em.clear();
    }

    @Test
    void findByCondition_no_condition(){
        MemberSearchCommand command = MemberSearchCommand.builder()
                .part(null)
                .build();

        List<MemberSearchResult> results = executiveMemberRepository.findByCondition(command);

        assertThat(results).isNotEmpty();
        assertThat(results).hasSize(7);
        assertThat(results.get(0).getPosition()).isEqualTo(Position.PRESIDENT);
        assertThat(results.get(results.size() - 1).getPosition()).isEqualTo(Position.MEMBER);
    }

    @Test
    void findByCondition_BACKEND_condition(){
        MemberSearchCommand command = MemberSearchCommand.builder()
                .part(Part.BACKEND)
                .build();

        List<MemberSearchResult> results = executiveMemberRepository.findByCondition(command);

        assertThat(results).isNotEmpty();
        assertThat(results).hasSize(3);
        assertThat(results.get(0).getPosition()).isEqualTo(Position.PART_LEADER);
    }

}
