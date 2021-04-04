package com.web_d4;

import javax.servlet.ServletContext;
import java.io.*;

public class StaticFileReader {


    public static String readFile(String path, ServletContext context) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        InputStream is = context.getResourceAsStream(path);
        if (is != null) {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String text;
            // We read the file line by line and later will be displayed on the
            // browser page.
            while ((text = reader.readLine()) != null) {
                stringBuilder.append(text);
            }
        }
        return stringBuilder.toString();

    }
}
