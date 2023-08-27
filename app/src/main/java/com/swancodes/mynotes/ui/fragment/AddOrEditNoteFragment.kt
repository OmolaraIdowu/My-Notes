package com.swancodes.mynotes.ui.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.swancodes.mynotes.R
import com.swancodes.mynotes.data.Note
import com.swancodes.mynotes.data.Priority
import com.swancodes.mynotes.databinding.FragmentAddNoteBinding
import com.swancodes.mynotes.databinding.PaletteBottomSheetBinding
import com.swancodes.mynotes.databinding.PriorityBottomSheetBinding
import com.swancodes.mynotes.util.hide
import com.swancodes.mynotes.util.show
import com.swancodes.mynotes.viewmodel.NoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddOrEditNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding
    private var selectedPriority: Priority = Priority.MEDIUM
    private var isAllFabVisible = false
    private val viewModel: NoteViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setClickListeners()
    }

    private fun setupUI() = with(binding) {
        priorityFab.hide()
        priorityText.hide()
        themeFab.hide()
        themeText.hide()

        isAllFabVisible = false

    }

    private fun setClickListeners() = with(binding) {
        moreFab.setOnClickListener {
            expandFab()
        }
        themeFab.setOnClickListener {
            showColorPalette()
        }
        priorityFab.setOnClickListener {
            showPriority()
        }
        backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        saveButton.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val title = binding.noteTitle.text.toString()
        val content = binding.noteContent.text.toString()
        val creationTime = System.currentTimeMillis()

        if (inputCheck(title, content)) {
            // Create Note Object
            val note = Note(title, content, creationTime, selectedPriority)

            viewModel.addNote(note)
            findNavController().navigate(AddOrEditNoteFragmentDirections.toNoteListFragment())
            Snackbar.make(binding.root, "Note saved successfully", Snackbar.LENGTH_SHORT).show()

        } else {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun expandFab() = with(binding) {
        isAllFabVisible = if (!isAllFabVisible) {
            priorityFab.show()
            themeFab.show()
            priorityText.show()
            themeText.show()
            true
        } else {
            priorityFab.hide()
            themeFab.hide()
            priorityText.hide()
            themeText.hide()
            false
        }
    }

    private fun showPriority() {
        val currentOrientation = resources.configuration.orientation

        val dialogBuilder: AlertDialog.Builder
        val binding: PriorityBottomSheetBinding
        val dialog: Dialog

        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            binding = PriorityBottomSheetBinding.inflate(LayoutInflater.from(requireContext()))
            val bottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            val bottomSheetView = binding.root
            bottomSheetView.setBackgroundResource(R.drawable.bottom_sheet_bg)

            binding.cancelButton.setOnClickListener { bottomSheetDialog.dismiss() }
            dialog = bottomSheetDialog
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        } else {
            dialogBuilder = AlertDialog.Builder(requireContext())
            binding = PriorityBottomSheetBinding.inflate(LayoutInflater.from(requireContext()))
            val dialogView = binding.root

            dialogBuilder.setView(dialogView)
            dialog = dialogBuilder.create()

            binding.cancelButton.setOnClickListener { dialog.dismiss() }
        }
        val buttons = listOf(
            binding.lowPriorityButton,
            binding.mediumPriorityButton,
            binding.highPriorityButton
        )

        for (button in buttons) {
            button.setOnClickListener {
                selectedPriority = when (button.id) {
                    R.id.lowPriorityButton -> Priority.LOW
                    R.id.mediumPriorityButton -> Priority.MEDIUM
                    R.id.highPriorityButton -> Priority.HIGH
                    else -> Priority.MEDIUM
                }
                //dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun showColorPalette() {
        val currentOrientation = resources.configuration.orientation

        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            // Show bottom sheet dialog
            val bottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
            val binding = PaletteBottomSheetBinding.inflate(LayoutInflater.from(requireContext()))
            val bottomSheetView = binding.root

            bottomSheetView.setBackgroundResource(R.drawable.bottom_sheet_bg)

            binding.cancelButton.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        } else {
            // Show dialog with color palette
            val dialogBuilder = AlertDialog.Builder(requireContext())
            val binding = PaletteBottomSheetBinding.inflate(LayoutInflater.from(requireContext()))
            val dialogView = binding.root

            dialogBuilder.setView(dialogView)
            val dialog = dialogBuilder.create()

            binding.cancelButton.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun inputCheck(title: String, content: String): Boolean {
        return title.isNotEmpty() && content.isNotEmpty()
    }
}