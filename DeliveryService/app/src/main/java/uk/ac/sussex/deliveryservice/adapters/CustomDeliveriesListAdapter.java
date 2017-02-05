package uk.ac.sussex.deliveryservice.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import uk.ac.sussex.deliveryservice.R;
import uk.ac.sussex.deliveryservice.RouteDeliveriesActivity;
import uk.ac.sussex.deliveryservice.RouteInformationActivity;
import uk.ac.sussex.deliveryservice.model.Address;
import uk.ac.sussex.deliveryservice.model.Delivery;
import uk.ac.sussex.deliveryservice.model.RouteViewModel;

public class CustomDeliveriesListAdapter extends ArrayAdapter<Delivery> implements View.OnClickListener {

    private static final String UNABLE_TO_CHANGE_STATUS_MASSAGE = "Unable to change status to ";
    private static final String SUCCESSFULL_UPDATE_STATUS_MESSAGE = "Updated status to ";
    private ArrayList<Delivery> dataSet;
    Context mContext;
    private String token;
    private RouteDeliveriesActivity activity;

    private static class ViewHolder {
        TextView statusField;
        TextView itemSizeField;
        TextView itemWeightField;
        TextView deliverByDateField;
        TextView clientNameField;
        TextView clientAddressField;
        Button mapView;
        Button statusUpdateFailed;
        Button statusUpdatePickedUp;
        Button statusUpdateInTransit;
        Button statusUpdateDelivered;
    }

    public CustomDeliveriesListAdapter(ArrayList<Delivery> data, RouteDeliveriesActivity activity) {
        super(activity.getApplicationContext(), R.layout.delivery_row_item, data);
        this.dataSet = data;
        this.mContext = activity.getApplicationContext();
        this.activity = activity;

    }


    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        RouteViewModel dataModel = (RouteViewModel) object;

        switch (v.getId()) {
            case R.id.item_info:
                Snackbar.make(v, "Delivery date " + dataModel.getDeliveryDate(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();

                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Delivery dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final CustomDeliveriesListAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new CustomDeliveriesListAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.delivery_row_item, parent, false);
            viewHolder.statusField = (TextView) convertView.findViewById(R.id.delivery_status);
            viewHolder.itemSizeField = (TextView) convertView.findViewById(R.id.item_size);
            viewHolder.itemWeightField = (TextView) convertView.findViewById(R.id.item_weight);
            viewHolder.deliverByDateField = (TextView) convertView.findViewById(R.id.deliver_by);
            viewHolder.clientNameField = (TextView) convertView.findViewById(R.id.client_name);
            viewHolder.clientAddressField = (TextView) convertView.findViewById(R.id.client_address);
            viewHolder.statusUpdateFailed = (Button) convertView.findViewById(R.id.failed_delivery_button);
            viewHolder.mapView   = (Button) convertView.findViewById(R.id.map_view_button);
            viewHolder.statusUpdatePickedUp   = (Button) convertView.findViewById(R.id.status_update_picked_up_button);
            viewHolder.statusUpdateInTransit   = (Button) convertView.findViewById(R.id.status_update_in_transit_button);
            viewHolder.statusUpdateDelivered   = (Button) convertView.findViewById(R.id.status_update_delivered_button);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomDeliveriesListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        String clientNameString = dataModel.getClient().getFirstName() + " " + dataModel.getClient().getLastName();
        Address clientAddress = dataModel.getClient().getAddress();
        String lineTwoOfAddress = clientAddress.getLineTwo() == null ? "\n" : "\n" + clientAddress.getLineTwo() + "\n";
        String clientAddressString = clientAddress.getLineOne() + lineTwoOfAddress + clientAddress.getCity() + "\n" + clientAddress.getPostCode();
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        String deliverByDateString = formatter.format(dataModel.getDeliverBy());

        viewHolder.statusField.setText(dataModel.getStatusString());
        viewHolder.itemSizeField.setText(dataModel.getItemSize());
        viewHolder.itemWeightField.setText("" + dataModel.getItemWeight());
        viewHolder.deliverByDateField.setText(deliverByDateString);
        viewHolder.clientNameField.setText(clientNameString);
        viewHolder.clientAddressField.setText(clientAddressString);

        viewHolder.statusUpdatePickedUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String status = "Picked Up";
                UpdateStatusTask task = new UpdateStatusTask();
                try {
                    String result = task.execute("token", dataModel.getID() + "", "PickedUpByDriver").get();
                    if (result.equals("Fail")) {
                        createAlertDialog(UNABLE_TO_CHANGE_STATUS_MASSAGE + status);
                    } else {
                        createAlertDialog(SUCCESSFULL_UPDATE_STATUS_MESSAGE + status);
                        dataModel.setStatusString(status);
                        viewHolder.statusField.setText(dataModel.getStatusString());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        viewHolder.statusUpdateInTransit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String status = "In Transit";
                UpdateStatusTask task = new UpdateStatusTask();
                try {
                    String result = task.execute("token", dataModel.getID() + "", "InTransit").get();
                    if (result.equals("Fail")) {
                        createAlertDialog(UNABLE_TO_CHANGE_STATUS_MASSAGE + status);
                    } else {
                        createAlertDialog(SUCCESSFULL_UPDATE_STATUS_MESSAGE + status);
                        dataModel.setStatusString(status);
                        viewHolder.statusField.setText(dataModel.getStatusString());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });

        viewHolder.statusUpdateDelivered.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String status = "Delivered";
                UpdateStatusTask task = new UpdateStatusTask();
                try {
                    String result = task.execute("token", dataModel.getID() + "", "Delivered").get();
                    if (result.equals("Fail")) {
                        createAlertDialog(UNABLE_TO_CHANGE_STATUS_MASSAGE + status);
                    } else {
                        createAlertDialog(SUCCESSFULL_UPDATE_STATUS_MESSAGE + status);
                        dataModel.setStatusString(status);
                        viewHolder.statusField.setText(dataModel.getStatusString());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });

        viewHolder.statusUpdateFailed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String status = "Failed Delivery";
                UpdateStatusTask task = new UpdateStatusTask();
                try {
                    String result = task.execute("token", dataModel.getID() + "", "FailedDelivery").get();
                    if (result.equals("Fail")) {
                        createAlertDialog(UNABLE_TO_CHANGE_STATUS_MASSAGE + status);
                    } else {
                        createAlertDialog(SUCCESSFULL_UPDATE_STATUS_MESSAGE + status);
                        dataModel.setStatusString(status);
                        viewHolder.statusField.setText(dataModel.getStatusString());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        viewHolder.mapView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               /*Uri gmmIntentUri = Uri.parse("google.geo:cbll=46.414382,10.013988?z=4");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(mapIntent);*/

                double lat = dataModel.getClient().getAddress().getLat();
                double lng = dataModel.getClient().getAddress().getLng();

                String uriString = "geo:" + lat + ","+ lng + "?q=" + lat + "," + lng;
                Uri gmmIntentUri = Uri.parse(uriString);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (mapIntent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    v.getContext().startActivity(mapIntent);
                }
            }
        });

        return convertView;
    }

    private void createAlertDialog(String message) {
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


    public void setToken(String token) {
        this.token = token;
    }

    public void setActivity(RouteDeliveriesActivity activity) {
        this.activity = activity;
    }


    private class UpdateStatusTask extends AsyncTask<String, Void, String> {

        private static final String UPDATE_STATUS = "http://192.168.1.7:44302/androidActionsApi/updateStatus";
        public final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10000, TimeUnit.SECONDS)
                    .build();

            JSONObject json = new JSONObject();
            try {
                json.put("DeliveryID", params[1]);
                json.put("Status", params[2]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(JSON, json.toString());
            Request request =
                    new Request.Builder()
                            .url(UPDATE_STATUS)
                            .header("Authorization", "Bearer " + "CfDJ8J2B5ugoBktPmGyjkBvvJmQkdhz-SNXELsi4n3OhqeJ-iDHBLcpNf15H1739rgT1cNwE8UM0yd3E8uT7qWkIqOf9Wehws2PQ45LyynFO6PucPh_TbsXO7YoG8kCXvR5OumQyYehCcNmMo9xcvOqoeWm3UKATTbxr8gutk5_Kh-pLWKcgGUgCD4XDY9bdKDruOlYyF_r_6-Vg1C-yc3ywuHuGFPM-HIsbCCVnRZyIAMUhJJM2qNJAVWjYVq1g0IcOjJMfH05Ds0XCuqMT2YrO7GtnX02KcUilCxzKqJADmxFRSpZkw-EwWICY9jMx1eD1b2B35_5qyDUjQyPv0D4EcrmF65QSXo7cOIjfavlC1XznBo_UxZP062XMKNbRtLHoPjaEpWyq2eIEzyqXr53X_AtcDrH-C6HNeJa8kBRbFNL45wSeTcPbiv-nqwuNpxbyRAvVNT_mfg1SSvo9fNTyRZiBgEeWVDHaC6S0XJQ1CRTyEZaXsIwLpWtrqTmdmbXj201idJlP0-DRB3HcVYiJx5ihp1PeIgeeBG0L0o5rd1z83MF5aXL8OBmyg6SykT9airrl5WKANpKw200Z9iGLkdyHqa7LohKoDqZUDXHXCb_7IxHq4O7-GJ0BlBlF9OYtVmfaLn9XkgT6OxSyjl74eqAKAx5_YPxM3W29XqHqCWquxshvu3T7_G8Odq3nwW001ejbDUwlvfSpNp891I4XsVPXm4XxEMZTdpkNK-3iKCt76K5IUY70CgQTVUdHEo4r9AwGli8dA2NxFTaJ4VEHq7UycJHgMYPonsGKq_pbhBp9Na091B5zD8ybPUM1DjIvsSktP2jSCzpMDRHNQDCbyE38sUcf_K9g6eETY4Vy3WGxoMNds_ntvbvwt9rlfZEnqQchu8e31WS-Ok_7EGNlI9iGBIV52JhcihjWAunI8cOysCDuZAcmXU2RaRzB6Y6W61vh1zFc95LcC5vfHdrB8DGm2ZKJWnAheBWBLvM0oth346jGDyA0a28IfnfSOcbLSQ")
                            .post(body)
                            .build();

            return "ok";
            /*Response response = null;
            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return "Success";
                } else {
                    return "Fail";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            return "Fail";*/
        }


        @Override
        protected void onPostExecute(String result) {
            // textView.setText(result);
        }
    }

}

