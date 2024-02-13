package com.gmcotta.a2mbor_trabalho_final.feature.create_event.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.gmcotta.a2mbor_trabalho_final.R
import com.gmcotta.a2mbor_trabalho_final.databinding.FragmentCreateEventBinding
import com.gmcotta.a2mbor_trabalho_final.model.Event
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import java.text.DateFormat
import java.text.DateFormat.getDateInstance
import java.text.DateFormat.getTimeInstance

class CreateEventFragment : Fragment() {
    private var binding: FragmentCreateEventBinding? = null
    private val viewModel: CreateEventViewModel by viewModels()
    private lateinit var event: Event

    private lateinit var nameEditText: TextInputEditText
    private lateinit var addressEditText: TextInputEditText
    private lateinit var showDatePickerButton: Button
    private lateinit var dateResult: TextView
    private lateinit var showTimePickerButton: Button
    private lateinit var timeResult: TextView
    private lateinit var saveEventButton: Button

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
            nameEditText = it.etName
            addressEditText = it.etAddress
            showDatePickerButton = it.btnPickDate
            dateResult = it.tvDate
            showTimePickerButton = it.btnPickTime
            timeResult = it.tvTime
            saveEventButton = it.btnSaveEvent

            materialDatePicker = MaterialDatePicker.Builder
                .datePicker()
                // TODO: Colocar no strings.xml
                .setTitleText("Select a date")
                .build()

            materialTimePicker = MaterialTimePicker.Builder()
                // TODO: Colocar no strings.xml
                .setTitleText("Select a time")
                .build()
        }
    }

    private fun setupObservers() {
        viewModel.status.observe(viewLifecycleOwner) {
            if (it) {
                goToHome()
            }
        }

        viewModel.msg.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                val msg = when(it) {
                    "required_fields_error_message" -> getString(R.string.required_fields_error_message)
                    "save_event_success_message" -> getString(R.string.save_event_success_message)
                    else -> getString(R.string.create_event_error_message)
                }
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupListeners() {
        showDatePickerButton.setOnClickListener {
            materialDatePicker.show(childFragmentManager, "MATERIAL_DATE_PICKER")
        }

        showTimePickerButton.setOnClickListener {
            materialTimePicker.show(childFragmentManager, "MATERIAL_TIME_PICKER")
        }

        saveEventButton.setOnClickListener {
            event.name = nameEditText.text.toString()
            event.address = addressEditText.text.toString()

            viewModel.saveEvent(event)
        }

        materialDatePicker.addOnPositiveButtonClickListener {
            val date = materialDatePicker.headerText
            val timestamp = getDateInstance().parse(date)
            // TODO: Arrumar aqui
            dateResult.text = "Date: ${date}"
            if (timestamp != null) {
                event.date = timestamp
            }
        }

        materialTimePicker.addOnPositiveButtonClickListener {
            val hour = materialTimePicker.hour
            val minute = materialTimePicker.minute
            val time = "${hour}:${minute}"
            val timestamp = getTimeInstance(DateFormat.SHORT).parse(time)
            // TODO: Arrumar aqui
            timeResult.text = "Time: ${time}"
            if (timestamp != null) {
                event.time = timestamp
            }
        }
    }

    private fun goToHome() {
        findNavController().navigate(R.id.action_createEventFragment_to_homeFragment)
    }
}
