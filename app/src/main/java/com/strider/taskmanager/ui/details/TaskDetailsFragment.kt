package com.strider.taskmanager.ui.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import com.jakewharton.rxbinding4.widget.textChanges
import com.strider.taskmanager.R
import com.strider.taskmanager.databinding.FragmentTaskDetailsBinding
import com.strider.taskmanager.enums.Priority
import com.strider.taskmanager.enums.Status
import com.strider.taskmanager.ui.MainActivity
import com.strider.taskmanager.ui.OnBackPressedListener
import com.strider.taskmanager.utils.observeNotNull
import com.strider.taskmanager.utils.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import splitties.init.appCtx

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TaskDetailsFragment : Fragment(R.layout.fragment_task_details) {

    private val viewModel: TaskDetailsViewModel by viewModels()
    private val args: TaskDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentTaskDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTaskDetailsBinding.bind(view)
        args.task?.let { viewModel.currentTask = it }


        setData()
        setHasOptionsMenu(true)

        (activity as MainActivity).setOnBackPressedListener(object :OnBackPressedListener {
            override fun onBackPressed() {
                saveTask()
            }
        })

    }

    private fun setData() {
        binding.apply {
            etTaskTitle.setText(viewModel.currentTask.name)
            etTaskDescription.setText(viewModel.currentTask.description)

            spinnerTaskStatus.adapter = getStatusSpinnerAdapter()
            spinnerTaskStatus.onItemSelectedListener = getOnStatusItemSelectedListener()
            spinnerTaskStatus.setSelection(viewModel.currentTask.status)

            spinnerTaskPriority.adapter = getPrioritySpinnerAdapter()
            spinnerTaskPriority.onItemSelectedListener = getOnPriorityItemSelectedListener()
            spinnerTaskPriority.setSelection(viewModel.currentTask.priority)

            etTaskTitle.textChanges()
                .autoDispose(scope())
                .subscribe {
                    viewModel.currentTask = viewModel.currentTask.copy(name = it.toString())
                }

            etTaskDescription.textChanges()
                .autoDispose(scope())
                .subscribe {
                    viewModel.currentTask = viewModel.currentTask.copy(description = it.toString())
                }


            if (viewModel.currentTask.name.isBlank()) {
                etTaskTitle.requestFocus()
                showKeyboard(etTaskTitle)
            }
        }
    }

    private fun getStatusSpinnerAdapter(): ArrayAdapter<Status> {
        val adapter = StatusArrayAdapter(
            appCtx,
            R.layout.item_spinner_status,
            Status.values(),
            layoutInflater
        )
        adapter.setDropDownViewResource(R.layout.item_dropdown_spinner_status)
        return adapter
    }

    private fun getPrioritySpinnerAdapter(): ArrayAdapter<Priority> {
        val adapter = PriorityArrayAdapter(
            appCtx,
            R.layout.item_spinner_priority,
            Priority.values(),
            layoutInflater
        )
        adapter.setDropDownViewResource(R.layout.item_dropdown_spinner_priority)
        return adapter
    }

    private fun getOnPriorityItemSelectedListener(): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.onTaskPriorityChanged(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun getOnStatusItemSelectedListener(): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.onTaskStatusChanged(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_task_details, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        saveTask()
        return super.onOptionsItemSelected(item)
    }

    private fun saveTask() {
        if (args.task == viewModel.currentTask || viewModel.currentTask.name.isBlank()) {
            findNavController().navigate(
                TaskDetailsFragmentDirections.actionSave(false)
            )
        } else {
            viewModel.currentTask = viewModel.currentTask.copy(
                lastChange = System.currentTimeMillis(),
            )
            viewModel.saveTask()
            findNavController().navigate(
                TaskDetailsFragmentDirections.actionSave(true)
            )
        }
    }
}