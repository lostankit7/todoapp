package com.android.noteapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.android.noteapp.R
import com.android.noteapp.databinding.FragmentNoteBinding
import com.android.noteapp.databinding.FragmentShowDetailBinding
import com.android.noteapp.model.Note
import com.android.noteapp.ui.viewModel.NoteViewModel

class ShowDetailFragment : Fragment() {

    private var note: Note? =null
    private lateinit var binding: FragmentShowDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowDetailBinding.inflate(layoutInflater, container, false)
        getObject()
        return binding.root
    }

    private fun getObject() {
        val bundle = arguments
        if(bundle!=null){
            note = bundle.getParcelable("note")
            val dt = "(${note!!.date},${note!!.time})"
            binding.tvTitle.text = note!!.title
            binding.tvDesc.text = "Description: \n${note!!.desc}"
            binding.tvTimeDate.text=dt
        }
    }
}