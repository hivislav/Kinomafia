package ru.kinomafia.view.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import ru.kinomafia.R
import ru.kinomafia.viewmodel.FilmInfoViewModel

class NoteFavouriteDialogFragment: DialogFragment() {

    private val viewModel: FilmInfoViewModel by lazy {
        ViewModelProvider(this).get(FilmInfoViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favourite_note_fragment_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.favourite_dialog_add_note_button).setOnClickListener {
            val note = view.findViewById<EditText>(R.id.favourite_dialog_add_note_edit_text).text.toString()
            val bundle = Bundle()
            bundle.putString(NOTE_FAVOURITE_DIALOG_FRAGMENT_KEY_EXTRA, note)
            requireActivity().supportFragmentManager.setFragmentResult(NOTE_FAVOURITE_DIALOG_FRAGMENT_REQUEST, bundle)
            dismiss()
        }
    }

    companion object {
        fun newInstance() = NoteFavouriteDialogFragment()

        const val NOTE_FAVOURITE_DIALOG_FRAGMENT_REQUEST = "NOTE_FAVOURITE_DIALOG_FRAGMENT_REQUEST"
        const val NOTE_FAVOURITE_DIALOG_FRAGMENT_KEY_EXTRA = "NOTE_FAVOURITE_DIALOG_FRAGMENT_KEY_EXTRA"
    }
}