package converters;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Currency implements Converter {
    private Double dollar;

    public Currency(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String url = "http://api.nbp.pl/api/exchangerates/rates/c/usd/"+dtf.format(now.minusHours(12))+"/?format=json";

        try {
            JSONObject json = readJsonFromUrl(url);
            this.dollar = json.optJSONArray("rates").optJSONObject(0).getDouble("bid");
        } catch (IOException e) {
            System.out.println(url);
            e.printStackTrace();
        }
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    @Override
    public String getConverterName() {
        return "Currency";
    }

    @Override
    public List<String> getUnits() {
        String[] units = {"PLN", "$"};
        return Arrays.asList(units);
    }

    @Override
    public double convert(String unitFrom, String unitTo, double input) {
        if (unitFrom.equals("PLN")){
            return input*(1/this.dollar);
        }
        else {
            return input*this.dollar;
        }
    }
}
