package org.likelion.recruit.resource.executiveMember.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.executiveMember.domain.Position;
import org.likelion.recruit.resource.executiveMember.dto.command.MemberSearchCommand;
import org.likelion.recruit.resource.executiveMember.dto.result.MemberSearchResult;

import java.util.List;

import static org.likelion.recruit.resource.executiveMember.domain.QExecutiveMember.executiveMember;

@RequiredArgsConstructor
public class ExecutiveMemberRepositoryImpl implements ExecutiveMemberCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberSearchResult> findByCondition(MemberSearchCommand command){
        return queryFactory.select(Projections.constructor(MemberSearchResult.class,
                executiveMember.imageUrl,
                executiveMember.name,
                executiveMember.cohort,
                executiveMember.position,
                                executiveMember.part))
                .from(executiveMember)
                .where(
                        partEq(command.getPart())
                )
                .orderBy(
                        positionOrder(),
                        partOrder(),
                        executiveMember.name.asc()
                )
                .fetch();
    }

    private BooleanExpression partEq(Part partCond){
        return partCond != null ? executiveMember.part.eq(partCond) : null;
    }

    private OrderSpecifier<Integer> positionOrder() {
        return new CaseBuilder()
                .when(executiveMember.position.eq(Position.PRESIDENT)).then(Position.PRESIDENT.getPriority())
                .when(executiveMember.position.eq(Position.VICE_PRESIDENT)).then(Position.VICE_PRESIDENT.getPriority())
                .when(executiveMember.position.eq(Position.PART_LEADER)).then(Position.PART_LEADER.getPriority())
                .when(executiveMember.position.eq(Position.MEMBER)).then(Position.MEMBER.getPriority())
                .otherwise(99)
                .asc();
    }

    private OrderSpecifier<Integer> partOrder() {
        return new CaseBuilder()
                .when(executiveMember.part.eq(Part.BACKEND)).then(0)
                .when(executiveMember.part.eq(Part.FRONTEND)).then(1)
                .when(executiveMember.part.eq(Part.PRODUCT_DESIGN)).then(2)
                .otherwise(99)
                .asc();
    }
}
