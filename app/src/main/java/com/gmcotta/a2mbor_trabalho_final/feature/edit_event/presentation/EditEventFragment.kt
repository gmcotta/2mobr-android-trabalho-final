package com.gmcotta.a2mbor_trabalho_final.feature.edit_event.presentation

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
import androidx.navigation.fragment.navArgs
import com.gmcotta.a2mbor_trabalho_final.R
import com.gmcotta.a2mbor_trabalho_final.base.LoggedFragment
import com.gmcotta.a2mbor_trabalho_final.databinding.FragmentEditEventBinding
import com.gmcotta.a2mbor_trabalho_final.model.Event
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import java.text.DateFormat

class EditEventFragment: LoggedFragment() {
    private var binding: FragmentEditEventBinding? = null
    private val viewModel: EditEventViewModel by viewModels()
    private val editEventFragmentArgs: EditEventFragmentArgs by navArgs()
    private val event: Event by lazy {
        editEventFragmentArgs.event
    }

    private lateinit var backButton: Button
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditEventBinding.inflate(inflater, container, false)
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
            val formattedDate = event.date?.let { date -> DateFormat.getDateInstance().format(date) }
            val formattedTime = event.time?.let { time -> DateFormat.getTimeInstance(DateFormat.SHORT).format(time) }

            backButton = it.btnBack
            nameEditText = it.etName
            addressEditText = it.etAddress
            showDatePickerButton = it.btnPickDate
            dateResult = it.tvDate
            showTimePickerButton = it.btnPickTime
            timeResult = it.tvTime
            saveEventButton = it.btnSaveEvent
            progressBar = it.progressBar

            nameEditText.setText(event.name)
            dateResult.text = getString(R.string.date_placeholder, formattedDate)
            timeResult.text = getString(R.string.time_placeholder, formattedTime)
            addressEditText.setText(event.address)

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
                    "edit_event_success_message" -> getString(R.string.edit_event_success_message)
                    "edit_event_error_message" -> getString(R.string.edit_event_error_message)
                    else -> getString(R.string.edit_event_error_message)
                }
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                progressBar.visibility = View.GONE

                if (it == "edit_event_success_message") {
                    navigateToHome()
                }
            }
        }
    }

    private fun setupListeners() {
        backButton.setOnClickListener {
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
            val timestamp = DateFormat.getDateInstance().parse(date)
            dateResult.text = getString(R.string.date_placeholder, date)
            if (timestamp != null) {
                event.date = timestamp
            }
        }

        materialTimePicker.addOnPositiveButtonClickListener {
            val hour = materialTimePicker.hour
            val minute = materialTimePicker.minute
            val time = "${hour}:${minute}"
            val timestamp = DateFormat.getTimeInstance(DateFormat.SHORT).parse(time)
            timeResult.text = getString(R.string.time_placeholder, time)
            if (timestamp != null) {
                event.time = timestamp
            }
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_editEventFragment_to_homeFragment)
    }
}