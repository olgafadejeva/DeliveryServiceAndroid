package uk.ac.sussex.deliveryservice.config;

import android.support.multidex.MultiDexApplication;

import uk.ac.sussex.deliveryservice.config.*;

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

    public DaggerGraphComponent getOrCreateApplicationComponent() {
        if (graph == null) {
            return DaggerDaggerGraphComponent.builder()
                    .mainModule(new MainModule(this))
                    .build();
        }
        return graph;
    }
}