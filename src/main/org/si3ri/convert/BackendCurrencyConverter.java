package main.org.si3ri.convert;

import main.org.si3ri.convert.exception.QueryCurrencyException;
import main.org.si3ri.convert.model.Currency;
import main.org.si3ri.convert.service.QueryCurrencyChange;
import java.io.IOException;
import java.util.Scanner;

public class BackendCurrencyConverter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QueryCurrencyChange consult = new QueryCurrencyChange();

        System.out.println("\n***** SISTEMA CONVERSOR DE MONEDAS *****" );

        convertCurrencies(consult);

        while (true) {
            System.out.println("\nPuede realizar conversiones entre:");
            System.out.println("* 1. USD (Dólar Estadounidense) - 2. EUR (Unión Europea) - 3. BRL (Real Brasileño)");
            System.out.println("* 4. COP (Peso Colombiano) - 5. ARS (Peso Argentino) - 6. MXN (Peso Mexicano)");
            System.out.println("De acuerdo con las monedas que se muestran, ingrese números del 1 al 6 (sin el punto) y presione 'Enter' *" + "\n");

            int attemptsOrigin = 0;
            final int maxAttemptsOrigin = 3;
            String currencyOrigin = null;

            while (attemptsOrigin < maxAttemptsOrigin) {
                System.out.print("Ingrese el número de la moneda de origen: ");
                String optionA = scanner.nextLine();
                currencyOrigin = getCurrency(optionA);

                if (currencyOrigin == null) {
                    System.out.println("\n" + "--- Moneda de origen inválida | Intentos restantes: " + (maxAttemptsOrigin - attemptsOrigin - 1) + " ---" + "\n");
                    attemptsOrigin++;
                } else {
                    break;
                }
            }

            if (currencyOrigin == null) {
                System.out.println("--- Ha excedido el número máximo de intentos para ingresar la moneda de origen. ---");
                continue;
            }

            boolean currencyDestinyValid = false;
            int attemptsDestiny = 0;
            final int maxAttemptsDestiny = 3;
            String currencyDestiny = null;

            while (!currencyDestinyValid && attemptsDestiny < maxAttemptsDestiny) {
                System.out.print("Ingrese el número de la moneda de destino: ");
                String optionB = scanner.nextLine();
                currencyDestiny = getCurrency(optionB);
                System.out.println(" ");

                if (currencyDestiny == null) {
                    System.out.println("--- Moneda de destino inválida | Intentos restantes: " + (maxAttemptsDestiny - attemptsDestiny - 1) + " ---" + "\n");
                    attemptsDestiny++;
                } else {
                    currencyDestinyValid = true;
                }
            }

            if (!currencyDestinyValid) {
                System.out.println("--- Ha excedido el número máximo de intentos para ingresar la moneda de destino. ---");
                continue;
            }

            if (currencyOrigin.equals(currencyDestiny)) {
                System.out.println("--- ¡No es necesario convertir la misma moneda! ---");
                continue;
            }

            convertCurrency(currencyOrigin, currencyDestiny, consult);

            System.out.print("¿Desea realizar otra conversión? (Ingrese S para SÍ o cualquier otra tecla para salir): ");
            String answer = scanner.nextLine();
            if (!answer.equalsIgnoreCase("s")) {
                System.out.println("\n" + "***** ¡GRACIAS! FINALIZÓ EL SISTEMA CONVERSOR DE MONEDAS. *****");
                break;
            }
        }
    }

    private static void convertCurrencies(QueryCurrencyChange queries) {
        try {
            Currency ratesConversion = queries.takeExchangeRates("USD");
            System.out.println("\n" + ratesConversion);
            if (ratesConversion == null) {
                System.out.println("""
                            --- No se pudieron obtener las tasas de cambio. Intente nuevamente más tarde. ---
                            """);
            }
        } catch (IOException | QueryCurrencyException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getCurrency(String option) {
        return switch (option) {
            case "1" -> "USD";
            case "2" -> "EUR";
            case "3" -> "BRL";
            case "4" -> "COP";
            case "5" -> "ARS";
            case "6" -> "MXN";
            default -> null;
        };
    }

    private static void convertCurrency(String currencyOrigin, String currencyDestiny, QueryCurrencyChange queries) {
        try {
            Currency exchangeRates = queries.takeExchangeRates(currencyOrigin);
            System.out.println(exchangeRates);

            if (exchangeRates == null) {
                System.out.println("""
                        --- No se pudieron obtener las tasas de cambio. Intente nuevamente más tarde. ---
                        """);
                return;
            }

            double exchangeRate = switch (currencyDestiny) {
                case "USD" -> exchangeRates.USD();
                case "EUR" -> exchangeRates.EUR();
                case "BRL" -> exchangeRates.BRL();
                case "COP" -> exchangeRates.COP();
                case "ARS" -> exchangeRates.ARS();
                case "MXN" -> exchangeRates.MXN();
                default -> throw new IllegalArgumentException("--- Moneda destino no válida ---");
            };

            Scanner scanner = new Scanner(System.in);
            double amount = 0;
            int attemptsEnterAmount = 0;
            final int maxAttemptsEnterAmount = 3;
            while (attemptsEnterAmount < maxAttemptsEnterAmount) {
                System.out.print("Ingrese la cantidad de " + currencyOrigin + " a convertir en " + currencyDestiny + ": ");
                if (scanner.hasNextDouble()) {
                    amount = scanner.nextDouble();
                    if (amount >= 0) {
                        break;
                    } else {
                        System.out.println("\n" + "--- Por favor, ingrese un número válido para conversión | Intentos restantes: " + (maxAttemptsEnterAmount - attemptsEnterAmount - 1) + " ---" + "\n");
                        scanner.nextLine();
                    }
                } else {
                    System.out.println("\n" + "--- Por favor, ingrese un número válido para conversión | Intentos restantes: " + (maxAttemptsEnterAmount - attemptsEnterAmount - 1) + " ---" + "\n");
                    scanner.nextLine();
                }

                attemptsEnterAmount++;

                if (attemptsEnterAmount == maxAttemptsEnterAmount) {
                    throw new IllegalArgumentException("Ha excedido el número máximo de intentos para ingresar la cantidad a convertir.");
                }
            }
            double result = amount * exchangeRate;
            System.out.println("\n" + "CONVERSIÓN: " + amount + " " + currencyOrigin + " equivale/n a " + result + " " + currencyDestiny + "\n");
        } catch (IOException | InterruptedException e) {
            System.out.println("\n" + "--- Error al obtener las tasas de cambio: " + e.getMessage() + " ---" + "\n");
        } catch (IllegalArgumentException e) {
            System.out.println("--- Error: " + e.getMessage() + " ---" + "\n");
        } catch (QueryCurrencyException e) {
            throw new RuntimeException(e);
        }
    }
}