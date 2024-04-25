package main.org.si3ri.convert.service;

import com.google.gson.Gson;
import main.org.si3ri.convert.exception.QueryCurrencyException;
import main.org.si3ri.convert.model.CurrencyTypeAPI;
import main.org.si3ri.convert.model.Currency;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class QueryCurrencyChange {

   public Currency takeExchangeRates(String coin) throws IOException, InterruptedException, QueryCurrencyException {
        URI url_str = URI.create("https://v6.exchangerate-api.com/v6/09b8636821aeea6bf42f8765/latest/" + coin);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url_str)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

       if (response.statusCode() != 200) {
           throw new QueryCurrencyException("--- Error al obtener las tasas de cambio. Código de estado: " + response.statusCode() + " ---");
       }

        Gson gson = new Gson();
        CurrencyTypeAPI apiResponse = gson.fromJson(response.body(), CurrencyTypeAPI.class);

        return apiResponse.getConverterRates();
    }
}