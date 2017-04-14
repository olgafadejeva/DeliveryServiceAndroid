package uk.ac.sussex.deliveryservice.util;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.concurrent.ExecutionException;

import uk.ac.sussex.deliveryservice.RouteDeliveriesActivity;
import uk.ac.sussex.deliveryservice.model.Delivery;
import uk.ac.sussex.deliveryservice.tasks.UpdateStatusTask;

/*
Calls the Task to update delivery status and generates an update dialog based on the results
 */
public class DeliveryStatusUpdateHelper {


    private static final String UNABLE_TO_CHANGE_STATUS_MASSAGE = "Unable to change status to ";
    private static final String SUCCESSFUL_UPDATE_STATUS_MESSAGE = "Updated status to ";

    public static void updateDeliveryStatus(String statusString, String displayStatusString, String token, Delivery dataModel, RouteDeliveriesActivity activity, UpdateStatusTask task) {
        try {
            String result = task.execute(token, dataModel.getID() + "", statusString).get();
            if (result.equals("Error")) {
                createAlertDialog(UNABLE_TO_CHANGE_STATUS_MASSAGE + displayStatusString, activity);
            } else {
                createAlertDialog(SUCCESSFUL_UPDATE_STATUS_MESSAGE + displayStatusString, activity);
                dataModel.setStatusString(displayStatusString);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    private static void createAlertDialog(String message, RouteDeliveriesActivity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        // set title
        alertDialogBuilder.setTitle("Status update message");

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
