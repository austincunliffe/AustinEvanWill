package com.example.lifestyleapp;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ScrollToAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UserProfileTest {

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void userProfileTest() {

        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date dateobj = new Date();
        String strDate = df.format(dateobj);

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.signUp), withText("Sign Up"),
                        childAtPosition(
                                allOf(withId(R.id.container),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.et_username),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withId(R.id.nsv_inputs),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.et_username),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withId(R.id.nsv_inputs),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("a"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.et_email),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withId(R.id.nsv_inputs),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("a"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.et_password),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withId(R.id.nsv_inputs),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.et_confirm_password),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withId(R.id.nsv_inputs),
                                                0)),
                                8),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.et_DOB),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withId(R.id.nsv_inputs),
                                                0)),
                                11),
                        isDisplayed()));
        appCompatEditText6.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.et_city),
                        childAtPosition(
                                allOf(withId(R.id.username),
                                        childAtPosition(
                                                withId(R.id.nsv_inputs),
                                                0)),
                                15),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("Tahoe"), closeSoftKeyboard());

        onView(withId(R.id.bt_signUp)).perform(customScrollTo, click());

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
                allOf(withId(R.id.nav_user_profile),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                3),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.et_name_big), withText("a"),
                        childAtPosition(
                                allOf(withId(R.id.userInfo),
                                        childAtPosition(
                                                withId(R.id.nsv_inputs),
                                                0)),
                                1),
                        isDisplayed()));
        editText.check(matches(withText("a")));

        ViewInteraction editText2 = onView(
                allOf(withId(R.id.et_DOB), withText(strDate),
                        childAtPosition(
                                allOf(withId(R.id.userInfo),
                                        childAtPosition(
                                                withId(R.id.nsv_inputs),
                                                0)),
                                3),
                        isDisplayed()));
        editText2.check(matches(withText(strDate)));

        ViewInteraction editText3 = onView(
                allOf(withId(R.id.et_city_big), withText("Tahoe"),
                        childAtPosition(
                                allOf(withId(R.id.userInfo),
                                        childAtPosition(
                                                withId(R.id.nsv_inputs),
                                                0)),
                                5),
                        isDisplayed()));
        editText3.check(matches(withText("Tahoe")));

        ViewInteraction textView = onView(
                allOf(withText("United States"),
                        childAtPosition(
                                allOf(withId(R.id.spinner_country),
                                        childAtPosition(
                                                withId(R.id.userInfo),
                                                7)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("United States")));
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

    ViewAction customScrollTo = new ViewAction() {
        @Override
        public Matcher<View> getConstraints() {
            return allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE), isDescendantOfA(anyOf(
                    isAssignableFrom(ScrollView.class),
                    isAssignableFrom(HorizontalScrollView.class),
                    isAssignableFrom(NestedScrollView.class)))
            );
        }

        @Override
        public String getDescription() {
            return null;
        }

        @Override
        public void perform(UiController uiController, View view) {
            new ScrollToAction().perform(uiController, view);
        }
    };
}
