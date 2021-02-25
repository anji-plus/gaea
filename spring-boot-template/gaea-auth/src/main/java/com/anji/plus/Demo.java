package com.anji.plus;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.PropertyResourceBundle;

/**
 * @author lr
 * @since 2021-02-23
 */
public class Demo {

    public static void main(String[] args) throws Exception {

        ClassLoader classLoader = Demo.class.getClassLoader();
        String resourceName = "messages.properties";

        InputStream inputStream = AccessController.doPrivileged((PrivilegedExceptionAction<InputStream>) () -> {
            InputStream is = null;
            if (false) {
                URL url = classLoader.getResource(resourceName);
                if (url != null) {
                    URLConnection connection = url.openConnection();
                    if (connection != null) {
                        connection.setUseCaches(false);
                        is = connection.getInputStream();
                    }
                }
            }
            else {
                is = classLoader.getResourceAsStream(resourceName);
            }
            return is;
        });

        InputStreamReader bundleReader = new InputStreamReader(inputStream, "UTF-8");

        PropertyResourceBundle propertyResourceBundle = new PropertyResourceBundle(bundleReader);

        System.out.println(propertyResourceBundle);
    }
}
