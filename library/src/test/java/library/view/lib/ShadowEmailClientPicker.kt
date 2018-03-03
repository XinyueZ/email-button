package library.view.lib

import android.app.Activity
import android.content.ActivityNotFoundException
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import org.robolectric.shadow.api.Shadow

@Implements(EmailClientPicker::class)
class ShadowEmailClientPicker {

    var shadowEmailValid: String? = null

    var noClientInstalled = true

    @Implementation
    fun emailValid(mailto: String): String? = shadowEmailValid

    @Implementation
    private fun startEmailClient(activity: Activity, to: String) {
        if (noClientInstalled) throw ActivityNotFoundException()
    }
}

internal fun shadowOf(real: EmailClientPicker): ShadowEmailClientPicker =
    Shadow.extract<ShadowEmailClientPicker>(
        real
    )