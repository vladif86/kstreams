package com.fastfur.messaging.producer.stocks;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManagerFactory;

public class CertificateInstaller {

    static void installVantageCertificate() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            Path ksPath = Paths.get(System.getProperty("java.home"),
                    "lib", "security", "cacerts");
            keyStore.load(Files.newInputStream(ksPath),
                    "changeit".toCharArray());

            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            try (InputStream caInput = new BufferedInputStream(
                    // this files is shipped with the application
                    CertificateInstaller.class.getResourceAsStream("/LetsEncryptAuthorityX3.cer"))) {
                Certificate crt = cf.generateCertificate(caInput);
                System.out.println("Added Cert for " + ((X509Certificate) crt)
                        .getSubjectDN());

                keyStore.setCertificateEntry("DSTRootCAX3", crt);
            }

/*
            System.out.println("Truststore now trusting: ");
            PKIXParameters params = new PKIXParameters(keyStore);
            params.getTrustAnchors().stream()
                    .map(TrustAnchor::getTrustedCert)
                    .map(X509Certificate::getSubjectDN)
                    .forEach(System.out::println);
            System.out.println();*/


            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) throws IOException {
        // signed by default trusted CAs.
       // connectToVantage();
        testUrl(new URL("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=MSFT&interval=1min&apikey=QZZNHVR9BNNVZW01\n"));
        testUrl(new URL("https://www.thawte.com"));

        // signed by letsencrypt
        testUrl(new URL("https://helloworld.letsencrypt.org"));
        // signed by LE's cross-sign CA
        testUrl(new URL("https://letsencrypt.org"));
        // expired
        testUrl(new URL("https://tv.eurosport.com/"));
        // self-signed
        testUrl(new URL("https://www.pcwebshop.co.uk/"));

        testUrl(new URL("https://www.pcwebshop.co.uk/"));

    }

    public static void connectToVantage (){
        try {


            URL request = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=MSFT&interval=1min&apikey=QZZNHVR9BNNVZW01");
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
            String response = responseBuilder.toString();
            System.out.println(response);
            System.out.println("--------------------------------------");
        } catch (IOException e) {
            try {
                throw new Exception("failure sending request", e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    static void testUrl(URL url) throws IOException {
        URLConnection connection = url.openConnection();
        try {
            connection.connect();
            System.out.println("Headers of " + url + " => "
                    + connection.getHeaderFields());
        } catch (SSLHandshakeException e) {
            System.out.println("Untrusted: " + url);
        }
    }



}
