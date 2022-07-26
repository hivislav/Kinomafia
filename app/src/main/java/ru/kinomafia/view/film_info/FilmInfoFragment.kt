package ru.kinomafia.view.film_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.snackbar.Snackbar
import ru.kinomafia.R
import ru.kinomafia.databinding.FilmInfoFragmentBinding
import ru.kinomafia.model.entities.FilmInfo
import ru.kinomafia.model.entities.FilmItem
import ru.kinomafia.view.favourite.NoteFavouriteDialogFragment
import ru.kinomafia.view.favourite.NoteFavouriteDialogFragment.Companion.NOTE_FAVOURITE_DIALOG_FRAGMENT_KEY_EXTRA
import ru.kinomafia.view.favourite.NoteFavouriteDialogFragment.Companion.NOTE_FAVOURITE_DIALOG_FRAGMENT_REQUEST
import ru.kinomafia.view.hide
import ru.kinomafia.view.show
import ru.kinomafia.viewmodel.AppStateFilmInfo
import ru.kinomafia.viewmodel.AppStateMain
import ru.kinomafia.viewmodel.FilmInfoViewModel

class FilmInfoFragment : Fragment() {
    private var _binding: FilmInfoFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FilmInfoViewModel by lazy {
        ViewModelProvider(this).get(FilmInfoViewModel::class.java)
    }

    private lateinit var localFilmInfo: FilmInfo


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FilmInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<FilmItem>(BUNDLE_EXTRA)?.let {

            viewModel.getLiveData().observe(viewLifecycleOwner,
                Observer<AppStateFilmInfo> { appStateFilmInfo: AppStateFilmInfo ->  renderData(appStateFilmInfo)})
            viewModel.loadFilmInfoFromServer(it.id)
        }

        binding.starIconFilmInfo.setOnClickListener {
            NoteFavouriteDialogFragment.newInstance().show(requireActivity().supportFragmentManager, "")

            requireActivity().supportFragmentManager
                .setFragmentResultListener(NOTE_FAVOURITE_DIALOG_FRAGMENT_REQUEST, this)
                { key, bundle ->
                    localFilmInfo.note = bundle.getString(NOTE_FAVOURITE_DIALOG_FRAGMENT_KEY_EXTRA) ?: ""
                    viewModel.addFavouriteFilm(localFilmInfo)
                }
        }
    }

    private fun renderData(appStateFilmInfo: AppStateFilmInfo) = with(binding) {
        when(appStateFilmInfo) {
            is AppStateFilmInfo.SuccessLoadingFilmInfo -> {
                loadingLayout.hide()
                localFilmInfo = appStateFilmInfo.filmInfo

                nameFilmInfo.text = appStateFilmInfo.filmInfo.title

                posterFilmInfo
                    .load(appStateFilmInfo.filmInfo.image) {
                        placeholder(R.drawable.poster_no)
                    }

                genreFilmInfo.text = appStateFilmInfo.filmInfo.genres
                yearFilmInfo.text = appStateFilmInfo.filmInfo.year
                durationFilmInfo.text = appStateFilmInfo.filmInfo.runtimeStr
                annotationFilmInfo.text = appStateFilmInfo.filmInfo.plot
                directorFilmInfo.text = appStateFilmInfo.filmInfo.directors
                actorsFilmInfo.text = appStateFilmInfo.filmInfo.stars
            }
            is AppStateFilmInfo.Loading -> {
                loadingLayout.show()
            }
            is AppStateFilmInfo.Error -> {
                loadingLayout.hide()
                appStateFilmInfo.error.localizedMessage?.let {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE)
                        .setAction("Попробуйте еще раз") {viewModel.loadFilmInfoFromServer(appStateFilmInfo.filmID)}.show()
                }
            }
            else -> {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val BUNDLE_EXTRA = "filmInfo"

        fun newInstance(bundle: Bundle): FilmInfoFragment {
            val fragment = FilmInfoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}