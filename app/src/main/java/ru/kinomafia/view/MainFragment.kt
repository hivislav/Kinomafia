package ru.kinomafia.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import ru.kinomafia.R
import ru.kinomafia.databinding.MainFragmentBinding
import ru.kinomafia.model.FilmInfo
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

    private val adapterHits = MainFragmentAdapterHits(object : OnItemViewClickListener {
        override fun onItemViewClick(filmInfo: FilmInfo) {
            activity?.supportFragmentManager?.apply {
                val bundle = Bundle()
                bundle.putParcelable(FilmInfoFragment.BUNDLE_EXTRA, filmInfo)
                beginTransaction()
                    .replace(R.id.container, FilmInfoFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    private val adapterNovelties = MainFragmentAdapterHits(object : OnItemViewClickListener {
        override fun onItemViewClick(filmInfo: FilmInfo) {
            activity?.supportFragmentManager?.apply {
                val bundle = Bundle()
                bundle.putParcelable(FilmInfoFragment.BUNDLE_EXTRA, filmInfo)
                beginTransaction()
                    .replace(R.id.container, FilmInfoFragment.newInstance(bundle))
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

        binding.hitsRecyclerMainFragment.also {
            it.adapter = adapterHits
            it.layoutManager = LinearLayoutManager(it.context,
                LinearLayoutManager.HORIZONTAL, false)
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

        binding.noveltiesRecyclerMainFragment.also {
            it.adapter = adapterNovelties
            it.layoutManager = LinearLayoutManager(it.context,
                LinearLayoutManager.HORIZONTAL, false)
            it.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        }

        val observer = Observer<AppState> {
            renderData(it)
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
                binding.loadingLayout.hide()
                adapterHits.setFilmInfo(appState.filmDataHits)
                adapterNovelties.setFilmInfo(appState.filmDataNovelties)
            }
            is AppState.Loading -> {
                binding.loadingLayout.show()
            }
            is AppState.Error -> {
                binding.loadingLayout.hide()
            }
        }
    }
    interface OnItemViewClickListener{
        fun onItemViewClick(filmInfo: FilmInfo)
    }
}