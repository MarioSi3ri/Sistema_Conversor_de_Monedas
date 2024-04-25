package main.org.si3ri.convert.model;

public class CurrencyTypeAPI {
    private final Currency conversion_rates;


    public CurrencyTypeAPI(Currency converterRates) {
        this.conversion_rates = converterRates;
    }

    public Currency getConverterRates() {
        return conversion_rates;
    }
}