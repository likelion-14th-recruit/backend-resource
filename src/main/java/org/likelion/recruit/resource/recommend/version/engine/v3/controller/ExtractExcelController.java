package org.likelion.recruit.resource.recommend.version.engine.v3.controller;

import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.request.InterviewAvailableExcelRequest;
import org.likelion.recruit.resource.recommend.version.engine.v3.service.ExtractExcelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class ExtractExcelController {

    private final ExtractExcelService extractExcelService;

    /**
     * 면접 배정 결과 추천 excel 추출
     */
    @PostMapping("/excel")
    public ResponseEntity<byte[]> downloadExcel(
            @RequestBody InterviewAvailableExcelRequest request
    ) {

        byte[] excelBytes =
                extractExcelService.generateInterviewAvailableExcel(request);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=interview_schedule.xlsx"
                )
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(excelBytes.length)
                .body(excelBytes);
    }
}
