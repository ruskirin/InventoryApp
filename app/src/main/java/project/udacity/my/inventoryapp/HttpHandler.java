package project.udacity.my.inventoryapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpHandler {

    public HttpHandler() {}

    public String getJsonResponse(String urlString) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String jsonResponse = null;

        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection)url.openConnection();

            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            if(responseCode >= 200 && responseCode < 300) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                jsonResponse = streamToString(inputStream);

            } else {
                throw new IOException("Bad HTTP response code " + responseCode);
            }

        } catch(MalformedURLException e) {
            e.printStackTrace();
            /***********
             *TODO: handle exception appropriately
             ***********/
        } catch(IOException e) {
            e.printStackTrace();
            /***********
             *TODO: handle exception appropriately
             ***********/
        } catch(NullPointerException e) {
            e.printStackTrace();
            /***********
             *TODO: handle exception appropriately
             ***********/
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }

        return jsonResponse;
    }

    private String streamToString(InputStream stream)
            throws IOException, NullPointerException {

        StringBuilder stringBuilder = null;

        if(stream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            stringBuilder = new StringBuilder(stream.available());
            String response;

            try {
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response);
                }

            } finally {
                stream.close();
            }
        }

        return stringBuilder.toString();
    }
}
