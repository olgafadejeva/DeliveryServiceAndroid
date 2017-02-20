package uk.ac.sussex.deliveryservice.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import uk.ac.sussex.deliveryservice.DeliveryServiceActivity;

public class ErrorAction {

    public static void showErrorDialogAndFinishActivity(final DeliveryServiceActivity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("Error occurred");


        alertDialogBuilder
                .setMessage("Unknown error, please try again later ")
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                        activity.finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        activity.setLastDialog(alertDialog);
        alertDialog.show();
    }
}
