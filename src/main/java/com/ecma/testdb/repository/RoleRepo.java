package com.ecma.testdb.repository;


import com.ecma.testdb.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, Integer> {
}
