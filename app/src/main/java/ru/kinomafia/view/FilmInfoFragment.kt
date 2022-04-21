package ru.kinomafia.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kinomafia.databinding.FilmInfoFragmentBinding
import ru.kinomafia.model.FilmInfo
import ru.kinomafia.model.resToString

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

        filmInfo?.also {
            binding.nameFilmInfo.text = it.film.name
            binding.genreFilmInfo.text = it.film.genre
            binding.posterFilmInfo.setImageResource(it.film.poster)
            binding.yearFilmInfo.text = it.film.year.toString()
            binding.durationFilmInfo.text = it.film.getDurationFilmInString(it.film.duration)
            binding.annotationFilmInfo.resToString(it.filmAnnotation)
            binding.directorFilmInfo.text = it.director
            binding.actorsFilmInfo.text = it.actors
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