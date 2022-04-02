package telgramBot.ui;

import telgramBot.dto.Currency;

public class PrettyPrintCurrencyService {
    public String convert (double rate, Currency currency){
        String template = "Rate ${currency} => UAH = ${rate}";

        return template.replace("${currency}", currency.name())
                .replace("${rate}", Double.toString(rate));
    }
}
