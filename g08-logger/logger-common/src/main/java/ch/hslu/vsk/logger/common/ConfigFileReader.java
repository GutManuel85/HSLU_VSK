package ch.hslu.vsk.logger.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * g08-logger
 * <p>
 * A Class to read Configurations out of a File.
 * </p>
 *
 * @author g08
 * @version V01.01
 */
public class ConfigFileReader {

    public static Properties readConfigurationFile(final String fileName) throws IOException {
        Properties prop = new Properties();
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
            is = null;
        }
        if (is != null) {
            try {
                prop.load(is);
            } catch (IOException | NullPointerException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return prop;
    }
}