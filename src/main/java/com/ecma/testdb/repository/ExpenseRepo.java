package com.ecma.testdb.repository;

import com.ecma.testdb.entity.Expense;
import com.ecma.testdb.entity.Income;
import com.ecma.testdb.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExpenseRepo extends JpaRepository<Expense, UUID> {
    List<Expense> findAllByUsersAndFinished(Users users, boolean full);
}
