package uk.ac.sussex.deliveryservice.tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetRoutesTask extends AsyncTask<String, Void, String> {

    private static final String ROUTES_URL = "http://192.168.1.7:44302/androidActionsApi/routes";
    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.SECONDS)
                .build();

        Request request =
                new Request.Builder()
                        .url(ROUTES_URL)
                        .header("Authorization", "Bearer " + params[0])
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