# email-button

[![Build Status](https://travis-ci.org/XinyueZ/email-button.svg?branch=master)](https://travis-ci.org/XinyueZ/email-button)

A email-client sensitive button. 

# work

- A button to open system-picker of email.
- If no picker would be found the button should not be clickable.
- Wrong email address would be tested if it is invalid, a message shows instead email.


![sample](media/sample.gif)

# Layout

```xml

<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <library.view.lib.EmailButton
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:foreground="?attr/selectableItemBackground"
        android:background="?attr/selectableItemBackground"
        android:text="dev.xinyue.zhao@gmail.com"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:padding="10dp"
        app:textColorEmailClientAvailable="@color/selector_email_text_default_color"
        app:textColorEmailClientUnavailable="@android:color/black"/>

</android.support.constraint.ConstraintLayout>

```

# klint support
 
The project uses [ktlint](https://ktlint.github.io/) to enforce Kotlin coding styles.
Here's how to configure it for use with Android Studio (instructions adapted
from the ktlint [README](https://github.com/shyiko/ktlint/blob/master/README.md)):

- Close Android Studio if it's open

- Download ktlint:

  `curl -sSLO https://github.com/shyiko/ktlint/releases/download/0.27.0/ktlint && chmod a+x ktlint`

- Inside the project root directory run:

  `ktlint --apply-to-idea-project --android`

- Remove ktlint if desired:

  `rm ktlint`

- Start Android Studio

# License

                                MIT License

                    Copyright (c) 2018 Chris Xinyue 

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
