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
import com.strider.taskmanager.preferenses.ApplicationPrefs
import com.strider.taskmanager.utils.observeNotNull
import com.strider.taskmanager.utils.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
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

        viewModel.tasksFlow.observeNotNull(viewLifecycleOwner) {
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
                ApplicationPrefs.sortOrder= SortOrder.BY_NAME
                true
            }
            R.id.action_sort_by_date -> {
                ApplicationPrefs.sortOrder = SortOrder.BY_DATE
                true
            }
            R.id.action_sort_by_creator_name -> {
                ApplicationPrefs.sortOrder = SortOrder.BY_CREATOR
                true
            }
            R.id.action_sort_by_priority -> {
                ApplicationPrefs.sortOrder = SortOrder.BY_PRIORITY
                true
            }
            R.id.action_delete_all_completed_tasks -> {
                true
            }
            R.id.action_hide_completed_tasks -> {
                item.isChecked  = !item.isChecked
                ApplicationPrefs.hideCompleted = item.isChecked
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}