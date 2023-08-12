package com.swancodes.mynotes.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.swancodes.mynotes.databinding.AboutDialogBinding
import com.swancodes.mynotes.databinding.FragmentNoteListBinding

class NoteListFragment : Fragment() {

    private lateinit var binding: FragmentNoteListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addNoteButton.setOnClickListener {
            findNavController().navigate(NoteListFragmentDirections.toAddOrEditNoteFragment())
        }
        binding.aboutButton.setOnClickListener {
            showAboutDialog()
        }
    }

    private fun showAboutDialog() {
        val binding = AboutDialogBinding.inflate(LayoutInflater.from(requireContext()))
        val builder = AlertDialog.Builder(requireContext())
            .setView(binding.root)

        val dialog = builder.create()

        binding.gitHubImage.setOnClickListener { }

        binding.twitterImage.setOnClickListener { }

        dialog.show()
    }
}