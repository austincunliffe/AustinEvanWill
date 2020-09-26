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

import com.example.lifestyleapp.ui.fitnessGoal.FitnessGoal;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
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
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HealthTest {

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
    public void healthCalculationTest(){
        FitnessGoal fitnessGoal = new FitnessGoal();
        assert (1697.5 == fitnessGoal.getBMRMenFormula(0,65,130,"Male"));
        assert(0 == fitnessGoal.getUserAge("2020"));
        assert ( "You need to eat 1537.0 calories/day to lose your goal lbs/week"== fitnessGoal.getCaloriesToEat(1697.5));
    }

    @Test
    public void healthTest() {
        pref.edit().putString("username", "ac13").apply();
        pref.edit().putString("email", "ac@gmail.com").apply();
        pref.edit().putString("password", "123").apply();
        pref.edit().putString("dob", "06/02/1998").apply();
        pref.edit().putString("country", "Canada").apply();
        pref.edit().putInt("country_idx", 1).apply();
        pref.edit().putString("city", "Tahoe").apply();
        pref.edit().putString("sex", "Male").apply();
        pref.edit().putInt("sex_idx", 0).apply();
        pref.edit().putInt("height", 65).apply();
        pref.edit().putInt("weight", 130).apply();
        pref.edit().putBoolean("LOGIN_KEY", false).apply();

        mActivityTestRule.launchActivity(new Intent());


        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.nav_fitness_goal),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                5),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.bt_setGoal), withText("Set Goal"),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withClassName(is("androidx.core.widget.NestedScrollView")),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction textView = onView(
                allOf(withText("Lose Weight"),
                        childAtPosition(
                                allOf(withId(R.id.spinner_goal),
                                        childAtPosition(
                                                withId(R.id.username),
                                                2)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Lose Weight")));

        ViewInteraction textView2 = onView(
                allOf(withText("Sedentary"),
                        childAtPosition(
                                allOf(withId(R.id.spinner_active),
                                        childAtPosition(
                                                withId(R.id.username),
                                                4)),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Sedentary")));

        ViewInteraction textView3 = onView(
                allOf(withText("Sedentary"),
                        childAtPosition(
                                allOf(withId(R.id.spinner_active),
                                        childAtPosition(
                                                withId(R.id.username),
                                                4)),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Sedentary")));

        ViewInteraction editText = onView(
                allOf(IsInstanceOf.<View>instanceOf(android.widget.EditText.class), withText("1"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.username),
                                        6),
                                1),
                        isDisplayed()));
        editText.check(matches(withText("1")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.bmr_big), withText("1697.5 calories/day"),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                                0)),
                                9),
                        isDisplayed()));
        textView4.check(matches(withText("1697.5 calories/day")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.tv_calorie_intake), withText("You need to eat 1537.0 calories/day to lose your goal lbs/week"),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                                0)),
                                10),
                        isDisplayed()));
        textView5.check(matches(withText("You need to eat 1537.0 calories/day to lose your goal lbs/week")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.tv_calorie_intake), withText("You need to eat 1537.0 calories/day to lose your goal lbs/week"),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class),
                                                0)),
                                10),
                        isDisplayed()));
        textView6.check(matches(withText("You need to eat 1537.0 calories/day to lose your goal lbs/week")));
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
