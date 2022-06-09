package ru.kinomafia.view.film_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ru.kinomafia.R
import ru.kinomafia.databinding.FilmInfoFragmentBinding
import ru.kinomafia.model.entities.FilmItem
import ru.kinomafia.view.hide
import ru.kinomafia.view.show
import ru.kinomafia.view.simpleFunWithoutAction
import ru.kinomafia.viewmodel.AppState
import ru.kinomafia.viewmodel.FilmInfoViewModel

class FilmInfoFragment : Fragment() {
    private var _binding: FilmInfoFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FilmInfoViewModel by lazy {
        ViewModelProvider(this).get(FilmInfoViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FilmInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filmInfo = arguments?.getParcelable<FilmItem>(BUNDLE_EXTRA)?.let {
            viewModel.getLiveData().observe(viewLifecycleOwner,
                Observer<AppState> {appState: AppState ->  renderData(appState)})
            viewModel.loadFilmInfoFromServer(it.id)
        }
    }

    private fun renderData(appState: AppState) = with(binding) {
        when(appState) {
            is AppState.SuccessLoadingFilmInfo -> {
                loadingLayout.hide()
                root.simpleFunWithoutAction()

                nameFilmInfo.text = appState.filmInfoDTO.title
                Picasso.get()
                    .load(appState.filmInfoDTO.image)
                    .resize(1760, 2640)
                    .onlyScaleDown()
                    .placeholder(R.drawable.poster_no)
                    .into(posterFilmInfo)
                genreFilmInfo.text = appState.filmInfoDTO.genres
                yearFilmInfo.text = appState.filmInfoDTO.year
                durationFilmInfo.text = appState.filmInfoDTO.runtimeStr
                annotationFilmInfo.text = appState.filmInfoDTO.plot
                directorFilmInfo.text = appState.filmInfoDTO.directors
                actorsFilmInfo.text = appState.filmInfoDTO.stars
            }
            is AppState.Loading -> {
                loadingLayout.show()
            }
            is AppState.Error -> {
                loadingLayout.hide()
                Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG)
                    .setAction("Попробуйте еще раз") {viewModel.loadFilmInfoFromServer(appState.filmID)}.show()
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