package com.ecma.testdb.telegramBot;

import com.ecma.testdb.entity.enums.BotPage;
import com.ecma.testdb.repository.MessageRepo;
import com.ecma.testdb.telegramBot.service.BotService;
import com.ecma.testdb.telegramBot.service.Pages;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    Pages pages;
    @Autowired
    BotService botService;

    @Autowired
    MessageRepo messageRepo;


    @Override
    synchronized public void onUpdateReceived(Update update) {
        String chatId=null;
        String text="";
        //register
        if(update.hasMessage()){
            Message message=update.getMessage();
            chatId=message.getChatId().toString();
            text=message.getText();

            if(message.hasText()){
                if(message.getText().equals("/start")){
                    botService.register(update);
                }
            }
        }

        //return to page
        if(update.hasMessage()){
            Message message=update.getMessage();
            BotPage userPage = botService.getUserPage(message.getChatId().toString());
            if(message.hasText()){
                boolean main = (userPage == BotPage.INCOME ||userPage == BotPage.EXPENSE || userPage == BotPage.BALANCE || userPage == BotPage.MAIN);
                //inputs
                if(userPage==BotPage.DEFAULT){
                    botService.registerPassword(chatId, text);
                }
                if(userPage==BotPage.NEW_INCOME_FOR){
                    botService.saveIncomeFor(chatId, text);
                    botService.changePage(BotPage.NEW_INCOME_SUM, chatId);

                }
                else if(userPage==BotPage.NEW_INCOME_SUM){
                    Double sum = null;
                    try {
                        sum = Double.valueOf(text);
                        botService.saveIncomeSum(chatId,sum);
                        botService.changePage(BotPage.NEW_INCOME_DESCRIPTION, chatId);
                    } catch (NumberFormatException e) {
                        SendMessage sendMessage=new SendMessage(chatId, "iltimos son kiriting");
                        executeMessage(sendMessage);
                    }
                }
                else if(userPage==BotPage.NEW_INCOME_DESCRIPTION){
                    botService.saveIncomeDesc(chatId, text);
                    botService.changePage(BotPage.MAIN, chatId);

                }


                if(userPage==BotPage.NEW_EXPENSE_FROM){
                    botService.saveExpenseFrom(chatId, text);
                    botService.changePage(BotPage.NEW_EXPENSE_SUM, chatId);

                }
                else if(userPage==BotPage.NEW_EXPENSE_SUM){
                    Double sum = null;
                    try {
                        sum = Double.valueOf(text);
                        botService.saveExpenseSum(chatId,sum);
                        botService.changePage(BotPage.NEW_EXPENSE_DESCRIPTION, chatId);
                    } catch (NumberFormatException e) {
                        SendMessage sendMessage=new SendMessage(chatId, "iltimos son kiriting");
                        executeMessage(sendMessage);
                    }
                }
                else if(userPage==BotPage.NEW_EXPENSE_DESCRIPTION){
                    botService.saveExpenseDesc(chatId, text);
                    botService.changePage(BotPage.MAIN, chatId);
                }
                //
                if(main&&message.getText().equals("balance")){
                    botService.changePage(BotPage.BALANCE, chatId);
                }
                if(main&&message.getText().equals("foyda")){
                    botService.changePage(BotPage.INCOME, chatId);
                }
                if(main&&message.getText().equals("xarajat")){
                    botService.changePage(BotPage.EXPENSE, chatId);
                }
                if(main&&message.getText().equals("foyda qo'shish")){
                    botService.changePage(BotPage.NEW_INCOME, chatId);
                }
                if(main&&message.getText().equals("xarajat qo'shish")){
                    botService.changePage(BotPage.NEW_EXPENSE, chatId);
                }
            }
            if(message.hasContact()){
                if(userPage==null){
                    botService.registerNumber(message.getChatId().toString(), message.getContact());
                }
            }

            botService.saveMessage(chatId, text, message.getMessageId());
            deleteAllMessages(chatId);
        }

        if(update.hasCallbackQuery()){
            CallbackQuery callback=update.getCallbackQuery();
            chatId=callback.getMessage().getChatId().toString();
            BotPage userPage = botService.getUserPage(callback.getMessage().getChatId().toString());


            if(callback.getData().equals("main")){
                botService.changePage(BotPage.MAIN, callback.getMessage().getChatId().toString());
            }
            if(callback.getData().equals("newIncome")){
                botService.changePage(BotPage.NEW_INCOME_FOR, callback.getMessage().getChatId().toString());
            }
            if(callback.getData().equals("newExpense")){
                botService.changePage(BotPage.NEW_EXPENSE_FROM, callback.getMessage().getChatId().toString());
            }
            if(callback.getData().equals("toMainFromIncomeDesc")){
                botService.saveIncomeDesc(chatId, text);
                botService.changePage(BotPage.MAIN, chatId);
            }
            if(callback.getData().equals("toMainFromExpenseDesc")){
                botService.saveExpenseDesc(chatId, text);
                botService.changePage(BotPage.MAIN, chatId);
            }
            if(callback.getData().contains("deleteIncome/")){
                int i = callback.getData().indexOf('/');
                String uuid = callback.getData().substring(i+1, callback.getData().length() - 1);
                botService.deleteIncome(uuid);
            }
            if(callback.getData().contains("deleteExpense/")){
                int i = callback.getData().indexOf('/');
                String uuid = callback.getData().substring(i+1, callback.getData().length() - 1);
                botService.deleteExpense(uuid);
            }
            deleteAllMessages(chatId);
        }

        //pages
        if(update.hasMessage()||update.hasCallbackQuery()){
            BotPage userPage = botService.getUserPage(chatId);
            if(userPage==null){
                pages.nullPage(chatId);
            }
            if(userPage==BotPage.DEFAULT){
                pages.defaultPage(chatId);
            }
            if(userPage==BotPage.MAIN){
                pages.mainPage(chatId);
            }
            if(userPage==BotPage.BALANCE){
                pages.balancePage(chatId);
            }
            if(userPage==BotPage.INCOME){
                pages.incomePage(chatId);
            }
            if(userPage==BotPage.EXPENSE){
                pages.expensePage(chatId);
            }
            if(userPage==BotPage.NEW_INCOME){
                pages.newIncomePage(chatId);
            }
            if(userPage==BotPage.NEW_INCOME_FOR){
                pages.newIncomeForPage(chatId);
            }
            if(userPage==BotPage.NEW_INCOME_SUM){
                pages.newIncomeSumPage(chatId);
            }
            if(userPage==BotPage.NEW_INCOME_DESCRIPTION){
                pages.newIncomeDescPage(chatId);
            }
            if(userPage==BotPage.NEW_EXPENSE){
                pages.newExpensePage(chatId);
            }
            if(userPage==BotPage.NEW_EXPENSE_FROM){
                pages.newExpenseFromPage(chatId);
            }
            if(userPage==BotPage.NEW_EXPENSE_SUM){
                pages.newExpenseSumPage(chatId);
            }
            if(userPage==BotPage.NEW_EXPENSE_DESCRIPTION){
                pages.newExpenseDescPage(chatId);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "t.me/foyda_xarajat_bot";
    }

    @Override
    public String getBotToken() {
        return "secretKeyGivenByTelegram!";
    }

    //services
    public boolean executeMessage(SendMessage incomingMessage){
        try {
            incomingMessage.setParseMode(ParseMode.HTML);
            Message execute = execute(incomingMessage);
            botService.saveMessage(execute.getChatId().toString(), execute.getText(), execute.getMessageId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public void deleteAllMessages(String chatId){
        List<com.ecma.testdb.entity.Message> messages = messageRepo.findAllByTelegramId(chatId);
        for (com.ecma.testdb.entity.Message message : messages) {
                try {
                    messageRepo.delete(message);
                    DeleteMessage deleteMessage=new DeleteMessage(message.getTelegramId(), message.getMessageId());
                    execute(deleteMessage);
                } catch (TelegramApiException e) {
                    System.out.println("already deleted");
                }
        }
    }

}
