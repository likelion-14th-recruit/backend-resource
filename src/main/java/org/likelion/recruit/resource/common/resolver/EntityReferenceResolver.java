package org.likelion.recruit.resource.common.resolver;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Question;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityReferenceResolver {

    @PersistenceContext
    private final EntityManager em;

    public Application application(Long id) {
        return em.getReference(Application.class, id);
    }

    public Question question(Long id) {
        return em.getReference(Question.class, id);
    }
}
