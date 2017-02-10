package uk.ac.sussex.deliveryservice.tasks;


import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccessDriverDetailsTask extends AsyncTask<String, Void, String> {

    private static final String BASE_URL = "http://192.168.1.7:44302/androidApiActions/driverDetails";
    public  final MediaType JSON
            = MediaType.parse("application/x-www-form-urlencoded");
    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.SECONDS)
                .build();

        Request request =
                new Request.Builder()
                        .url(BASE_URL)
                        .get()
                        .build();


        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "Fail";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Fail";
    }

}
