package com.example.dell.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.Guard;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class QueryUtils  {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static ArrayList<Section> fetchSectionData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
        }

        ArrayList<Section> sections = extractDataFrom(jsonResponse);
        return sections;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException m) {
            Log.e(LOG_TAG, "Problem building the url", m);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = null;
        if (url == null) {
            return null;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error code- " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            return jsonResponse;
        }
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<Section> extractDataFrom(String jsonResponse) {

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        ArrayList<Section> section = new ArrayList<Section>();
        try {
            JSONObject baseJsonObject = new JSONObject(jsonResponse);
            JSONObject responseJsonObject = baseJsonObject.getJSONObject("response");
            JSONArray resultsArray = responseJsonObject.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject currentSection = resultsArray.getJSONObject(i);
                String sectionName = currentSection.getString("sectionName");
                String webTitle = currentSection.getString("webTitle");
                String url = currentSection.getString("webUrl");
                String time = currentSection.getString("webPublicationDate");

                JSONArray tags = currentSection.getJSONArray(("tags"));

                for(int j = 0; j < tags.length(); j++) {

                    JSONObject tag = tags.getJSONObject(j);
                    String author = tag.getString("webTitle");


                    Section sections = new Section(Section.RESULT_TYPE, sectionName, webTitle, url, time, author);
                    section.add(sections);
                }
                }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the section json result", e);
        }

        return section;

    }
}
