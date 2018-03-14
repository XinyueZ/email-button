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
    fun emailValid(@Suppress("UNUSED_PARAMETER") mailto: String): String? = shadowEmailValid

    @Implementation
    private fun startEmailClient(@Suppress("UNUSED_PARAMETER") activity: Activity, @Suppress("UNUSED_PARAMETER") to: String) {
        if (noClientInstalled) throw ActivityNotFoundException()
    }
}

internal fun shadowOf(real: EmailClientPicker): ShadowEmailClientPicker =
    Shadow.extract<ShadowEmailClientPicker>(
        real
    )