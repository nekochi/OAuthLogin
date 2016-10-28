package com.nekomimi.oauthloginx.utils;

import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * Created by chenshuide on 16-2-17.
 */
public class PropertiesUtil {


    private static final String TAG = "PropertiesUtil";

    public static void writeProperties2File(Properties properties, File dest) {

        try {
            if (properties == null || properties.isEmpty() || dest == null)
                return;

            if (!dest.exists()) {
                dest.createNewFile();
            }


            PrintWriter pw = new PrintWriter(dest);

            properties.list(pw);

            properties.store(pw, "");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static String getProperties(File dest, String key) {

        Properties properties = getProperties4File(dest);
        if (properties == null || properties.isEmpty())
            return "";
        return properties.getProperty(key);
    }


    public static String setProperties(File dest, String key, String value) {

        Properties properties = getProperties4File(dest);
        if (properties == null) {
            properties = new Properties();
        }

        String oldValue = (String) properties.setProperty(key, value);

        writeProperties2File(properties, dest);

        return oldValue;

    }


    public static Properties getProperties4File(File dest) {

        if (dest == null || !dest.exists())
            return null;

        try {
            Properties properties = new Properties();
            properties.load(new FileReader(dest));
            return properties;

        } catch (IOException e) {
            Log.d(TAG, "properties file not exists" + dest.getName());

        }

        return null;

    }

    public static Properties getProperties4File(InputStream ios) throws IOException {

        if (ios == null) {
            return null;
        }

        Properties properties = new Properties();
        properties.load(ios);
        return properties;


    }

}
