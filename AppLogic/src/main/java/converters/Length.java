package converters;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Length implements Converter{
                            // m            km          mile      z / na
    private final double[][] x = {{0,            0.001,     0.0006215},   //m
                                  {1000.0,       0,         0.6215},      //km
                                  {1609,         1.609,     0}};          // mile
    private final Map<String, Integer> units = new HashMap<String, Integer>() {{
       put("m", 0);
       put("km", 1);
       put("mile", 2);
    }};

    public String getConverterName() {
        return "Length";
    }

    public List<String> getUnits() {
        List<String> list = new ArrayList<>();
        units.forEach((x, y) -> list.add(x));
        return list;
    }

    public double convert(String unitFrom, String unitTo, double input) {
        return input * x[units.get(unitFrom)][units.get(unitTo)];
    }
}
