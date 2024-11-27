package com.example.codeit.Model;

import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ApiClient {

    private static final String BASE_URL = " http://192.168.241.98:8080/api/projects/checkSubstring";

public static void sendStringToServer(String providedString, final ApiCallback callback) {
    OkHttpClient client = new OkHttpClient();

    // Create a JSON object to send the provided string
    JSONObject jsonObject = new JSONObject();
    try {
        jsonObject.put("providedString", providedString);
    } catch (JSONException e) {
        e.printStackTrace();
    }

    // Create the request body containing the JSON object
    RequestBody body = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json"));

    // Build the POST request
    Request request = new Request.Builder()
            .url(BASE_URL)
            .post(body)
            .build();

    // Make the request asynchronously
    client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            // Handle failure
            e.printStackTrace();
            // You can call callback.onError() or any error handling method if needed
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                // Read the response from the Spring Boot server
                String responseBody = response.body().string();
                try {
                    // Parse the JSON response
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    callback.onSuccess(jsonResponse);  // Pass the response to the callback
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Handle error
                System.out.println("Request failed with status code: " + response.code());
            }
        }
    });
}

    // Callback interface for handling the API response
    public interface ApiCallback {
        void onSuccess(JSONObject jsonResponse);  // Success callback with the parsed JSON response

        void onError(Exception e);  // Error callback for handling errors (optional)
    }
}


