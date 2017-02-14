package uk.ac.sussex.deliveryservice.tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GetVehiclesTask extends AsyncTask<String, Void, String> {

    private static final String VEHICLES_URL = "http://192.168.1.7:44302/androidActionsApi/vehicles";
    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        Request request =
                new Request.Builder()
                        .url(VEHICLES_URL)
                       // .header("Authorization", "Bearer " + params[0])
                        .get()
                        .build();


        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                return "Error";
            }
        } catch (IOException e) {
           // e.printStackTrace();
            return "Error";
        }


       // return "Error";
    }
}
