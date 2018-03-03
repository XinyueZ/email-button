package library.view.lib

import io.kotlintest.matchers.shouldBe
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.hamcrest.CoreMatchers.`is` as matchIs
import org.mockito.Mockito.`when` as mockWhen

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowEmailButton::class])
class EmailButtonUnitTest {

    @Test
    fun shouldEnableOrDisableAccordingToEmailClient() {
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

