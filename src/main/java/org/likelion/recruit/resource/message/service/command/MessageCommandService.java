package org.likelion.recruit.resource.message.service.command;

import com.solapi.sdk.SolapiClient;
import com.solapi.sdk.message.model.Message;
import com.solapi.sdk.message.service.DefaultMessageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.likelion.recruit.resource.application.dto.query.DocumentPassedMessageTarget;
import org.likelion.recruit.resource.application.dto.query.PassedMessageTarget;
import org.likelion.recruit.resource.application.dto.query.RejectedMessageTarget;
import org.likelion.recruit.resource.common.domain.Part;
import org.likelion.recruit.resource.common.exception.BusinessException;
import org.likelion.recruit.resource.common.exception.ErrorCode;
import org.likelion.recruit.resource.message.domain.MessageLog;
import org.likelion.recruit.resource.message.domain.MessageLog.MessageStatus;
import org.likelion.recruit.resource.message.domain.MessageLog.MessageType;
import org.likelion.recruit.resource.message.repository.MessageLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageCommandService {

    private DefaultMessageService messageService;
    private final MessageLogRepository messageLogRepository;

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

        // 메시지 로그 생성
        MessageLog messageLog = MessageLog.create(MessageType.VERIFICATION, senderNumber, phoneNumber);

        try {
            messageService.send(msg);
            messageLog.send();
        } catch (Exception e) {
            messageLog.sendFailed();
        } finally {
            messageLogRepository.save(messageLog);
        }

        if (messageLog.getMessageStatus() == MessageStatus.FAILED) {
            throw new BusinessException(ErrorCode.SMS_SEND_FAILED);
        }
    }

    public void sendDocumentPassedMessages(List<DocumentPassedMessageTarget> targets){

        for(DocumentPassedMessageTarget target : targets){
            Message msg = new Message();
            msg.setFrom(senderNumber);
            msg.setTo(target.getPhoneNumber());
            msg.setText("[멋쟁이사자처럼 서강대학교 14기 서류전형 결과 안내]\n\n" +
                    "안녕하세요 " + target.getName() + "님, 멋쟁이사자처럼 서강대학교 14기 운영진입니다.\n" +
                    "저희 14기 모집에 관심을 가지고 소중한 지원서를 제출해 주셔서 진심으로 감사드립니다.\n\n" +
                    "축하드립니다! 멋쟁이사자처럼 서강대학교 14기 서류 전형에 합격하셨음을 알려드립니다. 이하 최종 면접 과정에 대한 안내드리겠습니다.\n\n" +
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

            MessageLog messageLog =
                    MessageLog.create(MessageType.DOCUMENT_PASSED, senderNumber, target.getPhoneNumber());

            try {
                messageService.send(msg);
                messageLog.send();
            } catch (Exception e) {
                messageLog.sendFailed();
            }
            finally {
                messageLogRepository.save(messageLog);
            }
        }
    }

    public void sendDocumentFailedMessages(List<RejectedMessageTarget> targets) {

        for (RejectedMessageTarget target : targets) {
            Message msg = new Message();
            msg.setFrom(senderNumber);
            msg.setTo(target.getPhoneNumber());
            msg.setText("[멋쟁이사자처럼 서강대학교 14기 면접 결과 안내]\n\n" +
                    "안녕하세요" + target.getName() +"님, 멋쟁이사자처럼 서강대학교 14기 운영진입니다.\n" +
                    "저희 14기 모집에 관심을 가지고 소중한 지원서를 제출해 주셔서 진심으로 감사드립니다.\n\n" +
                    "보내주신 지원서를 신중히 검토하였으나, 한정된 인원으로 인해 아쉽게도 이번 14기 과정에서는 함께하지 못하게 되었다는 소식을 전해드립니다.\n" +
                    "모든 지원자분과 함께하지 못해 운영진 또한 매우 아쉬운 마음입니다. 비록 이번에는 연이 닿지 않았지만, 지원자님이 보여주신 열정을 진심으로 응원하겠습니다.\n\n" +
                    "다시 한번 지원해 주셔서 감사합니다.\n" +
                    "멋쟁이사자처럼 서강대학교 14기 운영진 드림"
            );

            MessageLog messageLog =
                    MessageLog.create(MessageType.DOCUMENT_FAILED, senderNumber, target.getPhoneNumber());

            try {
                messageService.send(msg);
                messageLog.send();
            } catch (Exception e) {
                messageLog.sendFailed();
            }
            finally {
                messageLogRepository.save(messageLog);
            }
        }
    }

    public void sendInterviewPassedMessages(List<PassedMessageTarget> targets) {

        for (PassedMessageTarget target : targets) {
            String part = ConvertType(target.getPart());
            Message msg = new Message();
            msg.setFrom(senderNumber);
            msg.setTo(target.getPhoneNumber());
            msg.setText("[멋쟁이사자처럼 서강대학교 14기 면접 결과 안내]\n\n" +
                    "안녕하세요" + target.getName() +"님, 멋쟁이사자처럼 서강대학교 14기 운영진입니다.\n" +
                    "축하드립니다! 멋쟁이사자처럼 서강대학교 14기 " + part + " 파트 최종 합격하셨음을 알려드립니다.\n" +
                    "14기 아기사자 활동을 시작하기 앞서 첫 번째 공지사항을 전달드립니다.\n\n" +
                    "1. 정규 세션 시간\n" +
                    "- 일시 : 매주 월요일 / 수요일 19:00 - 21:00\n\n" +
                    "2. 오리엔테이션(OT) 일정\n" +
                    "- 일시 : 3월 16일 (월) 19:00시\n" +
                    "- 장소 : 공덕 ICT COC - 마포 프론트원 6층\n\n" +
                    "다시 한번 멋쟁이사자처럼 서강대학교 14기의 일원이 되신 것을 환영합니다! 지원서와 면접에서 보여주신 열정을 이제는 동료들과 함께 실제 서비스로 구현하며 멋진 성장을 이뤄나가시길 기대하겠습니다.\n" +
                    "다가오는 OT에서 설레는 마음으로 뵙겠습니다. 감사합니다!\n" +
                    "멋쟁이사자처럼 서강대학교 14기 운영진 드림"
            );

            MessageLog messageLog =
                    MessageLog.create(MessageType.INTERVIEW_PASSED, senderNumber, target.getPhoneNumber());

            try {
                messageService.send(msg);
                messageLog.send();
            } catch (Exception e) {
                messageLog.sendFailed();
            }
            finally {
                messageLogRepository.save(messageLog);
            }
        }
    }

    public void sendInterviewFailedMessages(List<RejectedMessageTarget> targets) {

        for (RejectedMessageTarget target : targets) {
            Message msg = new Message();
            msg.setFrom(senderNumber);
            msg.setTo(target.getPhoneNumber());
            msg.setText("[멋쟁이사자처럼 서강대학교 14기 최종 결과 안내]\n\n" +
                    "안녕하세요" + target.getName() +"님, 멋쟁이사자처럼 서강대학교 14기 운영진입니다.\n" +
                    "바쁜 일정 중에도 시간을 내어 최종 면접에 참석해 주셔서 진심으로 감사드립니다.\n\n" +
                    "면접을 통해 지원자님의 열정을 직접 느낄 수 있어 운영진에게도 매우 뜻깊은 시간이었습니다. 다만, 한정된 선발 인원으로 인해 아쉽게도 이번 14기 과정에서는 최종적으로 함께하지 못하게 되었습니다.\n" +
                    "모든 분과 함께하지 못해 운영진 또한 아쉬운 마음이 큽니다. 비록 이번에는 연이 닿지 않았지만, 지원자님이 보여주신 뜨거운 도전을 진심으로 응원하겠습니다.\n\n" +
                    "다시 한번 지원해 주셔서 감사합니다.\n" +
                    "멋쟁이사자처럼 서강대학교 14기 운영진 드림"
            );

            MessageLog messageLog =
                    MessageLog.create(MessageType.INTERVIEW_FAILED, senderNumber, target.getPhoneNumber());

            try {
                messageService.send(msg);
                messageLog.send();
            } catch (Exception e) {
                messageLog.sendFailed();
            }
            finally {
                messageLogRepository.save(messageLog);
            }
        }
    }

    private String ConvertType(Part part) {
        if(part == Part.PRODUCT_DESIGN) {
            return "기획&디자인";
        }
        if(part == Part.BACKEND) {
            return "백엔드";
        }
        return "프론트엔드";
    }
}