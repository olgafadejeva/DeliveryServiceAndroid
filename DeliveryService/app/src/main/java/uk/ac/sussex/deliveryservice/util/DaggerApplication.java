package uk.ac.sussex.deliveryservice.util;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

public class DaggerApplication extends MultiDexApplication {
    private static DaggerGraphComponent graph;
    private static DaggerApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        buildComponentGraph();
    }
    public static DaggerGraphComponent component() {
        return graph;
    }
    public static void buildComponentGraph() {
        graph = DaggerGraphComponent.Initializer.init(instance);
    }
}