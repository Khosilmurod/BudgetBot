package com.ecma.testdb.repository;

import java.util.List;
import com.ecma.testdb.entity.Income;
import com.ecma.testdb.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface IncomeRepo extends JpaRepository<Income, UUID> {
    List<Income> findAllByUsersAndFinished(Users users, boolean full);
    @Query(value = "select ((select coalesce(sum(sum),0) from income i where i.users_uuid=?1)-(select coalesce(sum(sum),0) from expense e where e.users_uuid=?1)) as balance", nativeQuery = true)
    Double findBalance(UUID userId);
}
