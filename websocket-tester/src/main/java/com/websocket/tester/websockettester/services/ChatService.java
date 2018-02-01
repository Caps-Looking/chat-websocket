package com.websocket.tester.websockettester.services;

import com.websocket.tester.websockettester.models.dtos.MessageDTO;
import com.websocket.tester.websockettester.models.entities.Chat;
import com.websocket.tester.websockettester.models.entities.Message;
import com.websocket.tester.websockettester.repositories.ApplicationUserRepository;
import com.websocket.tester.websockettester.repositories.ChatRepository;
import com.websocket.tester.websockettester.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    /**
     * Verify if the Chat already exists,
     * if does, increment the number of messages,
     * if doesn't, create a new Chat
     *
     * @param id of the Chat to be verified
     */
    public void verifyChatExistency(String id) {
        Chat chatDB = chatRepository.findOne(id);
        if (chatDB == null) {
            Chat chat = new Chat();
            chat.setId(id);
            chat.setMessages(1);
            chat.setMessageList(new ArrayList<>());
            chatRepository.save(chat);
        } else {
            chatDB.setMessages(chatDB.getMessages() + 1);
            chatRepository.save(chatDB);
        }
    }

    /**
     * Saves the message
     *
     * @param messageDTO to be saved (without time)
     * @return the message with time
     */
    public Message saveMessage(MessageDTO messageDTO) {
        Message message = new Message();
        message.setTime(new SimpleDateFormat("HH:mm").format(new Date()));
        message.setText(messageDTO.getText());
        message.setApplicationUser(applicationUserRepository.findOne(messageDTO.getApplicationUser()));
        message.setChat(chatRepository.findOne(messageDTO.getChat()));
        message.setChatAll(messageDTO.isChatAll());

        return messageRepository.save(message);
    }

    /**
     * Get the 10 latest messages
     *
     * @param id of the Chat to be searched
     * @return the 10 latest messages of the Chat
     */
    public List<Message> getLatestMessages(String id) {
        return messageRepository.findTop10ByChatIdOrderByIdDesc(id);
    }
}
