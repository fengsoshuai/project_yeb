package org.feng.server.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 发送消息聊天
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMessage {
    private String from;
    private String to;
    private String content;
    private LocalDateTime date;
    private String fromNickName;
}
