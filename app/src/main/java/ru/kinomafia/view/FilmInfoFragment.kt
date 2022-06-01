package ru.kinomafia.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kinomafia.databinding.FilmInfoFragmentBinding
import ru.kinomafia.model.FilmInfo

class FilmInfoFragment : Fragment() {
    private var _binding: FilmInfoFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FilmInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filmInfo = arguments?.getParcelable<FilmInfo>(BUNDLE_EXTRA)

        if (filmInfo != null) {
            binding.nameFilmInfo.setText(filmInfo.film.name)
            binding.genreFilmInfo.setText(filmInfo.film.genre)
            binding.posterFilmInfo.setImageResource(filmInfo.film.poster)
            binding.yearFilmInfo.setText(filmInfo.film.year.toString())
            binding.durationFilmInfo.setText(filmInfo.film.getDurationFilmInString(filmInfo.film.duration))
            binding.annotationFilmInfo.setText(filmInfo.filmAnnotation)
            binding.directorFilmInfo.setText(filmInfo.director)
            binding.actorsFilmInfo.setText(filmInfo.actors)
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