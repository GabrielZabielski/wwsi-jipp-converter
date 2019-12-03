package propertiesInjector;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@ApplicationScoped
public class PropertyProducer {
    private Properties properties;

    @Property
    @Produces
    public String produceString(final InjectionPoint ip){
        return this.properties.getProperty(getKey(ip));
    }

    private String getKey(final InjectionPoint ip) {
        System.out.println("wtf");
        return (ip.getAnnotated()
                .isAnnotationPresent(Property.class) &&
                !ip.getAnnotated().getAnnotation(Property.class).value().isEmpty())
                ? ip.getAnnotated().getAnnotation(Property.class).value() : ip.getMember().getName();
    }

    @PostConstruct
    public void init() {
        System.out.println("wtf");
        this.properties = new Properties();
        final InputStream stream = PropertyProducer.class
                .getResourceAsStream("./config.properties");
        try {
            this.properties.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("Configuration could not be loaded!");
        }
    }
}
