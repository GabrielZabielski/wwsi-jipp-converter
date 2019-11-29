package converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Weight implements Converter{
    private double[][] x = {{0,            0.4535923, 0.0004535923},
                            {2.2046,       0,         0.0001},
                            {0.0022046,    1000,      0}};
    private String[] units = {"lb", "kg", "g"};
//    private Map<String, Integer> map = Arrays.stream(units).collect(Collectors.toMap(x, x -> x.indexOf(x)));


    @Override
    public String getConverterName() {
        return "Wight";
    }

    @Override
    public List<String> getUnits() {
        return new ArrayList<String>(Arrays.asList(units));
    }

    @Override
    public double convert(String unitFrom, String unitTo, double input) {
        return input* 100; //x[map.get(unitFrom)][map.get(unitTo)];
    }
}
