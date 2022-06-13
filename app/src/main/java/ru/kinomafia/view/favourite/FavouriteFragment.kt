package ru.kinomafia.view.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ru.kinomafia.R
import ru.kinomafia.databinding.FavouriteFragmentBinding
import ru.kinomafia.model.entities.FilmItem
import ru.kinomafia.view.hide
import ru.kinomafia.view.show
import ru.kinomafia.view.simpleFunWithoutAction
import ru.kinomafia.viewmodel.AppState
import ru.kinomafia.viewmodel.AppStateFavourite
import ru.kinomafia.viewmodel.FavouriteViewModel
import ru.kinomafia.viewmodel.FilmInfoViewModel

class FavouriteFragment : Fragment() {
    private var _binding: FavouriteFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavouriteViewModel by lazy {
        ViewModelProvider(this).get(FavouriteViewModel::class.java)
    }

    private val adapter = FavouriteFragmentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FavouriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer<AppStateFavourite> {renderData(it)})
        viewModel.getAllFavouriteFilms()

        binding.recyclerFavourite.adapter = adapter
        binding.recyclerFavourite.layoutManager = LinearLayoutManager(
                                            context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerFavourite.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    private fun renderData(appStateFavourite: AppStateFavourite) = with(binding) {
        when(appStateFavourite) {
            is AppStateFavourite.Success -> {
                loadingLayout.hide()
                adapter.setFavouriteFilmList(appStateFavourite.favoriteFilmList)
            }
            is AppStateFavourite.Loading -> {
                loadingLayout.show()
            }
            is AppStateFavourite.Error -> {
                loadingLayout.hide()
                Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG)
                    .setAction("Попробуйте еще раз") {viewModel.getAllFavouriteFilms()}.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = FavouriteFragment()
    }
}
