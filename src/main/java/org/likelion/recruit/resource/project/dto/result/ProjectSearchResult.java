package org.likelion.recruit.resource.project.dto.result;

import lombok.Getter;

@Getter
public class ProjectSearchResult {
    private String imageUrl;
    private String name;
    private Integer cohort;
    private String description;
    private String instagramUrl;

    public ProjectSearchResult(String imageUrl, String name, Integer cohort, String description, String instagramUrl) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.cohort = cohort;
        this.description = description;
        this.instagramUrl = instagramUrl;
    }
}
