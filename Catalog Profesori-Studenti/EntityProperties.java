package me.map.lab3.properties;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class EntityProperties {
    public Properties getProperty(){
        Properties prop = new Properties();
        String propFileName = "config.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        try {
            prop.load(inputStream);
            inputStream.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return prop;
    }
}
