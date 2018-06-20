package com.fastfur.messaging.producer.stocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class VantageConnector {

    private static final String API_KEY = "&apikey=";
    private static final String API_VALUE = "QZZNHVR9BNNVZW01";

    VantageConnector() {
        CertificateInstaller.installVantageCertificate();
    }

    public String getData(String baseUri) {
        String jsonResponse = null;
        try {
            URL request = new URL(baseUri + API_KEY + API_VALUE);
            URLConnection connection = request.openConnection();
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);

            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream(), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStream);
            StringBuilder responseBuilder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                responseBuilder.append(line);
            }
            bufferedReader.close();
            System.out.println("--------------------------------------");
            jsonResponse = responseBuilder.toString();
            System.out.println(jsonResponse);
            System.out.println("--------------------------------------");
        } catch (IOException e) {
            try {
                throw new Exception("failure sending request", e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return jsonResponse;
    }
}
