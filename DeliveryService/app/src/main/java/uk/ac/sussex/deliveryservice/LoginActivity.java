package uk.ac.sussex.deliveryservice;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import uk.ac.sussex.deliveryservice.tasks.LoginTask;
import uk.ac.sussex.deliveryservice.util.DaggerApplication;
import uk.ac.sussex.deliveryservice.util.ErrorAction;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView errorMessage;
    private Button login;


    @Inject
    LoginTask loginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DaggerApplication.component().inject(this);
        setupVariables();
    }

    public void authenticateLogin(View view) throws Exception {
        String[] params = new String[] {username.getText().toString(), password.getText().toString()};
        String result = loginTask.execute(params).get();

        if ( !result.equals("Error") && !result.equals("")) {
            Intent intent = new Intent(this, MainMenuActivity.class);

            Bundle b = new Bundle();
            b.putString("token", result);
            intent.putExtras(b);
            startActivity(intent);
        } else {
            errorMessage.setText("Error occurred, please try again later");
        }
    }

    private void setupVariables() {
        username = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        errorMessage = (TextView) findViewById(R.id.error_message);
        login = (Button) findViewById(R.id.email_sign_in_button);
    }

    public void setLoginTask(LoginTask task){
        this.loginTask = task;
    }
}




