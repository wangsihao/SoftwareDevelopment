package edu.gatech.seclass.sdpcryptogram;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> AdminMainActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private String mStringPlayer;

    private String mStringAdmin;

    private String mStringWrongUsername;

    @Before
    public void initValidStrings() {

        mStringAdmin = "Admin";
        mStringPlayer = "Player";
        mStringWrongUsername = "Username";
    }

    @Test
    public void userLogin() throws Exception {
        onView(withId(R.id.button)).check(matches(isClickable()));

        onView(withId(R.id.button)).perform(click());

        //onView(withText("Invalid Login")).check(matches(isDisplayed()));
    }

    @Test
    public void admin_login() {
        // Type a valid string and click on the button.
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringAdmin), closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());

    }

    @Test
    public void player_login() {
        // Type a valid string and click on the button.
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringPlayer), closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());

    }

    @Test
    public void wrong_username_login() {
        // Type a valid string and click on the button.
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringWrongUsername), closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());

        onView(withText("Invalid Login")).check(matches(isDisplayed()));
    }

}

