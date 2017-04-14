package uk.ac.sussex.deliveryservice.util;
import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
Calls GoogleMaps API
 */
public class JSONParser extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Request request =
                new Request.Builder()
                        .url(strings[0])
                        .get()
                        .build();


        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Fail";
    }
}