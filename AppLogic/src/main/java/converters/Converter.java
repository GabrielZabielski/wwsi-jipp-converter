package converters;

import java.util.List;

public interface Converter{
    double[][] x = null;
    String[] units = {};
    String getConverterName();
    List<String> getUnits();
    double convert(String unitFrom, String unitTo, double input);
//    static Map<String, Integer> map = Arrays.stream(units).collect(Collectors.toMap(x -> getName(), x -> x.indexOf(x)));
}