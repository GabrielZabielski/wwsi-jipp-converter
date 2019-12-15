package convertersController;

import converters.Converter;
import org.reflections.Reflections;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ConverterController {
    private Reflections reflections;
    private Set<Class<? extends Converter>> converters;
    public ConverterController(){
        this.reflections = new Reflections("converters");
        this.converters = reflections.getSubTypesOf(Converter.class);
    }

    public Map<String, Object> getMapConverters(){
        System.out.println("tu jestem");
        Map<String, Object> hashMap = new HashMap<>();
        converters.forEach(x -> {
            try {
                Converter obj = x.newInstance();
                hashMap.put(obj.getConverterName(), obj);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        List<URL> uriList = new ArrayList<>();
        try {
            Files.walk(Paths.get("Plugins"))
                    .filter(Files::isRegularFile)
                    .filter(file -> file.getFileName().toString().toLowerCase().endsWith(".jar"))
                    .forEach(path -> {
                        try {
                            uriList.add(path.toUri().toURL());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        URL[] urls = new URL[uriList.size()];
        for (int i = 0; i < uriList.size(); i++){
            urls[i] = uriList.get(i);
            System.out.println("1. " + uriList.get(i).toString());
        }

        // TODO OSGI

        URLClassLoader classLoader = new URLClassLoader(urls);
        System.out.println("2. " + Arrays.toString(classLoader.getURLs()));
        ServiceLoader<Converter> serviceLoader = ServiceLoader.load(Converter.class, classLoader);

        serviceLoader.forEach(x -> {
            System.out.println("3. " + x.getConverterName());
            hashMap.put(x.getConverterName(), x);
        });
        System.out.println("4. " + hashMap.toString());
        return hashMap;
    }

}