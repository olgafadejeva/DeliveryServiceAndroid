package uk.ac.sussex.deliveryservice;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainMenuActivity extends ListActivity {
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
       String[] values = new String[] { "My routes", "My vehicles", "My details" };

        setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_main_menu, R.id.listView, values));

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
        Toast.makeText(this, "Success login", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        if (item.contains("routes")) {
            Intent intent = new Intent(this, RoutesActivity.class);
            startActivity(intent);
        }
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    }

}
