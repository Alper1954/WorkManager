package com.example.workmanager.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.workmanager.data.SettingsDataStore
import com.example.workmanager.data.dataStore
import com.example.workmanager.databinding.MainFragmentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: MainFragmentBinding
    private lateinit var layoutDataStore: SettingsDataStore
    private lateinit var uuid: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)

        layoutDataStore = SettingsDataStore(context!!.dataStore)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.s_uuid.observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                layoutDataStore.workRequestIdToPreferencesStore(it, context!!)
            }
        })

        lifecycle.coroutineScope.launch {
            layoutDataStore.workRequestId.collect {
                uuid = it
            }
        }

        binding.button1.setOnClickListener { viewModel.start_single_task() }
        binding.button2.setOnClickListener { viewModel.start_periodic_task() }
        binding.button3.setOnClickListener { viewModel.cancel_periodic_task() }
        binding.button4.setOnClickListener {
            viewModel.get_status_periodic_task(uuid)
        }
        binding.button5.setOnClickListener { viewModel.start_single_task_with_constraint() }
    }
}
