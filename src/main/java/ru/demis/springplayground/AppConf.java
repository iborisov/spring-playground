package ru.demis.springplayground;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "app")
public class AppConf {
    private Map<String, String> mapProperty;

    public Map<String, String> getMapProperty() {
        return mapProperty;
    }

    public void setMapProperty(Map<String, String> mapProperty) {
        this.mapProperty = mapProperty;
    }

    @Override
    public String toString() {
        return "AppConf{" +
                "mapProperty=" + mapProperty +
                '}';
    }
}
