package ru.kinomafia.view.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.kinomafia.R
import ru.kinomafia.databinding.MainFragmentBinding
import ru.kinomafia.model.entities.FilmItem
import ru.kinomafia.view.favourite.FavouriteFragment
import ru.kinomafia.view.film_info.FilmInfoFragment
import ru.kinomafia.view.hide
import ru.kinomafia.view.show
import ru.kinomafia.view.simpleFunWithoutAction
import ru.kinomafia.viewmodel.AppState
import ru.kinomafia.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var isFilmChangedByRate = false

    companion object {
        fun newInstance() = MainFragment()

        const val IS_FILM_CHANGED_BY_RATE_LISTS ="IS_FILM_CHANGED_BY_RATE_LISTS"
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val adapterTop250 = MainFragmentAdapterTop250(object : OnItemViewClickListener {
        override fun onItemViewClick(filmItem: FilmItem) {
            activity?.supportFragmentManager?.apply {
                val bundle = Bundle()
                bundle.putParcelable(FilmInfoFragment.BUNDLE_EXTRA, filmItem)
                beginTransaction()
                    .add(R.id.container, FilmInfoFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    private val adapterMostPopular = MainFragmentAdapterMostPopular(object : OnItemViewClickListener {
        override fun onItemViewClick(filmItem: FilmItem) {
            activity?.supportFragmentManager?.apply {
                val bundle = Bundle()
                bundle.putParcelable(FilmInfoFragment.BUNDLE_EXTRA, filmItem)
                beginTransaction()
                    .add(R.id.container, FilmInfoFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                                                savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        binding.top250RecyclerMainFragment.also {
            it.adapter = adapterTop250
            it.layoutManager = LinearLayoutManager(it.context,
                LinearLayoutManager.HORIZONTAL, false)
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

        binding.mostPopularRecyclerMainFragment.also {
            it.adapter = adapterMostPopular
            it.layoutManager = LinearLayoutManager(it.context,
                LinearLayoutManager.HORIZONTAL, false)
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

        val observer = Observer<AppState> {
            renderData(it)
        }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        loadChangedFilmListsByRate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) {
        when(appState) {
            is AppState.Success -> {
                binding.loadingLayout.hide()
                binding.root.simpleFunWithoutAction()
                appState.filmItemListTop250.let { adapterTop250.setFilmInfo(it) }
                appState.filmItemListMostPopular.let { adapterMostPopular.setFilmInfo(it) }
            }
            is AppState.Loading -> {
                binding.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.loadingLayout.hide()
                Snackbar.make(binding.root, "Error", Snackbar.LENGTH_LONG)
                    .setAction("Попробуйте еще раз") {viewModel.getDataFromServer()}.show()
            }
        }
    }

    interface OnItemViewClickListener{
        fun onItemViewClick(filmItem: FilmItem)
    }

    private fun saveChangedFilmListsByRate () {
        val sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)?.edit()
        sharedPreferences?.putBoolean(IS_FILM_CHANGED_BY_RATE_LISTS, isFilmChangedByRate)
        sharedPreferences?.apply()
    }

    private fun loadChangedFilmListsByRate () {
        activity?.let {
            isFilmChangedByRate = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getBoolean(IS_FILM_CHANGED_BY_RATE_LISTS, false)
                ?: false
        }
        if (isFilmChangedByRate) {
            viewModel.getFilmListsSortByRateFromServer()
        } else {
            viewModel.getDataFromServer()
        }
    }

    private fun changeFilmListByRate () {
        isFilmChangedByRate = !isFilmChangedByRate

        if (isFilmChangedByRate) {
            viewModel.getFilmListsSortByRateFromServer()
        } else {
            viewModel.getDataFromServer()
        }
        saveChangedFilmListsByRate()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.main_menu_sort_by_rate -> {
                changeFilmListByRate()
                true
            }
            R.id.main_menu_favourite -> {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.add(R.id.container, FavouriteFragment.newInstance())
                    ?.addToBackStack("")
                    ?.commitAllowingStateLoss()
                true
            }
            else -> {
                false
            }
        }
    }
}