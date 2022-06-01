package ru.kinomafia.model

import android.widget.TextView

fun TextView.resToString (resourceID : Int ) {
    text = resources.getString(resourceID)
}
