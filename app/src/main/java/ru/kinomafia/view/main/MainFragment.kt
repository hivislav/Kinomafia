package ru.kinomafia.view.main

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
import ru.kinomafia.R
import ru.kinomafia.databinding.MainFragmentBinding
import ru.kinomafia.model.entities.FilmItem
import ru.kinomafia.view.film_info.FilmInfoFragment
import ru.kinomafia.view.hide
import ru.kinomafia.view.show
import ru.kinomafia.view.simpleFunWithoutAction
import ru.kinomafia.viewmodel.AppState
import ru.kinomafia.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
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
        viewModel.getDataFromServer()
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
                appState.filmItemListTop250?.let { adapterTop250.setFilmInfo(it) }
                appState.filmItemListMostPopular?.let { adapterMostPopular.setFilmInfo(it) }
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
}