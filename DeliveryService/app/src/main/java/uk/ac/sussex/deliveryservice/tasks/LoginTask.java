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

/*
Obtains the access token from the web app
 */
public class LoginTask extends AsyncTask<String, Void, String> {

    private static final String LOGIN_URL = "http://192.168.1.7:44302/connect/token";
    public  final MediaType MEDIA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded");
    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.SECONDS)
                .build();

        JSONObject json = new JSONObject();
        try {
            json.put("Email", params[0]);
            json.put("Password", params[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String bodyString = "grant_type=password&username=" + params[0] + "&password=" + params[1] + "&scope=openid+email+name+profile+roles";
        RequestBody body = RequestBody.create(MEDIA_TYPE, bodyString);
        Request request =
                new Request.Builder()
                        .url(LOGIN_URL)
                        .post(body)
                        .build();


        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {

                JSONObject result = null;
                String token ="";
                try {
                    result = new JSONObject(response.body().string());
                    token = result.getString("access_token");
                } catch (JSONException e) {
                    return "Error";
                }
                return token;
            } else {
                return "Error";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "Error";
    }


    @Override
    protected void onPostExecute(String result) {
        // textView.setText(result);
    }
}


