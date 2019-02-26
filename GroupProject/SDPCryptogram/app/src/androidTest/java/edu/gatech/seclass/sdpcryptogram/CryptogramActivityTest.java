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
public class CryptogramActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> AdminMainActivityTestRule = new ActivityTestRule<LoginActivity>(LoginActivity.class);

    private String mStringPlayer;
    private String mStringA;
    private String mStringB;
    private String mStringC;
    private String mStringD;
    private String mStringE;
    private String mStringF;
    private String mStringG;
    private String mStringH;
    private String mStringI;
    private String mStringJ;
    private String mStringK;
    private String mStringL;
    private String mStringM;
    private String mStringN;
    private String mStringO;
    private String mStringP;
    private String mStringQ;
    private String mStringR;
    private String mStringS;
    private String mStringT;
    private String mStringU;
    private String mStringV;
    private String mStringW;
    private String mStringX;
    private String mStringY;
    private String mStringZ;




    @Before
    public void initValidStrings() {
        mStringPlayer = "Player";
        mStringA = "A";
        mStringB = "B";
        mStringC = "C";
        mStringD = "D";
        mStringE = "E";
        mStringF = "F";
        mStringG = "G";
        mStringH = "H";
        mStringI = "I";
        mStringJ = "J";
        mStringK = "K";
        mStringL = "L";
        mStringM = "M";
        mStringN = "N";
        mStringO = "O";
        mStringP = "P";
        mStringQ = "Q";
        mStringR = "R";
        mStringS = "S";
        mStringT = "T";
        mStringU = "U";
        mStringV = "V";
        mStringW = "W";
        mStringX = "X";
        mStringY = "Y";
        mStringZ = "Z";


    }

    // The tests may be failed if the cryptogram is solved
    @Test
    public void onSubmit() throws Exception {
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringPlayer), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        onView(withText("Hbufdg")).perform(click());

        onView(withId(R.id.cryptogram_textinput_A))
                .perform(typeText(mStringZ), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_B))
                .perform(typeText(mStringA), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_C))
                .perform(typeText(mStringB), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_D))
                .perform(typeText(mStringC), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_E))
                .perform(typeText(mStringD), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_F))
                .perform(typeText(mStringE), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_G))
                .perform(typeText(mStringH), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_H))
                .perform(typeText(mStringG), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_I))
                .perform(typeText(mStringF), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_J))
                .perform(typeText(mStringI), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_K))
                .perform(typeText(mStringJ), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_L))
                .perform(typeText(mStringK), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_M))
                .perform(typeText(mStringL), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_N))
                .perform(typeText(mStringM), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_O))
                .perform(typeText(mStringN), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_P))
                .perform(typeText(mStringO), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_Q))
                .perform(typeText(mStringP), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_R))
                .perform(typeText(mStringQ), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_S))
                .perform(typeText(mStringR), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_T))
                .perform(typeText(mStringS), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_U))
                .perform(typeText(mStringT), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_V))
                .perform(typeText(mStringU), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_W))
                .perform(typeText(mStringV), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_X))
                .perform(typeText(mStringW), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_Y))
                .perform(typeText(mStringX), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_Z))
                .perform(typeText(mStringY), closeSoftKeyboard());

        onView(withId(R.id.cryptogram_button_submit)).check(matches(isClickable()));

        onView(withId(R.id.cryptogram_button_submit)).perform(click());

        onView(withText("SOLVED")).check(matches(isDisplayed()));

        onView(withId(R.id.cryptogram_button_cancel)).check(matches(isClickable()));

        onView(withId(R.id.cryptogram_button_cancel)).perform(click());
    }

    @Test
    public void onSubmit2() throws Exception {
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringPlayer), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        onView(withText("Hbufdg")).perform(click());

        onView(withId(R.id.cryptogram_textinput_A))
                .perform(typeText(mStringB), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_B))
                .perform(typeText(mStringC), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_C))
                .perform(typeText(mStringD), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_D))
                .perform(typeText(mStringE), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_E))
                .perform(typeText(mStringF), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_F))
                .perform(typeText(mStringG), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_G))
                .perform(typeText(mStringH), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_H))
                .perform(typeText(mStringI), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_I))
                .perform(typeText(mStringJ), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_J))
                .perform(typeText(mStringK), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_K))
                .perform(typeText(mStringL), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_L))
                .perform(typeText(mStringM), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_M))
                .perform(typeText(mStringN), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_N))
                .perform(typeText(mStringO), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_O))
                .perform(typeText(mStringP), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_P))
                .perform(typeText(mStringQ), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_Q))
                .perform(typeText(mStringR), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_R))
                .perform(typeText(mStringS), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_S))
                .perform(typeText(mStringT), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_T))
                .perform(typeText(mStringU), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_U))
                .perform(typeText(mStringV), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_V))
                .perform(typeText(mStringW), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_W))
                .perform(typeText(mStringX), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_X))
                .perform(typeText(mStringY), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_Y))
                .perform(typeText(mStringZ), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_Z))
                .perform(typeText(mStringA), closeSoftKeyboard());

        onView(withId(R.id.cryptogram_button_submit)).check(matches(isClickable()));

        onView(withId(R.id.cryptogram_button_submit)).perform(click());

        onView(withId(R.id.cryptogram_button_cancel)).check(matches(isClickable()));

        onView(withId(R.id.cryptogram_button_cancel)).perform(click());
    }

    @Test
    public void onSubmit3() throws Exception {
        onView(withId(R.id.textfield_login_username))
                .perform(typeText(mStringPlayer), closeSoftKeyboard());

        onView(withId(R.id.button)).perform(click());

        onView(withText("Hbufdg")).perform(click());

        onView(withId(R.id.cryptogram_textinput_O))
                .perform(typeText(mStringP), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_P))
                .perform(typeText(mStringQ), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_Q))
                .perform(typeText(mStringR), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_R))
                .perform(typeText(mStringS), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_S))
                .perform(typeText(mStringT), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_T))
                .perform(typeText(mStringU), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_U))
                .perform(typeText(mStringV), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_V))
                .perform(typeText(mStringW), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_W))
                .perform(typeText(mStringX), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_X))
                .perform(typeText(mStringY), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_Y))
                .perform(typeText(mStringZ), closeSoftKeyboard());
        onView(withId(R.id.cryptogram_textinput_Z))
                .perform(typeText(mStringA), closeSoftKeyboard());

        onView(withId(R.id.cryptogram_button_submit)).check(matches(isClickable()));

        onView(withId(R.id.cryptogram_button_submit)).perform(click());

        onView(withText("Incomplete Key")).check(matches(isDisplayed()));

    }


}