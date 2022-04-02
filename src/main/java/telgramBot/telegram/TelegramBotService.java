package telgramBot.telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telgramBot.dto.Currency;

public class TelegramBotService {

    private CurrencyTelegramBot currencyTelegramBot;
    public TelegramBotService(){
        currencyTelegramBot = new CurrencyTelegramBot();
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(currencyTelegramBot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
