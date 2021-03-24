package com.android.noteapp.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.android.noteapp.db.NoteDao
import com.android.noteapp.db.NoteDatabase
import com.android.noteapp.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(app: Application) : AndroidViewModel(app) {

    val allNotes: LiveData<List<Note>>
    private var dao: NoteDao = NoteDatabase.getDatabase(app).getNoteDao()

    init {
        allNotes = dao.getAllNotes()
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        dao.insert(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        dao.delete(note)
    }

    fun updateNote(note: Note) =  viewModelScope.launch(Dispatchers.IO) {
        dao.update(note)
    }
}