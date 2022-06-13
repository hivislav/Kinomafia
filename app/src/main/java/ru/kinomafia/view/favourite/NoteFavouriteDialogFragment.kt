package ru.kinomafia.view.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import ru.kinomafia.R

class NoteFavouriteDialogFragment: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favourite_note_fragment_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<Button>(R.id.favourite_dialog_add_note_button).setOnClickListener {

            val note = view.findViewById<EditText>(R.id.favourite_dialog_add_note_edit_text).text
            val bundle = Bundle()
            bundle.putString(NOTE_FAVOURITE_DIALOG_FRAGMENT_KEY, note.toString())
            dismiss()
        }
    }

    companion object {
        fun newInstance() = NoteFavouriteDialogFragment()

        const val NOTE_FAVOURITE_DIALOG_FRAGMENT_KEY = "NOTE_FAVOURITE_DIALOG_FRAGMENT_KEY"
    }
}