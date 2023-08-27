package com.swancodes.mynotes.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.swancodes.mynotes.adapter.ItemClickListener
import com.swancodes.mynotes.adapter.NoteListAdapter
import com.swancodes.mynotes.data.Note
import com.swancodes.mynotes.databinding.AboutDialogBinding
import com.swancodes.mynotes.databinding.FragmentNoteListBinding
import com.swancodes.mynotes.viewmodel.NoteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class NoteListFragment : Fragment() {

    private lateinit var binding: FragmentNoteListBinding
    private val viewModel: NoteViewModel by viewModel()

    private val noteListAdapter: NoteListAdapter by lazy {
        NoteListAdapter(object : ItemClickListener {
            override fun onItemClick(note: Note) {
                findNavController().navigate(NoteListFragmentDirections.toAddOrEditNoteFragment())
            }
        })
    }

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

        setUpRecyclerView()

        setUpObserver()

        setClickListeners()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = noteListAdapter.currentList[position]
                viewModel.deleteNote(note)
                Snackbar.make(binding.root, "Note deleted successfully", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo") {
                            viewModel.addNote(note)
                        }
                            .show()
                    }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.noteRecyclerView)
        }
    }

    private fun setUpRecyclerView() {
        binding.noteRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteListAdapter
        }
    }

    private fun setUpObserver() {
        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            noteListAdapter.submitList(notes)
            binding.emptyView.isVisible = notes.isEmpty()
        }
    }

    private fun setClickListeners() {
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