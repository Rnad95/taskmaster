package EspressoUI;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.action.ViewActions.click;

import com.example.taskmaster.MainActivity;
import com.example.taskmaster.State;
import com.example.taskmaster.Task;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Test
    public void test_List(){
        int LIST_ITEM = 5;
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Reading","Read The Sea and The Old Man", State.NEW));
        tasks.add(new Task("Cook","Cook Pasta", State.COMPLETE));
        tasks.add(new Task("Mira","Take care of your niece", State.PROGRESS));

//        onView(wit)
    }


}

