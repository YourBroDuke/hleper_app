package com.yourbroduke.android.hleper;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.yourbroduke.android.hleper.data.ListOrderItem;

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
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private final static String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() { }

    private static List<ListOrderItem> extractOrdersFromJson(String orderJSON) {
        if (TextUtils.isEmpty(orderJSON)) {
            return null;
        }

        List<ListOrderItem> orders = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(orderJSON);
            JSONObject metaData = baseJsonResponse.getJSONObject("metadata");
            boolean dataValid = metaData.getBoolean("valid");
            if (dataValid) {
                int total = metaData.getInt("total");
                JSONArray ordersArray = baseJsonResponse.getJSONArray("orders");
                for (int i = 0; i < total; i++) {
                    JSONObject currentOrder = ordersArray.getJSONObject(i);

                    int ID = currentOrder.getInt("order_id");
                    int type = currentOrder.getInt("type");
                    int totalPeople = currentOrder.getInt("max_num");
                    int currentPeople = currentOrder.getInt("crt_num");
                    double reward = currentOrder.getDouble("reward");
                    String title = currentOrder.getString("title");
                    String description = currentOrder.getString("description");

                    ListOrderItem orderItem = new ListOrderItem(ID, type, totalPeople, currentPeople, reward, title, description);
                    orders.add(orderItem);
                }
            }
            else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return orders;
    }

    static List<ListOrderItem> fetchOrdersData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        jsonResponse = makeHttpRequest(url);
        Log.d(LOG_TAG, jsonResponse);
        return extractOrdersFromJson(jsonResponse);
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    private static String makeHttpRequest(URL url) {
        String jsonResponse = "";

        if (url == null) return jsonResponse;

        HttpURLConnection urlConnection;
        InputStream inputStream;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
