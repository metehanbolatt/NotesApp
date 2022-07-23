package com.metehanbolat.notesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.metehanbolat.notesapp.data.Note
import com.metehanbolat.notesapp.databinding.FragmentNoteBinding
import com.metehanbolat.notesapp.viewmodel.NoteViewModel

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<NoteFragmentArgs>()
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.note?.let {
            binding.apply {
                edTitle.setText(it.noteTitle)
                edNote.setText(it.noteText)
            }
            binding.imgDeleteNote.visibility = View.VISIBLE
        }

        binding.apply {
            btnSaveNote.setOnClickListener {
                val id = args.note?.noteId ?: 0
                val noteTitle = edTitle.text.toString()
                val noteText = edNote.text.toString()

                Note(id, noteTitle, noteText).also { note ->
                    if (noteTitle.isEmpty() && noteText.isEmpty()) {
                        Toast.makeText(context, "All fields must be filled.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    viewModel.upsertNote(note)
                    findNavController().navigateUp()
                }
            }
        }

        binding.apply {
            imgDeleteNote.setOnClickListener {
                val noteId = args.note!!.noteId
                val noteTitle = edTitle.text.toString()
                val noteText = edNote.text.toString()

                Note(noteId, noteTitle, noteText).also { note ->
                    viewModel.deleteNote(note)
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}