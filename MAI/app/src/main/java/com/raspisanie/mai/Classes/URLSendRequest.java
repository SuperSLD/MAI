package com.raspisanie.mai.Classes;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Класс для упрощения отправки запросов на сервер
 *
 *  Пример строки с параметрами:
 *  param1 = 9
 *  param2 = "str"
 *  param3 = false
 *  строка будет выглядеть так
 *  "param1=9&param2=str&param3=false"
 */

public class URLSendRequest {
    private int TIME_OUT;
    private String SERVER_URL;

    /**
     * Конструктор класса
     * @param SERVER_URL адрес + порт
     * @param TIME_OUT  ожидание подключения и ответа в мс
     */
    public URLSendRequest(String SERVER_URL,
                          int TIME_OUT) {
        this.TIME_OUT   = TIME_OUT;
        this.SERVER_URL = SERVER_URL;
    }

    /**
     * Метод отправляющий запрос GET на сервер.
     *
     * В первом варианте идет обычный запрос во втором
     * варианте запрос с отправкой id сесии
     *
     * @param SERVER_GET запрос для сервера
     * @return строка ответ сервера
     */
    public String get(final String SERVER_GET) {
        final String[] input = {null};
        Thread send = new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                String q = SERVER_URL + SERVER_GET;
                Logger.getLogger("url").log(Level.INFO, q);
                URL url = new URL(q);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(TIME_OUT);
                conn.setReadTimeout(TIME_OUT);
                conn.connect();

                if(HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    input[0] = readBuffer(bufferedReader);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        });
        send.start();
        while (send.isAlive()) { }
        return input[0];
    }

    public String get(final String SERVER_GET,
                      final String SessionID) {
        final String[] input = {null};
        Thread send = new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                String q = SERVER_URL + SERVER_GET;
                URL url = new URL(q);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Cookie", "JSESSIONID="+SessionID);
                conn.setConnectTimeout(TIME_OUT);
                conn.setReadTimeout(TIME_OUT);
                conn.connect();

                if(HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    input[0] = readBuffer(bufferedReader);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        });
        send.start();
        while (send.isAlive()) { }
        return input[0];
    }

    /**
     * Метод отправляющий запрос POST на сервер.
     *
     * В первом варианте идет обычный запрос во втором
     * варианте запрос с отправкой id сесии
     *
     * @param SERVER_POST запрос для сервера
     * @param urlParameters параметры передаваемые в запросе
     * @return строка ответ сервера
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String post(final String SERVER_POST,
                       final String urlParameters) {
        final String[] input = {null};
        Thread send = new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
                int    postDataLength = postData.length;
                String request        = SERVER_URL + SERVER_POST;
                URL    url            = new URL( request );
                conn                  = (HttpURLConnection) url.openConnection();
                conn.setDoOutput( true );
                conn.setInstanceFollowRedirects( false );
                conn.setRequestMethod( "POST" );
                conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty( "charset", "utf-8");
                conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
                conn.setUseCaches( false );
                try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                    wr.write( postData );
                }

                if(HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    input[0] = readBuffer(bufferedReader);
                    input[0] = URLDecoder.decode(input[0], "UTF-8");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        });
        send.start();
        while (send.isAlive()) { }
        return input[0];
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String post(final String SERVER_POST,
                       final String urlParameters,
                       final String SessionID) {
        final String[] input = {null};
        Thread send = new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
                int    postDataLength = postData.length;
                String request        = SERVER_URL + SERVER_POST;
                URL    url            = new URL( request );
                conn                  = (HttpURLConnection) url.openConnection();
                conn.setDoOutput( true );
                conn.setInstanceFollowRedirects( false );
                conn.setRequestMethod( "POST" );
                conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty( "charset", "utf-8");
                conn.setRequestProperty("Cookie",SessionID);
                conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
                conn.setUseCaches( false );
                try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                    wr.write( postData );
                }

                if(HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    input[0] = readBuffer(bufferedReader);
                    input[0] = URLDecoder.decode(input[0], "UTF-8");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        });
        send.start();
        while (send.isAlive()) { }
        return input[0];
    }

    private String readBuffer(BufferedReader bufferedReader) {
        String s = "";
        String out = "";
        while (s != null) {
            out += s;
            try {
                s = bufferedReader.readLine();
                if (s != null) out += "\n";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out;
    }

    public void setTIME_OUT (int TIME_OUT) {
        this.TIME_OUT = TIME_OUT;
    }

    public void setSERVER_URL(String SERVER_URL) {
        this.SERVER_URL = SERVER_URL;
    }
}
