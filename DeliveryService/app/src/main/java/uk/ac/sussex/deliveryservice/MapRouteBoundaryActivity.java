package uk.ac.sussex.deliveryservice;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import uk.ac.sussex.deliveryservice.model.Address;
import uk.ac.sussex.deliveryservice.model.Client;
import uk.ac.sussex.deliveryservice.model.Delivery;
import uk.ac.sussex.deliveryservice.model.DrivingInstruction;
import uk.ac.sussex.deliveryservice.model.PickUpAddress;
import uk.ac.sussex.deliveryservice.model.RouteViewModel;
import uk.ac.sussex.deliveryservice.util.JSONParser;

/*
Draws a route on the map, idea taken from: http://stackoverflow.com/questions/17425499/how-to-draw-interactive-polyline-on-route-google-maps-v2-android
 */
public class MapRouteBoundaryActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private RouteViewModel route;
    private ArrayList<DrivingInstruction> instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_route_boundary);
        Intent i = getIntent();
        route = (RouteViewModel)i.getSerializableExtra("route");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(6.0f);
        mMap.setMaxZoomPreference(14.0f);


        PickUpAddress address = route.getPickUpAddress();
        LatLng depotLocation = new LatLng(route.getPickUpAddress().getLat(), route.getPickUpAddress().getLng());
        createMarker(route.getPickUpAddress().getLat(), route.getPickUpAddress().getLng(), address.getLineOne(), true, address.getPostCode());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(depotLocation));


        ArrayList<Delivery> markersArray = route.getDeliveries();


        for (int i = 0; i < markersArray.size(); i++) {

            double lat = markersArray.get(i).getClient().getAddress().getLat();
            double longitude = markersArray.get(i).getClient().getAddress().getLng();
            Address clientAddress = markersArray.get(i).getClient().getAddress();
            String clientAddressString = clientAddress.getLineOne();
            String snippet = clientAddress.getPostCode();
            createMarker(lat, longitude, clientAddressString, snippet);
        }
        useGoogleMaps();

    }

    protected void createMarker(double latitude, double longitude, String title, String snippet) {

         mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet));
    }

    protected void createMarker(double latitude, double longitude, String title, boolean isDepot, String snippet) {

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.truck)));
    }

    private String getMapsApiDirectionsUrl() {

        double depotLat = route.getPickUpAddress().getLat();
        double depotLng = route.getPickUpAddress().getLng();
        String origin = "origin=" + depotLat + "," + depotLng;
        String waypoints = "waypoints=optimize:true|" + getWaypointsStringFromDeliveries(route.getDeliveries());
        String destination = "destination=" + depotLat + "," + depotLng;

        String params = origin + "&" + waypoints + "&"  + destination;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"
                + output + "?" + params;
        return url;
    }

    private String getWaypointsStringFromDeliveries(ArrayList<Delivery> deliveries) {
        String waypoints = "";
        for (Delivery del : deliveries) {
            double lat = del.getClient().getAddress().getLat();
            double lng = del.getClient().getAddress().getLng();
            waypoints += lat +"," + lng + "|";
        }
        return waypoints;
    }

    public void useGoogleMaps() {
        JSONParser parser = new JSONParser();
        String json = null;
        try {
            json = parser.execute(getMapsApiDirectionsUrl()).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        drawPath(json);
    }

    public void drawPath(String result) {

        try {
            final JSONObject json = new JSONObject(result);
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);

            JSONArray legs = routes.getJSONArray("legs");
            instructions = new ArrayList<>();
            for (int i =0; i<legs.length(); i++) {
                JSONObject leg = legs.getJSONObject(i);
                JSONArray steps = leg.getJSONArray("steps");
                DrivingInstruction instruction = new DrivingInstruction();
                for (int k =0; k< steps.length(); k++) {
                    JSONObject step = steps.getJSONObject(k);
                    String guide = step.get("html_instructions").toString();
                    instruction.getDirections().add(guide);
                }
                instructions.add(instruction);
            }

           legs.getJSONObject(0).getJSONArray("steps").getJSONObject(0).get("html_instructions");


            //routes > legs > steps > html_nstructions
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .addAll(list)
                    .width(12)
                    .color(Color.parseColor("#05b1fb"))
                    .geodesic(true)
            );

        } catch (JSONException e) {

        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
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

    public void showDirections(View view) {
        Intent intent = new Intent(MapRouteBoundaryActivity.this, RouteDirectionsActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("directions", instructions);
        intent.putExtras(b);
        startActivity(intent);
    }
}
