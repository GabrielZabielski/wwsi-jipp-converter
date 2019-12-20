package converters;

import java.util.*;

public class Weight implements Converter{
    private final double[][] x = {{0,            0.4535923, 0.004535923},
                                  {2.2046,       0,         0.001},
                                  {0.0022046,    1000,      0}};
    private final Map<String, Integer> units = new HashMap<String, Integer>() {{
        put("lb", 0);
        put("kg", 1);
        put("g", 2);
    }};

    public String getConverterName() {
        return "Wight";
    }

    public List<String> getUnits() {
        List<String> list = new ArrayList<>();
        units.forEach((x, y) -> list.add(x));
        return list;
    }

    public double convert(String unitFrom, String unitTo, double input) {
        return input* x[units.get(unitFrom)][units.get(unitTo)];
    }
}
