package com.strider.taskmanager.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.strider.taskmanager.R
import com.strider.taskmanager.databinding.FragmentTaskDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskDetailsFragment : Fragment(R.layout.fragment_task_details) {

    val viewModel: TaskDetailsViewModel by viewModels()
    val args: TaskDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTaskDetailsBinding.bind(view)
        args.task?.let { task ->
            binding.apply {
                etTaskTitle.setText(task.name)
                etTaskDescription.setText(task.description)
            }
        }
    }
}