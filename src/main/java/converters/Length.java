package converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Length implements Converter{
    private double[][] x;
    private String[] units = {"m", "km", "mile"};
//    private Map<String, Integer> map = Arrays.stream(units).collect(Collectors.toMap(x -> getName(), x -> x.indexOf(x)));

    public String getConverterName() {
        return "Currency";
    }

    public List<String> getUnits() {
        return new ArrayList<String>(Arrays.asList(units));
    }

    public double convert(String unitFrom, String unitTo, double input) {
        return input* 10000; //x[map.get(unitFrom)][map.get(unitTo)];
    }
}
