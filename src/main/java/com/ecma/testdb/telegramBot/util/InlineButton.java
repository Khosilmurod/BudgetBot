package com.ecma.testdb.telegramBot.util;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class InlineButton {
    public InlineKeyboardMarkup toMain(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> all = new ArrayList<>();

        List<InlineKeyboardButton> row1=  new ArrayList<>();
        InlineKeyboardButton row1button1 = new InlineKeyboardButton();
        row1button1.setText("Bosh sahifa");

        row1button1.setCallbackData("main");
        row1.add(row1button1);

        all.add(row1);

        inlineKeyboardMarkup.setKeyboard(all);

        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup newIncomePage(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> all = new ArrayList<>();

        List<InlineKeyboardButton> row1=  new ArrayList<>();
        InlineKeyboardButton row1button1 = new InlineKeyboardButton();
        InlineKeyboardButton row1button2 = new InlineKeyboardButton();

        row1button1.setText("Yangi Foyda");
        row1button2.setText("Bosh sahifa");

        row1button1.setCallbackData("newIncome");
        row1button2.setCallbackData("main");
        row1.add(row1button1);
        row1.add(row1button2);

        all.add(row1);

        inlineKeyboardMarkup.setKeyboard(all);

        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup newExpensePage(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> all = new ArrayList<>();

        List<InlineKeyboardButton> row1=  new ArrayList<>();
        InlineKeyboardButton row1button1 = new InlineKeyboardButton();
        InlineKeyboardButton row1button2 = new InlineKeyboardButton();

        row1button1.setText("Yangi Xarajat");
        row1button2.setText("Bosh sahifa");

        row1button1.setCallbackData("newExpense");
        row1button2.setCallbackData("main");
        row1.add(row1button1);
        row1.add(row1button2);

        all.add(row1);

        inlineKeyboardMarkup.setKeyboard(all);

        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup toMainFromDesc(String type){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> all = new ArrayList<>();

        List<InlineKeyboardButton> row1=  new ArrayList<>();
        InlineKeyboardButton row1button1 = new InlineKeyboardButton();
        row1button1.setText("izoh qo'shmaslik");
        row1button1.setCallbackData("toMainFrom"+type+"Desc");
        row1.add(row1button1);

        all.add(row1);

        inlineKeyboardMarkup.setKeyboard(all);

        return inlineKeyboardMarkup;
    }
    public InlineKeyboardMarkup deleteExpenseIncome(String type){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> all = new ArrayList<>();

        List<InlineKeyboardButton> row1=  new ArrayList<>();
        InlineKeyboardButton row1button1 = new InlineKeyboardButton();
        row1button1.setText("❌");
        row1button1.setCallbackData("delete"+type);
        row1.add(row1button1);

        all.add(row1);

        inlineKeyboardMarkup.setKeyboard(all);

        return inlineKeyboardMarkup;
    }


}
