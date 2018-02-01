package com.websocket.tester.websockettester.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OutputMessage {

    private Long id;
    private String from;
    private String text;
    private String time;

}
