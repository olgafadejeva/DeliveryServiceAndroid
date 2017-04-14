package uk.ac.sussex.deliveryservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import uk.ac.sussex.deliveryservice.adapters.MainMenuListAdapter;

/*
Generates the main menu accessed when a user has been successfully logged in
 */
public class MainMenuActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main_menu);
        Bundle b = getIntent().getExtras();
        String tokenString = "";
        if (b!=null) {
            tokenString = b.getString("token");
        }


        ArrayList<String> values = new ArrayList<>();
        values.add("My routes");
        values.add("My vehicles");
        values.add("My details");



        final MainMenuListAdapter adapter =  new MainMenuListAdapter(values, getApplicationContext());
        ListView listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

        final String finalTokenString = tokenString;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String item = adapter.getItem(position);
                if (item.contains("routes")) {
                    Intent intent = new Intent(MainMenuActivity.this, RoutesActivity.class);
                    Bundle b = new Bundle();
                    b.putString("token", finalTokenString);
                    intent.putExtras(b);
                    startActivity(intent);
                }

                if (item.contains("vehicles")) {
                    Intent intent = new Intent(MainMenuActivity.this, VehiclesActivity.class);
                    Bundle b = new Bundle();
                    b.putString("token", finalTokenString);
                    intent.putExtras(b);
                    startActivity(intent);
                }

                if (item.contains("details")) {
                    Intent intent = new Intent(MainMenuActivity.this, DriverDetailsActivity.class);
                    Bundle b = new Bundle();
                    b.putString("token", finalTokenString);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });
    }
}

