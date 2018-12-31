package com.yourbroduke.android.hleper;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.yourbroduke.android.hleper.data.HleperUser;
import com.yourbroduke.android.hleper.data.ListOrderItem;
import com.yourbroduke.android.hleper.data.OrderDetailItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private static OrderDetailItem extractSpecificOrderFromJson(String orderJSON) {
        if (TextUtils.isEmpty(orderJSON)) {
            return null;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(orderJSON);
            JSONObject metaData = baseJsonResponse.getJSONObject("metadata");
            JSONObject orderInfo = baseJsonResponse.getJSONObject("order_info");

            boolean dataValid = metaData.getBoolean("valid");

            if (dataValid) {
                int userID = orderInfo.getInt("user_id");
                String userName = orderInfo.getString("user_name");
                String detail = orderInfo.getString("detail");
                int orderID = orderInfo.getInt("order_id");
                int type = orderInfo.getInt("type");
                int max_num = orderInfo.getInt("max_num");
                int crt_num = orderInfo.getInt("current_num");
                double reward = orderInfo.getDouble("reward");
                String title = orderInfo.getString("title");
                String description = orderInfo.getString("description");

                return new OrderDetailItem(userName, userID, detail, orderID, type, max_num, crt_num, reward, title, description);
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HleperUser extractUserDataFromJson(String userJSON) {
        if (TextUtils.isEmpty(userJSON)) {
            return null;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(userJSON);
            JSONObject userInfo = baseJsonResponse.getJSONObject("user");
            boolean dataValid = baseJsonResponse.getBoolean("valid");

            if (dataValid) {
                int userID = userInfo.getInt("id");
                String userName = userInfo.getString("name");
                String userEmail = userInfo.getString("email");
                String userPhone = userInfo.getString("phone");
                double userBalance = userInfo.getDouble("balance");

                return new HleperUser(userID, userName, userEmail, userPhone, (float)userBalance);
            } else {
                return new HleperUser();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    static List<ListOrderItem> fetchOrdersData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        jsonResponse = makeHttpRequest(url);
        Log.d(LOG_TAG, jsonResponse);
        return extractOrdersFromJson(jsonResponse);
    }

    static OrderDetailItem fetchSpecificData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        jsonResponse = makeHttpRequest(url);
        Log.d(LOG_TAG, jsonResponse);
        return extractSpecificOrderFromJson(jsonResponse);
    }

    static HleperUser fetchUserData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;

        jsonResponse = makeHttpRequest(url);
        Log.d(LOG_TAG, jsonResponse);
        return extractUserDataFromJson(jsonResponse);
    }

    static OrderDetailItem postOrderPlus(String requestUrl) {
        URL url = createUrl(requestUrl);

        String response;
        response = makeHttpRequest(url);

        if (response.equals("Success"))
            return new OrderDetailItem(-1, -1);
        else
            return new OrderDetailItem(-1, -2);
    }

    static OrderDetailItem postOrderDone(String requestUrl) {
        URL url = createUrl(requestUrl);

        String response = null;
        response = makeHttpRequest(url);

        if (response.equals("Success"))
            return new OrderDetailItem(-2, -1);
        else
            return new OrderDetailItem(-2, -2);
    }

    static String postOrderData(String postUrl, Map<String, String> params) {
        URL url = createUrl(postUrl);

        try {
            return makeHttpPost(url, params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

            Log.i("YQFragment", "Response code" + urlConnection.getResponseCode());
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

    private static String makeHttpPost(URL url, Map<String, String> params) throws IOException {

        StringBuilder postData = new StringBuilder();

        for (Map.Entry<String,String> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;)
            sb.append((char)c);
        return sb.toString();
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
