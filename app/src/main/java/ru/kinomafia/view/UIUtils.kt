package ru.kinomafia.view

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.show() : View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}
fun View.hide() : View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

fun View.simpleFunWithoutAction(text: String = "Загрузка успешна",
                                    length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, text, length).show()
}
