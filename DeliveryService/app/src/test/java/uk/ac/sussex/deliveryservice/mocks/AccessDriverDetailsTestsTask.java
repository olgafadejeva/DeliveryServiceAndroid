package uk.ac.sussex.deliveryservice.mocks;


import uk.ac.sussex.deliveryservice.tasks.AccessDriverDetailsTask;
import uk.ac.sussex.deliveryservice.testConfig.TestTask;

public class AccessDriverDetailsTestsTask extends AccessDriverDetailsTask implements TestTask {

    @Override
    protected String doInBackground(String... params) {
        return testResult;
    }

    @Override
    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }
}
