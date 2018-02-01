package com.websocket.tester.websockettester.models.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ControllerResponse {

    private String message;
    private String messageType;
    private Object data;

}
