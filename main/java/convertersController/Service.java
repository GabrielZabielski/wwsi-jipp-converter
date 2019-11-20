package convertersController;

import converters.Converter;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Service {
    private Reflections reflections;

    public Service(){
        this.reflections = new Reflections("converters");
    }

    public List<String> listConverters() throws IllegalAccessException, InstantiationException {
        List<String> c = new ArrayList<>();
        Set<Class<? extends Converter>> converters =
                reflections.getSubTypesOf(Converter.class);
        for (Class<? extends Converter> xClass : converters){
            xClass.newInstance();
            System.out.println(xClass.getName());
            c.add(xClass.getName());
        }
        return c;
    }

}