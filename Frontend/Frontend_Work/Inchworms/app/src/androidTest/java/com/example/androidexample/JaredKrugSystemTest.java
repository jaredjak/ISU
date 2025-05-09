package com.example.androidexample;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertEquals;

import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.TreeIterables;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
//import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


import java.util.function.Consumer;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JaredKrugSystemTest {

    private static final String TEST_USERNAME = "NoTest23";
    private static final String TEST_EMAIL = "nobestng2@what.com";
    private static final String TEST_PASSWORD = "NoTest23!";
    private static final String TEST_SSN = "143211831";

    private static boolean signedUp = false;

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void navigateToHomeIfSignedUp() {
        System.out.println("SignedUp: " + signedUp);
        if (signedUp) {
            onView(isRoot()).perform(waitForView(R.id.btnSignIn, 5000));

            SystemClock.sleep(2000);
            onView(withId(R.id.btnSignIn)).perform(click());

            onView(isRoot()).perform(waitForView(R.id.signin_username_edt, 5000));

            SystemClock.sleep(1500);
            onView(withId(R.id.signin_username_edt)).perform(typeText(TEST_USERNAME), closeSoftKeyboard());
            SystemClock.sleep(1500);
            onView(withId(R.id.signin_password_edt)).perform(typeText(TEST_PASSWORD), closeSoftKeyboard());

            SystemClock.sleep(1500);
            onView(withId(R.id.btnSignIn)).perform(click());

            // Wait for HomeActivity to load
            SystemClock.sleep(5000);
        }
    }

    public static ViewAction waitForView(final int viewId, final long timeoutMillis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait up to " + timeoutMillis + "ms for view with id " + viewId + " to appear.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                final long startTime = SystemClock.elapsedRealtime();
                final long endTime = startTime + timeoutMillis;
                final Matcher<View> viewMatcher = withId(viewId);

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                } while (SystemClock.elapsedRealtime() < endTime);

                throw new AssertionError("View with id " + viewId + " not found within " + timeoutMillis + "ms.");
            }
        };
    }

    @Test
    public void test01_signUp() {
        signedUp = true;
        onView(withId(R.id.btnSignUpRequest)).perform(click());

        SystemClock.sleep(2000);
        onView(withId(R.id.signup_username_edt)).perform(typeText(TEST_USERNAME), closeSoftKeyboard());
        onView(withId(R.id.signup_email_edt)).perform(typeText(TEST_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.signup_password_edt)).perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.signup_confirm_edt)).perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        onView(withId(R.id.signup_signup_btn)).perform(click());

        // Wait for SSN field to appear
        SystemClock.sleep(1000);

        onView(withId(R.id.signup_ssn_edt)).perform(typeText(TEST_SSN), closeSoftKeyboard());
        onView(withId(R.id.signup_signup_btn)).perform(click());

        SystemClock.sleep(3000); // wait for navigation to HomeActivity

    }

    // Quick test for leaderboard (checking that each view is nt empty)
    @Test
    public void test02_leaderboard() {
        SystemClock.sleep(1000);
        onView(withId(R.id.nav_lb)).perform(click());

        SystemClock.sleep(5000);

        onView(withId(R.id.topRecyclerView))
                .check(matches(hasDescendant(allOf(isDisplayed(), instanceOf(TextView.class), not(withText(""))))));

        onView(withId(R.id.middleRecyclerView))
                .check(matches(hasDescendant(allOf(isDisplayed(), instanceOf(TextView.class), not(withText(""))))));

        onView(withId(R.id.bottomRecyclerView))
                .check(matches(hasDescendant(allOf(isDisplayed(), instanceOf(TextView.class), not(withText(""))))));
    }

    @Test
    public void test03_MarketplaceAndCosmetics() {

        SystemClock.sleep(1000);
        onView(withId(R.id.nav_mp)).perform(click());

        SystemClock.sleep(5000);

        final int[] originalBucks = new int[1];
        onView(withId(R.id.wormbucks)).check((view, noViewFoundException) -> {
            TextView bucksView = (TextView) view;
            originalBucks[0] = Integer.parseInt(bucksView.getText().toString().replaceAll("[^\\d]", ""));
        });

        SystemClock.sleep(1500);

        // Grab original count, buy price, and sell price of the first skin
        final int[] originalCount = new int[1];
        final int[] buyPrice = new int[1];
        final int[] sellPrice = new int[1];

        onView(withId(R.id.market_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, getTextFromChild(R.id.tv_count, text -> {
                    originalCount[0] = Integer.parseInt(text.replaceAll("[^\\d]", ""));
                })));

        SystemClock.sleep(1500);
        onView(withId(R.id.market_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, getTextFromChild(R.id.tv_skin_buy_price, text -> {
                    buyPrice[0] = Integer.parseInt(text.replaceAll("[^\\d]", ""));
                })));

        SystemClock.sleep(1500);
        onView(withId(R.id.market_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, getTextFromChild(R.id.tv_skin_sell_price, text -> {
                    sellPrice[0] = Integer.parseInt(text.replaceAll("[^\\d]", ""));
                })));

        SystemClock.sleep(1500);
        // Perform BUY
        onView(withId(R.id.market_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, clickChildViewWithId(R.id.buttonBuy)));

        SystemClock.sleep(1500);

        // Check that count went down by 1
        onView(withId(R.id.market_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, getTextFromChild(R.id.tv_count, text -> {
//                    int updatedCount = Integer.parseInt(text);
                    int updatedCount = Integer.parseInt(text.replaceAll("[^\\d]", ""));
                    assertEquals(originalCount[0] - 1, updatedCount);
                })));

        SystemClock.sleep(1500);
        // Check that wormbucks decreased by buyPrice
        onView(withId(R.id.wormbucks)).check((view, noViewFoundException) -> {
            TextView bucksView = (TextView) view;
            int currentBucks = Integer.parseInt(bucksView.getText().toString().replaceAll("[^\\d]", ""));
            assertEquals(originalBucks[0] - buyPrice[0], currentBucks);
        });


        SystemClock.sleep(1500);
        onView(withId(R.id.nav_skins)).perform(click());

        SystemClock.sleep(5000);
        onView(withId(R.id.skins_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, clickChildViewWithId(R.id.equip_skin_button)));

//        SystemClock.sleep(1500);
//        onView(withId(R.id.skins_recycler_view))
//                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Black"))));
//
//        SystemClock.sleep(1500);
//        onView(allOf(
//             withId(R.id.equip_skin_button),
//             hasSibling(withText(containsString("Beige")))
//        )).perform(click());


        SystemClock.sleep(1000);

        onView(withId(R.id.nav_mp)).perform(click());
        SystemClock.sleep(5000);

//        SystemClock.sleep(1000);
        // Perform SELL
        onView(withId(R.id.market_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, clickChildViewWithId(R.id.sellButton)));

        SystemClock.sleep(1000);

        // Check that count returns to original
        onView(withId(R.id.market_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, getTextFromChild(R.id.tv_count, text -> {
//                    int updatedCount = Integer.parseInt(text);
                    int updatedCount = Integer.parseInt(text.replaceAll("[^\\d]", ""));
                    assertEquals(originalCount[0], updatedCount);
                })));

        // Check that wormbucks returned to original
        onView(withId(R.id.wormbucks)).check((view, noViewFoundException) -> {
            TextView bucksView = (TextView) view;
            int currentBucks = Integer.parseInt(bucksView.getText().toString().replaceAll("[^\\d]", ""));
            assertEquals(originalBucks[0], currentBucks);
        });
    }

    @Test
    public void test04_clubChatCreateSendLeave() {
        String createClubName = "TestClub8000";
        String existingClubName = "NotATestClub";

        // create a new club and leave

        SystemClock.sleep(1000);
        onView(withId(R.id.nav_club)).perform(click());

        SystemClock.sleep(1000);

        onView(withId(R.id.club_btn)).perform(click());

        onView(withId(R.id.btnCreateClub)).perform(click());
        onView(withId(R.id.new_club_name)).perform(typeText(createClubName), closeSoftKeyboard());
        onView(withId(R.id.create_btn)).perform(click());

        SystemClock.sleep(2000);

        onView(withId(R.id.club_name)).check(matches(withText(createClubName))); // Test that club names match

        onView(withId(R.id.chat_input)).perform(typeText("Hello from test!"), closeSoftKeyboard());
        onView(withId(R.id.send_btn)).perform(click());

        SystemClock.sleep(1000);

        onView(withId(R.id.leave_btn)).perform(click()); //leave

        SystemClock.sleep(2000);

//        onView(withId(R.id.club_name)).check(matches(not(withText(createClubName))));
        onView(withId(R.id.club_name))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        // join an existing club

        SystemClock.sleep(2000);

        onView(withId(R.id.club_btn)).perform(click());

        SystemClock.sleep(2000);

        //please let this work
        onView(withId(R.id.joinClubs)).perform(RecyclerViewActions.scrollTo(
                hasDescendant(withText(existingClubName))
        ));

        SystemClock.sleep(2000);

        onView(allOf(
                withId(R.id.join_btn),
                hasSibling(withText(existingClubName))
        )).perform(click());

        SystemClock.sleep(2000);

        onView(withId(R.id.club_name)).check(matches(withText(existingClubName))); // Test that club names match

        onView(withId(R.id.leave_btn)).perform(click()); //leave

        SystemClock.sleep(2000);

//        onView(withId(R.id.club_name)).check(matches(not(withText(existingClubName))));
        onView(withId(R.id.club_name))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        SystemClock.sleep(1000);
    }

    @Test
    public void test05_achievements() {
        String existingClubName = "NotATestClub";

        SystemClock.sleep(1000);

        onView(withId(R.id.nav_club)).perform(click());
        SystemClock.sleep(3000);

        onView(withId(R.id.club_btn)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.joinClubs)).perform(RecyclerViewActions.scrollTo(
                hasDescendant(withText(existingClubName))
        ));

        SystemClock.sleep(2000);

        onView(allOf(
                withId(R.id.join_btn),
                hasSibling(withText(existingClubName))
        )).perform(click());

        //go to achievements after joining a club
        onView(withId(R.id.achievement_btn)).perform(click());
        SystemClock.sleep(3000);

        onView(withId(R.id.achievements_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Clubber"))));
        SystemClock.sleep(1000);

        onView(allOf(
                withId(R.id.achievement_equip),
                hasSibling(withText(containsString("Clubber")))
        )).perform(click());
        SystemClock.sleep(500);

        onView(allOf(
                withId(R.id.achievement_equip),
                hasSibling(withText(containsString("Clubber")))
        )).check(matches(withText("Equipped")));

        onView(withId(R.id.achievements_recycler_view))
                .perform(RecyclerViewActions.scrollTo(hasDescendant(withText("Grub"))));
        SystemClock.sleep(2000);

        onView(allOf(
                withId(R.id.achievement_equip),
                hasSibling(withText(containsString("Grub")))
        )).check(matches(withText("Equip")));
        SystemClock.sleep(1000);

        onView(withId(R.id.nav_club)).perform(click());
        SystemClock.sleep(5000);

        onView(withId(R.id.leave_btn)).perform(click()); //leave
    }

    @Test
    public void test06_globalChat() {
        SystemClock.sleep(1000);
        onView(withId(R.id.global_chat_btn)).perform(click());

        SystemClock.sleep(1000);

        String testMessage = "System TeHELP THEY ARE HOLDING ME HOSTAGE";
        onView(withId(R.id.globalInputMessage)).perform(typeText(testMessage), closeSoftKeyboard());

        SystemClock.sleep(1000);

        onView(withId(R.id.globalSendButton)).perform(click());

        SystemClock.sleep(2000);

        onView(withId(R.id.globalChatRecyclerView))
                .check(matches(hasDescendant(withText(containsString(testMessage)))));

        SystemClock.sleep(1000);

        onView(withId(R.id.globalExitButton)).perform(click());

        SystemClock.sleep(1000);

        onView(withId(R.id.global_chat_btn)).check(matches(isDisplayed()));

        SystemClock.sleep(1000);
    }

    @Test
    public void test07_settingsBeforeDelete() {
        SystemClock.sleep(2000);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), SettingsActivity.class);
        ActivityScenario.launch(intent);

        SystemClock.sleep(2000);

        onView(withId(R.id.reportBtn)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.report_exit_btn)).perform(click());
        SystemClock.sleep(500);

        onView(withId(R.id.reportBtn)).perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.settings_report_btn)).perform(click()); // both fields empty
        SystemClock.sleep(1000);

//        onView(withId(R.id.reportBtn)).perform(click());
//        SystemClock.sleep(500);
        onView(withId(R.id.bad_username_input)).perform(typeText("jaredBrokeChat4"), closeSoftKeyboard());
        onView(withId(R.id.settings_report_btn)).perform(click()); // missing explanation goes through
        SystemClock.sleep(1000);


        onView(withId(R.id.logoutBtn)).perform(click());
        SystemClock.sleep(2000);

        onView(withId(R.id.btnSignIn)).perform(click());

        SystemClock.sleep(1000);
        onView(withId(R.id.exitBtn)).perform(click());

        SystemClock.sleep(1000);
        onView(withId(R.id.btnSignIn)).perform(click());
        SystemClock.sleep(2000);

        onView(withId(R.id.signin_username_edt)).perform(typeText(TEST_USERNAME), closeSoftKeyboard());
        SystemClock.sleep(2000);
        onView(withId(R.id.signin_password_edt)).perform(typeText(TEST_PASSWORD), closeSoftKeyboard());

        SystemClock.sleep(1000);

        onView(withId(R.id.btnSignIn)).perform(click());
        SystemClock.sleep(3000);

        onView(withId(R.id.settings_btn)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.exitBtn)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.global_chat_btn)).check(matches(isDisplayed()));
    }

    @Test
    public void test08_dailyChallenges() {
        SystemClock.sleep(1000);
        onView(withId(R.id.daily_btn)).perform(click());

        SystemClock.sleep(2000);
        onView(withId(R.id.daily_recycler)).check(matches(isDisplayed()));

        SystemClock.sleep(1000);
        onView(withId(R.id.DailyExitButton)).perform(click());

        SystemClock.sleep(2000);
        onView(withId(R.id.global_chat_btn)).check(matches(isDisplayed()));
    }

    @Test
    public void test09_adminPanel() {
        SystemClock.sleep(2000);

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), SettingsActivity.class);
        ActivityScenario.launch(intent);

        SystemClock.sleep(2000);

        onView(withId(R.id.logoutBtn)).perform(click());
        SystemClock.sleep(2000);

        onView(withId(R.id.btnSignIn)).perform(click());

        SystemClock.sleep(1000);

        onView(withId(R.id.signin_username_edt)).perform(typeText("HouseFan123"), closeSoftKeyboard());
        onView(withId(R.id.signin_password_edt)).perform(typeText("Password!"), closeSoftKeyboard());

        SystemClock.sleep(1000);

        onView(withId(R.id.btnSignIn)).perform(click());

        SystemClock.sleep(3000);

        onView(withId(R.id.admin_btn)).perform(click());

        SystemClock.sleep(2000);

        onView(withId(R.id.reportsRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.admin_report_details)));

        SystemClock.sleep(1000);

        onView(withId(R.id.reportDetailsUser))
                .check(matches(withText(startsWith("Reported User:"))));

        onView(withId(R.id.reportDetailsReporter))
                .check(matches(withText(startsWith("Reporting User:"))));

        onView(withId(R.id.reportDetailsExplanation))
                .check(matches(withText(startsWith("Explanation:"))));

        onView(withId(R.id.reportDetailsMessage))
                .check(matches(withText(startsWith("They deserve to be banned!"))));

        onView(withId(R.id.reportDetailsTimestamp))
                .check(matches(withText(startsWith("Timestamp:"))));

        SystemClock.sleep(2000);

        onView(withId(R.id.chatSearchEditText)).perform(typeText("HouseFan123"), closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withId(R.id.searchChats)).perform(click());

        onView(withId(R.id.chatsRecyclerView))
                .check(matches(hasMinimumChildCount(1)));

        SystemClock.sleep(1000);

        onView(withId(R.id.adminSearchEditText)).perform(typeText(TEST_USERNAME), closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withId(R.id.suspendTimeEditText)).perform(typeText("5"), closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withId(R.id.suspendButton)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.exitButton)).perform(click());
        SystemClock.sleep(2000);

        onView(withId(R.id.global_chat_btn)).check(matches(isDisplayed()));
        SystemClock.sleep(1000);
    }

    @Test
    public void test10_settingsAccountDeletion() {
        SystemClock.sleep(2000);
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), SettingsActivity.class);
        ActivityScenario.launch(intent);

        SystemClock.sleep(2000);

        onView(withId(R.id.deleteBtn)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.delete_exit_btn)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.deleteBtn)).perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.username_input)).perform(typeText(TEST_USERNAME), closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withId(R.id.password_input)).perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withId(R.id.ssn_input)).perform(typeText(TEST_SSN), closeSoftKeyboard());

        SystemClock.sleep(2000);

        onView(withId(R.id.settings_delete_btn)).perform(click());

        SystemClock.sleep(2000);

        onView(withId(R.id.btnSignIn)).perform(click());

        SystemClock.sleep(1000);

        onView(withId(R.id.signin_username_edt)).perform(typeText(TEST_USERNAME), closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withId(R.id.signin_password_edt)).perform(typeText(TEST_PASSWORD), closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withId(R.id.btnSignIn)).perform(click());

        onView(withId(R.id.btnSignIn)).check(matches(isDisplayed()));

        SystemClock.sleep(1000);
    }

    // Helper: click a specific child view in a RecyclerView item
    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(View.class);
            }

            @Override
            public String getDescription() {
                return "Click on a child view with id " + id;
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                if (v != null) {
                    v.performClick();
                }
            }
        };
    }

    // Helper: extract text from a child view in a RecyclerView item
    public static ViewAction getTextFromChild(@IdRes int id, Consumer<String> callback) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(View.class);
            }

            @Override
            public String getDescription() {
                return "Get text from child view with id " + id;
            }

            @Override
            public void perform(UiController uiController, View view) {
                View child = view.findViewById(id);
                if (child instanceof TextView) {
                    callback.accept(((TextView) child).getText().toString());
                }
            }
        };
    }

    // Another helper because i have a dialog that I did not make a new class for (why do i do these things)
    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(View view) {
                if (matcher.matches(view)) {
                    if (currentIndex == index) {
                        return true;
                    }
                    currentIndex++;
                }
                return false;
            }
        };
    }
}
