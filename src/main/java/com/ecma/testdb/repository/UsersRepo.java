package com.ecma.testdb.repository;


import com.ecma.testdb.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsersRepo extends JpaRepository<Users, UUID> {
    Users findByUsername(String username);
    Users findByTelegramId(String telegramId);
}
