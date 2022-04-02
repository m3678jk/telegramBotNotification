package telgramBot.currency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import telgramBot.currency.CurrencyService;
import telgramBot.dto.Currency;
import telgramBot.dto.CurrencyItem;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class PrivatBankCurrencyService implements CurrencyService {


    @Override
    public double getRate(Currency currency) throws IOException {
        String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

        //get json
        String json = Jsoup.connect(url)
                .ignoreContentType(true)
                .get()
                .body()
                .text();
        // json => to java object
        Type collectionType = TypeToken
                .getParameterized(List.class, CurrencyItem.class).getType();

        List<CurrencyItem> currencyItems = new Gson().fromJson(json, collectionType);


        return currencyItems
                .stream()
                .filter(it -> it.getCcy() == currency)
                .filter(it -> it.getBase_ccy() == Currency.UAH)
                .map(it -> it.getBuy())
                .findFirst()
                .orElseThrow();
    }
}
