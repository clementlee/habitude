package com.clementl.habitude;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class GetData {

    //get a string representation of the data from the url param
    public static String GET(String url) {
        InputStream is = null;
        String result = "GET: 404";
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

//    //Basic post request using name value pairs
//    public static String POST(String value) {
//        final String server = "http://dev.clementl.com/getuser";
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpPost httppost = new HttpPost(server);
//
//        // create a list to store HTTP variables and their values
//        List pairs = new ArrayList<NameValuePair>();
//        //TODO: set name value pair for habitude post requests
//        pairs.add(new BasicNameValuePair("email", value));
//
//        try {
//            httppost.setEntity(new UrlEncodedFormEntity(pairs));
//            // execute post request
//            HttpResponse response = httpclient.execute(httppost);
//            return response.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "404";
//    }


    public static String POST(String value) {
        final String server = "https://dev.clementl.com/getuser";

        //setup http connection
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(server);

        try {
            JSONObject json = new JSONObject();
            json.put("email", value);
            StringEntity se = new StringEntity(json.toString());
            post.setEntity(se);
            HttpResponse response = client.execute(post);

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "POST: 404";
    }

}