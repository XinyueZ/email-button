package library.view.lib

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.app.FragmentActivity
import android.support.v4.content.res.ResourcesCompat.getColor
import android.util.AttributeSet
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.TextView

class EmailButton : TextView {
    private var isValidated = true

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(attrs)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        validateText(text)
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        validateText(text)
    }

    private fun validateText(text: CharSequence?) {
        if (isValidated && !EMAIL_ADDRESS.matcher(text).matches()) {
            isValidated = false
            this.text = context.getString(R.string.invalid_email)
            isClickable = false
            isEnabled = false
        } else if (!isValidated && EMAIL_ADDRESS.matcher(text).matches()) {
            isValidated = true
            isClickable = true
            isEnabled = true
        }
    }

    private fun init(attrs: AttributeSet? = null) {
        if (attrs != null) {
            with(context.theme.obtainStyledAttributes(attrs, R.styleable.emailbtn, 0, 0)) {
                findEmailClient().apply {
                    isClickable = this
                    isEnabled = this
                    if (this) {
                        setOnClickListener {
                            EmailClientPicker().show(
                                context as FragmentActivity,
                                text.toString()
                            )
                        }
                        setTextColor(
                            getColor(
                                resources,
                                getResourceId(
                                    R.styleable.emailbtn_textColorEmailClientAvailable,
                                    R.color.selector_email_text_default_color
                                ),
                                null
                            )
                        )
                    } else {
                        setTextColor(
                            getColor(
                                resources,
                                getResourceId(
                                    R.styleable.emailbtn_textColorEmailClientUnavailable,
                                    android.R.color.black
                                ),
                                null
                            )
                        )
                    }
                }
                recycle()
            }
        } else {
            findEmailClient().apply {
                isClickable = this
                isEnabled = this
                if (this) {
                    setOnClickListener {
                        EmailClientPicker().show(
                            context as FragmentActivity,
                            text.toString()
                        )
                    }
                    setTextColor(
                        getColor(
                            resources,
                            R.color.selector_email_text_default_color,
                            null
                        )
                    )
                } else {
                    setTextColor(getColor(resources, android.R.color.black, null))
                }
            }
        }
    }

    private fun findEmailClient(): Boolean {
        with(Intent(Intent.ACTION_SENDTO)) {
            data = Uri.parse("mailto:")
            return context.packageManager.queryIntentActivities(this, 0).isNotEmpty()
        }
    }
}