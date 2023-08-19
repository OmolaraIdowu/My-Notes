package com.swancodes.mynotes.ui.fragment

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.swancodes.mynotes.R
import com.swancodes.mynotes.databinding.FragmentAddNoteBinding
import com.swancodes.mynotes.databinding.PaletteBottomSheetBinding

class AddOrEditNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding

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

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.saveButton.setOnClickListener {
            findNavController().navigate(AddOrEditNoteFragmentDirections.toNoteListFragment())
        }
        binding.themeButton.setOnClickListener {
            showColorPalette()
        }
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

}