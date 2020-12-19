package com.darkcoder.diagonal.ui.home

import android.app.SearchManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.codinginflow.imagesearchapp.ui.gallery.MovieAdapter
import com.darkcoder.diagonal.R
import com.darkcoder.diagonal.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_home) {

    private val viewModel by viewModels<MovieViewModel>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var grid: GridLayoutManager

    @Inject
    lateinit var adapter: MovieAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        grid = GridLayoutManager(requireActivity(), if (isPortraitMode()) 3 else 7)
        binding.apply {
            recycler.setHasFixedSize(true)
            recycler.layoutManager = grid
            recycler.adapter = adapter
        }
        setHasOptionsMenu(true)
        observers()
    }

    private fun observers() {
        viewModel.movies.observe(viewLifecycleOwner) { movie ->
            adapter.submitData(viewLifecycleOwner.lifecycle, movie)
            Log.d("My", "onViewCreated: " + adapter.itemCount)
        }
    }

    private fun isPortraitMode() =
        context?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val searchItem: MenuItem? = menu.findItem(R.id.action_settings)
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = searchItem?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.length > 3)
                    viewModel.fetchPageData(query)
                else if (query == null || query.isEmpty())
                    viewModel.fetchPageData("1")
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null && query.length > 3)
                    viewModel.fetchPageData(query)
                else if (query == null || query.isEmpty())
                    viewModel.fetchPageData("1")
                return false
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}