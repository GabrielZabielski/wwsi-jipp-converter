package plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Currency implements Converter{
    private double[][] x;
    private String[] units = {"PLN", "$", "*"};
//    private Map<String, Integer> map = Arrays.stream(units).collect(Collectors.toMap(x -> getName(), x -> x.indexOf(x)));

    public String getConverterName() {
        return "plugin.Currency";
    }

    public List<String> getUnits() {
        return new ArrayList<String>(Arrays.asList(units));
    }

    public double convert(String unitFrom, String unitTo, double input) {
        return input* 0.1; //x[map.get(unitFrom)][map.get(unitTo)];
    }
    public static void main(String[] args){}
}
