package com.strider.taskmanager.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.strider.taskmanager.R
import com.strider.taskmanager.database.entity.Task
import com.strider.taskmanager.databinding.FragmentHomeBinding
import com.strider.taskmanager.enums.SortOrder
import com.strider.taskmanager.preferenses.ApplicationPrefs
import com.strider.taskmanager.ui.MainViewModel
import com.strider.taskmanager.events.TasksEvent
import com.strider.taskmanager.utils.hideKeyboard
import com.strider.taskmanager.utils.observeNotNull
import com.strider.taskmanager.utils.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        val taskAdapter = TaskAdapter(getOnItemClickListener())
        hideKeyboard()

        binding.apply {

            rvTasks.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            setUpItemTouchHelper(taskAdapter, rvTasks)

            btnAdd.setOnClickListener {
                val navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToTaskDetailsFragment(
                        getString(R.string.task_title_text), null
                    ), navOptions
                )
            }
        }
        if (viewModel.isTaskSaved) {
            Snackbar.make(
                requireView(),
                getString(R.string.task_saved_message),
                1000
            ).show()
            viewModel.isTaskSaved = false
        }

        setHasOptionsMenu(true)

        viewModel.tasksFlow.observeNotNull(viewLifecycleOwner) {
            taskAdapter.submitList(it) {
                binding.rvTasks.smoothScrollToPosition(0)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.events.collect { event ->
                when (event) {
                    is TasksEvent.ShowUndoDeleteMessage -> {
                        Snackbar.make(
                            requireView(),
                            getString(R.string.task_deleted_message),
                            Snackbar.LENGTH_LONG
                        ).addCallback(getCallBack())
                            .setAction(getString(R.string.undo_upper_text)) {
                                viewModel.onUndoDeleteClick(event.task)
                            }.show()
                    }
                }
            }
        }
    }

    private fun getCallBack(): BaseTransientBottomBar.BaseCallback<Snackbar> {
        return object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                if (event == DISMISS_EVENT_TIMEOUT || event == DISMISS_EVENT_SWIPE)
                    viewModel.confirmDeleteTask()
                super.onDismissed(transientBottomBar, event)
            }
        }
    }

    private fun setUpItemTouchHelper(adapter: TaskAdapter, rvTasks: RecyclerView) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val task = adapter.currentList[viewHolder.absoluteAdapterPosition]
                viewModel.onTaskSwiped(task)
            }
        }).attachToRecyclerView(rvTasks)
    }

    private fun getOnItemClickListener(): OnItemClickListener {
        return object : OnItemClickListener {
            override fun onItemClick(item: Task) {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToTaskDetailsFragment(item.name, item)
                val navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
                findNavController().navigate(action, navOptions)
            }

            override fun onCheckBoxClick(item: Task, isChecked: Boolean) {
                viewModel.onTaskCheckedChanged(item.id, isChecked)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_home, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        val cbHideCompleted = menu.findItem(R.id.action_hide_completed_tasks)
        cbHideCompleted.isChecked = ApplicationPrefs.hideCompleted

        searchView.onQueryTextChanged {
            viewModel.searchQuery.value = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_by_name -> {
                ApplicationPrefs.sortOrder = SortOrder.BY_NAME
                true
            }
            R.id.action_sort_by_date -> {
                ApplicationPrefs.sortOrder = SortOrder.BY_DATE
                true
            }
//            R.id.action_sort_by_creator_name -> {
//                ApplicationPrefs.sortOrder = SortOrder.BY_CREATOR
//                true
//            }
            R.id.action_sort_by_priority -> {
                ApplicationPrefs.sortOrder = SortOrder.BY_PRIORITY
                true
            }
            R.id.action_delete_all_completed_tasks -> {
                viewModel.deleteAllCompletedTasks()
                true
            }
            R.id.action_hide_completed_tasks -> {
                item.isChecked = !item.isChecked
                ApplicationPrefs.hideCompleted = item.isChecked
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}