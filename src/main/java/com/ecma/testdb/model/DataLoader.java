package com.ecma.testdb.model;

import com.ecma.testdb.entity.Role;
import com.ecma.testdb.entity.enums.RoleName;
import com.ecma.testdb.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    RoleRepo roleRepo;

    @Value("${spring.datasource.initialization-mode}")
    private String initialMode;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            roleRepo.saveAll(
                    new ArrayList<>(Arrays.asList(
                            new Role(1, RoleName.ROLE_ADMIN),
                            new Role(2, RoleName.ROLE_USER)
                    )));
        }
    }
}
