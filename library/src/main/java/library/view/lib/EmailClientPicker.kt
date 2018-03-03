package library.view.lib

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog.Builder
import android.support.v7.app.AppCompatDialogFragment
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.Toast

/**
 * Start a picker-list for email clients. It dependents on different OS, however, it tries to give user
 * chance to send email.
 */
internal class EmailClientPicker {
    /**
     * Pickup a url and try to open email app or play-store to download mail app.
     * Returns `true` when the [mailto] is an email validated link, otherwise  `false`.
     */
    fun show(activity: FragmentActivity, mailto: String): Boolean {
        emailValid(mailto)?.let { validatedMailTo ->
            return when {
                !validatedMailTo.isBlank() -> {
                    try {
                        startEmailClient(activity, mailto)
                    } catch (e: ActivityNotFoundException) {
                        showDialogFragment(
                            activity.supportFragmentManager,
                            TryInStoreDownloadFragment.newInstance(activity)
                        )
                    }
                    true
                }
                else -> false
            }
        } ?: kotlin.run {
            return false
        }
    }

    private fun startEmailClient(
        activity: Activity,
        to: String,
        subject: String = "",
        body: String = ""
    ) {
        with(Intent(Intent.ACTION_VIEW)) {
            putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf(to))
            type = "text/plain"
            data = Uri.parse(
                StringBuilder("mailto:").append("?subject=")
                    .append(subject)
                    .append("&body=")
                    .append(body)
                    .toString()
            )
            ActivityCompat.startActivity(activity, this, Bundle.EMPTY)
        }
    }

    private fun emailValid(mailto: String): String? {
        var mailAddress: String = mailto
        if (mailto.contains("mailto:")) {
            val mailEntities =
                mailAddress.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            mailAddress = mailEntities[1]
        }
        return if (!mailAddress.isBlank() && EMAIL_ADDRESS.matcher(
                mailAddress
            ).matches()
        ) {
            mailAddress
        } else {
            null
        }
    }

    @SuppressLint("CommitTransaction")
    private fun showDialogFragment(
        mgr: FragmentManager,
        dlgFrg: AppCompatDialogFragment
    ) {
        with(mgr) {
            beginTransaction().apply {
                // Ensure that there's only one dialog to the user.
                findFragmentByTag(TryInStoreDownloadFragment.TAG)?.run {
                    remove(this)
                }

                dlgFrg.show(
                    this,
                    TryInStoreDownloadFragment.TAG
                )
            }
        }
    }

    class TryInStoreDownloadFragment : AppCompatDialogFragment() {

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            Builder(activity!!).run {
                setTitle(R.string.no_email_app_title)
                    .setMessage(R.string.no_email_app_content)
                    .setPositiveButton(
                        android.R.string.ok,
                        { _, _ ->
                            val mailClient = getString(R.string.fav_email_client)
                            try {
                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(APP_STORE_LINK + mailClient)
                                    )
                                )
                            } catch (ex: ActivityNotFoundException) {
                                try {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(APP_STORE_WEB_LINK + mailClient)
                                        )
                                    )
                                } catch (ex: ActivityNotFoundException) {
                                    Toast.makeText(
                                        context,
                                        R.string.no_email_app_title,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    )
                    .setNegativeButton(
                        android.R.string.cancel,
                        { _, _ ->
                            // User cancelled the dialog
                        }
                    )
                // Create the AlertDialog object and return it
                create()
            }

        companion object {
            private val APP_STORE_LINK: String by lazy { "market://details?id=" }
            private val APP_STORE_WEB_LINK: String by lazy { "https://play.google.com/store/apps/details?id=" }
            internal val TAG: String by lazy { TryInStoreDownloadFragment::class.java.name }

            fun newInstance(cxt: Context): TryInStoreDownloadFragment {
                return Fragment.instantiate(
                    cxt,
                    TryInStoreDownloadFragment::class.java.name
                ) as TryInStoreDownloadFragment
            }
        }
    }
}
