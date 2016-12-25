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

        if (result.equals("Ok")) {
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        }
    }

    private void setupVariables() {
        username = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.email_sign_in_button);
    }
    private class LoginTask extends AsyncTask<String, Void, String> {

        private static final String LOGIN_URL = "http://192.168.1.7:44302/androidapi/login";
        public  final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .build();

            JSONObject json = new JSONObject();
            try {
                json.put("Email", params[0]);
                json.put("Password", params[1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(JSON, json.toString());
            Request request =
                    new Request.Builder()
                            .url(LOGIN_URL)
                            .post(body)
                            .build();


            Response response = null;
            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return "Ok";
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
