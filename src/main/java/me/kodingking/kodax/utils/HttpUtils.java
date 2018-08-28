package me.kodingking.kodax.utils;

import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class HttpUtils {
    public static String get(String url) throws IOException {
        URL u = new URL(url);
        URLConnection conn = u.openConnection();
        conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
        InputStream is = conn.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder out = new StringBuilder();

        String inputLine;
        while((inputLine = br.readLine()) != null) {
            out.append(inputLine);
        }

        br.close();
        return out.toString();
    }
}
