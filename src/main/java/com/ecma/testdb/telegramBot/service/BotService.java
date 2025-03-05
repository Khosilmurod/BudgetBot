package com.ecma.testdb.telegramBot.service;


import com.ecma.testdb.entity.*;
import com.ecma.testdb.entity.enums.BotPage;
import com.ecma.testdb.entity.enums.RoleName;
import com.ecma.testdb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class BotService {
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    RoleRepo roleRepo;

    @Autowired
    MessageRepo messageRepo;
    @Autowired
    IncomeRepo incomeRepo;
    @Autowired
    ExpenseRepo expenseRepo;

    //user
    public BotPage getUserPage(String telegramId){
       return usersRepo.findByTelegramId(telegramId).getBotPage();
    }
    public void register(Update update){
        Users user = usersRepo.findByTelegramId(update.getMessage().getChatId().toString());
        if(user==null){
            User tUser=update.getMessage().getFrom();
            usersRepo.save(new Users(tUser.getFirstName(), tUser.getLastName(), tUser.getUserName(), update.getMessage().getChatId().toString(), roleRepo.saveAll(new ArrayList(Arrays.asList(new Role(2,RoleName.ROLE_USER))))));
        }
    }
    public void registerNumber(String telegramId, Contact contact){
        Users user = usersRepo.findByTelegramId(telegramId);
        if(user!=null){
            user.setPhoneNumber(contact.getPhoneNumber());
            user.setBotPage(BotPage.DEFAULT);
            usersRepo.save(user);
        }
    }
    public void registerPassword(String telegramId, String password){
        Users user = usersRepo.findByTelegramId(telegramId);
        if(user!=null){
            user.setPassword(password);
            user.setBotPage(BotPage.MAIN);
            usersRepo.save(user);
        }
    }
    public void changePage(BotPage botPage, String telegramId){
        Users user = usersRepo.findByTelegramId(telegramId);
        user.setBotPage(botPage);
        usersRepo.save(user);
    }

    //messages
    public void saveMessage(String telegramId, String text, Integer messageId){
        messageRepo.save(new Message(text, telegramId, messageId));
    }

    //income
    public Income getNotFull(String telegramId){
        Users user = usersRepo.findByTelegramId(telegramId);
        return incomeRepo.findAllByUsersAndFinished(user, false).get(0);
    }

    public void saveIncomeFor(String telegramId, String text){
        Income income=new Income();
        income.setForWhat(text);
        income.setFinished(false);
        income.setUsers(usersRepo.findByTelegramId(telegramId));
        incomeRepo.save(income);
    }
    public void saveIncomeSum(String telegramId, Double sum){
        Income income = getNotFull(telegramId);
        income.setSum(sum);
        incomeRepo.save(income);
    }
    public void saveIncomeDesc(String telegramId, String desc){
        Income income = getNotFull(telegramId);
        income.setDescription(desc);
        income.setFinished(true);
        incomeRepo.save(income);
    }

    //expense
    public Expense getExpenseNotFull(String telegramId){
        Users user = usersRepo.findByTelegramId(telegramId);
        return expenseRepo.findAllByUsersAndFinished(user, false).get(0);
    }

    public void saveExpenseFrom(String telegramId, String text){
        Expense expense=new Expense();
        expense.setFromWhat(text);
        expense.setFinished(false);
        expense.setUsers(usersRepo.findByTelegramId(telegramId));
        expenseRepo.save(expense);
    }
    public void saveExpenseSum(String telegramId, Double sum){
        Expense expense = getExpenseNotFull(telegramId);
        expense.setSum(sum);
        expenseRepo.save(expense);
    }
    public void saveExpenseDesc(String telegramId, String desc){
        Expense expense = getExpenseNotFull(telegramId);
        expense.setDescription(desc);
        expense.setFinished(true);
        expenseRepo.save(expense);
    }

    //problem
    public void deleteIncome(String uuid){
        UUID uuid1 = UUID.fromString(uuid);
        incomeRepo.deleteById(uuid1);
    }
    public void deleteExpense(String uuid){
        expenseRepo.deleteById(java.util.UUID.fromString(uuid));
    }
}
