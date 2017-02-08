package uk.ac.sussex.deliveryservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import uk.ac.sussex.deliveryservice.model.Address;
import uk.ac.sussex.deliveryservice.model.DriverDetails;
import uk.ac.sussex.deliveryservice.model.Holiday;

public class DriverDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);
        DriverDetails details = new DriverDetails();
        details.setEmail("Email@email.com");
        Address address = new Address();
        address.setCity("Brighton");
        address.setLineOne("sdsdasd");
        address.setPostCode("BN2 3QF");
        details.setAddress(address);

        populateLayout(details);
    }

    private void populateLayout(DriverDetails details) {
        TextView emailText = (TextView) findViewById(R.id.email);
        emailText.setText(details.getEmail());

        TextView addressText = (TextView) findViewById(R.id.address);
        Address address = details.getAddress();
        String lineTwoOfAddress = address.getLineTwo() == null ? "\n" : "\n" + address.getLineTwo() + "\n";
        String addressString = address.getLineOne() + lineTwoOfAddress + address.getCity() + "\n" + address.getPostCode();
        addressText.setText(addressString);

        TableLayout tl = (TableLayout) findViewById(R.id.holidays_table);
        if (details.getHolidays()==null || details.getHolidays().isEmpty()) {
            tl.setVisibility(View.INVISIBLE);
        } else {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            for (Holiday hol : details.getHolidays()) {
                TextView to = new TextView(this);
                to.setText(hol.getTo());
                to.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView from = new TextView(this);
                from.setText(hol.getFrom());
                from.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                tr.addView(from);
                tr.addView(to);

                tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }
    }
}
