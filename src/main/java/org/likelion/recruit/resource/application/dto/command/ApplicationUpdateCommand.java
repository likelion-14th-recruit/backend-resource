package org.likelion.recruit.resource.application.dto.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.likelion.recruit.resource.application.domain.Application;
import org.likelion.recruit.resource.application.domain.Application.AcademicStatus;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;

import java.util.Map;

@Getter
@AllArgsConstructor
public class ApplicationUpdateCommand {
    private String name;
    private String studentNumber;
    private String major;

    private Boolean hasDoubleMajor;
    private String doubleMajor;

    private AcademicStatus academicStatus;
    private Integer semester;
    private Part part;

    public static ApplicationUpdateCommand from(Map<String, Object> body) {

        //string
        String name = body.containsKey("name") ? toString(body, "name") : null;
        String studentNumber = body.containsKey("studentNumber") ? toString(body, "studentNumber") : null;
        String major = body.containsKey("major") ? toString(body, "major") : null;

        //nullable string
        Boolean hasDoubleMajor = body.containsKey("doubleMajor");
        String doubleMajor = null;
        if(hasDoubleMajor) {
            Object value = body.get("doubleMajor");
            if (value != null) {
                doubleMajor = value.toString();
            }
        }

        //enum
        AcademicStatus academicStatus = null;
        try {
            academicStatus = body.containsKey("academicStatus")
                    ? Enum.valueOf(AcademicStatus.class, toString(body, "academicStatus"))
                    : null;
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST_FORMAT);
        }

        Part part = null;
        try {
            part = body.containsKey("part")
                    ? Enum.valueOf(Part.class, toString(body, "part"))
                    : null;
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST_FORMAT);
        }

        //integer
        Integer semester = body.containsKey("semester") ? Integer.parseInt(toString(body, "semester")) : null;

        return new ApplicationUpdateCommand(name,studentNumber,major,hasDoubleMajor,doubleMajor,academicStatus,semester,part);
    }

    private static String toString(Map<String, Object> body, String key) {
        Object value = body.get(key);
        if (value == null) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST_FORMAT);
        }
        return value.toString();
    }

    Integer toInteger(Map<String, Object> body, String key) {
        Object value = body.get(key);
        if (value == null) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST_FORMAT);
        }
        return Integer.parseInt(value.toString());
    }
}
