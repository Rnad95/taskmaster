package EspressoUI;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.core.AllOf.allOf;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.taskmaster.MainActivity;
import com.example.taskmaster.R;
import com.example.taskmaster.SettingPage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SettingPageTest {



    @Rule
    public ActivityScenarioRule<SettingPage> activityRule =
        new ActivityScenarioRule<SettingPage>(SettingPage.class);

    @Test
    public void testSettingPage() {

        onView(withId(R.id.edit_text_username_setting)).perform(typeText("Renad"));
        onView(withId(R.id.btn_save)).perform(click());
        onView(withText("Renad")).check(matches(isDisplayed()));

    }
}
