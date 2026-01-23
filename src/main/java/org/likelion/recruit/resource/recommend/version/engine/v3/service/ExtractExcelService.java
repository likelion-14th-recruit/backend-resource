package org.likelion.recruit.resource.recommend.version.engine.v3.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.recommend.dto.request.*;
import org.likelion.recruit.resource.recommend.version.engine.v3.dto.request.InterviewAvailableExcelRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtractExcelService {

    public byte[] generateInterviewAvailableExcel(InterviewAvailableExcelRequest request) {

        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Interview Schedule");

            // =========================
            // 색상 정의
            // =========================
            XSSFColor HEADER = new XSSFColor(new byte[]{(byte) 217, (byte) 217, (byte) 217}, null);
            XSSFColor BACKEND = new XSSFColor(new byte[]{(byte) 217, (byte) 225, (byte) 242}, null);
            XSSFColor FRONTEND = new XSSFColor(new byte[]{(byte) 226, (byte) 239, (byte) 218}, null);
            XSSFColor DESIGN = new XSSFColor(new byte[]{(byte) 252, (byte) 228, (byte) 214}, null);
            XSSFColor EMPTY = new XSSFColor(new byte[]{(byte) 242, (byte) 242, (byte) 242}, null);

            // =========================
            // 스타일
            // =========================
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);

            XSSFCellStyle headerStyle = baseStyle(workbook);
            headerStyle.setFont(headerFont);
            fill(headerStyle, HEADER);

            XSSFCellStyle centerStyle = baseStyle(workbook);

            XSSFCellStyle backendStyle = baseStyle(workbook);
            fill(backendStyle, BACKEND);

            XSSFCellStyle frontendStyle = baseStyle(workbook);
            fill(frontendStyle, FRONTEND);

            XSSFCellStyle designStyle = baseStyle(workbook);
            fill(designStyle, DESIGN);

            XSSFCellStyle emptyStyle = baseStyle(workbook);
            fill(emptyStyle, EMPTY);

            // =========================
            // 헤더
            // =========================
            Row header = sheet.createRow(0);
            create(header, 0, "Date", headerStyle);
            create(header, 1, "Time", headerStyle);
            create(header, 2, "Slot 1", headerStyle);
            create(header, 3, "Slot 2", headerStyle);

            // =========================
            // 데이터 정렬
            // =========================
            List<InterviewTimeAssignmentRequest> assignments = request.getAssignments();
            assignments.sort(
                    Comparator.comparing(InterviewTimeAssignmentRequest::getDate)
                            .thenComparing(InterviewTimeAssignmentRequest::getStartTime)
            );

            DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

            int rowIdx = 1;
            int dateMergeStart = 1;
            var currentDate = assignments.isEmpty() ? null : assignments.get(0).getDate();

            // =========================
            // 본문
            // =========================
            for (InterviewTimeAssignmentRequest a : assignments) {

                if (!a.getDate().equals(currentDate)) {
                    mergeDate(sheet, dateMergeStart, rowIdx - 1);
                    dateMergeStart = rowIdx;
                    currentDate = a.getDate();
                }

                Row row = sheet.createRow(rowIdx);

                // Date
                create(row, 0, a.getDate().toString(), centerStyle);

                // Time
                String time = a.getStartTime().format(timeFmt)
                        + " - "
                        + a.getEndTime().format(timeFmt);
                create(row, 1, time, centerStyle);

                // Slot 1 / 2
                fillSlot(row, 2, a.getAssignedApplications(), 0,
                        backendStyle, frontendStyle, designStyle, emptyStyle);

                fillSlot(row, 3, a.getAssignedApplications(), 1,
                        backendStyle, frontendStyle, designStyle, emptyStyle);

                rowIdx++;
            }

            // 마지막 날짜 merge
            if (rowIdx > dateMergeStart) {
                mergeDate(sheet, dateMergeStart, rowIdx - 1);
            }

            // =========================
            // 컬럼 너비 / 고정
            // =========================
            sheet.setColumnWidth(0, 14 * 256);
            sheet.setColumnWidth(1, 18 * 256);
            sheet.setColumnWidth(2, 24 * 256);
            sheet.setColumnWidth(3, 24 * 256);
            sheet.createFreezePane(0, 1);

            workbook.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("엑셀 생성 실패", e);
        }
    }

    private XSSFCellStyle baseStyle(XSSFWorkbook wb) {
        XSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setWrapText(true);
        return style;
    }

    private void fill(XSSFCellStyle style, XSSFColor color) {
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(color);
    }

    private void create(Row row, int col, String value, CellStyle style) {
        Cell cell = row.createCell(col);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private void mergeDate(Sheet sheet, int start, int end) {
        if (end > start) {
            sheet.addMergedRegion(new CellRangeAddress(start, end, 0, 0));
        }
    }

    private void fillSlot(
            Row row,
            int col,
            List<AssignedApplicationRequest> apps,
            int idx,
            CellStyle backend,
            CellStyle frontend,
            CellStyle design,
            CellStyle empty
    ) {
        if (apps == null || apps.size() <= idx) {
            create(row, col, "—", empty);
            return;
        }

        AssignedApplicationRequest app = apps.get(idx);
        String text = app.getName() + " (" + app.getPart().name() + ")";

        CellStyle style = switch (app.getPart()) {
            case BACKEND -> backend;
            case FRONTEND -> frontend;
            case PRODUCT_DESIGN -> design;
        };

        create(row, col, text, style);
    }

}
