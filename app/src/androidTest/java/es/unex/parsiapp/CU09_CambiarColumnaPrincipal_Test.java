package es.unex.parsiapp;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CU09_CambiarColumnaPrincipal_Test {

    @Rule
    public ActivityScenarioRule<LoginActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @BeforeClass
    public static void beforeClass(){
        ApplicationProvider.getApplicationContext().deleteDatabase("parsi.db");
    }

    @Test
    public void cU09_CambiarColumnaPrincipal_Test() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.register_button), withText("Registrarse"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.username_register),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("CambiarColumna"), closeSoftKeyboard());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.email_register),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("cu09@gmail.com"), closeSoftKeyboard());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.password_register),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.password_register),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("12345"), closeSoftKeyboard());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.register_confirm_button), withText("Registrarse"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.nav_columnas),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                4),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withText("Columnas"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView.check(matches(withText("Columnas")));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.columna_button), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_menu_lateral),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton3.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.columnName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("Mundial"), closeSoftKeyboard());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.queryOrUser),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("mundial"), closeSoftKeyboard());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.create_column_button), withText("Crear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton4.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction navigationMenuItemView2 = onView(
                allOf(withId(R.id.nav_columnas),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                4),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.columna_button), withText("+"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_content_menu_lateral),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton5.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.columnName),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("UsuarioRandom"), closeSoftKeyboard());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.queryOrUser),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("rodmarkun"), closeSoftKeyboard());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatRadioButton = onView(
                allOf(withId(R.id.user_selection), withText("Usuario"),
                        childAtPosition(
                                allOf(withId(R.id.api_call_type_selection),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                3)),
                                1),
                        isDisplayed()));
        appCompatRadioButton.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.create_column_button), withText("Crear"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton6.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction navigationMenuItemView3 = onView(
                allOf(withId(R.id.nav_columnas),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                4),
                        isDisplayed()));
        navigationMenuItemView3.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView2 = onView(
                allOf(withText("Columnas"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView2.check(matches(withText("Columnas")));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.listRecyclerView))
                .perform(actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.cdF)));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView3 = onView(
                allOf(withText("Columnas"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView3.check(matches(withText("Columnas")));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("Open navigation drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("com.google.android.material.appbar.AppBarLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction navigationMenuItemView4 = onView(
                allOf(withId(R.id.nav_home),
                        childAtPosition(
                                allOf(withId(com.google.android.material.R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.nav_view),
                                                0)),
                                1),
                        isDisplayed()));
        navigationMenuItemView4.perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView4 = onView(
                allOf(withText("Inicio"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        isDisplayed()));
        textView4.check(matches(withText("Inicio")));
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

    public static class MyViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }
    }
}
