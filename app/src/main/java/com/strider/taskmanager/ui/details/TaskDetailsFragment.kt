package com.strider.taskmanager.ui.details

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import autodispose2.androidx.lifecycle.scope
import autodispose2.autoDispose
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.textChanges
import com.strider.taskmanager.R
import com.strider.taskmanager.database.entity.Task
import com.strider.taskmanager.databinding.FragmentTaskDetailsBinding
import com.strider.taskmanager.enums.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.newSingleThreadContext
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import splitties.init.appCtx
import timber.log.Timber

@AndroidEntryPoint
class TaskDetailsFragment : Fragment(R.layout.fragment_task_details) {

    private val viewModel: TaskDetailsViewModel by viewModels()
    private val args: TaskDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentTaskDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTaskDetailsBinding.bind(view)
        args.task?.let { task ->
            viewModel.currentTask = task
        }
        binding.apply {
            etTaskTitle.setText(viewModel.currentTask.name)
            etTaskDescription.setText(viewModel.currentTask.description)
            spinnerTaskStatus.adapter = getSpinnerAdapter()
            spinnerTaskStatus.onItemSelectedListener = getOnItemSelectedListener()
            spinnerTaskStatus.setSelection(viewModel.currentTask.status)

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
        }

        setHasOptionsMenu(true)
    }

    private fun getSpinnerAdapter(): ArrayAdapter<Status> {
        val adapter = CustomArrayAdapter(
            appCtx,
            R.layout.item_spinner_status,
            Status.values(),
            layoutInflater
        )
        adapter.setDropDownViewResource(R.layout.item_dropdown_spinner_status)
        return adapter
    }

    private fun getOnItemSelectedListener(): AdapterView.OnItemSelectedListener {
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
        return when (item.itemId) {
            R.id.action_save_task -> saveTask()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveTask(): Boolean {
        viewModel.currentTask = viewModel.currentTask.copy(
            lastChange = System.currentTimeMillis(),
            status = binding.spinnerTaskStatus.selectedItemPosition
        )
        viewModel.saveTask()

        Snackbar.make(
            requireView(),
            getString(R.string.task_saved_message),
            Snackbar.LENGTH_LONG
        ).show()

        return true
    }
}