package com.example.lifestyleapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WeatherFragmentTest {

    SharedPreferences pref;

    @Rule
    public ActivityTestRule<MainDrawerActivity> mActivityTestRule = new ActivityTestRule<>(
            MainDrawerActivity.class, true, false);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Before
    public void setUp() {
        Context targetContext = getInstrumentation().getTargetContext();
        pref = targetContext.getSharedPreferences("com.example.lifestyleapp",
                Context.MODE_PRIVATE);
    }

    @Test
    public void weatherFragmentTest() {
        pref.edit().putString("username", "ac13").apply();
        pref.edit().putString("email", "ac@gmail.com").apply();
        pref.edit().putString("password", "123").apply();
        pref.edit().putString("dob", "06/02/1998").apply();
        pref.edit().putString("country", "Canada").apply();
        pref.edit().putInt("country_idx", 1).apply();
        pref.edit().putString("city", "Montreal").apply();
        pref.edit().putString("sex", "Male").apply();
        pref.edit().putInt("sex_idx", 0).apply();
        pref.edit().putInt("height", 72).apply();
        pref.edit().putInt("weight", 175).apply();
        pref.edit().putBoolean("LOGIN_KEY", false).apply();

        mActivityTestRule.launchActivity(new Intent());


        ViewInteraction textView = onView(
                allOf(withId(R.id.weatherCity), withText("Montreal"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Montreal")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
