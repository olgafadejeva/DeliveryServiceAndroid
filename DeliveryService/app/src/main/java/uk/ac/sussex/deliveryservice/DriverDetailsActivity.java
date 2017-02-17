package uk.ac.sussex.deliveryservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import uk.ac.sussex.deliveryservice.model.Address;
import uk.ac.sussex.deliveryservice.model.DriverDetails;
import uk.ac.sussex.deliveryservice.model.Holiday;
import uk.ac.sussex.deliveryservice.tasks.AccessDriverDetailsTask;
import uk.ac.sussex.deliveryservice.config.DaggerApplication;
import uk.ac.sussex.deliveryservice.util.ErrorAction;

public class DriverDetailsActivity extends AppCompatActivity {

    @Inject
    AccessDriverDetailsTask accessDriverDetailsTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);
        Bundle b = getIntent().getExtras();
        String tokenString = "";
        if (b!=null) {
            final String token = b.getString("token");
            tokenString = token;
        }

        ((DaggerApplication) getApplication()).getOrCreateApplicationComponent().inject(this);
        String[] params = new String[] {tokenString};
        String detailsJson = "";
        try {
            detailsJson = accessDriverDetailsTask.execute(params).get();
        } catch (InterruptedException e) {
            ErrorAction.showErrorDialogAndFinishActivity(this);
        } catch (ExecutionException e) {
            ErrorAction.showErrorDialogAndFinishActivity(this);
        }

        if (detailsJson.equals("Error")) {
            ErrorAction.showErrorDialogAndFinishActivity(this);
        } else {
            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
            DriverDetails details = gSon.fromJson(detailsJson, DriverDetails.class);
            populateLayout(details);
        }
    }

    private void populateLayout(DriverDetails details) {
        TextView emailText = (TextView) findViewById(R.id.email);
        emailText.setText(details.getEmail());

        TextView addressText = (TextView) findViewById(R.id.address);
        Address address = details.getDriverAddress();
        String lineTwoOfAddress = address.getLineTwo() == null ? "\n" : "\n" + address.getLineTwo() + "\n";
        String addressString = address.getLineOne() + lineTwoOfAddress + address.getCity() + "\n" + address.getPostCode();
        addressText.setText(addressString);

        TableLayout tl = (TableLayout) findViewById(R.id.holidays_table);
        if (details.getHolidays()==null || details.getHolidays().isEmpty()) {
            tl.setVisibility(View.INVISIBLE);
        } else {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
            for (Holiday hol : details.getHolidays()) {
                TextView to = new TextView(this);
                to.setText(dt.format(hol.getTo()));
                to.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView from = new TextView(this);
                from.setText(dt.format(hol.getFrom()));
                from.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                tr.addView(from);
                tr.addView(to);

                tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

}
