package org.likelion.recruit.resource.verification.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.common.domain.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Verification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    private Integer code;

    private boolean verified;

    private Verification(String phoneNumber){
        this.phoneNumber = phoneNumber;
        this.verified = false;
    }

    public static Verification create(String phoneNumber){
        return new Verification(phoneNumber);
    }

    // 편의 메서드
    public Integer makeCode(){
        int code = randomNumber();
        this.code = code;

        return code;
    }

    public void verify(){
        this.verified = true;
    }

    private int randomNumber(){
        return (int)(Math.random() * 90000000) + 10000000;
    }

    public Integer renewCode(){
        Integer code = makeCode();
        this.verified = false;

        return code;
    }
}
