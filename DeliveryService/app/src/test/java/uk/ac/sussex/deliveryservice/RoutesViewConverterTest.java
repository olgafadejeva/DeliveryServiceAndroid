package uk.ac.sussex.deliveryservice;


import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import uk.ac.sussex.deliveryservice.model.RouteViewModel;
import uk.ac.sussex.deliveryservice.util.RouteViewConverter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class RoutesViewConverterTest {

    @Test
    public void testConversionFromJson() throws IOException {
        String json = FileUtils.readFileToString(new File("D:\\DeliveryServiceAndroid\\DeliveryService\\app\\src\\test\\java\\uk\\ac\\sussex\\deliveryservice\\resources\\routes.json"));
        ArrayList<RouteViewModel> models = RouteViewConverter.convertJsonToModels(json);
        assertEquals(3, models.size());

        RouteViewModel modelOne = models.get(0);
        assertEquals("19-11-2016", modelOne.getDeliverByDate());
        assertEquals("18-11-2016", modelOne.getDeliveryDate());
        assertEquals(1, modelOne.getDeliveries().size());
        assertNotNull(modelOne.getPickUpAddress());
        assertNotNull(modelOne.getVehicle());

        RouteViewModel modelTwo = models.get(1);
        assertEquals("29-11-2016", modelTwo.getDeliverByDate());
        assertEquals("28-11-2016", modelTwo.getDeliveryDate());
        assertEquals(1, modelTwo.getDeliveries().size());
        assertNotNull(modelTwo.getPickUpAddress());
        assertNotNull(modelTwo.getVehicle());

        RouteViewModel modelThree = models.get(2);
        assertEquals("30-11-2016", modelThree.getDeliverByDate());
        assertEquals("29-11-2016", modelThree.getDeliveryDate());
        assertEquals(1, modelThree.getDeliveries().size());
        assertNotNull(modelThree.getPickUpAddress());
        assertNotNull(modelThree.getVehicle());
    }
}
