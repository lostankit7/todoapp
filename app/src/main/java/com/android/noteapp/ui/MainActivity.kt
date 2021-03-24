package com.android.noteapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.noteapp.R
import com.android.noteapp.adapter.IDeleteNote
import com.android.noteapp.adapter.RvListAdapter
import com.android.noteapp.databinding.ActivityMainBinding
import com.android.noteapp.model.Note
import com.android.noteapp.ui.viewModel.NoteViewModel


class MainActivity : AppCompatActivity(), IDeleteNote {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        binding.rvNoteList.layoutManager = LinearLayoutManager(this)
        val mAdapter = RvListAdapter(this, this)
        binding.rvNoteList.adapter = mAdapter

        viewModel.allNotes.observe(this, Observer {
            mAdapter.updateList(it)
        })

        binding.btnAddNote.setOnClickListener {
            val frag = AddNoteFragment();
            val bundle = Bundle()
            bundle.putBoolean("upd", false)
            frag.arguments = bundle
            addFragment(frag)
        }
    }

    private fun addFragment(frag: Fragment) {
        binding.containerFrag.show()
        binding.container.hide()
        supportFragmentManager.beginTransaction()
            .add(binding.containerFrag.id, frag)
            .addToBackStack(frag::class.simpleName)
            .commit()
        binding.btnAddNote.hide()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.containerFrag.hide()
        binding.container.show()
        binding.btnAddNote.show()
    }

    override fun onItemClick(note: Note) {
        val frag = ShowDetailFragment()
        val bundle = Bundle()
        bundle.putParcelable("note", note)
        frag.arguments = bundle
        addFragment(frag)
    }

    override fun delete(note: Note, iv: ImageView) {
        val popupMenu = PopupMenu(this, iv)
        val menu = popupMenu.menu
        popupMenu.menuInflater.inflate(R.menu.dropdown_menu, menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> {
                    viewModel.deleteNote(note)
                }
                R.id.update -> {
                    val frag = AddNoteFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("note", note)
                    bundle.putBoolean("upd", true)
                    frag.arguments = bundle
                    addFragment(frag)
                }
            }
            true
        }
        popupMenu.show()
    }
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}