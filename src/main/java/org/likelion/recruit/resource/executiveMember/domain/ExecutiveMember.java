package org.likelion.recruit.resource.executiveMember.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.common.domain.BaseTimeEntity;
import org.likelion.recruit.resource.common.domain.Part;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ExecutiveMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "executive_member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Part part;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position position;

    private ExecutiveMember(String name, String imageUrl, Part part, Position position) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.part = part;
        this.position = position;
    }

    public static ExecutiveMember create(String name, String imageUrl, Part part, Position position) {
        return new ExecutiveMember(name, imageUrl, part, position);
    }
}
