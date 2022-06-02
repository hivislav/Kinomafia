package ru.kinomafia.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kinomafia.databinding.FilmInfoFragmentBinding
import ru.kinomafia.model.FilmItem
import ru.kinomafia.model.getDurationFilmInString
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

        val filmInfo = arguments?.getParcelable<FilmItem>(BUNDLE_EXTRA)

        filmInfo?.also {
//            binding.nameFilmInfo.text = it.name
//            binding.genreFilmInfo.text = it.genre
//            binding.posterFilmInfo.setImageResource(it.poster)
//            binding.yearFilmInfo.text = it.year.toString()
//            binding.durationFilmInfo.text = getDurationFilmInString(it.duration)
//            binding.annotationFilmInfo.resToString(it.filmAnnotation)
//            binding.directorFilmInfo.text = it.director
//            binding.actorsFilmInfo.text = it.actors
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