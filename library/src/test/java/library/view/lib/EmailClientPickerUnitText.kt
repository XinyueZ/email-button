package library.view.lib

import android.support.v4.app.FragmentActivity
import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.properties.Gen
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowDatePickerDialog

@RunWith(RobolectricTestRunner::class)
@Config(shadows = [ShadowEmailClientPicker::class], sdk = [27])
class EmailClientPickerUnitText {
    private lateinit var picker: EmailClientPicker
    private lateinit var activityCtrl: ActivityController<FragmentActivity>
    private val activity: FragmentActivity
        get() = activityCtrl.get()

    @Before
    fun setUp() {
        picker = EmailClientPicker()
        activityCtrl = Robolectric.buildActivity(FragmentActivity::class.java).setup()
    }

    @After
    fun tearDown() {
        activityCtrl.finish()
    }

    @Test
    fun shouldNotShowPickerIfNoValidEmail() {
        shadowOf(picker).shadowEmailValid = ""
        picker.show(activity, Gen.string().generate()).shouldBe(false)
    }

    @Test
    fun shouldNotShowPickerIfNoValidEmailButBlank() {
        shadowOf(picker).shadowEmailValid = ""
        picker.show(activity, Gen.string().generate()).shouldBe(false)

        shadowOf(picker).shadowEmailValid = "        "
        picker.show(activity, Gen.string().generate()).shouldBe(false)
    }

    @Test
    fun shouldShowTryInStoreDownloadPopup_NoClientInstalled() {
        val randomEmail =
            Gen.negativeIntegers().generate().toString() // Just use some int to simulate text.
        shadowOf(picker).apply {
            noClientInstalled = true
            shadowEmailValid = randomEmail
        }
        picker.show(activity, randomEmail).shouldBe(true)
        ShadowDatePickerDialog.getLatestDialog().apply {
            should {
                it != null
            }
        }
    }
}

