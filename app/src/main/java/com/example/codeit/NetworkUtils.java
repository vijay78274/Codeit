package com.example.codeit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import org.json.JSONObject;

public class NetworkUtils {

    private static final String BASE_URL = "https://c896-2409-4085-8e83-743a-cd73-394-518a-a8c5.ngrok-free.app" ; // Or your backend URL

    // Initialize OkHttp client
    private static OkHttpClient client = new OkHttpClient();

    // Method to make a POST request
    public static void submitRepository(String repoUrl, Callback callback) {
        String url = BASE_URL + "/api/projects/submit";
        JSONObject json = new JSONObject();
        try {
            json.put("repoUrl", repoUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // Make the request asynchronously
        client.newCall(request).enqueue(callback);
    }
}

