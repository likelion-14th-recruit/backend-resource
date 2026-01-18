package org.likelion.recruit.resource.project.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.common.domain.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(nullable = false)
    private Integer cohort;

    @Column(nullable = false)
    private String category;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String description;

    @Column(name = "instagram_url", nullable = false)
    private String instagramUrl;

    private Project(Integer cohort, String category, String imageUrl, String description, String instagramUrl) {
        this.cohort = cohort;
        this.category = category;
        this.imageUrl = imageUrl;
        this.description = description;
        this.instagramUrl = instagramUrl;
    }

    public static Project create(Integer cohort, String category, String imageUrl, String description, String instagramUrl) {
        return new Project(cohort, category, imageUrl, description, instagramUrl);
    }
}
