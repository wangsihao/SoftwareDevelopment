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
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class AddEditUserActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> AdminMainActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private String mStringAdmin;

    private String mStringFirstName;

    private String mStringLastName;

    private String mStringUsername;

    @Before
    public void initValidStrings() {
        mStringAdmin = "Admin";
        mStringFirstName = "First";
        mStringLastName = "Last";
        // To make the tests run successfully, you need to make an unique username
        mStringUsername = "name5";
    }

    @Test
    public void Cancel() {
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringAdmin), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        onView(withText("USERS")).perform(click());

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.admin_addedit_user_cancel)).check(matches(isClickable()));

        onView(withId(R.id.admin_addedit_user_cancel)).perform(click());

    }

    @Test
    public void Submit_Full_Information() {
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringAdmin), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        onView(withText("USERS")).perform(click());

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.admin_addedit_user_submit)).check(matches(isClickable()));

        onView(withId(R.id.admin_addedit_user_firstname))
                .perform(typeText(mStringFirstName), closeSoftKeyboard());

        onView(withId(R.id.admin_addedit_user_lastname))
                .perform(typeText(mStringLastName), closeSoftKeyboard());

        onView(withId(R.id.admin_addedit_user_username))
                .perform(typeText(mStringUsername), closeSoftKeyboard());

        onView(withId(R.id.admin_addedit_user_submit)).perform(click());

        onView(withText("Confirmation")).check(matches(isDisplayed()));
    }
    @Test
    public void Submit_Full_Information2() {
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringAdmin), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        onView(withText("USERS")).perform(click());

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.admin_addedit_user_submit)).check(matches(isClickable()));

        onView(withId(R.id.admin_addedit_user_firstname))
                .perform(typeText(mStringFirstName), closeSoftKeyboard());

        onView(withId(R.id.admin_addedit_user_username))
                .perform(typeText(mStringUsername), closeSoftKeyboard());

        onView(withId(R.id.admin_addedit_user_submit)).perform(click());

        onView(withText("Input Validation")).check(matches(isDisplayed()));
    }

    @Test
    public void Submit_Full_Information3() {
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringAdmin), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        onView(withText("USERS")).perform(click());

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.admin_addedit_user_submit)).check(matches(isClickable()));

        onView(withId(R.id.admin_addedit_user_lastname))
                .perform(typeText(mStringLastName), closeSoftKeyboard());

        onView(withId(R.id.admin_addedit_user_username))
                .perform(typeText(mStringUsername), closeSoftKeyboard());

        onView(withId(R.id.admin_addedit_user_submit)).perform(click());

        onView(withText("Input Validation")).check(matches(isDisplayed()));
    }
    @Test
    public void Do_Not_Submit_Information() {
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringAdmin), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        onView(withText("USERS")).perform(click());

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.admin_addedit_user_submit)).check(matches(isClickable()));

        onView(withId(R.id.admin_addedit_user_submit)).perform(click());

        onView(withText("Input Validation")).check(matches(isDisplayed()));

    }

}