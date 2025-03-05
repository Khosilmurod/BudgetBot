package com.ecma.testdb.telegramBot.util;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReplyMarkup {
    public ReplyKeyboardMarkup contactButton(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardButton row1button1 = new KeyboardButton();
        row1button1.setRequestContact(true);
        row1button1.setText("Ro'yxatdan o'ting");
        row1.add(row1button1);
        rows.add(row1);
        return replyKeyboardMarkup.setKeyboard(rows);
    }

    public ReplyKeyboardMarkup mainButton(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        List<KeyboardRow> rows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();

        KeyboardButton row1button1 = new KeyboardButton();
        KeyboardButton row1button2 = new KeyboardButton();
        KeyboardButton row1button3 = new KeyboardButton();
        KeyboardButton row2button1 = new KeyboardButton();
        KeyboardButton row2button2 = new KeyboardButton();
        row1button1.setText("foyda");
        row1button2.setText("balance");
        row1button3.setText("xarajat");
        row2button1.setText("foyda qo'shish");
        row2button2.setText("xarajat qo'shish");
        row1.add(row1button1);
        row1.add(row1button2);
        row1.add(row1button3);
        row2.add(row2button1);
        row2.add(row2button2);
        rows.add(row1);
        rows.add(row2);
        return replyKeyboardMarkup.setKeyboard(rows);
    }
}
