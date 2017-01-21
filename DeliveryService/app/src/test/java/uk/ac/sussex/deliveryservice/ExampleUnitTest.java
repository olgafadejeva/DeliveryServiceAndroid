package uk.ac.sussex.deliveryservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import uk.ac.sussex.deliveryservice.model.Route;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

       String json = FileUtils.readFileToString(new File("D:\\DeliveryServiceAndroid\\DeliveryService\\app\\src\\test\\java\\uk\\ac\\sussex\\deliveryservice\\routes.json"));

        Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        List<Route> routes = gSon.fromJson(json,  new TypeToken<ArrayList<Route>>() {
        }.getType());
        System.out.println(routes.size());
        assertEquals(4, 2 + 2);
    }
}