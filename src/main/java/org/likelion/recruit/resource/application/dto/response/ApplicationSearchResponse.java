package org.likelion.recruit.resource.application.dto.response;


import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.dto.result.ApplicationSearchResult;
import org.likelion.recruit.resource.common.domain.Part;

@Getter
@Builder
public class ApplicationSearchResponse {
    private String applicationPublicId;
    private String name;
    private String studentNumber;
    private String academicStatus;
    private String phoneNumber;
    private String part;
    private String passStatus;

    public static ApplicationSearchResponse from(ApplicationSearchResult result){
        return ApplicationSearchResponse.builder()
                .applicationPublicId(result.getApplicationPublicId())
                .name(result.getName())
                .studentNumber(result.getStudentNumber())
                .academicStatus(result.getAcademicStatus().toString())
                .phoneNumber(result.getPhoneNumber())
                .part(result.getPart().toString())
                .passStatus(result.getPassStatus().toString())
                .build();
    }
}
