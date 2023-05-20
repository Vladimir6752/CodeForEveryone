package dev.vladimir.cfemain.loggerbot;

import org.apache.commons.io.input.CharSequenceInputStream;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.charset.StandardCharsets;

public final class LoggerBot extends TelegramLongPollingBot {
    private static final String ADMIN_CHAT_ID = "815538639";

    public LoggerBot(String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {}

    public void log(String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(ADMIN_CHAT_ID);
        sendMessage.setText(message);
        sendMessage.enableMarkdown(true);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            //e.printStackTrace();
        }
    }

    public void sendDocument(String content) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setDocument(
                new InputFile(
                        new CharSequenceInputStream(content, StandardCharsets.UTF_8),
                        "exercises.json"
                )
        );
        sendDocument.setChatId(ADMIN_CHAT_ID);

        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "LoggerCodeForEveryoneServer";
    }
}