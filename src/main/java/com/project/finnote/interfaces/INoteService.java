package com.project.finnote.interfaces;

import com.project.finnote.entity.Notes;

import java.util.List;

public interface INoteService {
    List<Notes> findAll();

    /** Dodaje novu bilješku i vraća spremljeni objekt (s postavljenim noteId). */
    Notes addNote(String title, String content, int categoryId);

}
