package dan.wilkie.cv

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import dan.wilkie.cv.view.CvActivity
import org.hamcrest.CoreMatchers.containsString
import org.junit.Rule
import org.junit.Test

class CvActivityTest {

    @get:Rule
    val rule = ActivityTestRule(CvActivity::class.java)

    @Test
    fun displaysProfile() {
        onView(withText("Daniel Wilkie")).check(matches(isDisplayed()))
        onView(withText(containsString("An experienced and skilled Android developer"))).check(matches(isDisplayed()))
    }
}
