package uk.ac.sussex.deliveryservice;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupVariables();
    }

    public void authenticateLogin(View view) throws Exception {
        LoginTask task = new LoginTask();
        String[] params = new String[] {username.getText().toString(), password.getText().toString()};
        String result = task.execute(params).get();

        if (!result.equals("") || !result.equals("Fail")) {
            Intent intent = new Intent(this, RoutesActivity.class);

            Bundle b = new Bundle();
            b.putString("key", result);
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
            finish();
        }
    }

    private void setupVariables() {
        username = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.email_sign_in_button);
    }
    private class LoginTask extends AsyncTask<String, Void, String> {

        private static final String LOGIN_URL = "http://192.168.1.7:44302/connect/token";
        public  final MediaType JSON
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
            RequestBody body = RequestBody.create(JSON, "grant_type=password&username=rebecca@gmail.com&password=aaa123&scope=openid+email+name+profile+roles");
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
                        e.printStackTrace();
                    }
                    System.out.println(result);
                    return token;
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
