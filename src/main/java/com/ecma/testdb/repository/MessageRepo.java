package com.ecma.testdb.repository;

import com.ecma.testdb.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MessageRepo extends JpaRepository<Message, UUID> {
    List<Message> findAllByTelegramId(String telegramId);
    List<Message> deleteAllByTelegramId(String telegramId);

}
