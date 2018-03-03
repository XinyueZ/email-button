package library.view.lib

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import io.kotlintest.matchers.shouldBe
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.hamcrest.CoreMatchers.`is` as matchIs
import org.mockito.Mockito.`when` as mockWhen

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowEmailButton::class])
class EmailButtonUnitTest {
    private lateinit var activityCtrl: ActivityController<TestActivity>
    private val activity: TestActivity
        get() = activityCtrl.get()

    @Test
    fun shouldEnableOrDisableAccordingToEmailClient1() {
        val emailButton = EmailButton(context())
        shadowOf(emailButton).shadowFindEmailClient.let { findClient ->
            with(emailButton) {
                isEnabled.shouldBe(findClient)
                isClickable.shouldBe(findClient)
                if (findClient) currentTextColor.shouldBe(context().getColor(R.color.selector_email_text_default_color))
                else currentTextColor.shouldBe(context().getColor(android.R.color.black))
            }
        }
    }

    @Test
    fun shouldEnableOrDisableAccordingToEmailClient2() {

        activityCtrl = Robolectric.buildActivity(TestActivity::class.java).setup()

        val emailButton = activity.findViewById<EmailButton>(R.id.emailButton)
        shadowOf(emailButton).shadowFindEmailClient.let { findClient ->
            with(emailButton) {
                isEnabled.shouldBe(findClient)
                isClickable.shouldBe(findClient)
                if (findClient) currentTextColor.shouldBe(context().getColor(R.color.selector_email_text_test_color))
                else currentTextColor.shouldBe(context().getColor(R.color.test_color))
            }
        }


        activityCtrl.finish()
    }
}

class TestActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }
}
