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

    private lateinit var viewModel: MainViewModel

    private val adapterHits = MainFragmentAdapterHits(object : MainFragmentAdapterHits.OnItemViewClickListener {
        override fun onItemViewClick(filmInfo: FilmInfo) {
            val fragmentManager = activity?.supportFragmentManager

            if (fragmentManager != null) {
                val bundle = Bundle()
                bundle.putParcelable(FilmInfoFragment.BUNDLE_EXTRA, filmInfo)
                fragmentManager.beginTransaction()
                    .replace(R.id.container, FilmInfoFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    private val adapterNovelties = MainFragmentAdapterHits(object : MainFragmentAdapterHits.OnItemViewClickListener {
        override fun onItemViewClick(filmInfo: FilmInfo) {
            val fragmentManager = activity?.supportFragmentManager

            if (fragmentManager != null) {
                val bundle = Bundle()
                bundle.putParcelable(FilmInfoFragment.BUNDLE_EXTRA, filmInfo)
                fragmentManager.beginTransaction()
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

        val hitsRecyclerView = binding.hitsRecyclerMainFragment
        hitsRecyclerView.adapter = adapterHits
        hitsRecyclerView.layoutManager = LinearLayoutManager(hitsRecyclerView.context,
            LinearLayoutManager.HORIZONTAL, false)
        hitsRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))

        val noveltiesRecyclerView = binding.noveltiesRecyclerMainFragment
        noveltiesRecyclerView.adapter = adapterNovelties
        noveltiesRecyclerView.layoutManager = LinearLayoutManager(noveltiesRecyclerView.context,
            LinearLayoutManager.HORIZONTAL, false)
        noveltiesRecyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

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
                binding.loadingLayout.visibility = View.GONE
                adapterHits.setFilmInfo(appState.filmDataHits)
                adapterNovelties.setFilmInfo(appState.filmDataNovelties)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
            }
        }
    }
}