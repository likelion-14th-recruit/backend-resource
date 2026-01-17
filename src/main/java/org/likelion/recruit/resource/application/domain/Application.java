package org.likelion.recruit.resource.application.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.common.domain.BaseTimeEntity;
import org.likelion.recruit.resource.common.domain.Part;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Application extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @Column(nullable = false, updatable = false, unique = true, length = 39)
    private String publicId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String studentNumber;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String major;

    private String doubleMajor;

    @Column(nullable = false)
    private String semester;

    private boolean submitted;

    private LocalDateTime submittedAt;

    /**
     * 학적 상태
     */
    public enum AcademicStatus {
        ENROLLED,               // 재학
        ON_LEAVE,              // 휴학
        GRADUATION_DEFERRED,     // 졸업 유예
        GRADUATED               // 졸업
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "academic_status")
    private AcademicStatus academicStatus;

    /**
     * 파트(백,프,기디)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Part part;

    public enum PassStatus {
        PENDING,                // 검토
        DOCUMENT_FAILED,        // 1차 탈락
        DOCUMENT_PASSED,        // 1차 합격
        INTERVIEW_FAILED,       // 2차 탈락
        INTERVIEW_PASSED        // 2차 합격
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PassStatus passStatus;

    private Application(String name, String studentNumber, String phoneNumber, String passwordHash,
                       String major, String doubleMajor, String semester, boolean submitted,
                       LocalDateTime submittedAt, AcademicStatus academicStatus, Part part,
                       PassStatus passStatus) {
        this.publicId = "app" + UUID.randomUUID();
        this.name = name;
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.major = major;
        this.doubleMajor = doubleMajor;
        this.semester = semester;
        this.submitted = submitted;
        this.submittedAt = submittedAt;
        this.academicStatus = academicStatus;
        this.part = part;
        this.passStatus = passStatus;
    }

    public static Application create(String name, String studentNumber, String phoneNumber, String password,
                        String major, String doubleMajor, String semester, boolean submitted,
                        LocalDateTime submittedAt, AcademicStatus academicStatus, Part part){
        return new Application(name, studentNumber, phoneNumber, password, major, doubleMajor,
                semester, submitted, submittedAt, academicStatus, part, PassStatus.PENDING);
    }

}
