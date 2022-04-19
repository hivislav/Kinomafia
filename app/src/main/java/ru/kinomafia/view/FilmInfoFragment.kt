package ru.kinomafia.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import ru.kinomafia.databinding.FilmInfoFragmentBinding
import ru.kinomafia.model.FilmInfo
import ru.kinomafia.viewmodel.AppState
import ru.kinomafia.viewmodel.MainViewModel

class FilmInfoFragment : Fragment() {

    private var _binding: FilmInfoFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = FilmInfoFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FilmInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val observer = object: Observer<AppState> {
            override fun onChanged(t: AppState?) {
                renderData(t!!)
            }
        }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getFilmInfo()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun renderData(appState: AppState) {
        when(appState) {
            is AppState.Success -> {
                val filmData = appState.filmData

                binding.loadingLayout.visibility = View.GONE
                setData(filmData)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
            }
        }
    }

    private fun setData(filmData: FilmInfo) {
        binding.nameFilmInfo.setText(filmData.film.name)
        binding.genreFilmInfo.setText(filmData.film.genre)
        binding.posterFilmInfo.setImageResource(filmData.film.poster)
        binding.yearFilmInfo.setText(filmData.film.year.toString())
        binding.durationFilmInfo.setText(filmData.film.getDurationFilmInString(filmData.film.duration))
        binding.annotationFilmInfo.setText(filmData.filmAnnotation)
        binding.directorFilmInfo.setText(filmData.director)
        binding.actorsFilmInfo.setText(filmData.actors)
    }
}