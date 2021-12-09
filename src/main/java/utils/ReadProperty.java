package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ReadProperty {

    private final static Logger logger = Logger.getLogger(ReadProperty.class.getName());
    public static Properties loadRmqProperties() {
        return load("rabbitMQ.properties");
    }

    private static Properties load(String propertyFilename) {
        Properties prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream(propertyFilename);
        try {
            prop.load(input);
        } catch (IOException e) {
            logger.info("cannot load config file: " + propertyFilename);
            e.printStackTrace();
        }
        return prop;

    }
}
