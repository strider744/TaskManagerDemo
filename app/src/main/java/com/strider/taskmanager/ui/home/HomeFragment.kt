package com.strider.taskmanager.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.strider.taskmanager.R
import com.strider.taskmanager.databinding.FragmentHomeBinding
import com.strider.taskmanager.enums.SortOrder
import com.strider.taskmanager.view.observeNotNull
import com.strider.taskmanager.view.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)

        val taskAdapter = TaskAdapter()

        binding.apply {
            rvTasks.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        setHasOptionsMenu(true)

        viewModel.tasks.observeNotNull(viewLifecycleOwner) {
            Timber.e("qwe list $it")
            taskAdapter.submitList(it)
        }

        viewModel.setUpDatabase()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_home, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            viewModel.searchQuery.value = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_by_name -> {
                viewModel.sortOrder.value = SortOrder.BY_NAME
                true
            }
            R.id.action_sort_by_date -> {
                viewModel.sortOrder.value = SortOrder.BY_DATE
                true
            }
            R.id.action_sort_by_creator_name -> {
                viewModel.sortOrder.value = SortOrder.BY_CREATOR
                true
            }
            R.id.action_sort_by_priority -> {
                viewModel.sortOrder.value = SortOrder.BY_PRIORITY
                true
            }
            R.id.action_delete_all_completed_tasks -> {
                true
            }
            R.id.action_hide_completed_tasks -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}