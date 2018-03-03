package library.view.lib

import io.kotlintest.properties.Gen
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import org.robolectric.shadow.api.Shadow
import org.robolectric.shadows.ShadowTextView

@Implements(EmailButton::class)
class ShadowEmailButton : ShadowTextView() {

    val shadowFindEmailClient: Boolean = Gen.bool().generate()

    @Implementation
    fun findEmailClient() = shadowFindEmailClient
}


fun shadowOf(real: EmailButton): ShadowEmailButton =
    Shadow.extract<ShadowEmailButton>(
        real
    )