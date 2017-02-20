package uk.ac.sussex.deliveryservice.mocks;


import uk.ac.sussex.deliveryservice.tasks.GetVehiclesTask;
import uk.ac.sussex.deliveryservice.testConfig.TestTask;

public class GetVehiclesTestTask extends GetVehiclesTask  implements TestTask {

    @Override
    protected String doInBackground(String... params) {
        return testResult;
    }

    @Override
    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }
}

