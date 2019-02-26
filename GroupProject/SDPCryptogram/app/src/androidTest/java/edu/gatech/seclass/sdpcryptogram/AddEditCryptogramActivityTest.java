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
public class AddEditCryptogramActivityTest {
    @Rule

    public ActivityTestRule<LoginActivity> AdminMainActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private String mStringAdmin;

    private String mStringCryptogram;

    private String mStringSolution;

    private String mStringCryptogram2;

    private String mStringSolution2;

    private String mStringCryptogram3;

    private String mStringSolution3;

    @Before
    public void initValidStrings() {
        mStringAdmin = "Admin";
        mStringCryptogram = "Gatech";
        mStringSolution = "Hbufdg";
        mStringCryptogram2 = "123";
        mStringSolution2 = "123";
        mStringCryptogram3 = "Gatech123";
        mStringSolution3 = "Hbufdg123";
    }

    @Test
    public void onCancel() {
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringAdmin), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.admin_addedit_crypt_cancel)).check(matches(isClickable()));

        onView(withId(R.id.admin_addedit_crypt_cancel)).perform(click());


    }

    @Test
    public void onSubmit1() {
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringAdmin), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.admin_addedit_crypt_submit)).check(matches(isClickable()));

        onView(withId(R.id.admin_addedit_crypt_cleartext))
                .perform(typeText(mStringCryptogram), closeSoftKeyboard());

        onView(withId(R.id.admin_addedit_crypt_ciphertext))
                .perform(typeText(mStringSolution), closeSoftKeyboard());

        onView(withId(R.id.admin_addedit_crypt_submit)).perform(click());

        onView(withText("Confirmation")).check(matches(isDisplayed()));
    }
    @Test
    public void onSubmit2() {
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringAdmin), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.admin_addedit_crypt_submit)).check(matches(isClickable()));

        onView(withId(R.id.admin_addedit_crypt_cleartext))
                .perform(typeText(mStringCryptogram2), closeSoftKeyboard());

        onView(withId(R.id.admin_addedit_crypt_ciphertext))
                .perform(typeText(mStringSolution2), closeSoftKeyboard());

        onView(withId(R.id.admin_addedit_crypt_submit)).perform(click());

        onView(withText("Input Validation")).check(matches(isDisplayed()));
    }
    @Test
    public void onSubmit3() {
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringAdmin), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.admin_addedit_crypt_submit)).check(matches(isClickable()));

        onView(withId(R.id.admin_addedit_crypt_cleartext))
                .perform(typeText(mStringCryptogram3), closeSoftKeyboard());

        onView(withId(R.id.admin_addedit_crypt_ciphertext))
                .perform(typeText(mStringSolution3), closeSoftKeyboard());

        onView(withId(R.id.admin_addedit_crypt_submit)).perform(click());

        onView(withText("Confirmation")).check(matches(isDisplayed()));
    }
    @Test
    public void onSubmit4() throws Exception {
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringAdmin), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.admin_addedit_crypt_submit)).check(matches(isClickable()));

        onView(withId(R.id.admin_addedit_crypt_submit)).perform(click());

        onView(withText("Input Validation")).check(matches(isDisplayed()));

    }
}