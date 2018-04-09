package library.view.lib

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.annotation.ColorInt
import android.support.v4.app.FragmentActivity
import android.support.v4.content.res.ResourcesCompat.getColor
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.util.Patterns.EMAIL_ADDRESS

class EmailButton : AppCompatTextView {
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
                process(
                    getColor(
                        resources,
                        getResourceId(
                            R.styleable.emailbtn_textColorEmailClientAvailable,
                            R.color.selector_email_text_default_color
                        ),
                        null
                    ), getColor(
                        resources,
                        getResourceId(
                            R.styleable.emailbtn_textColorEmailClientUnavailable,
                            android.R.color.black
                        ),
                        null
                    )
                )
                recycle()
            }
        } else {
            process(
                getColor(
                    resources,
                    R.color.selector_email_text_default_color,
                    null
                ),
                getColor(resources, android.R.color.black, null)
            )
        }
    }

    private fun process(@ColorInt textColorAvailable: Int, @ColorInt textColorUnavailable: Int) {
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
                    textColorAvailable
                )
            } else {
                setTextColor(
                    textColorUnavailable
                )
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