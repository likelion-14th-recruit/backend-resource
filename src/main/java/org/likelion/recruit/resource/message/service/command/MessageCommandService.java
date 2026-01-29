package org.likelion.recruit.resource.message.service.command;

import com.solapi.sdk.SolapiClient;
import com.solapi.sdk.message.model.Message;
import com.solapi.sdk.message.service.DefaultMessageService;
import jakarta.annotation.PostConstruct;
import org.likelion.recruit.resource.application.dto.query.DocumentPassedMessageTarget;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
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

    public void sendVerificationMessage(String phoneNumber, Integer code) {

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

    public void sendDocumentPassedMessages(List<DocumentPassedMessageTarget> targets){

        for(DocumentPassedMessageTarget target : targets){
            Message msg = new Message();
            msg.setFrom(senderNumber);
            msg.setTo(target.getPhoneNumber());
            msg.setText("[멋쟁이사자처럼 서강대학교 14기 서류전형 결과 안내]\n\n" +
                    "안녕하세요, 멋쟁이사자처럼 서강대학교 14기 운영진입니다.\n" +
                    "저희 14기 모집에 관심을 가지고 소중한 지원서를 제출해 주셔서 진심으로 감사드립니다.\n\n" +
                    "축하드립니다! " + target.getName() + "님은 멋쟁이사자처럼 서강대학교 14기 서류 전형에 합격하셨음을 알려드립니다. 이하 최종 면접 과정에 대한 안내드리겠습니다.\n\n" +
                    "1. 면접 일정 및 장소\n" +
                    "- 면접 일시 : " + target.getDate().getMonthValue() + "월 " + target.getDate().getDayOfMonth() +"일 " + target.getStartTime().format(DateTimeFormatter.ofPattern("H:mm")) + "-" + target.getEndTime().format(DateTimeFormatter.ofPattern("H:mm")) + "\n" +
                    "- 면접 장소 : " + target.getPlace() + "\n\n" +
                    "2. 안내 사항\n" +
                    "- 면접은 지원자 두 분이 함께 약 20분간 진행하며, 공정한 평가를 위해 녹화될 예정입니다. (영상은 선발 종료 후 즉시 폐기됩니다.)\n" +
                    "- 안내 사항 전달을 위해 면접 15분 전까지 도착해 주시기 바랍니다.\n" +
                    "- 최종 결과는 3월 14일 문자로 안내해 드리겠습니다. (추후 변동 될 수 있습니다.)\n\n" +
                    "3. 문의 사항\n" +
                    "- 대표 : 010-6264-7243\n" +
                    "- 부대표 : 010-9338-5848, 010-3076-2799\n" +
                    "- 인스타그램 : @likelion_sg\n\n" +
                    "다시 한번 축하드리며, 면접에서 좋은 결과 있으시길 응원하겠습니다.\n" +
                    "멋쟁이사자처럼 서강대학교 14기 운영진 드림"
            );

            try {
                messageService.send(msg);
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.SMS_SEND_FAILED);
            }
        }
    }

    public void sendDocumentFailedMessages(List<String> phoneNumbers) {

        for (String phoneNumber : phoneNumbers) {
            Message msg = new Message();
            msg.setFrom(senderNumber);
            msg.setTo(phoneNumber);
            msg.setText("[멋쟁이사자처럼 서강대학교 14기 서류전형 결과 안내]\n\n" +
                    "안녕하세요, 멋쟁이사자처럼 서강대학교 14기 운영진입니다.\n" +
                    "저희 14기 모집에 관심을 가지고 소중한 지원서를 제출해 주셔서 진심으로 감사드립니다.\n\n" +
                    "보내주신 지원서를 신중히 검토하였으나, 한정된 인원으로 인해 아쉽게도 이번 14기 과정에서는 함께하지 못하게 되었다는 소식을 전해드립니다.\n" +
                    "모든 지원자분과 함께하지 못해 운영진 또한 매우 아쉬운 마음입니다. 비록 이번에는 연이 닿지 않았지만, 지원자님이 보여주신 열정을 진심으로 응원하겠습니다.\n\n" +
                    "다시 한번 지원해 주셔서 감사합니다.\n" +
                    "멋쟁이사자처럼 서강대학교 14기 운영진 드림"
            );

            try {
                messageService.send(msg);
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.SMS_SEND_FAILED);
            }
        }
    }
}