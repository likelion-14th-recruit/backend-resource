package org.likelion.recruit.resource.verification.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VerificationTest {

    @Test
    void createVerification() {
        String phoneNumber = "01012345678";

        Verification verification = Verification.create(phoneNumber);

        assertThat(verification.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(verification.isVerified()).isFalse();
    }

    @Test
    void makeCode(){
        String phoneNumber = "01012345678";

        Verification verification = Verification.create(phoneNumber);

        verification.makeCode();
        Integer code = Integer.parseInt(verification.getCode());

        assertThat(verification.getCode()).isNotNull();
        assertThat(code).isBetween(10000000,99999999);
    }

    @Test
    void verify(){
        String phoneNumber = "01012345678";
        Verification verification = Verification.create(phoneNumber);
        verification.verify();

        assertThat(verification.isVerified()).isTrue();
    }

    @Test
    void renewCode(){
        String phoneNumber = "01012345678";
        Verification verification = Verification.create(phoneNumber);
        verification.renewCode();

        assertThat(verification.isVerified()).isFalse();
    }

}