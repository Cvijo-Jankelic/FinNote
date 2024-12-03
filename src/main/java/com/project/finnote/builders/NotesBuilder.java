package com.project.finnote.builders;

import com.project.finnote.entity.Category;
import com.project.finnote.entity.Notes;

import java.time.LocalDateTime;

public class NotesBuilder {
    private Integer noteId;
    private Integer userId;
    private String title;
    private String content;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NotesBuilder setNoteId(Integer noteId) {
        this.noteId = noteId;
        return this;
    }

    public NotesBuilder setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public NotesBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public NotesBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public NotesBuilder setCategory(Category category) {
        this.category = category;
        return this;
    }

    public NotesBuilder setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public NotesBuilder setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Notes createNotes() {
        return new Notes(noteId, userId, title, content, category, createdAt, updatedAt);
    }
}