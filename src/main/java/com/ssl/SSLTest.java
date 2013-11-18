package com.ssl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: sergey.vyatkin
 * Date: 11/18/13
 * Time: 1:42 PM
 *  SSL connection test to Tomcat server
 */
public class SSLTest {

    static final private String urlWsdl = "https://localhost:8443";

    static final private String KEY_STORE = "src/main/resources/local/localhost_client.p12";
    static final private String KEY_STORE_TYPE = "PKCS12";
    static final private String KEY_STORE_PASS = "changeit";


    static final private String TRUST_STORE = "src/main/resources/local/localhost_client_trust_store.jks";
    static final private String TRUST_STORE_TYPE = "jks";
    static final private String TRUST_STORE_PASS = "changeit";
/*
    if you self-signed certificate is not CN=localhost please un-comments

    static {
        //for localhost testing only
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier() {

                    public boolean verify(String hostname,
                                          javax.net.ssl.SSLSession sslSession) {
                        if (hostname.equals("localhost")) {
                            return true;
                        }
                        return false;
                    }
                });
    }
*/
    public static void main(String[] args) {
            System.setProperty("javax.net.ssl.keyStoreType", KEY_STORE_TYPE);
            System.setProperty("javax.net.ssl.keyStore", KEY_STORE);
            System.setProperty("javax.net.ssl.keyStorePassword", KEY_STORE_PASS );

            System.setProperty("javax.net.ssl.trustStoreType", TRUST_STORE_TYPE);
            System.setProperty("javax.net.ssl.trustStore", TRUST_STORE);
            System.setProperty("javax.net.ssl.trustStorePassword", TRUST_STORE_PASS);

            System.setProperty("javax.net.debug", "ssl,handshake");

        try {
            URL constructedUrl = new URL(urlWsdl);
            URLConnection conn = constructedUrl.openConnection();
            conn.setConnectTimeout(15000);  // 15 secs

            InputStreamReader reader = new InputStreamReader(conn.getInputStream(), "UTF-8");
            BufferedReader in = new BufferedReader(reader);

            String line = in.readLine();
            line = in.readLine();
            System.out.println("localHost:" + line);

            in.close();
            reader.close();
        } catch (Exception e) {
            System.out.println("Could not connect to the host address " + urlWsdl);
            System.out.println("The error is: " + e.getMessage());
        }
    }
}
