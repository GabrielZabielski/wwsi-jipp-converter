package converters;

import java.util.List;

public interface Converter{
    public String getName();
    public List<String> getUnits();
    public double Convert(String unitFrom, String unitTo, double input);
}