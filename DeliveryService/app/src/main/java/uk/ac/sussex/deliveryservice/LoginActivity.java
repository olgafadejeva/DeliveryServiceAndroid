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
import uk.ac.sussex.deliveryservice.tasks.LoginTask;

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
        }
    }

    private void setupVariables() {
        username = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.email_sign_in_button);
    }

}




