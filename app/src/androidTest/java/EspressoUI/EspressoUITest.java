package EspressoUI;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import com.example.taskmaster.MainActivity;
import com.example.taskmaster.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);

    //  Test the main Activity
    @Test
    public void test_ViewMainPage(){
        onView(withId(R.id.main)).check(matches(isDisplayed()));
    }

    // Test th element  in main Page
    @Test
    public void test_logo_view_correct(){
        onView(withId(R.id.add_task)).check(matches(isDisplayed()));
    }

    @Test
    public void test_recycleView_view_correct(){
        onView(withId(R.id.show_recycler_view)).check(matches(isDisplayed()));
    }
    @Test
    public void test_allTask_button(){
        onView(withId(R.id.all_task)).check(matches(isDisplayed()));
    }

    @Test
    public void test_navToAddTask(){
        onView(withId(R.id.add_task)).perform(click());
        onView(withId(R.id.add_task_activity)).check(matches(isDisplayed()));

    }

//    Navigate to DetailsTask Activity
    @Test
    public void test_navToTaskDetails(){
        onView(withId(R.id.show_recycler_view)).perform(click());
        onView(withId(R.id.task_details_activity)).check(matches(isDisplayed()));

    }



}
