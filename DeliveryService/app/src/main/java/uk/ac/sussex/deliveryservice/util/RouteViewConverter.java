package uk.ac.sussex.deliveryservice.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import uk.ac.sussex.deliveryservice.model.Delivery;
import uk.ac.sussex.deliveryservice.model.Route;
import uk.ac.sussex.deliveryservice.model.RouteViewModel;

public class RouteViewConverter {
    public static ArrayList<RouteViewModel> convertJsonToModels(String json) {
        Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        List<Route> routes = gSon.fromJson(json,  new TypeToken<ArrayList<Route>>() {}.getType());
        ArrayList<RouteViewModel> models = new ArrayList<>();
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        for (Route route : routes) {
            RouteViewModel model = new RouteViewModel();
            model.setDeliveryDate(formatter.format(route.getDeliveryDate()));
            model.setID(route.getID());
            model.setStatus(route.getRouteStatusString());
            model.setDeliveries((ArrayList<Delivery>) route.getDeliveries());
            model.setDeliverByDate(formatter.format(route.getDeliverBy()));
            model.setOverallDistance(route.getOverallDistance());
            model.setOverallTime(route.getOverallTimeRequired());
            model.setPickUpAddress(route.getPickUpAddress());
            model.setVehicle(route.getVehicle());
            models.add(model);
        }
        return models;
    }
}
