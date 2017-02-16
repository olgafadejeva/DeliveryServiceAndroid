package uk.ac.sussex.deliveryservice;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import uk.ac.sussex.deliveryservice.tasks.LoginTask;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

/**
 * Instrumentation test, which tests LoginActivity for correct behaviour
 */

@RunWith(AndroidJUnit4.class)
public class LoginActivityInstrumentedTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule
            = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testLoginErrorReturnedFromApiErrorMessageDisplayed() {
        mActivityRule.getActivity().setLoginTask(new LoginTaskError());
        onView(withId(R.id.email))
                .perform(typeText("jake@gmail.com"), closeSoftKeyboard());

        onView(withId(R.id.password))
                .perform(typeText("password123"), closeSoftKeyboard());

        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.error_message)).check(matches(withText("Error occurred, please try again later")));
    }

    @Test
    public void testLoginSuccessNewActivityStarted() {
        mActivityRule.getActivity().setLoginTask(new LoginTaskSuccess());
        onView(withId(R.id.email))
                .perform(typeText("jake@gmail.com"), closeSoftKeyboard());

        onView(withId(R.id.password))
                .perform(typeText("password123"), closeSoftKeyboard());


        Intents.init();
        onView(withId(R.id.email_sign_in_button)).perform(click());

        intended(hasComponent(MainMenuActivity.class.getName()));
        Intents.release();
    }

}

class LoginTaskError extends LoginTask {

    @Override
    protected String doInBackground(String... params) {
        return "Error";
    }
}

class LoginTaskSuccess extends LoginTask {

    @Override
    protected String doInBackground(String... params) {
        return "tokenString";
    }
}