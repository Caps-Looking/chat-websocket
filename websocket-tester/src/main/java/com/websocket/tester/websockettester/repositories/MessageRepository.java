package com.websocket.tester.websockettester.repositories;

import com.websocket.tester.websockettester.models.entities.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findTop10ByChatIdOrderByIdDesc(String id);

}
