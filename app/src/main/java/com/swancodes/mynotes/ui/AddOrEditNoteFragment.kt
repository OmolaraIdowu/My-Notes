package com.swancodes.mynotes.ui

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
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDailogTheme)
        val binding = PaletteBottomSheetBinding.inflate(LayoutInflater.from(requireContext()))
        val bottomSheetView = binding.root

        binding.cancelButton.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }
}