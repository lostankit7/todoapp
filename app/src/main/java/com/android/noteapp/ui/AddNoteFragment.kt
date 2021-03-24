package com.android.noteapp.ui

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.noteapp.databinding.FragmentNoteBinding
import com.android.noteapp.model.Note
import com.android.noteapp.ui.viewModel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class AddNoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding
    private lateinit var viewModel: NoteViewModel
    private var isUpdate by Delegates.notNull<Boolean>()
    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)
        getObject()
        return binding.root
    }

    private fun getObject() {
        val bundle = arguments
        if (bundle != null) {
            isUpdate = bundle.getBoolean("upd")
            if (isUpdate) {
                note = bundle.getParcelable("note")
                val title = SpannableStringBuilder(note!!.title)
                val desc = SpannableStringBuilder(note!!.desc)
                binding.etTitle.text = title
                binding.etDescription.text = desc
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnAdd.setOnClickListener { addNoteBtn() }

    }

    private fun addNoteBtn() {
        val etTitle = binding.etTitle
        val etDesc = binding.etDescription
        val title = etTitle.text.toString()
        val des = etDesc.text.toString()
        if (title.isEmpty()) {
            etTitle.error = "Required"
            etTitle.requestFocus()
        } else if (des.isEmpty()) {
            etDesc.error = "Required"
            etDesc.requestFocus()
        } else {
            val date =
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())


            if (isUpdate) {
                note!!.title = title
                note!!.desc = des
                viewModel.updateNote(note!!)
            }
            else viewModel.addNote(Note(title, des, date, time))
            activity!!.onBackPressed()
        }
    }
}