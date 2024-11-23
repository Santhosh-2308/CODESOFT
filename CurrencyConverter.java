import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // User input: Base and target currencies
            System.out.print("Enter the base currency (e.g., USD): ");
            String baseCurrency = scanner.next().toUpperCase();

            System.out.print("Enter the target currency (e.g., EUR): ");
            String targetCurrency = scanner.next().toUpperCase();

            System.out.print("Enter the amount to convert: ");
            double amount = scanner.nextDouble();

            // Fetch exchange rate
            double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);

            if (exchangeRate != -1) {
                // Perform conversion
                double convertedAmount = amount * exchangeRate;
                System.out.printf("Converted Amount: %.2f %s%n", convertedAmount, targetCurrency);
            } else {
                System.out.println("Error fetching exchange rate. Please check the currency codes.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // Method to fetch exchange rate
    private static double fetchExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            URL url = new URL(API_URL + baseCurrency);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Extract exchange rate
            String responseText = response.toString();
            String key = "\"" + targetCurrency + "\":";
            int startIndex = responseText.indexOf(key);
            if (startIndex == -1) {
                return -1; // Currency not found
            }
            int valueStartIndex = startIndex + key.length();
            int valueEndIndex = responseText.indexOf(",", valueStartIndex);
            if (valueEndIndex == -1) {
                valueEndIndex = responseText.indexOf("}", valueStartIndex);
            }
            String rateString = responseText.substring(valueStartIndex, valueEndIndex).trim();
            return Double.parseDouble(rateString);

        } catch (Exception e) {
            System.out.println("Error fetching data: " + e.getMessage());
            return -1;
        }
    }
}
