package com.gmcotta.a2mbor_trabalho_final.feature.home.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmcotta.a2mbor_trabalho_final.R
import com.gmcotta.a2mbor_trabalho_final.adapters.HomeAdapter
import com.gmcotta.a2mbor_trabalho_final.databinding.FragmentHomeBinding
import com.gmcotta.a2mbor_trabalho_final.model.Event

class HomeFragment: Fragment() {
    private var binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(
            { event ->
                findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToEditEventFragment(event))
            },
            { event ->
                Log.i("deleteButton", event.toString())
                createDialog(event)
            }
        )
    }

    private lateinit var buttonLogout: Button
    private lateinit var buttonAddEvent: Button
    private lateinit var emailText: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getEvents()
        setupElements()
        setupObservers()
        setupTexts()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getEvents() {
        viewModel.getEvents()
    }

    private fun setupElements() {
        binding?.let {
            buttonLogout = it.btnLogout
            buttonAddEvent = it.btnAddEvent
            emailText = it.tvUserEmail
            progressBar = it.progressBar

            it.rvEventsList.apply {
                layoutManager = LinearLayoutManager(this@HomeFragment.context)
                adapter = homeAdapter
            }
        }
    }

    private fun setupObservers() {
        viewModel.eventsList.observe(viewLifecycleOwner) {
            it?.let {
                homeAdapter.submitList(it)
                progressBar.visibility = View.GONE
            }
        }

        viewModel.msg.observe(viewLifecycleOwner) {
            if (!it.isNullOrBlank()) {
                val msg = when(it) {
                    "get_events_error_message" -> getString(R.string.get_events_error_message)
                    "delete_event_success_message" -> getString(R.string.delete_event_success_message)
                    "delete_event_error_message" -> getString(R.string.delete_event_error_message)
                    else -> getString(R.string.create_event_error_message)
                }
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()

                getEvents()
            }
        }
    }

    private fun setupTexts() {
        val email = viewModel.getUserEmail()
        if (email == null) {
            Toast.makeText(requireContext(), getString(R.string.session_user_not_loggedin_error_message), Toast.LENGTH_LONG).show()
            navigateToLogin()
            return
        }
        emailText.text = email
    }

    private fun setupListeners() {
        buttonLogout.setOnClickListener {
            viewModel.logout()
            navigateToLogin()
        }

        buttonAddEvent.setOnClickListener {
            navigateToAddEvent()
        }
    }

    private fun createDialog(event: Event) {
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
        dialogBuilder
            .setTitle("Delete event")
            .setMessage("Are you sure?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteEvent(event)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.cancel()
            }

        val dialog: AlertDialog = dialogBuilder.create()
        dialog.show()
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
    }

    private fun navigateToAddEvent() {
        findNavController().navigate(R.id.action_homeFragment_to_createEventFragment)
    }
}