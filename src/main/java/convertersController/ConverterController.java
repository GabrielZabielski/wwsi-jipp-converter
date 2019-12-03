package convertersController;

import converters.Converter;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConverterController {
    private Reflections reflections;
    private Set<Class<? extends Converter>> converters;
    public ConverterController(){
        this.reflections = new Reflections("converters");
        this.converters = reflections.getSubTypesOf(Converter.class);
    }

    public Map<String, Object> getMapConverters() {
        Map<String, Object> hashMap = new HashMap<>();
        converters.forEach(x -> {
            try {
                Object obj = x.newInstance();
                hashMap.put(((Converter)obj).getConverterName(), obj);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

//        File dir = new File("plugins");
//        try {
//            System.out.println(dir.get);
//            URL paths = dir.toURI().toURL();
//            URL[] classURL = new URL[]{paths};
//            ClassLoader classLoader = new URLClassLoader(classURL);
//            System.out.println(classLoader.loadClass());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return hashMap;
    }

}