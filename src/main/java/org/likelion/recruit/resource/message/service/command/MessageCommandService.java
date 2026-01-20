package org.likelion.recruit.resource.message.service.command;

import com.solapi.sdk.SolapiClient;
import com.solapi.sdk.message.model.Message;
import com.solapi.sdk.message.service.DefaultMessageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageCommandService {

    private DefaultMessageService messageService;

    @Value("${solapi.sender}")
    private String senderNumber;

    @Value("${solapi.api-key}")
    private String apiKey;

    @Value("${solapi.api-secret}")
    private String apiSecret;

    @PostConstruct
    public void initSolapi() {
        this.messageService = SolapiClient.INSTANCE.createInstance(apiKey, apiSecret);
    }

    public void sendMessage(String phoneNumber, String code) {

            Message msg = new Message();
            msg.setFrom(senderNumber);
            msg.setTo(phoneNumber);
            msg.setText("[서강대학교 멋쟁이사자처럼] 본인확인 인증 번호는 [" + code + "] 입니다.");

            try {
                messageService.send(msg);
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.SMS_SEND_FAILED);
            }
    }
}