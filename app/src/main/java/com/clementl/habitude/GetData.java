package com.clementl.habitude;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GetData {

    //get a string representation of the data from the url param
    public static String GET(String url) {
        InputStream is = null;
        String result = "404";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpresponse = httpclient.execute(new HttpGet(url));
            is = httpresponse.getEntity().getContent();

            //convert is to string
            if (is != null)
                result = convertToString(is);
        } catch (Exception e) {e.printStackTrace();}
        return result;
    }

    private static String convertToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = "";
        StringBuilder result = new StringBuilder();
        while((line = reader.readLine()) != null)
            result.append(line);
        is.close();
        return result.toString();
    }

    public static boolean POST(String value) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(/*Some server to receive*/);
        try {
            // create a list to store HTTP variables and their values
            List nameValuePairs = new ArrayList();
            // add an HTTP variable and value pair
            nameValuePairs.add(new BasicNameValuePair("myHttpData", value));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // send the variable and value, in other words post, to the URL
            HttpResponse response = httpclient.execute(httppost);
            return true;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}