package ru.kinomafia.view.film_info

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_film_holder.view.*
import ru.kinomafia.R
import ru.kinomafia.databinding.FilmInfoFragmentBinding
import ru.kinomafia.model.entities.FilmInfo
import ru.kinomafia.model.entities.FilmItem
import ru.kinomafia.model.entities.rest_entities.FilmInfoDTO
import ru.kinomafia.view.hide
import ru.kinomafia.view.show
import ru.kinomafia.view.simpleFunWithoutAction
import ru.kinomafia.viewmodel.AppState
import ru.kinomafia.viewmodel.FilmInfoViewModel

class FilmInfoFragment : Fragment() {
    private var _binding: FilmInfoFragmentBinding? = null
    private val binding get() = _binding!!

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val filmInfoDTO = it.getParcelableExtra<FilmInfoDTO>(BUNDLE_KEY_FILM_INFO)
                if (filmInfoDTO != null) {
                    with(binding) {
                        loadingLayout.hide()
                        root.simpleFunWithoutAction()
                        nameFilmInfo.text = filmInfoDTO.title
                        Picasso.get().load(filmInfoDTO.image).placeholder(R.drawable.poster_no)
                            .into(posterFilmInfo)
                        genreFilmInfo.text = filmInfoDTO.genres
                        yearFilmInfo.text = filmInfoDTO.year
                        durationFilmInfo.text = filmInfoDTO.runtimeStr
                        annotationFilmInfo.text = filmInfoDTO.plot
                        directorFilmInfo.text = filmInfoDTO.directors
                        actorsFilmInfo.text = filmInfoDTO.stars
                    }
                }
            }
        }
    }

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

//        val filmInfo = arguments?.getParcelable<FilmItem>(BUNDLE_EXTRA)?.let {
//            viewModel.getLiveData().observe(viewLifecycleOwner,
//                Observer<AppState> {appState: AppState ->  renderData(appState)})
//            viewModel.loadFilmInfoFromServer(it.id)
//        }

        arguments?.let {
            it.getParcelable<FilmItem>(BUNDLE_EXTRA)?.let {

                val intent = Intent(requireActivity(), FilmInfoService::class.java)
                intent.putExtra(FILM_ID_EXTRA, it.id)
                requireActivity().startService(intent)
                LocalBroadcastManager.getInstance(requireActivity()).registerReceiver(receiver, IntentFilter(
                    FILM_INFO_INTENT_FILTER))
            }
        }
    }

    private fun renderData(appState: AppState) = with(binding) {
        when(appState) {
            is AppState.SuccessLoadingFilmInfo -> {
//                loadingLayout.hide()
//                root.simpleFunWithoutAction()
//
//                nameFilmInfo.text = appState.filmInfo.title
//                Picasso.get().load(appState.filmInfo.image).placeholder(R.drawable.poster_no).into(posterFilmInfo)
//                genreFilmInfo.text = appState.filmInfo.genres
//                yearFilmInfo.text = appState.filmInfo.year
//                durationFilmInfo.text = appState.filmInfo.runtimeStr
//                annotationFilmInfo.text = appState.filmInfo.plot
//                directorFilmInfo.text = appState.filmInfo.directors
//                actorsFilmInfo.text = appState.filmInfo.stars
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
        LocalBroadcastManager.getInstance(requireActivity()).unregisterReceiver(receiver)
    }

    companion object {
        const val BUNDLE_EXTRA = "filmInfo"
        const val FILM_ID_EXTRA = "FILM_ID_EXTRA"
        const val BUNDLE_KEY_FILM_INFO = "BUNDLE_KEY_FILM_INFO"
        const val FILM_INFO_INTENT_FILTER = "FILM_INFO_INTENT_FILTER"

        fun newInstance(bundle: Bundle): FilmInfoFragment {
            val fragment = FilmInfoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}