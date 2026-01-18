package org.likelion.recruit.resource.executiveMember.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.executiveMember.domain.Position;
import org.likelion.recruit.resource.executiveMember.domain.QExecutiveMember;
import org.likelion.recruit.resource.executiveMember.dto.command.MemberSearchCommand;
import org.likelion.recruit.resource.executiveMember.dto.result.MemberSearchResult;

import java.util.List;

import static org.likelion.recruit.resource.executiveMember.domain.QExecutiveMember.executiveMember;

@RequiredArgsConstructor
public class ExecutiveMemberImpl implements ExecutiveMemberCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberSearchResult> findByCondition(MemberSearchCommand command){
        return queryFactory.select(Projections.constructor(MemberSearchResult.class,
                executiveMember.imageUrl,
                executiveMember.name,
                executiveMember.cohort,
                executiveMember.part,
                executiveMember.position))
                .from(executiveMember)
                .where(
                        partEq(command.getPart())
                )
                .orderBy(
                        getPositionOrder(command.getPart()),
                        executiveMember.name.asc()
                )
                .fetch();
    }

    private BooleanExpression partEq(Part partCond){
        return partCond != null ? executiveMember.part.eq(partCond) : null;
    }

    private OrderSpecifier<Integer> getPositionOrder(Part partFilter) {

        if (partFilter == null) {
            return new CaseBuilder()
                    .when(executiveMember.position.eq(Position.PRESIDENT)).then(1)
                    .when(executiveMember.position.eq(Position.VICE_PRESIDENT)).then(2)
                    .when(executiveMember.position.eq(Position.PART_LEADER)).then(3)
                    .otherwise(4)
                    .asc();
        }

        return new CaseBuilder()
                .when(executiveMember.position.eq(Position.PART_LEADER)).then(1)
                .otherwise(2)
                .asc();
    }
}
