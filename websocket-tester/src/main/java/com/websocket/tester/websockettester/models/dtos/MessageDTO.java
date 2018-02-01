package com.websocket.tester.websockettester.models.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class MessageDTO {

    private Long applicationUser;

    private String text;

    private String time;

    private String chat;

    private boolean chatAll;
}
