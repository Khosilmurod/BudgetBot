package com.ecma.testdb.telegramBot.service;

import com.ecma.testdb.entity.Expense;
import com.ecma.testdb.entity.Income;
import com.ecma.testdb.entity.Users;
import com.ecma.testdb.entity.enums.BotPage;
import com.ecma.testdb.repository.ExpenseRepo;
import com.ecma.testdb.repository.IncomeRepo;
import com.ecma.testdb.repository.UsersRepo;
import com.ecma.testdb.telegramBot.TelegramBot;
import com.ecma.testdb.telegramBot.util.InlineButton;
import com.ecma.testdb.telegramBot.util.ReplyMarkup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
public class Pages {
    @Autowired
    TelegramBot bot;
    @Autowired
    ReplyMarkup replyMarkup;
    @Autowired
    InlineButton inlineButton;
    @Autowired BotService botService;
    @Autowired
    IncomeRepo incomeRepo;
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    ExpenseRepo expenseRepo;

    //null page
    public void nullPage(String chatId){
        SendMessage sendMessage=new SendMessage(chatId, "Assalomu aleykum. Bizning botga xush kelibsiz");
        bot.executeMessage(sendMessage);

        SendMessage sendMessage1=new SendMessage(chatId, "Iltimos botdan to'liq foydalanish uchun ro'yxatdan o'ting");
        sendMessage1.setReplyMarkup(replyMarkup.contactButton());
        bot.executeMessage(sendMessage1);
    }

    //DEFAULT page
    public void defaultPage(String chatId){
        SendMessage sendMessage=new SendMessage(chatId, "iltimos ishlatishingiz uchun parol kiriting");
        bot.executeMessage(sendMessage);
    }

    //MAIN page
    public void mainPage(String chatId){
        SendMessage sendMessage=new SendMessage(chatId, "Assalomu aleykum xurmatli foydalanuvchi. Bu botda siz o'z sarf-xarajatlaringizni boshqarib borishingiz mumkin.");
        sendMessage.setReplyMarkup(replyMarkup.mainButton());
        bot.executeMessage(sendMessage);
    }

    //BALANCE page
    public void balancePage(String chatId){
        SendMessage sendMessage=new SendMessage(chatId, "Sizning hisobingiz "+incomeRepo.findBalance(usersRepo.findByTelegramId(chatId).getUuid())+" so'm");
        sendMessage.setReplyMarkup(inlineButton.toMain());
        bot.executeMessage(sendMessage);
    }

    //INCOME page
    public void incomePage(String chatId){
        List<Income> incomes = incomeRepo.findAllByUsersAndFinished(usersRepo.findByTelegramId(chatId), true);
        for (Income one : incomes) {
            String message="<b>foyda:</b> "+one.getForWhat()+" <b>narx:</b> "+one.getSum()+" <b>izoh:</b> "+one.getDescription();
            SendMessage sendMessage=new SendMessage(chatId, message);
//            sendMessage.setReplyMarkup(inlineButton.deleteExpenseIncome("Income/"+one.getUuid()));
            bot.executeMessage(sendMessage);
        }
        SendMessage sendMessage=new SendMessage(chatId, "<b><i>Foyda</i></b>");
        sendMessage.setReplyMarkup(inlineButton.toMain());
        bot.executeMessage(sendMessage);
    }

    //EXPENSE page
    public void expensePage(String chatId){
        List<Expense> expenses = expenseRepo.findAllByUsersAndFinished(usersRepo.findByTelegramId(chatId), true);
        for (Expense one : expenses) {
            String message="<b>foyda:</b> "+one.getFromWhat()+" <b>narx:</b> "+one.getSum()+" <b>izoh:</b> "+one.getDescription();
            SendMessage sendMessage=new SendMessage(chatId, message);
//            sendMessage.setReplyMarkup(inlineButton.deleteExpenseIncome("Expense/"+one.getUuid()));
            bot.executeMessage(sendMessage);
        }
        SendMessage sendMessage=new SendMessage(chatId, "<b><i>Xarajat</i></b>");
        sendMessage.setReplyMarkup(inlineButton.toMain());
        bot.executeMessage(sendMessage);
    }


    //NEW_INCOME page
    public void newIncomePage(String chatId){
        SendMessage sendMessage=new SendMessage(chatId, "Bosh sahifaga qaytish uchun 'Bosh sahifa' ni bosing. Yangi foyda qo'shish uchun 'Yangi Foyda' ni bosing");
        sendMessage.setReplyMarkup(inlineButton.newIncomePage());
        bot.executeMessage(sendMessage);
    }

    //NEW_INCOME_FOR page
    public void newIncomeForPage(String chatId){
        SendMessage sendMessage1=new SendMessage(chatId, "foyda: * narxi: * izoh:*");
        bot.executeMessage(sendMessage1);
        SendMessage sendMessage=new SendMessage(chatId, "Iltimos foydangizning manbasi yozing");
        bot.executeMessage(sendMessage);
    }

    //NEW_INCOME_SUM page
    public void newIncomeSumPage(String chatId){
        Income notFull = botService.getNotFull(chatId);

        SendMessage sendMessage1=new SendMessage(chatId, "foyda: "+notFull.getForWhat()+" narxi: * izoh:*");
        bot.executeMessage(sendMessage1);
        SendMessage sendMessage=new SendMessage(chatId, "Iltimos foydangizning narxini kiriting. Faqat sonlarda");
        bot.executeMessage(sendMessage);
    }

    //NEW_INCOME_DESC page
    public void newIncomeDescPage(String chatId){
        Income notFull = botService.getNotFull(chatId);

        SendMessage sendMessage1=new SendMessage(chatId, "foyda: "+notFull.getForWhat()+" narxi: "+notFull.getSum()+" izoh:*");
        bot.executeMessage(sendMessage1);
        SendMessage sendMessage=new SendMessage(chatId, "Qo'shimcha izoh");
        sendMessage.setReplyMarkup(inlineButton.toMainFromDesc("Income"));
        bot.executeMessage(sendMessage);
    }

    //NEW_EXPENSE page
    public void newExpensePage(String chatId){
        SendMessage sendMessage=new SendMessage(chatId, "Bosh sahifaga qaytish uchun 'Bosh sahifa' ni bosing. Yangi xarajat qo'shish uchun 'Yangi Xarajat' ni bosing");
        sendMessage.setReplyMarkup(inlineButton.newExpensePage());

        bot.executeMessage(sendMessage);
    }

    //NEW_INCOME_FOR page
    public void newExpenseFromPage(String chatId){
        SendMessage sendMessage1=new SendMessage(chatId, "xarajat: * narxi: * izoh:*");
        bot.executeMessage(sendMessage1);
        SendMessage sendMessage=new SendMessage(chatId, "Iltimos xarajatingizni yozing");
        bot.executeMessage(sendMessage);
    }

    //NEW_INCOME_SUM page
    public void newExpenseSumPage(String chatId){
        Expense notFull = botService.getExpenseNotFull(chatId);

        SendMessage sendMessage1=new SendMessage(chatId, "xarajat: "+notFull.getFromWhat()+" narxi: * izoh:*");
        bot.executeMessage(sendMessage1);
        SendMessage sendMessage=new SendMessage(chatId, "Iltimos xarajatingizni narxini kiriting. Faqat sonlarda");
        bot.executeMessage(sendMessage);
    }

    //NEW_INCOME_DESC page
    public void newExpenseDescPage(String chatId){
        Expense notFull = botService.getExpenseNotFull(chatId);

        SendMessage sendMessage1=new SendMessage(chatId, "xarajat: "+notFull.getFromWhat()+" narxi: "+notFull.getSum()+" izoh:*");
        bot.executeMessage(sendMessage1);
        SendMessage sendMessage=new SendMessage(chatId, "Qo'shimcha izoh");
        sendMessage.setReplyMarkup(inlineButton.toMainFromDesc("Expense"));
        bot.executeMessage(sendMessage);
    }


}
