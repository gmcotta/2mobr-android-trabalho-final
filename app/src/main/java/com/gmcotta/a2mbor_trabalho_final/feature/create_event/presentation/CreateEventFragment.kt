package com.gmcotta.a2mbor_trabalho_final.feature.create_event.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gmcotta.a2mbor_trabalho_final.R
import com.gmcotta.a2mbor_trabalho_final.base.LoggedFragment
import com.gmcotta.a2mbor_trabalho_final.databinding.FragmentCreateEventBinding
import com.gmcotta.a2mbor_trabalho_final.model.Event
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import java.text.DateFormat
import java.text.DateFormat.getDateInstance
import java.text.DateFormat.getTimeInstance

class CreateEventFragment : LoggedFragment() {
    private var binding: FragmentCreateEventBinding? = null
    private val viewModel: CreateEventViewModel by viewModels()
    private lateinit var event: Event

    private lateinit var backTextView: TextView
    private lateinit var nameEditText: TextInputEditText
    private lateinit var addressEditText: TextInputEditText
    private lateinit var showDatePickerButton: Button
    private lateinit var dateResult: TextView
    private lateinit var showTimePickerButton: Button
    private lateinit var timeResult: TextView
    private lateinit var saveEventButton: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var materialDatePicker: MaterialDatePicker<*>
    private lateinit var materialTimePicker: MaterialTimePicker

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        event = Event()
        binding = FragmentCreateEventBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupElements()
        setupObservers()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupElements() {
        binding?.let {
            backTextView = it.tvBack
            nameEditText = it.etName
            addressEditText = it.etAddress
            showDatePickerButton = it.btnPickDate
            dateResult = it.tvDate
            showTimePickerButton = it.btnPickTime
            timeResult = it.tvTime
            saveEventButton = it.btnSaveEvent
            progressBar = it.progressBar

            materialDatePicker = MaterialDatePicker.Builder
                .datePicker()
                .setTitleText(R.string.select_date_dialog_title)
                .build()

            materialTimePicker = MaterialTimePicker.Builder()
                .setTitleText(R.string.select_time_dialog_title)
                .build()
        }
    }

    private fun setupObservers() {
        viewModel.msg.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                val msg = when(it) {
                    "required_fields_error_message" -> getString(R.string.required_fields_error_message)
                    "save_event_success_message" -> getString(R.string.save_event_success_message)
                    "save_event_error_message" -> getString(R.string.create_event_error_message)
                    else -> getString(R.string.create_event_error_message)
                }
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE

                if (it == "save_event_success_message") {
                    navigateToHome()
                }
            }
        }
    }

    private fun setupListeners() {
        backTextView.setOnClickListener {
            navigateToHome()
        }

        showDatePickerButton.setOnClickListener {
            materialDatePicker.show(childFragmentManager, "MATERIAL_DATE_PICKER")
        }

        showTimePickerButton.setOnClickListener {
            materialTimePicker.show(childFragmentManager, "MATERIAL_TIME_PICKER")
        }

        saveEventButton.setOnClickListener {
            event.name = nameEditText.text.toString()
            event.address = addressEditText.text.toString()
            progressBar.visibility = View.VISIBLE
            viewModel.saveEvent(event)
        }

        materialDatePicker.addOnPositiveButtonClickListener {
            val date = materialDatePicker.headerText
            val timestamp = getDateInstance().parse(date)
            dateResult.text = getString(R.string.date_placeholder, date)
            if (timestamp != null) {
                event.date = timestamp
            }
        }

        materialTimePicker.addOnPositiveButtonClickListener {
            val hour = materialTimePicker.hour
            val minute = materialTimePicker.minute
            val time = "${hour}:${minute}"
            val timestamp = getTimeInstance(DateFormat.SHORT).parse(time)
            timeResult.text = getString(R.string.time_placeholder, time)
            if (timestamp != null) {
                event.time = timestamp
            }
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_createEventFragment_to_homeFragment)
    }
}
