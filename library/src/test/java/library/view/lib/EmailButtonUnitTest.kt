package library.view.lib

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import io.kotlintest.matchers.shouldBe
import io.kotlintest.properties.Gen
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowEmailButton::class])
class EmailButtonUnitTest {
    @Test
    fun shouldShowInvalidEmailMessage() {
        val activityCtrl = Robolectric.buildActivity(TestWrongEmailActivity::class.java).setup()

        val emailButton = activityCtrl.get().findViewById<EmailButton>(R.id.emailButton)
        emailButton.text.shouldBe(context().getString(R.string.invalid_email))
        emailButton.isClickable.shouldBe(false)
        emailButton.isEnabled.shouldBe(false)

        emailButton.text = "zxy@gmail.com"
        emailButton.text.shouldBe("zxy@gmail.com")
        emailButton.isClickable.shouldBe(true)
        emailButton.isEnabled.shouldBe(true)

        emailButton.text = Gen.string().generate()
        emailButton.text.shouldBe(context().getString(R.string.invalid_email))
        emailButton.isClickable.shouldBe(false)
        emailButton.isEnabled.shouldBe(false)

        activityCtrl.finish()
    }

    @Test
    fun shouldEnableOrDisableAccordingToEmailClient2() {
        val activityCtrl = Robolectric.buildActivity(TestActivity::class.java).setup()

        val emailButton = activityCtrl.get().findViewById<EmailButton>(R.id.emailButton)
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
}

class TestActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }
}

class TestWrongEmailActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_init_with_wrong_email)
    }
}
