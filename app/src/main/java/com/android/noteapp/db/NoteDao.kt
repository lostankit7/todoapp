package com.android.noteapp.db

import androidx.lifecycle.LiveData
import com.android.noteapp.model.Note
import androidx.room.*


@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

}