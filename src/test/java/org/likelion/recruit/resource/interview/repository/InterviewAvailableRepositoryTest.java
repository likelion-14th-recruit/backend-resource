package org.likelion.recruit.resource.interview.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.common.config.QuerydslConfig;
import org.likelion.recruit.resource.common.init.ApplicationFixture;
import org.likelion.recruit.resource.interview.domain.InterviewAvailable;
import org.likelion.recruit.resource.interview.domain.InterviewTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5432/likelion14th",
        "spring.datasource.username=postgres",
        "spring.datasource.password=20200129",
        "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect",
        "spring.jpa.hibernate.ddl-auto=none"
})
@Import(QuerydslConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class InterviewAvailableRepositoryTest {

    @Autowired
    private InterviewAvailableRepository interviewAvailableRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("지원서로 면접 가능 시간 조회 시 시간 정보까지 Fetch Join으로 가져온다")
    void findAllByApplication_Success() {
        // given
        Application app = ApplicationFixture.createApplication("test-id");
        InterviewTime time = InterviewTime.create(LocalDate.of(2026, 1, 1), LocalTime.of(10, 0));
        InterviewAvailable available = InterviewAvailable.create(time, app);

        em.persist(app);
        em.persist(time);
        em.persist(available);

        em.flush();
        em.clear();

        // when
        List<InterviewAvailable> results = interviewAvailableRepository.findAllByApplication(app);

        // then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getInterviewTime().getStartTime()).isEqualTo(LocalTime.of(10, 0));
    }
}