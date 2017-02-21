package uk.ac.sussex.deliveryservice.tasks;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateStatusTask extends AsyncTask<String, Void, String> {

    private static final String UPDATE_STATUS = "http://192.168.1.7:44302/api/statusUpdate";
    public final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.SECONDS)
                .build();

        JSONObject json = new JSONObject();
        try {
            json.put("DeliveryID", params[1]);
            json.put("Status", params[2]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request =
                new Request.Builder()
                        .url(UPDATE_STATUS)
                        .header("Authorization", "Bearer " + params[0])
                        .post(body)
                        .build();


            Response response = null;
            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return "Success";
                } else {
                    return "Error";
                }
            } catch (IOException e) {
                return "Error";
            }
    }

}