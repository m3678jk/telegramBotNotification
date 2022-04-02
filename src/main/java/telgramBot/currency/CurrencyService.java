package telgramBot.currency;

import telgramBot.dto.Currency;

import java.io.IOException;

public interface CurrencyService {
    double getRate(Currency currency) throws IOException;
}