package org.likelion.recruit.resource.common.util;

public class PhoneNumberUtils {

    public static String normalize(String phoneNumber) {
        return phoneNumber.replaceAll("[^0-9]", "");
    }
}
