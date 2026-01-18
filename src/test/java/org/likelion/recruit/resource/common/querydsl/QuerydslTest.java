package org.likelion.recruit.resource.common.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.executiveMember.domain.ExecutiveMember;
import org.likelion.recruit.resource.executiveMember.domain.Position;
import org.likelion.recruit.resource.executiveMember.domain.QExecutiveMember;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.likelion.recruit.resource.executiveMember.domain.QExecutiveMember.executiveMember;

@SpringBootTest
@Transactional
class QuerydslTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    void contextLoads() {
        ExecutiveMember member = ExecutiveMember.create("이름", 14, "url",
                Part.BACKEND, Position.PRESIDENT);

        em.persist(member);

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<ExecutiveMember> result = queryFactory.selectFrom(executiveMember)
                .fetch();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("이름");
    }

}
