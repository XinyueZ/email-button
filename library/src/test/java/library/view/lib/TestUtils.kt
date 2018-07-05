package library.view.lib

import android.app.Activity
import android.app.Application
import org.robolectric.RuntimeEnvironment
import org.robolectric.android.controller.ActivityController

fun context(): Application = RuntimeEnvironment.application

fun <T : Activity> ActivityController<T>.finish() {
    try {
        pause().stop().destroy()
    } catch (ignored: Throwable) {
    }
}