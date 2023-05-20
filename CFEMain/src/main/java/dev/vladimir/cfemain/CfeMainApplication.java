package dev.vladimir.cfemain;

import dev.vladimir.cfemain.loggerbot.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.OutputStream;
import java.io.PrintStream;

@SpringBootApplication
@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class CfeMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(CfeMainApplication.class, args);

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(Logger.getLoggerBot());
        } catch (TelegramApiException e) {
            throw new RuntimeException("Failed to start logging in telegram");
        }

        Logger.log("CfeMainApplication was started");

        setOutputStream();
    }

    private static void setOutputStream() {
        PrintStream out = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                Logger.addInQueue((char) b);
            }
        });
        System.setOut(out);
    }
}
