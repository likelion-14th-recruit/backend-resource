package org.likelion.recruit.resource.message.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.likelion.recruit.resource.common.domain.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public enum MessageType {
        VERIFICATION,           // 인증
        DOCUMENT_PASSED,        // 서류 통과
        DOCUMENT_FAILED,        // 서류 탈락
        INTERVIEW_PASSED,       // 면접 통과
        INTERVIEW_FAILED,       // 면접 탈락
    }

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    public enum MessageStatus {
        PENDING,                // 대기
        SENT,                   // 전송 완료
        FAILED                  // 전송 실패
    }

    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;

    @Column(nullable = false, name = "sender_phone_number")
    private String sender;

    @Column(nullable = false, name = "receiver_phone_number")
    private String receiver;

    // 생성 메서드
    private MessageLog(MessageType messageType, MessageStatus messageStatus, String sender, String receiver) {
        this.messageType = messageType;
        this.messageStatus = messageStatus;
        this.sender = sender;
        this.receiver = receiver;
    }

    public static MessageLog create(MessageType messageType, String sender, String receiver) {
        return new MessageLog(messageType, MessageStatus.PENDING, sender, receiver);
    }

    // 비즈니스 로직
    public void send() {
        if (this.messageStatus != MessageStatus.PENDING) {
            return;
        }
        messageStatus = MessageStatus.SENT;
    }

    public void sendFailed() {
        if (this.messageStatus != MessageStatus.PENDING) {
            return;
        }
        messageStatus = MessageStatus.FAILED;
    }

}
