package uk.ac.sussex.deliveryservice;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class DeliveryServiceActivity extends AppCompatActivity{

    protected AlertDialog lastDialog;

    public void setLastDialog(AlertDialog dialog) {
        this.lastDialog = dialog;
    }

    public AlertDialog getLastDialog() {
        return lastDialog;
    }
}
