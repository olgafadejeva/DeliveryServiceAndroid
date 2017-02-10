package uk.ac.sussex.deliveryservice.tasks;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UpdateStatusTask extends AsyncTask<String, Void, String> {

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