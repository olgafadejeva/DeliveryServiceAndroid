package uk.ac.sussex.deliveryservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import uk.ac.sussex.deliveryservice.model.Route;

public class RoutesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        Bundle b = getIntent().getExtras();
        String key = "";
        if(b != null) {
            key = b.getString("key");
        }

        getRoutes(key);
    }

    public void getRoutes(String token) {
        GetRoutesTask task = new GetRoutesTask();
        try {
            String result = task.execute(token).get();
            if (!result.equals("Fail")) {
                Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                List<Route> routes = gSon.fromJson(result,  new TypeToken<ArrayList<Route>>() {}.getType());
                System.out.println(routes.size());
                //display routes
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class GetRoutesTask extends AsyncTask<String, Void, String> {

        private static final String LOGIN_URL = "http://192.168.1.7:44302/androidActionsApi/routes";
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10000, TimeUnit.SECONDS)
                    .build();

            Request request =
                    new Request.Builder()
                            .url(LOGIN_URL)
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


        @Override
        protected void onPostExecute(String result) {
            // textView.setText(result);
        }
    }





}
