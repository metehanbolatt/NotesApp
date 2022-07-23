package com.metehanbolat.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.metehanbolat.notesapp.databinding.FragmentNoteListBinding
import com.metehanbolat.notesapp.viewmodel.NoteViewModel
import kotlinx.coroutines.flow.collect

class NoteListFragment : Fragment() {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesAdapter: NoteAdapter
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        lifecycleScope.launchWhenStarted {
            viewModel.notes.collect { notesList ->
                notesAdapter.differ.submitList(notesList)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.searchNotes.collect { searchedNotes ->
                notesAdapter.differ.submitList(searchedNotes)
            }
        }

        binding.edSearch.addTextChangedListener {
            viewModel.searchNotes(it.toString().trim())
        }

        binding.btnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_notesListFragment_to_noteFragment)
        }

        notesAdapter.onClick = { note ->
            val bundle = Bundle().apply {
                putParcelable("note", note)
            }
            findNavController().navigate(R.id.action_notesListFragment_to_noteFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() {
        notesAdapter = NoteAdapter()
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesAdapter
            addItemDecoration(VerticalItemDecoration(40))
        }
    }

}