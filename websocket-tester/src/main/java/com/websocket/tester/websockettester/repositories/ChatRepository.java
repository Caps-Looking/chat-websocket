package com.websocket.tester.websockettester.repositories;

import com.websocket.tester.websockettester.models.entities.Chat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends CrudRepository<Chat, String> {
}
